package com.kingsware.kdev.core.context;

import com.kingsware.kdev.core.auth.BaseUserInfo;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 客户端信息
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/21 10:12 上午
 */
@Data
public class ClientInfo {

    /** 身份标识，token **/
    private String token;
    /** 客户端ip **/
    private String ip;
    /** 请求url **/
    private String url;
    /** 语言 **/
    private String lang;
    /** 当前登录用户信息 **/
    private BaseUserInfo userInfo;
    // http请求
    private HttpServletRequest request;
    // http请求
    private HttpServletResponse response;
    // 是否校验图标验证码
    private boolean validateCodeFlag = true;

}
