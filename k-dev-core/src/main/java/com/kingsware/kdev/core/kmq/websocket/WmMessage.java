package com.kingsware.kdev.core.kmq.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Websocket消息
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 4:24 下午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WmMessage {
    /**
     * topic
     **/
    private String topic;
    /**
     * 消息体
     **/
    private String body;
}
