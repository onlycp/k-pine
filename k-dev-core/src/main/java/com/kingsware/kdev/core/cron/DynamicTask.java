package com.kingsware.kdev.core.cron;

import com.kingsware.kdev.core.kflow.KFlowContext;
import com.kingsware.kdev.core.kflow.KdbFlowExecutor;
import com.kingsware.kdev.core.model.SysLogicFlow;
import com.kingsware.kdev.core.model.SysTask;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.SqlWrapper;
import com.kingsware.kdev.core.orm.kdb.FlowInfo;
import com.kingsware.kdev.core.orm.kdb.KdbFlowQueryArgv;
import com.kingsware.kdev.core.util.ClassUtils;
import com.kingsware.kdev.core.util.DateUtils;
import com.kingsware.kdev.core.util.ExceptionUtils;
import com.kingsware.kdev.core.util.StringUtils;
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

    private final ThreadPoolTaskScheduler threadPoolTaskScheduler;

    private final Map<String, ScheduledFutureHolder> scheduledFutureMap;

    public DynamicTask() {
        this.scheduledFutureMap = new HashMap<>(1);
        this.threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        this.threadPoolTaskScheduler.setPoolSize(10);
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

        // 如果不是分布式任务，直接运行
        if (sysTask.getDistributed() == 0) {
            executeTask(sysTask);
            return;
        }
        // 设置锁，通过返回的数量才判断是否被锁
        long cnt = DB.executeUpdateSql("update sys_task set lock_status=1, lock_for_time=? where id=? and lock_status=0", DateUtils.getNow(), sysTask.getId());
        // 如果影响行数为0，说明当前是锁定状态
        if (cnt == 0) {
//            log.info("任务:{} 处于锁定状态", sysTask.getName());
            return;
        }

        SysTask myTask = DB.findById(SysTask.class, sysTask.getId());
        if (myTask.getEnable() == 0) {
            return;
        }
        executeTask(myTask);

    }

    /**
     * 运行任务
     * @param myTask
     */
    private void executeTask(SysTask myTask)  {
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
            String sql = "update sys_task set last_execute_status=?, last_execute_take = ?, last_execute_msg = ?,  last_execute_time=?, lock_status=0 where id=?";
            DB.executeUpdateSql(sql, executeStatus, (t2 - t1),  errorMessage, DateUtils.formatDate(new Timestamp(t1), DateUtils.DATE_TIME), myTask.getId());
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
            KdbFlowExecutor.getInstance().execute(sysTask.getTaskResourceId(), "", new HashMap<>(), context, false);
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
                    log.info("定时任务启动时即运行:{}", task.name());
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
                 unlockTask(tasks);
             }
             catch (Exception e) {
                 log.error("定时任务注册失败, {}", e.getMessage());
             }

        }, new CronTrigger("0/30 * * * * ?"));

    }

    private void unlockTask(final List<SysTask> tasks) {
        // 解锁任务
        List<SysTask> lockedTasks = tasks.stream().filter(it -> it.getLockStatus() != null && it.getLockStatus() == 1).collect(Collectors.toList());
        for (SysTask task: lockedTasks) {
            Integer lockForMost = task.getLockForMost();
            if (task.getLockForMost() == null) {
                lockForMost = 60;
            }
            // 如果达到解锁标准了
            if (task.getLockForTime() != null) {
                if ((task.getLockForTime().getTime() + lockForMost*1000) < System.currentTimeMillis()) {
                    DB.executeUpdateSql("update sys_task set lock_status=0 where id=?", task.getId());
                    log.info("定时任务:{} 自动解锁", task.getName());
                }
            }
        }
    }
}
