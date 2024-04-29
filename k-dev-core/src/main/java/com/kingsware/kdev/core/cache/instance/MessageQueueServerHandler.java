package com.kingsware.kdev.core.cache.instance;

import com.kingsware.kdev.core.util.JsonUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.Jar;

import java.net.InetSocketAddress;

/**
 * @author chenp
 * @date 2024/4/24
 */
@Slf4j
public class MessageQueueServerHandler extends SimpleChannelInboundHandler<MessageItem> {

    private final MessageQueueServer server;

    public MessageQueueServerHandler(MessageQueueServer server) {
        this.server = server;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageItem messageItem) throws Exception {
        // 心跳检测
        if ("ping".equalsIgnoreCase(messageItem.getTopic())) {
            MessageItem pong = new MessageItem();
            pong.setTopic("ping");
            pong.setData("pong");
            channelHandlerContext.writeAndFlush(pong);
        }
        else {
            // 接收消息，然后转发给所有的客户端
            for (ChannelHandlerContext ctx : server.getMyClientContexts()) {
                ctx.writeAndFlush(messageItem);
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        InetSocketAddress remoteAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        String remoteIp = remoteAddress.getAddress().getHostAddress();
        int remotePort = remoteAddress.getPort();
        log.info("队列客户端异常关闭:{},{},{}", remoteIp, remotePort, ctx.channel().id());
        ctx.close();
        server.removeClientContext(ctx);
    }

    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        InetSocketAddress remoteAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        String remoteIp = remoteAddress.getAddress().getHostAddress();
        int remotePort = remoteAddress.getPort();
        log.info("队列客户端连接:{},{},{}", remoteIp, remotePort, ctx.channel().id());
        server.addClientContext(ctx);
    }

    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //log.info("channelInactive");
        InetSocketAddress remoteAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        String remoteIp = remoteAddress.getAddress().getHostAddress();
        int remotePort = remoteAddress.getPort();
        log.info("队列客户端断开:{},{},{}", remoteIp, remotePort, ctx.channel().id());
        server.removeClientContext(ctx);
    }
}
