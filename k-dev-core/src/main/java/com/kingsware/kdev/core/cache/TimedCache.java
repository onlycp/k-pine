package com.kingsware.kdev.core.cache;

import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.core.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class TimedCache<K, V> {

    private final Map<K, V> cache = new ConcurrentHashMap<>();
    private final Map<K, Long> expirationTimes = new ConcurrentHashMap<>();
    private final ScheduledExecutorService expirationScheduler = Executors.newScheduledThreadPool(1);

    public TimedCache() {
        // 启动定时任务，定期清理过期缓存项
        expirationScheduler.scheduleAtFixedRate(this::cleanupExpiredEntries, 0, 1, TimeUnit.SECONDS);
    }

    public void put(K key, V value, long timeoutMillis) {
        cache.put(key, value);
        expirationTimes.put(key, System.currentTimeMillis() + timeoutMillis);
    }

    public V get(K key) {
        cleanupExpiredEntries(); // 清理过期缓存项
        return cache.get(key);
    }

    public boolean containsKey(K key) {
        cleanupExpiredEntries(); // 清理过期缓存项
        return cache.containsKey(key);
    }

    public void remove(K key) {
        cache.remove(key);
        expirationTimes.remove(key);
    }

    /**
     * 获取所有键的集合
     * @return
     */
    public Set<K> keySet() {
        return cache.keySet();
    }

    private void cleanupExpiredEntries() {
        long currentTime = System.currentTimeMillis();
        for (Map.Entry<K, Long> entry : expirationTimes.entrySet()) {
            K key = entry.getKey();
            long expirationTime = entry.getValue();

            if (currentTime > expirationTime) {
                log.info("移除TimeCache:{}, 值:{}", key, StringUtils.retrench(JsonUtil.toJson(cache.get(key)), 200) );
                cache.remove(key);
                expirationTimes.remove(key);
            }
        }
    }


}
