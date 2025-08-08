package com.kingsware.kdev.core.orm;

import com.kingsware.kdev.core.cache.kcache.KCache;
import com.kingsware.kdev.core.cache.kcache.KCacheManager;
import com.kingsware.kdev.core.mode.AppModeProperties;
import com.kingsware.kdev.core.orm.annotation.Transactional;
import com.kingsware.kdev.core.orm.exception.TransactionException;
import com.kingsware.kdev.core.orm.kdb.TransactionManager;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.core.util.MD5Utils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

@Aspect
@Slf4j
@Component
@Order(1)
public class KTransactionAspect {

    @Resource
    private AppModeProperties appModeProperties;

    @Around("execution(public * com.kingsware..*.*(..)) && @annotation(transaction)")
    public Object process(ProceedingJoinPoint pjd, Transactional transaction) throws Throwable {

        try {

            TransactionManager.getInstance().begin(transaction.timeout(), transaction.rollbackFor(), pjd.getSignature().toShortString() );
            Object obj = pjd.proceed();
            TransactionManager.getInstance().commit();
            return obj;
        }
        catch (Exception e) {
            // 如果默认不配置，那么就是所有异常都会触发回滚
            boolean rollback = false;
            if (transaction.rollbackFor().length == 0) {
                rollback = true;
            }
            else {
                for(Class<? extends Throwable> th: transaction.rollbackFor()) {
                   if (th.isAssignableFrom(e.getClass())) {
                       rollback = true;
                       break;
                   }
                }
            }
            if (rollback) {
                try {
                    TransactionManager.getInstance().rollback();
                }
                catch (TransactionException ex) {
                    log.warn("error", ex);
                }


            }
            throw e;
        }
    }
}
