package com.kingsware.kdev.core.auth;

import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chenp
 * @date 2024/6/27
 */
@Data
public class RateLimiter {

    /**
     * 时间窗口秒数
     */
    private long time;
    /**
     * 窗口内请求次数
     */
    private AtomicInteger count = new AtomicInteger(0);

    /**
     * @param limit 窗口内最大请求次数
     */
    public RateLimiter() {
        this.time = System.currentTimeMillis()/1000;
    }

    public int getCount() {
        return count.get();
    }

    /**
     *
     * @return
     */
    public boolean tryAcquire(int limit) {
        // 如果时间不是当前秒，就重新计数
        if (System.currentTimeMillis()/1000 != time) {
            time = System.currentTimeMillis()/1000;
            count.set(0);
        }
        return count.incrementAndGet() <= limit;
    }
}
