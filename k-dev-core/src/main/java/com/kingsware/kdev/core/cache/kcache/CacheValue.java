package com.kingsware.kdev.core.cache.kcache;

import lombok.Data;

/**
 * 缓存值的包装
 */
@Data
public class CacheValue {

    private Object value;
    /** 创建时间 **/
    private long whenCreated;
    /** 过期时间，单位为秒 **/
    private long expire;

    /**
     * 判断缓存是否过期
     * @return
     */
    public boolean isExpired() {
        return ((whenCreated + expire*1000) < System.currentTimeMillis()) && expire > 0;
    }
}
