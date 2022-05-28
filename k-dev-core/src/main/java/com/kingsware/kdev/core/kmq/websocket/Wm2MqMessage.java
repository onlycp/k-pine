package com.kingsware.kdev.core.kmq.websocket;

import lombok.Data;

/**
 * 将websocket消息转为mq内部消息
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 5:09 下午
 */
@Data
public class Wm2MqMessage {
    /**
     * 用户token
     **/
    private String token;
    /** 消息 **/
    private WmMessage wmMessage;
}

