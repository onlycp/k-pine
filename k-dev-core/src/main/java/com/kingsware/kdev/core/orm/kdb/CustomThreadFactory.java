package com.kingsware.kdev.core.orm.kdb;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chenp
 * @date 2024/3/1
 */
public class CustomThreadFactory implements ThreadFactory {

    private final String threadNamePrefix;
    private final AtomicInteger threadId = new AtomicInteger(0);

    public CustomThreadFactory(String threadNamePrefix) {
        this.threadNamePrefix = threadNamePrefix;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setName(threadNamePrefix + "-" + threadId.incrementAndGet());
        return thread;
    }
}
