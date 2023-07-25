package com.kingsware.kdev.core.cron;

import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.cache.instance.InstanceManager;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.kflow.KFlowContext;
import com.kingsware.kdev.core.kflow.KdbFlowExecutor;
import com.kingsware.kdev.core.kflow.bean.KdbFlowResult;
import com.kingsware.kdev.core.model.SysInstance;
import com.kingsware.kdev.core.model.SysLogicFlow;
import com.kingsware.kdev.core.model.SysTask;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.DBInitialize;
import com.kingsware.kdev.core.orm.kdb.FlowInfo;
import com.kingsware.kdev.core.orm.kdb.KdbFlowQueryArgv;
import com.kingsware.kdev.core.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

/**
 * 动态定时任务
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/2/17 9:30 上午
 */
@Component
@Order(1)
@DependsOn("springContext")
@Slf4j
public class DynamicTask implements CommandLineRunner {

    @Value("${schedule.scan-package:com.kingsware.kdev}")
    private String scanPackage;
    /**
     * 是否将结果回写到数据库
     **/
    @Value("${schedule.result-to-db:true}")
    private boolean resultToDb;
    /**
     * 是否自动分布式
     **/
    @Value("${schedule.distributed-auto:true}")
    private boolean distributedAuto;
    /**
     * 是否运行分布式
     **/
    @Value("${schedule.distributed-run:true}")
    private boolean distributedRun;

    /**
     * 是否运行分布式
     **/
    @Value("${schedule.share-cron:true}")
    private boolean shareCron;

    /**
     * 是否运行分布式
     **/
    @Value("${schedule.async-execute:true}")
    private boolean asyncExecute;


    private final ThreadPoolTaskScheduler threadPoolTaskScheduler;

    private final Map<String, ScheduledFutureHolder> scheduledFutureMap;

    private final CopyOnWriteArrayList<SysTask> sysTaskList = new CopyOnWriteArrayList<>();

    /**
     * 线程池
     */
    private static final ExecutorService executorService = Executors.newCachedThreadPool();

    public DynamicTask() {
        this.scheduledFutureMap = new HashMap<>(1);
        this.threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        this.threadPoolTaskScheduler.setPoolSize(50);
        this.threadPoolTaskScheduler.initialize();
    }


    /**
     * 修复表达式
     * @param cron
     * @return
     */
    private static String fixedCron(String cron) {
        String trimCron = cron.trim();
        if (NumberUtils.isInteger(trimCron)) {
            return cron;
        }
        String tempCron = cron.trim();
        String[] cronArr = tempCron.split(" ");
        if (cronArr.length == 7) {
            tempCron = tempCron.substring(0,tempCron.lastIndexOf(" ")).trim();
        }
        return tempCron;
    }

//    public static void main(String[] argv) {
//        String c1 = "0 20 05 ? * 2 *";
//        System.out.println(fixedCron(c1));
//        String c2 = "0 20 05 ? * 2 ";
//        System.out.println(fixedCron(c2));
//        String c3 = "0 20 05 ? * 2";
//        System.out.println(fixedCron(c3));
//    }



    public void registerTask(String cron, String cronKey) {

        // 判断是否有
        ScheduledFutureHolder scheduledFutureHolder = scheduledFutureMap.get(cronKey);
        if (scheduledFutureHolder == null) {
            //将任务交给任务调度器执行
            ScheduledFuture<?> schedule = createTaskScheduler( cron, ()-> runTask(cronKey));
            //将任务包装成ScheduledFutureHolder
            scheduledFutureHolder = new ScheduledFutureHolder();
            scheduledFutureHolder.setScheduledFuture(schedule);
            scheduledFutureHolder.setCron(cron);
            scheduledFutureHolder.setCron(cronKey);
            scheduledFutureMap.put(cron, scheduledFutureHolder);
        }

    }

    /***
     * 创建定时执行器
     * @param cron
     * @param runnable
     * @return
     */
    public ScheduledFuture<?> createTaskScheduler(String cron, Runnable runnable) {
        ScheduledFuture<?> schedule = null;
        if (NumberUtils.isInteger(cron)) {
            schedule = threadPoolTaskScheduler.scheduleAtFixedRate(runnable, Duration.ofSeconds(Integer.parseInt(cron)));
        }
        else {
            schedule = threadPoolTaskScheduler.schedule(runnable, new CronTrigger(cron));
        }
        return schedule;
    }

    /**
     * 运行任务
     *
     * @param cronKey 任务便利店
     */
    private void runTask(String cronKey) {
        // 同时通过cron和任务id去查找
        List<SysTask> cronTasks = sysTaskList.stream().filter(it -> it.getEnable() == 1).filter(it -> fixedCron(it.getCron()).equals(cronKey) || cronKey.contains("@" + it.getId())).collect(Collectors.toList());
        ScheduledFutureHolder scheduledFutureHolder = scheduledFutureMap.get(cronKey);

        // 如果不存在任务，则自毁
        if (cronTasks.isEmpty()) {
            ScheduledFuture<?> scheduledFuture = scheduledFutureHolder.getScheduledFuture();
            scheduledFuture.cancel(true);
            scheduledFutureMap.remove(cronKey);
            log.debug("表达式调度器由于没有可执行的任务，已经进行自毁:{}", cronKey);
        }
        else {
            log.debug("表达式调度器:{} 开始执行任务， 任务数:{}", cronKey, cronTasks.size());
            // 如果是异步执行
            if(asyncExecute) {
                for (SysTask sysTask: cronTasks) {
                    executorService.submit(() -> {
                       toTask(sysTask);
                    });
                }
            }
            else {
                for (SysTask sysTask: cronTasks) {
                    executorService.submit(() -> {
                       toTask(sysTask);
                    });

                }
            }

        }

    }

    private void toTask(SysTask sysTask) {
        // 如果不是分布式任务，直接运行
        if (sysTask.getDistributed() != null && sysTask.getDistributed() == 0) {
            executeTask(sysTask);
            return;
        }
        if (distributedAuto) {
            AtomicInteger atomicInteger = new AtomicInteger(0);
            callTask(sysTask, atomicInteger, "");
        } else {
            if (distributedRun) {
                if (sysTask.getEnable() == 0) {
                    return;
                }
                executeTask(sysTask);
            }
        }
    }

    /**
     * 调用http接口
     *
     * @param sysTask 任务
     */
    public void callTask(SysTask sysTask, AtomicInteger atomicInteger, String excludeInstanceName) {
        if (atomicInteger.intValue() == 3) {
            log.warn("任务:{}执行失败次数为:{}, 调度器将终止任务执行", sysTask.getName(), atomicInteger.intValue());
        }
        // 取主实例作为调度器
        SysInstance masterInstance = InstanceManager.getInstance().masterInstance();
        // 只有是调度器才执行
        if (InstanceManager.getInstance().isMaster()) {
            atomicInteger.incrementAndGet();
            // 随机取一个实例
            SysInstance executeInstance = InstanceManager.getInstance().getToExecuteInstance(sysTask.getId(), excludeInstanceName);
            // 如果是当前实例，直接调用即可，不通过http调用
            if (executeInstance.instanceName().equalsIgnoreCase(masterInstance.instanceName())) {
//                log.info("本机触发定时任务，任务id:{}", sysTask.getId());
                executeTask(sysTask);
            }
            // 通过http调用
            else {
                boolean b = InstanceManager.getInstance().sendMessage(executeInstance, "task-execute", JsonUtil.toJson(sysTask));
                if (!b) {
                    callTask(sysTask, atomicInteger, excludeInstanceName);
                }
            }

        }
    }

    /**
     * 运行任务
     *
     * @param myTask
     */
    public void executeTask(SysTask myTask) {
        long t1 = System.currentTimeMillis();
        int executeStatus = 1;
        String errorMessage = "";
        try {
            // 如果是java类
            if (myTask.getTaskType() == 1) {
                runJavaTask(myTask);
            } else if (myTask.getTaskType() == 2) {
                runFlowTask(myTask);
            }
        } catch (CronException e) {
            if (e.getErrorCode() == 1 || e.getErrorCode() == 2) {
                executeStatus = 0;
                errorMessage = e.getMessage();
            }
        } catch (Exception e) {
            log.error("定时任务执行失败, 任务名: {}， 错误信息:{}", myTask.getName(), e.getMessage());
            executeStatus = 0;
            errorMessage = ExceptionUtils.getStackTrace(e);
        } finally {
            long t2 = System.currentTimeMillis();
            log.debug("任务执行完成，名称:{}, 执行结果:{}, 用时:{}, 信息:{}", myTask.getName(), executeStatus == 1 ? "成功" : "失败", (t2 - t1), errorMessage);
            if (resultToDb) {
                String sql = "update sys_task set last_execute_status=?, last_execute_take = ?, last_execute_msg = ?,  last_execute_time=?, next_inst=? where id=?";
                DB.executeUpdateSql(sql, executeStatus, (t2 - t1), errorMessage, DateUtils.formatDate(new Timestamp(t1), DateUtils.DATE_TIME), SystemUtil.getHost().instanceName(), myTask.getId());
            }

        }

    }

    /**
     * 执行java类任务
     *
     * @param sysTask 任务
     */
    private void runJavaTask(SysTask sysTask) throws Exception {
        try {
            KTask kTask = (KTask) Class.forName(sysTask.getClassName()).newInstance();
            kTask.execute();
        } catch (ClassNotFoundException e) {
            throw new CronException("调度Class不存在", 1);
        }


    }

    /**
     * 运行流程任务
     *
     * @param sysTask 流程
     */
    private void runFlowTask(SysTask sysTask) {


        // 先查找一下看流程是否存在
        SysLogicFlow logicFlow = DB.findOne(SysLogicFlow.class, "select in_argv, out_argv from sys_logic_flow where flow_id=?", sysTask.getTaskResourceId());
        String inArgv = "{}";
        String outArgv = "{}";
        if (logicFlow != null) {
            if (StringUtils.isNotEmpty(logicFlow.getInArgv())) {
                inArgv = logicFlow.getInArgv();
            }
            if (StringUtils.isNotEmpty(logicFlow.getOutArgv())) {
                outArgv = logicFlow.getOutArgv();
            }
        }
        KFlowContext context = KFlowContext.createBaseContext(inArgv, outArgv);
        Map<String, Object> params = new HashMap<>();
        if (StringUtils.isNotEmpty(sysTask.getTaskArgv())) {
            Map<String, Object> taskArgvMap = JsonUtil.toMap(sysTask.getTaskArgv());
            if (taskArgvMap != null) {
                params = taskArgvMap;
            }
        }
        params.put("_expireTime",  getNextTriggerTime(sysTask.getCron()));
        long t1 = System.currentTimeMillis();

        KdbFlowResult kdbFlowResult = KdbFlowExecutor.getInstance().execute(sysTask.getTaskResourceId(), "", params, context, false, asyncExecute);
        long t2 = System.currentTimeMillis();
            log.debug("流程任务完成：{}", sysTask.getName());
    }

    private long getNextTriggerTime(String cron) {
        if (NumberUtils.isInteger(cron)) {
            return System.currentTimeMillis() + Long.parseLong(cron)*1000;
        }
        else {
            CronTrigger cronTrigger = new CronTrigger(cron); // 设置与任务的调度时间表达式一致的CronTrigger
            Date nextExecutionTime = cronTrigger.nextExecutionTime(new TriggerContext() {
                @Override
                public Date lastScheduledExecutionTime() {
                    return null; // 返回上一次任务调度的时间，如果没有则返回null
                }

                @Override
                public Date lastActualExecutionTime() {
                    return null; // 返回上一次任务实际执行的时间，如果没有则返回null
                }

                @Override
                public Date lastCompletionTime() {
                    return null; // 返回上一次任务完成的时间，如果没有则返回null
                }
            });

            assert nextExecutionTime != null;
            ScheduledFuture<?> scheduledFuture = threadPoolTaskScheduler.schedule(() -> {}, nextExecutionTime);
            long delay = scheduledFuture.getDelay(TimeUnit.MILLISECONDS);
            scheduledFuture.cancel(true);
            return System.currentTimeMillis() + delay;
        }
    }

    /**
     * 扫描Class类
     */
    private void scanJavaClassTask(String scanPackage) {
        // 扫描所有的定时器类
        List<Class<?>> classList = ClassUtils.getClassesByParentClass(scanPackage, KTask.class);
        for (Class<?> tClass : classList) {
            // 生成实例
            try {
                KTask task = (KTask) tClass.newInstance();
                // 查找平台已经是否存在此任务
                long count = DB.findCount("select count(1) from sys_task where task_type=1 and name=?", task.name());
                // 如果已存在就不处理
                if (count == 0) {
                    SysTask sysTask = new SysTask();
                    sysTask.setName(task.name());
                    sysTask.setTaskType(1);
                    sysTask.setCron(task.cron());
                    sysTask.setEnable(1);
                    sysTask.setDistributed(0);
                    sysTask.setClassName(tClass.getName());
                    sysTask.setLockForLeast(1);
                    sysTask.setLockForMost(30);
                    sysTask.setLastExecuteStatus(0);
                    sysTask.setLastExecuteTake(0L);
                    // 保存
                    DB.save(sysTask);
                    log.debug("发现任务，任务名称:{}, cron:{}, Class: {}", sysTask.getName(), sysTask.getCron(), sysTask.getClassName());
                }
                if (task instanceof KRunner) {
                    ((KRunner) task).runNow();
//                    log.info("定时任务启动时即运行:{}", task.name());
                }
            } catch (Exception e) {
                log.error("定时类扫描初始化失败:{}", e.getMessage());
            }
        }
    }


    @Override
    public void run(String... args) throws Exception {
        scanJavaClassTask(scanPackage);
        threadPoolTaskScheduler.schedule(() -> {

            try {
                if (DBInitialize.initCompleted) {
                    List<SysTask> tasks = DB.findList(SysTask.class, "select * from sys_task where enable=1 order by when_created asc");
                    log.debug("线程池数量：{}，当前活动：{}， 任务数:{}", threadPoolTaskScheduler.getPoolSize(),  threadPoolTaskScheduler.getActiveCount(),  tasks.size());
                    if (sysTaskList.isEmpty()) {
                        sysTaskList.addAll(tasks);
                    }
                    else {
                        // 删除不存在的任务
                        List<SysTask> removeTasks = new ArrayList<>();
                        sysTaskList.forEach(it -> {
                            Optional<SysTask> optional = tasks.stream().filter(task -> task.getId().equals(it.getId())).findFirst();
                            // 如果找到，就放到替换列表中
                            if (optional.isPresent()) {
                                int index = sysTaskList.indexOf(it);
                                sysTaskList.set(index, optional.get());
                            }
                            else {
                                removeTasks.add(it);
                            }
                        });
                        // 增加新的
                        for (SysTask task: tasks) {
                            if (!sysTaskList.contains(task)) {
                                sysTaskList.add(task);
                            }
                        }
                        // 移除不存在的
                        sysTaskList.removeAll(removeTasks);
                    }
                    // 处理一下表达式
                    for (SysTask task: tasks) {
                        try {

                            String cron = fixedCron(task.getCron());
                            // 判断是否共享表达式
                            String cronKey = shareCron ? cron: task.getCron()+ "@" + task.getId();
                            if (!scheduledFutureMap.containsKey(cron)) {
                                if (task instanceof KRunner) {
                                    ((KRunner)task).runNow();
                                }
                            }

                            task.setCron(cron);
                            registerTask(cron, cronKey);

                        }
                        catch (IllegalArgumentException e) {
                            log.warn("任务表达式注册不成功, 任务名称:{}, 表达式{}", task.getName(), task.getCron());
                            task.setEnable(0);
                            task.setLastExecuteMsg("cron表达式不合法");
                            DB.update(task);
                        }
                        catch (Exception e) {
                            log.warn("任务注册失败：{}， {}", task.getName(), task.getCron());

                        }
                    }
                }
                else {
                    log.info("当前系统未初始化完成....");
                }

            }
            catch (Exception e) {
                log.error("error", e);
            }

            // 解锁任务
            // unlockTask(tasks);


        }, new CronTrigger("0/10 * * * * ?"));

    }
}
