package com.kingsware.kdev.core.kmq.websocket;

import javax.websocket.Session;

/**
 * Websocket的session实体
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 3:42 下午
 */
public class SessionToken {
    /** socket会话 **/
    private Session session;
    /** 用户id **/
    private String userId;
    /** 令牌 **/
    private String token;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
