package com.kingsware.kdev.core.kmq;

import com.kingsware.kdev.core.util.ThreadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 消息消费线程
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/17 5:19 下午
 */
public class KmqConsumerThread implements Runnable {
    /** 日志打印 **/
    private final Logger logger  = LoggerFactory.getLogger(KmqConsumerThread.class);
    /** 主题 **/
    private final String topic;
    /** 队列 **/
    private final LinkedBlockingQueue<String> queue;
    /** 所有消费者 **/
    private final Set<KmqConsumer> consumers;

    /**
     * 构造函数
     * @param topic 主题
     * @param queue 队列
     */
    public KmqConsumerThread(String topic, LinkedBlockingQueue<String> queue, Set<KmqConsumer> consumers) {
        this.topic = topic;
        this.queue = queue;
        this.consumers = consumers;
    }

    @Override
    public void run() {
        // 循环处理消息
        while (true) {
            try {
//                String payload = queue.take();
                List<String> payloads = new ArrayList<>();
                queue.drainTo(payloads, 50);
                if (!payloads.isEmpty()) {
                    for (KmqConsumer consumer: consumers) {
                        try {
                            consumer.onMessage(payloads);
                        }
                        catch (Exception e) {
//                            e.printStackTrace();
//                            logger.warn("消费者: {} 消费失败，消息内容:{}, 异常信息:{}", consumer.topic(), payloads, e.getMessage());
//                        queue.add(payload);
                        }
                    }
                }


            } finally {
                ThreadUtils.sleep(5);
            }
        }
    }
}
