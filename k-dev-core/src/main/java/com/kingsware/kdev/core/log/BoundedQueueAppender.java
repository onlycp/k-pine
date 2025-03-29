package com.kingsware.kdev.core.log;

import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.classic.spi.ILoggingEvent;

import java.util.Deque;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class BoundedQueueAppender extends AppenderBase<ILoggingEvent> {
    private static final int MAX_LOG_SIZE = 500; // 最大日志条数
    private static final BlockingDeque<String> logQueue = new LinkedBlockingDeque<>(MAX_LOG_SIZE);

    @Override
    protected void append(ILoggingEvent eventObject) {
        // 如果队列已满，移除最旧的日志（阻塞模式）
        if (logQueue.size() >= MAX_LOG_SIZE) {
            logQueue.pollFirst(); // 移除队头
        }
        logQueue.offerLast(eventObject.getFormattedMessage()); // 添加新日志到队尾
    }

    public static BlockingDeque<String> getLogQueue() {
        return logQueue;
    }
}
