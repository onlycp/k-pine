package com.kingsware.kdev.core.orm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 是否逻辑删除
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/21 10:38 上午
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.SOURCE)
public @interface LogicDelete {
    /** 逻辑删除字段名 **/
    String column() default "deleted";
    /** 默认值 **/
    int defValue() default 0;
}
