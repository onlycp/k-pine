package com.kingsware.kdev.core.cache.kcache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 缓存cache定义
 * @author chenpeng
 * @date  2021-11-15
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface KCache {
    /** 过期时间，单位为秒 **/
    int expire() default 0;
    /** 针对模式,是否只针对生产模式 **/
    boolean onlyForProd() default false;
}
