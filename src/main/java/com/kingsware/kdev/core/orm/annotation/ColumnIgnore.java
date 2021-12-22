package com.kingsware.kdev.core.orm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 忽略列
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/22 10:08 上午
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ColumnIgnore {
}
