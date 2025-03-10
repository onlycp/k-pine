package com.kingsware.kdev.core.kmq;

import com.kingsware.kdev.core.orm.kdb.CustomThreadFactory;
import com.kingsware.kdev.core.util.StringUtils;
import com.lmax.disruptor.EventHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author chenp
 * @date 2024/2/29
 */
@Slf4j
public class PacketEventHandler implements EventHandler<PacketEvent> {

    private final String topic;
    private final Set<KmqConsumer> consumers;
    private final List<String> payloads = new ArrayList<>();
    private final ScheduledExecutorService scheduler;
    private final int batchSize = 10; // 批量大小
    private final long timeoutMs = 3_000; // 10秒超时
    private long lastProcessedTime = System.currentTimeMillis();
    private final AtomicBoolean isProcessing = new AtomicBoolean(false);

    public PacketEventHandler(String topic, Set<KmqConsumer> consumers) {
        this.topic = topic;
        this.consumers = consumers;
        this.scheduler = Executors.newSingleThreadScheduledExecutor(
                r -> new Thread(r, "PacketEventHandler-Timer-" + topic)
        );
        // 启动定时任务，每秒检查超时
        scheduler.scheduleAtFixedRate(this::checkTimeout, 1, 1, TimeUnit.SECONDS);
    }

    @Override
    public void onEvent(PacketEvent packetEvent, long sequence, boolean endOfBatch) {
        try {
            synchronized (payloads) {
                payloads.add(packetEvent.getMessage());

                // 检查是否达到批量大小
                if (payloads.size() >= batchSize) {
                    processBatch();
                }
            }
        } catch (Exception e) {
            log.error("Error processing event, topic={}, message={}", topic, packetEvent.getMessage(), e);
        }
    }

    private void checkTimeout() {
        synchronized (payloads) {
            long currentTime = System.currentTimeMillis();
            if (!payloads.isEmpty() && (currentTime - lastProcessedTime >= timeoutMs)) {
                processBatch();
            }
        }
    }

    private void processBatch() {
        if (isProcessing.get() || payloads.isEmpty()) {
            return;
        }

        if (isProcessing.compareAndSet(false, true)) {
            try {
                List<String> batchToProcess;
                synchronized (payloads) {
                    batchToProcess = new ArrayList<>(payloads);
                    payloads.clear();
                    lastProcessedTime = System.currentTimeMillis();
                }
                log.info("Processing batch of {} messages, topic={}", batchToProcess.size(), topic);

                for (KmqConsumer consumer : consumers) {
                    try {
                        consumer.onMessage(batchToProcess);
                    } catch (Exception e) {
                        log.warn("Consumer {} failed to process batch, topic={}, error={}",
                                consumer.topic(), topic, e.getMessage());
                    }
                }
            } catch (Exception e) {
                log.error("Error processing batch, topic={}", topic, e);
            } finally {
                isProcessing.set(false);
            }
        }
    }

    public void shutdown() {
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
            Thread.currentThread().interrupt();
        }
        // 处理剩余消息
        synchronized (payloads) {
            if (!payloads.isEmpty()) {
                processBatch();
            }
        }
    }
}