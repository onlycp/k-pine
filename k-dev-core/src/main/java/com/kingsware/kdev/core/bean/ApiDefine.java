package com.kingsware.kdev.core.bean;

import com.kingsware.kdev.core.auth.ApiCode;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * api定义
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/4/26 3:34 PM
 */
@Data
public class ApiDefine {

    /** 接口名称 **/
    private String name;
    /** 模块 **/
    private String module;
    /** url **/
    private String url;
    /** 方法 **/
    private Set<String> method = new HashSet<>();
    /** 是否不验证权限 **/
    private boolean ignore;
    /** 类名 **/
    private String className;
    /** 接口代码 **/
    private String apiCode;
}
