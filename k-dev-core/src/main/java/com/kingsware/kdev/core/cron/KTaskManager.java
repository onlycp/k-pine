package com.kingsware.kdev.core.cron;

import com.google.common.collect.Maps;
import com.kingsware.kdev.core.context.KClientContext;
import com.kingsware.kdev.core.kflow.KFlowContext;
import com.kingsware.kdev.core.kflow.KdbFlowExecutor;
import com.kingsware.kdev.core.model.SysTask;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.util.DateUtils;
import com.kingsware.kdev.core.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;

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
            synchronized (KTaskManager.class) {
                if (instance == null) {
                    instance = new KTaskManager();
                }
            }
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
            log.error("定时任务执行失败, 任务名: {}， 错误信息:{}", sysTask.getName(), e.getMessage());
            executeStatus = 0;
            errorMessage = e.getMessage();
        }
        finally {
            long t2 = System.currentTimeMillis();
            String sql = "update sys_task set last_execute_status=?, last_execute_take = ?, last_execute_msg = ?,  last_execute_time=?, lock_status=0 where id=?";
            DB.executeUpdateSql(sql, executeStatus, (t2 - t1),  errorMessage, DateUtils.formatDate(new Timestamp(t1), DateUtils.DATE_TIME), sysTask.getId());
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
        KFlowContext context = KFlowContext.createBaseContext();
        KdbFlowExecutor.getInstance().execute(sysTask.getTaskResourceId(), Maps.newHashMap(), context);
    }
}
