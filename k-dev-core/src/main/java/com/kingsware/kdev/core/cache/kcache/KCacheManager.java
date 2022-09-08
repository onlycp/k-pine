package com.kingsware.kdev.core.cache.kcache;

import com.kingsware.kdev.core.context.SpringContext;


public class KCacheManager {

    private static KCacheManager instance;

    public static KCacheManager getInstance() {
        if (instance == null) {
            instance = new KCacheManager();
        }
        return instance;
    }

    private final LruCache cache;

    private KCacheManager() {
        // 获取缓存数量限制
        int cacheLimit = Integer.parseInt(SpringContext.getProperties("app.cache.limit", "500"));
        this.cache = new LruCache(cacheLimit);
    }

    /**
     * 写入缓存
     * @param key
     * @param value
     */
    public void put(String key, Object value, long expire) {
        if (value == null) {
            return;
        }
        cache.put(key, value, expire);
    }



    /**
     * 获取缓存内容
     * @param key
     * @return
     */
    public Object get(String key) {
        return cache.get(key);
    }

    /**
     * 清理所有的缓存
     */
    public void clear() {

    }
}
