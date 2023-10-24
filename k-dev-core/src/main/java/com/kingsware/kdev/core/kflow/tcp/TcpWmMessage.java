package com.kingsware.kdev.core.kflow.tcp;

import lombok.Data;

/**
 * @author chenp
 * @date 2023/10/18
 */
@Data
public class TcpWmMessage {

    /**
     * topic
     **/
    private String topic;
    /**
     * 消息体
     **/
    private String body;

    /** 接送人 **/
    private String token;

    /** 接收用户id **/
    private String userId;
}
