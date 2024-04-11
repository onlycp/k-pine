package com.kingsware.kdev.core.kflow.tcp;

import com.kingsware.kdev.core.util.JsonUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author chenp
 * @date 2024/4/7
 */
@Slf4j
public class SimpleClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private final TcpClient client;

    public SimpleClientHandler(TcpClient client) {
        this.client = client;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        // 我活着
        client.iActive();
        // 处理接收到的消息
        byte[] bytes = new byte[msg.readableBytes()];
        msg.readBytes(bytes, 0, bytes.length);
        //log.info("tcp <= {}", ProtocolHelper.getPrintString(bytes, bytes.length) );
        if (bytes.length < 7) {
            return;
        }
        //处理消息
        TRspMessage resp = TRspMessage.parseFrom(bytes);
        if (resp != null) {
            // 响应数据
            TMessage tMessage = new TMessage();
            tMessage.setClientId(client.getId());
            tMessage.setBody(JsonUtil.toJson(resp));
            client.read(tMessage);
        }

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        client.setChannelHandlerContext(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("tcp channelInactive");
        client.disConnect();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("tcp exceptionCaught", cause);
        client.disConnect();
        // ctx.close();
    }
}
