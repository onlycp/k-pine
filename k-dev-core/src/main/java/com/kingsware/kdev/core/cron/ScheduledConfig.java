package com.kingsware.kdev.core.cron;

import com.kingsware.kdev.core.model.SysTask;
import com.kingsware.kdev.core.orm.DB;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import java.util.List;
import java.util.concurrent.Executor;

/**
 *  定时任务配置器
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/11 2:11 下午
 */
@Slf4j
@Configuration
@EnableScheduling
public class ScheduledConfig implements SchedulingConfigurer {

    @Value("${schedule.corePoolSize:10}")
    private int corePoolSize;
    @Value("${schedule.maxPoolSize:100}")
    private int maxPoolSize;
    @Value("${schedule.queueCapacity:50}")
    private int queueCapacity;
    @Value("${schedule.scan-package:com.kingsware.kdev}")
    private String scanPackage ;

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        // 先加载类
        KTaskManager.getInstance().scanJavaClassTask(scanPackage);
        // 从数据库里加载所有定时任务
        List<SysTask> tasks = DB.findList(SysTask.class, "select * from sys_task where enable=1");
        for (SysTask sysTask: tasks) {
            // 执行任务
            scheduledTaskRegistrar.addTriggerTask(() -> {
                KTaskManager.getInstance().runTask(sysTask);

            }, triggerContext -> {
                SysTask myTask = DB.findById(SysTask.class, sysTask.getId());
                return new CronTrigger(myTask.getCron()).nextExecutionTime(triggerContext);
            });
        }
    }


    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.initialize();
        return executor;
    }


}
