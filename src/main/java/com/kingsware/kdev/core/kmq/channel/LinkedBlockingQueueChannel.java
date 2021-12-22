package com.kingsware.kdev.core.kmq.channel;

import com.kingsware.kdev.core.kmq.KmqConsumerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * LinkedBlockingQueue实现的通道
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/22 5:51 下午
 */
public class LinkedBlockingQueueChannel implements MqChannel{
    /** 日志打印 **/
    private final Logger logger  = LoggerFactory.getLogger(LinkedBlockingQueueChannel.class);
    /** 消息队列最大数 **/
    private static final int QUEUE_MAX_SIZE = 10000;
    /** 队列 **/
    private final LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(QUEUE_MAX_SIZE);

    @Override
    public void write(String payload) throws Exception {
        try {
            queue.put(payload);
        } catch (InterruptedException e) {
            logger.error("消息入通道失败：{}", e.getLocalizedMessage());
            throw e;
        }
    }

    @Override
    public String read() throws Exception {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            logger.error("消息出通道失败：{}", e.getLocalizedMessage());
            throw e;
        }
    }
}
