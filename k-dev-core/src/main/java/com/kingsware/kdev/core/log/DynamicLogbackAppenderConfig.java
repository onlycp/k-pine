package com.kingsware.kdev.core.log;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import lombok.Data;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

//@Component
public class DynamicLogbackAppenderConfig {

    @PostConstruct
    public void addBoundedQueueAppender() {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger rootLogger = context.getLogger("ROOT");

        // 创建并配置自定义 Appender
        BoundedQueueAppender appender = new BoundedQueueAppender();
        appender.setContext(context);
        appender.start();

        // 动态添加到 Root Logger
        rootLogger.addAppender(appender);

        System.out.println("BoundedQueueAppender added to Root Logger.");
    }
}