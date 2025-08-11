package com.kingsware.kdev.core.cache.instance;

import com.kingsware.kdev.core.util.JsonUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author chenp
 * @date 2024/4/24
 */
@Slf4j
public class MessageEncoder extends MessageToByteEncoder<MessageItem> {
    @Override
    protected void encode(ChannelHandlerContext ctx, MessageItem msg, ByteBuf out) throws Exception {

        try {
            // 编码逻辑：将MyMessage对象的内容转换为字节数组并写入ByteBuf
            String json = JsonUtil.toJson(msg);
            byte[] contentBytes =  JsonUtil.compressJSON(json);
            out.writeBytes(new byte[]{0x23, 0x23, 0x23});
            out.writeInt(contentBytes.length);
            out.writeBytes(contentBytes);
        }
        catch (Exception e) {
            log.error("error", e);
            throw e;
        }
    }
}
