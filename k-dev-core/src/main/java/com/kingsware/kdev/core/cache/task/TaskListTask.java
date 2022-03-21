package com.kingsware.kdev.core.cache.task;

import com.kingsware.kdev.core.cache.api.ApiInfo;
import com.kingsware.kdev.core.cache.api.ApiManager;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.cron.KTask;
import com.kingsware.kdev.core.kflow.KFlowProperties;
import com.kingsware.kdev.core.model.SysTask;
import com.kingsware.kdev.core.orm.DB;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 接口定时任务
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/6 9:41 上午
 */
@Slf4j
@Component
@Order(0)
public class TaskListTask  {

    public TaskListTask() {
        this.execute();
    }

    /**
     * 定时拉取任务
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void execute() {
        // 查找所有接口
        try {
            List<SysTask> tasks = DB.findList(SysTask.class, "select * from sys_task where enable=1");
            TaskListManager.getInstance().addTask(tasks);
        }
        catch (Exception e) {
            log.warn("任务同步出错， 错误信息: {}", e.getMessage());
        }

    }
}
