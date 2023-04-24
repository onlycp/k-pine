package com.kingsware.kdev.core.orm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * column定义
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/21 10:45 上午
 */
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
    /** 列名 **/
    String name() default "";
    /** 是否唯一 **/
    boolean unique() default false;
    /** 是否允许为空 **/
    boolean nullable() default true;
    /** 是否允许插入 **/
    boolean insertable() default true;
    /** 是否允许编辑 **/
    boolean updatable() default true;
    /** 自动赋值 **/
    AutoEnum auto() default AutoEnum.NONE;
    /** 整型转为字符型 **/
    String intToStrSchemeType() default "";

}
