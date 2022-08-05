package com.kingsware.kdev.core.kflow.tcp;

import lombok.Data;

/**
 * 会话内容
 */
@Data
public class ExchangeMessage {
    /** 消息内容 **/
    private String type;
    /** 会话id **/
    private String sessionId;
    /** 消息内容 **/
    private String body;
}
