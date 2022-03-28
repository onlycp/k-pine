package com.kingsware.kdev.core.cron;

import com.kingsware.kdev.core.model.SysTask;
import com.kingsware.kdev.core.orm.DB;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 解锁任务
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/2/16 2:05 下午
 */
@Component
@Slf4j
public class UnLockTask {

    @Scheduled(cron = "0/10 * * * * ?")
    public void execute() {
        // 为了兼容多种数据库，这里就先查出来计算，再处理
        String sql = "select * from sys_task where lock_status=1";
        List<SysTask> taskList = DB.findList(SysTask.class, sql);

        for (SysTask task: taskList) {
            Integer lockForMost = task.getLockForMost();
            if (task.getLockForMost() == null) {
                lockForMost = 60;
            }
            // 如果达到解锁标准了
            if ((task.getLockForTime().getTime() + lockForMost*1000) < System.currentTimeMillis()) {
                DB.executeUpdateSql("update sys_task set lock_status=0 where id=?", task.getId());
                log.info("定时任务:{} 自动解锁", task.getName());
            }
        }
    }
}
