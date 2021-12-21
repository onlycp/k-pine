package com.kingsware.kdev.core.auth;

import com.kingsware.kdev.core.enums.ApiSystemEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 接口编码
 * 如果配置了接口编码，那么前端如果要访问，必须得配置相关的权限
 * @author chenpeng
 * @date  2021-11-15
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiCode {
    /** 接口代码 **/
    String value() default "";
    /** 属于哪个认证体系 **/
    ApiSystemEnum s() default ApiSystemEnum.ADMIN;
}
