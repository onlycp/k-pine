package com.kingsware.kdev.core.kmq;

import com.kingsware.kdev.core.context.KClientContext;
import com.kingsware.kdev.core.kmq.websocket.WmMessage;
import com.kingsware.kdev.core.kmq.websocket.WmMessageArgv;
import com.kingsware.kdev.core.util.ClassUtils;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.core.util.StringUtils;
import com.lmax.disruptor.dsl.Disruptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import static com.kingsware.kdev.core.kmq.websocket.WebsocketConstants.MQ_TO_WEBSOCKET;

/**
 * KMQ手消息队列仓库存储
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/17 3:46 下午
 */
public class KmqMessageCenter {

    /** 日志打印 **/
    private static final Logger logger  = LoggerFactory.getLogger(KmqMessageCenter.class);
    /** 私有实例 **/
    private static KmqMessageCenter messageCenter;

    private Map<String, PacketEventProducerWithTranslator> packetEventProducerWithTranslatorMap = new HashMap<>();

    /**
     * 私有构造
     */
    private KmqMessageCenter() {}

    /**
     * 获取实例
     * @return  返回单实例对象
     */
    public static KmqMessageCenter getInstance() {
        // 如果对象为空，需要对storage进行实例
        if (Objects.isNull(messageCenter)) {
            synchronized (KmqMessageCenter.class) {
                messageCenter = new KmqMessageCenter();
                messageCenter.initDisruptor();
            }
        }
        return messageCenter;
    }

    public void initDisruptor() {

        Map<String, Set<KmqConsumer>> consumers = new HashMap<>();
        // 查找所有消费类
        List<Class<?>> classList =  ClassUtils.getClassesByParentClass("com.kingsware.kdev", KmqConsumer.class);
        // 实例化消费实例并放到消费者的map
        for (Class<?> clazz: classList) {
            if (clazz.isInterface()) {
                continue;
            }
            try {
                KmqConsumer consumer = (KmqConsumer)clazz.newInstance();
                Set<KmqConsumer> consumerSet = consumers.computeIfAbsent(consumer.topic(), key -> new HashSet<>());
                consumerSet.add(consumer);
            }
            catch (InstantiationException | IllegalAccessException e) {
                logger.warn("实例化消费者失败，类名: {}", clazz.getName());
            }
        }

        // 启动线程
        for (Map.Entry<String, Set<KmqConsumer>> entry: consumers.entrySet()) {
            // 创建线程池
            ExecutorService executorService = Executors.newCachedThreadPool();
            //创建事件工厂
            PacketEventFactory eventFactory = new PacketEventFactory();
            // RingBuffer 大小，必须是 2 的 N 次方
            int ringBufferSize = 1024 * 1024;
            // Construct the Disruptor
            Disruptor<PacketEvent> disruptor  = new Disruptor<>(eventFactory, ringBufferSize, executorService);
            disruptor.handleEventsWith(new PacketEventHandler(entry.getKey(), entry.getValue()));
            //启动disruptor，启动所有线程
            disruptor.start();
            PacketEventProducerWithTranslator packetEventProducerWithTranslator = new PacketEventProducerWithTranslator(disruptor.getRingBuffer());
            packetEventProducerWithTranslatorMap.put(entry.getKey(), packetEventProducerWithTranslator);
        }



    }

    /**
     *  生产消息
     * @param topic     主题
     * @param payload   消息体
     */
    public void produce(String topic, String payload) {
        produce(topic, Collections.singletonList(payload));
    }

    /**
     *  生产消息
     * @param topic     主题
     * @param payloads   消息体列表
     */
    public void produce(String topic, List<String> payloads) {
        // 如果没有消费者，直接丢弃
        if (!packetEventProducerWithTranslatorMap.containsKey(topic)) {
            return;
        }
        // 将消息加入队列中
        try {
            PacketEventProducerWithTranslator withTranslator = packetEventProducerWithTranslatorMap.get(topic);
            for(String payload: payloads) {
                PacketEvent event = new PacketEvent();
                event.setMessage(payload);
                event.setTimestamp(System.currentTimeMillis());
                withTranslator.send(event);
                //logger.info("Produce，topic:{},  message:{}", topic, StringUtils.retrench(payload, 100));
            }


        }
        catch (Exception e) {
            logger.error("生产消息失败，topic: {}", topic, e);
        }

    }

    /**
     * 发送消息给当前会话
     * @param topic
     * @param message
     */
    public void produceWebsocketMessageToSession(String topic, String message) {

        WmMessage wmMessage = new WmMessage();
        wmMessage.setTopic(topic);
        wmMessage.setBody(message);
        WmMessageArgv argv = new WmMessageArgv();
        argv.setToken(KClientContext.getContext().getToken());
        argv.setMessage(JsonUtil.toJson(wmMessage));
        this.produce(MQ_TO_WEBSOCKET, JsonUtil.toJson(argv));

    }

    /**
     * 发送给用户
     * @param userId    用户id
     * @param topic     topic
     * @param message   消息内容
     */
    public void produceWebsocketMessageToUser(String userId, String topic, String message) {

        WmMessage wmMessage = new WmMessage();
        wmMessage.setTopic(topic);
        wmMessage.setBody(message);
        WmMessageArgv argv = new WmMessageArgv();
        argv.setUserId(userId);
        argv.setMessage(JsonUtil.toJson(wmMessage));
        this.produce(MQ_TO_WEBSOCKET, JsonUtil.toJson(argv));

    }



}
