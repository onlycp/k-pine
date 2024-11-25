package com.kingsware.kdev.core.cache.api;

import com.kingsware.kdev.core.cache.TimedCache;
import com.kingsware.kdev.core.context.KClientContext;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.cron.DynamicTask;
import com.kingsware.kdev.core.kflow.bean.KdbFlowResult;
import com.kingsware.kdev.core.util.ServletUtil;
import com.kingsware.kdev.core.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author chenp
 * @date 2024/5/14
 */
public class ApiResultCacheManager {
    private static final Logger log = LoggerFactory.getLogger(ApiResultCacheManager.class);
    private static ApiResultCacheManager instance = new ApiResultCacheManager();
    /** 接口缓存 **/
    private final TimedCache<String, ApiResultCache> cache = new TimedCache<>();

    public static ApiResultCacheManager getInstance() {
        if (instance == null) {
            instance = new ApiResultCacheManager();
        }
        return instance;
    }

    /**
     * 私有构造方法，防止外部实例化。
     */
    private ApiResultCacheManager() {
    }

    /**
     * 对于给定的键值对，在缓存中放置数据，并指定过期时间。
     * @param key 用于在缓存中标识数据的键，不可为null。
     * @param value 要缓存的数据，不可为null。
     * @param timeoutMillis 数据在缓存中保持有效的毫秒数。
     */
    public void put(String key, ApiResultCache value, long timeoutMillis) {
        cache.put(key, value, timeoutMillis);
    }


    public void clearMyCache() {
        if (KClientContext.getContext() == null) {
            return;
        }
        String token = KClientContext.getContext().getToken();
        if (StringUtils.isEmpty(token)) {
            return;
        }
        for (String key : cache.keySet()) {
            if (cache.get(key).getTokens().contains(token)) {
                cache.remove(key);
                DynamicTask dynamicTask = SpringContext.getBean(DynamicTask.class);
                dynamicTask.removeVirtualTask(key);
                log.info("清除缓存：" + key);
            }
        }
    }



    /**
     * 通过键从缓存中获取数据。
     * @param key 用于检索缓存中数据的键，不可为null。
     * @return 如果找到相应的键，则返回缓存的数据；否则返回null。
     */
    public ApiResultCache get(String key) {
        return cache.get(key);
    }

    /**
     * 检查缓存是否包含指定的键。
     * @param key 要检查的键，不可为null。
     * @return 如果缓存包含该键，则返回true；否则返回false。
     */
    public boolean has(String key) {
        return cache.containsKey(key);
    }

    public void remove(String key) {
        cache.remove(key);
    }

}
