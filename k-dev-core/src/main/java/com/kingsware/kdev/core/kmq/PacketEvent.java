package com.kingsware.kdev.core.kmq;

import lombok.Data;

/**
 * 消息事件类
 */
@Data
public class PacketEvent {

    /**
     * 事件消息
     */
    private String message;
    private long timestamp;

}
