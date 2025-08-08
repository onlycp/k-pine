package com.kingsware.kdev.core.cache.kcache;

import com.kingsware.kdev.core.mode.AppModeProperties;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.core.util.MD5Utils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

@Aspect
@Slf4j
@Component
@Order(1)
public class KCacheAspect {

    @Resource
    private AppModeProperties appModeProperties;

    @Around("execution(public * com.kingsware..*.*(..)) && @annotation(kCache)")
    public Object process(ProceedingJoinPoint pjd, KCache kCache) throws Throwable {
        // 如果是开发模式，并且只针对于生产模式
        if (appModeProperties.getDev() && kCache.onlyForProd()) {
            return pjd.proceed();
        }
        Map<String, Object> keysMap = new TreeMap<>();
        keysMap.put("args", pjd.getArgs());
        keysMap.put("target", pjd.getTarget().toString());
        keysMap.put("kind", pjd.getKind());
        String key = MD5Utils.md5(Objects.requireNonNull(JsonUtil.toJson(keysMap)));
        Object obj = KCacheManager.getInstance().get(key);
         if (obj != null) {
            return obj;
        }
        else {
            Object object =  pjd.proceed();
            KCacheManager.getInstance().put(key, object, kCache.expire());
            return object;
        }

    }
}
