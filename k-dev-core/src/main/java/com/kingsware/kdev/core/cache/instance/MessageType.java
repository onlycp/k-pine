package com.kingsware.kdev.core.cache.instance;

/**
 * 消息类型常量枚举
 * 该枚举定义了系统中用到的几种消息类型
 * @author chenp
 * @date 2024/4/24
 */
public enum MessageType {
//    HEART_BEAT, // 心跳消息，用于保持连接 alive
    PRODUCE,    // 生产者消息，标识消息生产操作
    CONSUMER    // 消费者消息，标识消息消费操作
}
