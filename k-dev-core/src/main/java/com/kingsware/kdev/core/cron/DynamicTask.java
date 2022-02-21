//package com.kingsware.kdev.core.cron;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
//import org.springframework.stereotype.Component;
//import org.springframework.util.ConcurrentReferenceHashMap;
//import org.springframework.util.concurrent.ListenableFuture;
//
//import javax.annotation.Resource;
//import java.util.Map;
//import java.util.concurrent.ScheduledFuture;
//
///**
// * 动态定时任务
// *
// * @author chen peng
// * @version 1.0.0
// * @date 2022/2/17 9:30 上午
// */
//@Component
//public class DynamicTask {
//
//    @Resource
//    private ThreadPoolTaskScheduler threadPoolTaskScheduler;
//
//    private final Map<String, ScheduledFuture<?>> scheduledFutureMap;
//
//    public DynamicTask() {
//        this.scheduledFutureMap = new ConcurrentReferenceHashMap(16, ConcurrentReferenceHashMap.ReferenceType.WEAK);
//    }
//
//    @Bean
//    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
//        return new ThreadPoolTaskScheduler();
//    }
//
//    public void
//
//
//}
