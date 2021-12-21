package com.kingsware.kdev.core.context;

import com.kingsware.kdev.core.auth.BaseUserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 客户端信息
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/21 10:12 上午
 */
public class ClientInfo {

    /** 身份标识，token **/
    private String token;
    /** 客户端ip **/
    private String ip;
    /** 当前登录用户信息 **/
    private BaseUserInfo userInfo;
    // http请求
    private HttpServletRequest request;
    // http请求
    private HttpServletResponse response;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public BaseUserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(BaseUserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }
}
