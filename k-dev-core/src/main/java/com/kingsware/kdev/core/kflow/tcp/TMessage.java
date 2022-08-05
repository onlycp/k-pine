package com.kingsware.kdev.core.kflow.tcp;

import lombok.Data;

/**
 * 消息
 */
@Data
public class TMessage {
    /** 消息体 **/
    private String body;
    /** 客户端id **/
    private String clientId;
}
