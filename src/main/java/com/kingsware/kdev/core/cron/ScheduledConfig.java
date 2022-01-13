package com.kingsware.kdev.core.cron;

import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.util.ClassUtils;
import com.kingsware.kdev.core.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
        // 扫描所有的定时器类
        List<Class<?>> classList =  ClassUtils.getClassesByParentClass(scanPackage, KTask.class);
        for (Class<?> tClass: classList) {
            KTask task;
            try {
                // 生成实例
                task = (KTask) tClass.newInstance();
                // 执行任务
                scheduledTaskRegistrar.addTriggerTask(() -> {
                    try {
                        long t1 = System.currentTimeMillis();
                        task.execute();
                        log.debug("定时任务执行成功, 任务名: {}， Class: {}, 耗时:{} ms", task.name(), task.getClass().getName(), System.currentTimeMillis() - t1);
                    }
                    catch (Exception e) {
                        log.error("定时任务执行失败, 任务名: {}， Class: {}, 错误信息:{}", task.name(), task.getClass().getName(), e.getMessage());
                    }

                }, triggerContext -> {
                    return new CronTrigger(task.cron()).nextExecutionTime(triggerContext);
                });
            }
            catch (Exception e) {
                log.error("定时任务初始化失败:{}" , e.getMessage());
            }
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
