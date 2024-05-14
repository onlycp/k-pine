package com.kingsware.kdev.core.cache.kcache;

import com.kingsware.kdev.core.util.ThreadUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * LRU缓存实现
 */
@Slf4j
public class LruCache {

    private static final ExecutorService executorService = Executors.newCachedThreadPool();
    /** 缓存队列 **/
    private  final Map<String, CacheValue> caches;

    /**
     * 构造函数
     * @param limit
     */
    public LruCache(int limit) {
        caches = new LinkedHashMap<String, CacheValue>(50, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, CacheValue> eldest) {
                return size() > limit;
            }
        };
        executorService.submit((Runnable) () -> {
            while (true) {
                try {
                    Set<String> keys = new HashSet<>(caches.keySet());
                    for (String key: keys) {
                        CacheValue cacheValue = caches.get(key);
                        if (cacheValue.isExpired()) {
                            caches.remove(key);
                            log.info("LRU过期自动清理，键名:{}", key);
                        }
                    }
                    ThreadUtils.sleep(5000);
                }
                catch (Exception e) {
                    log.error("error", e);
                }

            }

        });

    }

    /**
     * 清空缓存
     */
    public void clean() {
        this.caches.clear();
    }

    /**
     * 写入缓存
     * @param key
     * @param value
     */
    public void put(String key, Object value, long expire) {
        CacheValue cv = new CacheValue();
        cv.setExpire(expire);
        cv.setWhenCreated(System.currentTimeMillis());
        cv.setValue(value);
        this.caches.put(key, cv);
    }



    /**
     * 获取缓存内容
     * @param key
     * @return
     */
    public Object get(String key) {
        if (this.caches.containsKey(key)) {
            CacheValue cv = this.caches.get(key);
            if (!cv.isExpired()) {
                // 更新时间
                cv.setWhenCreated(System.currentTimeMillis());
                return cv.getValue();
            }
        }
        return null;
    }

    /**
     * 缓存
     * @param key
     */
    public void delete(String key) {
        this.caches.remove(key);
    }


    public void deleteAll() {
        this.caches.clear();
    }


}
