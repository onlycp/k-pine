package com.kingsware.kdev.core.cache.instance;

/**
 * 实例
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2023/7/12 11:00
 */
public interface InstanceService {

    /**
     * 接收消息
     * @param topic     主题
     * @param message   消息
     */
    void recvMessage(String topic, String message);
}
