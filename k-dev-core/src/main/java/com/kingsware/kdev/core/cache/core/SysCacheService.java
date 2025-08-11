package com.kingsware.kdev.core.cache.core;

public interface SysCacheService {
    String getCache(String key);

    void setCache(String key, String value);

    void setCache(String key, String value, long expireTime);

    void removeCache(String key);
}
