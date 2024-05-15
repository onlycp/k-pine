package com.kingsware.kdev.core.cache.instance;

import com.kingsware.kdev.core.util.JsonUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenp
 * @date 2024/4/24
 */
@Slf4j
public class MessageDecoder extends MessageToMessageDecoder<ByteBuf> {


    private ByteBuf cacheBuf = null;
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        try {
            if (cacheBuf == null) {
                cacheBuf = channelHandlerContext.alloc().directBuffer();
            }
            if (byteBuf.readableBytes() > 0) {
                cacheBuf.writeBytes(byteBuf);
            }
            // 从cacheBuf读取内容，起始符是0x23,0x23,0x23
            List<MessageBytes> messageBytesList = new ArrayList<>();
            parse(cacheBuf, messageBytesList);

            for (MessageBytes messageBytes : messageBytesList) {
                String content = JsonUtil.decompressJSON((messageBytes.getData()));
                MessageItem message = JsonUtil.toBean(content, MessageItem.class);
                list.add(message);
            }

        }
        catch (Exception e) {
            log.error("error",e );
            throw e;
        }

    }

    public void parse(ByteBuf cacheBuf, List<MessageBytes> list) {
        if (cacheBuf.readableBytes() < 6) {
            return;
        }
        cacheBuf.markReaderIndex();
        boolean findStart = false;
        while (cacheBuf.readableBytes() > 6) {
            byte b = cacheBuf.readByte();
            if (b == 0x23) {
                b = cacheBuf.readByte();
                if (b == 0x23) {
                    b = cacheBuf.readByte();
                    if (b == 0x23) {
                        findStart = true;
                        break;
                    }
                }
            }
        }
        // 读取长度
        if (!findStart) {
            cacheBuf.resetReaderIndex();
            return;
        }
        int length = cacheBuf.readInt();
        if (cacheBuf.readableBytes() < length) {
            cacheBuf.resetReaderIndex();
            return;
        }
        byte[] data = new byte[length];
        cacheBuf.readBytes(data, 0, length);
        MessageBytes messageBytes = new MessageBytes();
        messageBytes.setLen(length);
        messageBytes.setData(data);
        list.add(messageBytes);
        if (cacheBuf.readableBytes() > 6) {
            parse(cacheBuf, list);
        }
        if (cacheBuf.readableBytes() == 0) {
            cacheBuf.clear();
        }
    }
}
