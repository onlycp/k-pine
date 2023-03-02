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
import com.kingsware.kdev.core.orm.SqlWrapper;
import com.kingsware.kdev.core.orm.kdb.FlowInfo;
import com.kingsware.kdev.core.orm.kdb.KdbFlowQueryArgv;
import com.kingsware.kdev.core.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.util.ConcurrentReferenceHashMap;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicInteger;
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
@Slf4j
public class DynamicTask implements CommandLineRunner {

    @Value("${schedule.scan-package:com.kingsware.kdev}")
    private String scanPackage ;
    /** 是否将结果回写到数据库 **/
    @Value("${schedule.result-to-db:true}")
    private boolean resultToDb ;
    /** 是否自动分布式 **/
    @Value("${schedule.distributed-auto:true}")
    private boolean distributedAuto;
    /** 是否运行分布式 **/
    @Value("${schedule.distributed-run:true}")
    private boolean distributedRun;


    private final ThreadPoolTaskScheduler threadPoolTaskScheduler;

    private final Map<String, ScheduledFutureHolder> scheduledFutureMap;

    public DynamicTask() {
        this.scheduledFutureMap = new HashMap<>(1);
        this.threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        this.threadPoolTaskScheduler.setPoolSize(50);
        this.threadPoolTaskScheduler.initialize();
    }

    public void startTask (SysTask sysTask) {
        //将任务交给任务调度器执行
        ScheduledFuture<?> schedule = threadPoolTaskScheduler.schedule(() -> runTask(sysTask), new CronTrigger(sysTask.getCron()));
        //将任务包装成ScheduledFutureHolder
        ScheduledFutureHolder scheduledFutureHolder = new ScheduledFutureHolder();
        scheduledFutureHolder.setScheduledFuture(schedule);
        scheduledFutureHolder.setSysTask(sysTask);
        scheduledFutureMap.put(sysTask.getId(), scheduledFutureHolder);
    }


    public void stopTask(SysTask sysTask) {
        //如果包含这个任务
        if(scheduledFutureMap.containsKey(sysTask.getId())){
            log.info("停止定时任务:{}", sysTask.getName());
            ScheduledFutureHolder scheduledFutureHolder = scheduledFutureMap.get(sysTask.getId());
            ScheduledFuture<?> scheduledFuture = scheduledFutureHolder.getScheduledFuture();
            scheduledFuture.cancel(true);
            scheduledFutureMap.remove(sysTask.getId());
        }
    }

    public void updateTask(SysTask sysTask) {
        if (sysTask.getEnable() == 0) {
            stopTask(sysTask);
        }
        else {
            stopTask(sysTask);
            startTask(sysTask);
        }

    }

    public void registerTask(SysTask sysTask) {
        // 判断是否有
        ScheduledFutureHolder scheduledFutureHolder = scheduledFutureMap.get(sysTask.getId());
        if (scheduledFutureHolder != null) {
            // 如果表达式和启用状态相同，直接返回
            if (sysTask.getEnable().equals(scheduledFutureHolder.getSysTask().getEnable())  && sysTask.getCron().equals(scheduledFutureHolder.getSysTask().getCron())) {
                scheduledFutureHolder.setSysTask(sysTask);
                return;
            }
            updateTask(sysTask);
        }
        else if (sysTask.getEnable() == 1){
            startTask(sysTask);
        }

    }

    /**
     * 运行任务
     * @param sysTask   任务便利店
     */
    private void runTask(SysTask sysTask) {
        sysTask = DB.findById(SysTask.class, sysTask.getId());
        if (sysTask.getEnable() == 0) {
            return;
        }
        // 如果不是分布式任务，直接运行
        if (sysTask.getDistributed() == 0) {
            executeTask(sysTask);
            return;
        }
        if (distributedAuto) {
            AtomicInteger atomicInteger = new AtomicInteger(0);
            callTask(sysTask, atomicInteger, "");

        }
        else {
            if (distributedRun) {
                SysTask myTask = DB.findById(SysTask.class, sysTask.getId());
                if (myTask.getEnable() == 0) {
                    return;
                }
                executeTask(myTask);
            }
        }
    }

    /**
     * 调用http接口
     * @param sysTask    任务
     */
    public void callTask(SysTask sysTask, AtomicInteger atomicInteger,String excludeInstanceName) {
        if (atomicInteger.intValue() == 3) {
            log.warn("任务:{}执行失败次数为:{}, 调度器将终止任务执行", sysTask.getName(), atomicInteger.intValue() );
        }
        // 取主实例作为调度器
        SysInstance masterInstance = InstanceManager.getInstance().masterInstance();
        // 只有是调度器才执行
        if (SystemUtil.getHost().instanceName().equalsIgnoreCase(masterInstance.instanceName())) {
            atomicInteger.incrementAndGet();
            // 随机取一个实例
            SysInstance executeInstance =  InstanceManager.getInstance().getToExecuteInstance(sysTask.getId(), excludeInstanceName);
            // 如果是当前实例，直接调用即可，不通过http调用
            if (executeInstance.instanceName().equalsIgnoreCase(masterInstance.instanceName())) {
                log.info("本机触发定时任务，任务id:{}", sysTask.getId());
                executeTask(sysTask);
            }
            // 通过http调用
            else {
                String contextPath = SpringContext.getProperties("server.servlet.context-path", "/");
                if (StringUtils.isEmpty(contextPath)) {
                    contextPath = "/";
                }
                if (!contextPath.endsWith("/")) {
                    contextPath = contextPath + "/";
                }

                String url = "http://" + executeInstance.getHostName() + ":" + executeInstance.getPort() + contextPath + "api/v1/sys-tasks/executeTask/" + sysTask.getId();
                try {
                    String res = HttpUtil.get(url, new HashMap<>());
                    BaseRet<?> ret = JsonUtil.toBean(res, BaseRet.class);
                    if (ret.getCode() != 200)  {
                        log.error("实例：{} 执行任务失败，系统将重试,异常信息:{}", executeInstance.instanceName(), ret.getMessage());
                        callTask(sysTask, atomicInteger, executeInstance.instanceName());
                    }
                    else  {
//                        log.info("http触发定时任务，任务id:{}，url:{},响应信息:{}", sysTask.getId(), url, ret.getMessage());
                    }

                }
                catch (Exception e) {
                    // 如果发生异常，那么就重试
                    log.error("实例：{} 执行任务失败，系统将重试,异常信息:{}", executeInstance.instanceName(), e);
                    callTask(sysTask, atomicInteger, executeInstance.instanceName());
                }

            }

        }
    }

    /**
     * 运行任务
     * @param myTask
     */
    public void executeTask(SysTask myTask)  {
        long t1 = System.currentTimeMillis();
        int executeStatus = 1;
        String errorMessage = "";
        try {
            // 如果是java类
            if (myTask.getTaskType() == 1) {
                runJavaTask(myTask);
            }
            else if (myTask.getTaskType() == 2) {
                runFlowTask(myTask);
            }
        }
        catch (CronException e) {
            if (e.getErrorCode() == 1 || e.getErrorCode() == 2 ) {
                executeStatus = 0;
                errorMessage = e.getMessage();
            }
        }
        catch (Exception e) {
            log.error("定时任务执行失败, 任务名: {}， 错误信息:{}", myTask.getName(), e.getMessage());
            executeStatus = 0;
            errorMessage = ExceptionUtils.getStackTrace(e);
        }
        finally {
            long t2 = System.currentTimeMillis();
            if ((!resultToDb) && myTask.getDistributed()==0) {
                log.info("任务执行完成，名称:{}, 执行结果:{}, 用时:{}, 信息:{}", myTask.getName(), executeStatus==1 ?"成功":"失败", (t2-t1), errorMessage);
            }
            else {
                // 如果流程任务，延迟1秒更新日志
                if (myTask.getTaskType() == 2) {
                    ThreadUtils.sleep(1000);
                }
                String sql = "update sys_task set last_execute_status=?, last_execute_take = ?, last_execute_msg = ?,  last_execute_time=?, next_inst=? where id=?";
                DB.executeUpdateSql(sql, executeStatus, (t2 - t1), errorMessage, DateUtils.formatDate(new Timestamp(t1), DateUtils.DATE_TIME), SystemUtil.getHost().instanceName(), myTask.getId());

            }

        }

    }

    /**
     * 执行java类任务
     * @param sysTask   任务
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
     * @param sysTask 流程
     */
    private void runFlowTask(SysTask sysTask) {
        // 先查找一下看流程是否存在
        KdbFlowQueryArgv flowInfo = new KdbFlowQueryArgv();
        flowInfo.setFlowId(sysTask.getTaskResourceId());
        List<FlowInfo> flowInfos = DB.kdbApi().query(flowInfo);
        if (!flowInfos.isEmpty()) {
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
            long t1 = System.currentTimeMillis();
            KdbFlowResult kdbFlowResult = KdbFlowExecutor.getInstance().execute(sysTask.getTaskResourceId(), "", params, context, false, true);
            long t2 = System.currentTimeMillis();
//            log.info("流程任务：{}, 结果:{}", sysTask.getName(), JsonUtil.toJson(kdbFlowResult));
        }
        else {
            throw new CronException("流程不存在", 2);
        }

    }

    /**
     * 扫描Class类
     */
    private void scanJavaClassTask(String scanPackage) {
        // 扫描所有的定时器类
        List<Class<?>> classList =  ClassUtils.getClassesByParentClass(scanPackage, KTask.class);
        for (Class<?> tClass: classList) {
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
                    log.info("发现任务，任务名称:{}, cron:{}, Class: {}", sysTask.getName(), sysTask.getCron(), sysTask.getClassName());
                }
                if (task instanceof KRunner) {
                    ((KRunner)task).runNow();
//                    log.info("定时任务启动时即运行:{}", task.name());
                }
            } catch (Exception e) {
                log.error("定时类扫描初始化失败:{}" , e.getMessage());
            }
        }
    }


    @Override
    public void run(String... args) throws Exception {
        scanJavaClassTask(scanPackage);
         threadPoolTaskScheduler.schedule(() -> {
             try {
                 List<SysTask> tasks = DB.findList(SysTask.class, "select * from sys_task");
                 for (SysTask task: tasks) {
                     registerTask(task);
                 }
                 // 删除不存在的任务
                 Set<String> deleteTaskIds = new HashSet<>();
                 scheduledFutureMap.forEach((k, v) ->{
                     boolean match = tasks.stream().anyMatch(it -> it.getId().equals(v.getSysTask().getId()));
                     if (!match) {
                        deleteTaskIds.add(k);
                     }
                 });
                 for (String tid: deleteTaskIds) {
                     stopTask(scheduledFutureMap.get(tid).getSysTask());
                 }
                 // 解锁任务
                 // unlockTask(tasks);
             }
             catch (Exception e) {
                 log.error("定时任务注册失败, {}", e.getMessage());
             }

        }, new CronTrigger("0/30 * * * * ?"));

    }
}
