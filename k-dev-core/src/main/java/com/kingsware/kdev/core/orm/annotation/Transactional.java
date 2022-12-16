package com.kingsware.kdev.core.orm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 事务注解
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Transactional {
    /** 触发事务 **/
    Class<? extends Throwable>[] rollbackFor() default {};
    /** 超时时间 **/
    int timeout() default 60;
}
