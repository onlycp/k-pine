package com.kingsware.kdev.core.kmq.websocket;

/**
 * Websocket消息
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 4:24 下午
 */
public class WmMessage {
    /** topic **/
    private String topic;
    /** 消息类型 **/
    private String type;
    /** 消息体 **/
    private String body;
    /** 是否需要ack 1: 需要，0：不需要**/
    private int ack;

    public WmMessage() {
    }

    public WmMessage(String topic, String type, String body, int ack) {
        this.topic = topic;
        this.type = type;
        this.body = body;
        this.ack = ack;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getAck() {
        return ack;
    }

    public void setAck(int ack) {
        this.ack = ack;
    }
}
