package com.kingsware.kdev.core.kmq.websocket;

import jakarta.websocket.Session;

import java.util.Objects;

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
    /** 心跳时间 **/
    private long heartTime;

    public long getHeartTime() {
        return heartTime;
    }

    public void setHeartTime(long heartTime) {
        this.heartTime = heartTime;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SessionToken that = (SessionToken) o;

        if (!session.equals(that.session)) return false;
        if (!userId.equals(that.userId)) return false;
        return token.equals(that.token);
    }

    @Override
    public int hashCode() {
        int result = session.hashCode();
        result = 31 * result + userId.hashCode();
        result = 31 * result + token.hashCode();
        return result;
    }
}
