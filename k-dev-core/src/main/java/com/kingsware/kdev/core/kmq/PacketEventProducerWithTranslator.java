package com.kingsware.kdev.core.kmq;

import com.kingsware.kdev.core.util.JsonUtil;
import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

/**
 * @author chenp
 * @date 2024/2/29
 */
public class PacketEventProducerWithTranslator {
    // 缓冲区
    private RingBuffer<PacketEvent> ringBuffer;

    // 事件翻译器
    private static EventTranslatorOneArg<PacketEvent, ByteBuffer> TRANSLATOR = (packetEvent, l, byteBuffer) -> {
        String msg = ByteBufferUtil.getString(byteBuffer);
        PacketEvent tmp = JsonUtil.toBean(msg, PacketEvent.class);;
        packetEvent.setMessage(tmp.getMessage());
        packetEvent.setTimestamp(tmp.getTimestamp());
    };

    // 构造函数
    public PacketEventProducerWithTranslator(RingBuffer<PacketEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    /**
     * 将数据放入缓存区
     * @param buffer 缓存区
     */
    private void onData(ByteBuffer buffer) {
        // 将事件发布到缓冲区
        ringBuffer.publishEvent(TRANSLATOR, buffer);
    }

    /**
     * 发送数据
     * @param[obj] 对象
     */
    public void send(Object obj) {
        String data = JsonUtil.toJson(obj);
        ByteBuffer buffer = ByteBufferUtil.getByteBuffer(data);
        onData(buffer);
    }



}
