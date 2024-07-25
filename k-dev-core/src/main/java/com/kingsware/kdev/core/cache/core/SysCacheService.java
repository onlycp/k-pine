package com.kingsware.kdev.core.cache.core;

import com.kingsware.kdev.core.model.SysCache;

import java.util.List;

public interface SysCacheService {
    String getCache(String key);

    void setCache(String key, String value);

    void setCache(String key, String value, long expireTime);

    void removeCache(String key);
}
