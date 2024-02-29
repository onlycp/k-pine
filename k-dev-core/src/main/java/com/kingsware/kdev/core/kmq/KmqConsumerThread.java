//package com.kingsware.kdev.core.kmq;
//
//import com.kingsware.kdev.core.util.ThreadUtils;
//import lombok.extern.slf4j.Slf4j;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Set;
//import java.util.concurrent.LinkedBlockingQueue;
//
///**
// * 消息消费线程
// *
// * @author chen peng
// * @version 1.0.0
// * @date 2021/12/17 5:19 下午
// */
//@Slf4j
//public class KmqConsumerThread implements Runnable {
//    /** 主题 **/
//    private final String topic;
//    /** 队列 **/
//    private final LinkedBlockingQueue<String> queue;
//    /** 所有消费者 **/
//    private final Set<KmqConsumer> consumers;
//
//    /**
//     * 构造函数
//     * @param topic 主题
//     * @param queue 队列
//     */
//    public KmqConsumerThread(String topic, LinkedBlockingQueue<String> queue, Set<KmqConsumer> consumers) {
//        this.topic = topic;
//        this.queue = queue;
//        this.consumers = consumers;
//    }
//
//    public LinkedBlockingQueue<String> getQueue() {
//        return queue;
//    }
//
//    @Override
//    public void run() {
//        // 在任务内部可以使用 taskId 或其他信息设置线程的具体名称
//        String threadName = String.format("K-Queue-Thread-[%s]-C[%d]", topic, consumers.size()) ;
//        Thread.currentThread().setName(threadName);
//        // 循环处理消息
//        while (true) {
//            try {
//                List<String> payloads = drainQueue(20);
//                if (payloads.size() > 0) {
//                    log.info("消息出列, Topic:{}, 队列可用余量:{}", topic, queue.remainingCapacity());
//
//
//                }
//                ThreadUtils.sleep(5);
//            }
//            catch (Exception e) {
//                log.error("消息消费线程异常", e);
//            }
//        }
//    }
//
//    public List<String> drainQueue(int batchSize) throws InterruptedException {
//        List<String> payloads = new ArrayList<>();
//        for (int i = 0; i < batchSize; i++) {
//            String payload = queue.take();
//            payloads.add(payload);
//            if (queue.isEmpty()) {
//                break;
//            }
//        }
//        return payloads;
//    }
//}
