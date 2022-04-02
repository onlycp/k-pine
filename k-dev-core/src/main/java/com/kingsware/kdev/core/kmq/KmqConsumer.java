package com.kingsware.kdev.core.kmq;

import java.util.List;

/**
 * 消费者接口
 * 示例:
 * <pre>
 *    public claas MyConsumer implements KConsumer {
 *
 *    }
 * </pre>
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/17 3:04 下午
 */
public interface KmqConsumer {

    /**
     * 消费消息
     * @param payload       消息
     * @throws Exception    业务消费失败时，将放到失败队列文件日志
     */
    void onMessage(List<String> payload) throws Exception;

    /**
     * 获取主题
     * @return  队列名
     */
    String topic();
}
