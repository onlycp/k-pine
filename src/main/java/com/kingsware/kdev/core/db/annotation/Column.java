package com.kingsware.kdev.core.db.annotation;

/**
 * //todo 描述当前类是干什么用的.
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/21 10:45 上午
 */
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

}
