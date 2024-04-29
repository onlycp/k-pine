package com.kingsware.kdev.core.cache.instance;

import com.kingsware.kdev.core.util.JsonUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * @author chenp
 * @date 2024/4/24
 */
public class MessageDecoder extends MessageToMessageDecoder<ByteBuf> {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        // 解码逻辑：从ByteBuf中读取内容并构造MyMessage对象
        int length = byteBuf.readInt();
        byte[] contentBytes = new byte[length];
        byteBuf.readBytes(contentBytes);
        String content = JsonUtil.decompressJSON(contentBytes);
        MessageItem message = JsonUtil.toBean(content, MessageItem.class);
        list.add(message);
    }
}
