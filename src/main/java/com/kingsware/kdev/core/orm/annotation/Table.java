package com.kingsware.kdev.core.orm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表名注解
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/21 10:38 上午
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.SOURCE)
public @interface Table {
    /** 表名 **/
    String value() default "";
}
