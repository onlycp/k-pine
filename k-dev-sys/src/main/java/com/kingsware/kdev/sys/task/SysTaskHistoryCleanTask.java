package com.kingsware.kdev.sys.task;

import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.cron.KTask;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.util.DateUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 定时清理任务调度历史数据
 */
@Slf4j

public class SysTaskHistoryCleanTask implements KTask {

    private static final AtomicBoolean running = new AtomicBoolean(false);

    @Override
    public void execute() throws Exception {
        // 使用compareAndSet原子地“检查并设置”
        if (running.compareAndSet(false, true)) {
            try {
                // 成功获取到锁，执行业务逻辑
                doBusiness();
            } finally {
                // 确保在业务执行完毕或异常后释放锁
                running.set(false);
            }
        } else {
            // 未获取到锁，说明任务已在运行
            log.warn("任务 {} 正在运行，本次调度将被忽略。", name());
        }
    }

    private void doBusiness() throws Exception {
        // 从配置中获取保留天数，默认为7天
        int retentionDays = SpringContext.getInt("sys.task.clean.interval", 7);
        if (retentionDays <= 0) {
            retentionDays = 7;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -retentionDays);
        String sevenDaysAgo = DateUtils.formatDate(calendar.getTime(), "yyyy-MM-dd HH:mm:ss");

        // 删除数据
        String deleteSql = "delete from sys_task_history where execute_begin_time < ?";
        DB.executeUpdateSql(deleteSql, sevenDaysAgo);
        log.info("成功删除了 {} 以前的任务历史数据。", sevenDaysAgo);
    }

    @Override
    public String cron() {
        // 每天凌晨1点执行
        return "0 0 1 * * ?";
    }

    @Override
    public String name() {
        return "定时清理任务调度历史数据";
    }

    @Override
    public String note() {
        int retentionDays = SpringContext.getInt("sys.task.clean.interval", 7);
        return "将"+ retentionDays +"天前的任务历史数据从数据库中删除。";
    }
}
