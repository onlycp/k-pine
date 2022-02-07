package com.kingsware.kdev.core.kmq.websocket;

/**
 * 将websocket消息转为mq内部消息
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 5:09 下午
 */
public class Wm2MqMessage {
    /** 用户token **/
    private String token;
    /** 用户id **/
    private String userId;
    /** 原始的websocket消息 **/
    private String message;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
