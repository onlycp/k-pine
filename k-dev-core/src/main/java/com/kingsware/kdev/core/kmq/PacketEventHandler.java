package com.kingsware.kdev.core.kmq;


import com.kingsware.kdev.core.orm.kdb.CustomThreadFactory;
import com.kingsware.kdev.core.util.StringUtils;
import com.lmax.disruptor.EventHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

/**
 * @author chenp
 * @date 2024/2/29
 */
@Slf4j
public class PacketEventHandler implements EventHandler<PacketEvent> {

    private final String topic;

    private final Set<KmqConsumer> consumers;

    private final static ExecutorService executor = Executors.newFixedThreadPool(10, new CustomThreadFactory("PacketEventHandler"));


    public PacketEventHandler(String topic, Set<KmqConsumer> consumers) {
        this.consumers = consumers;
        this.topic = topic;
    }

    @Override
    public void onEvent(PacketEvent packetEvent, long sequence, boolean b) throws Exception {
        try {
//            log.info("T-消费者: {} 消费成功，消息内容:{}", topic, packetEvent.getMessage());
            for (KmqConsumer consumer : consumers) {
                try {
                    List<String> payloads = new ArrayList<>();
                    payloads.add(packetEvent.getMessage());
                    consumer.onMessage(payloads);
                }
                catch (Exception e) {
                    log.warn("消费者: {} 消费失败，消息内容:{}, 异常信息:{}", consumer.topic(), packetEvent.getMessage(), e.getMessage());
                }
            }

        }
        catch (Exception e) {
            log.error("error", e);
            log.error("T-消费者: {} 消费失败，消息内容:{}, 异常信息:{}", topic, packetEvent.getMessage(), e.getMessage());
        }

    }


}
