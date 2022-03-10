package com.kingsware.kdev.core.cron;

import com.google.common.collect.Maps;
import com.kingsware.kdev.core.kflow.KFlowContext;
import com.kingsware.kdev.core.kflow.KdbFlowExecutor;
import com.kingsware.kdev.core.model.SysTask;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.util.ClassUtils;
import com.kingsware.kdev.core.util.DateUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.util.List;

/**
 * 任务管理器
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/2/15 3:38 下午
 */
@Slf4j
public class KTaskManager {
    private static KTaskManager instance;

    public static KTaskManager getInstance() {
        if (instance == null) {
            instance = new KTaskManager();
        }
        return instance;
    }

    private KTaskManager() {
    }

    /**
     * 运行任务
     * @param sysTask   任务便利店
     */
    public void runTask(SysTask sysTask) {

        // 如果不是分布式任务，直接运行
        if (sysTask.getDistributed() == 0) {
            executeTask(sysTask);
            return;
        }

        // 设置锁，通过返回的数量才判断是否被锁
        long cnt = DB.executeUpdateSql("update sys_task set lock_status=1, lock_for_time=now() where id=? and lock_status=0", sysTask.getId());
        // 如果影响行数为0，说明当前是锁定状态
        if (cnt == 0) {
            log.info("任务:{} 处于锁定状态", sysTask.getName());
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
        catch (Exception e) {
            log.error("定时任务执行失败, 任务名: {}， 错误信息:{}", myTask.getName(), e.getMessage());
            executeStatus = 0;
            errorMessage = e.getMessage();
        }
        finally {
            long t2 = System.currentTimeMillis();
            String sql = "update sys_task set last_execute_status=?, last_execute_take = ?, last_execute_msg = ?,  last_execute_time=?, lock_status=0 where id=?";
            DB.executeUpdateSql(sql, executeStatus, (t2 - t1),  errorMessage, DateUtils.formatDate(new Timestamp(t1), DateUtils.DATE_TIME), myTask.getId());
            //log.debug("定时任务执行, 任务名: {}", sysTask.getName());
        }

    }

    /**
     * 执行java类任务
     * @param sysTask   任务
     */
    private void runJavaTask(SysTask sysTask) throws Exception {
        KTask kTask = (KTask) Class.forName(sysTask.getClassName()).newInstance();
        kTask.execute();
    }

    /**
     * 运行流程任务
     * @param sysTask 流程
     */
    private void runFlowTask(SysTask sysTask) {
        KFlowContext context = KFlowContext.createBaseContext("{}", "{}");
        KdbFlowExecutor.getInstance().execute(sysTask.getTaskResourceId(), Maps.newHashMap(), context);
    }

    /**
     * 扫描Class类
     */
    public void scanJavaClassTask(String scanPackage) {
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
                    // 保存
                    DB.save(sysTask);
                    log.info("发现任务，任务名称:{}, cron:{}, Class: {}", sysTask.getName(), sysTask.getCron(), sysTask.getClassName());
                }
            } catch (Exception e) {
                log.error("定时类扫描初始化失败:{}" , e.getMessage());
            }
        }
    }
}
