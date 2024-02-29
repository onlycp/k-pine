package com.kingsware.kdev.core.kmq;
import com.lmax.disruptor.EventFactory;

/**
 * @author chenp
 * @date 2024/2/29
 */
public class PacketEventFactory implements EventFactory<PacketEvent> {
    @Override
    public PacketEvent newInstance() {
        return new PacketEvent();
    }
}
