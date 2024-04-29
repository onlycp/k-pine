package com.kingsware.kdev.core.cache.instance;

import com.kingsware.kdev.core.context.SpringContext;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

public class MessageQueueClientHandler extends SimpleChannelInboundHandler<MessageItem> {

    private final MessageQueueClient client;

    public MessageQueueClientHandler(MessageQueueClient client) {
        this.client = client;
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        client.setChannelHandlerContext(ctx);
        // 连接建立时调用此方法
        // 在这里发送消息到服务器
        MessageItem item = new MessageItem();
        item.setData("hello");
        item.setTopic("xxx");
        ctx.writeAndFlush(item);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 当发生异常时调用此方法
        client.setChannelHandlerContext(null);
        ctx.close();
    }

    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        client.setChannelHandlerContext(null);
    }



    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageItem messageItem) throws Exception {
        // 设置心跳时间
        client.iActive();
        // 接收到消息时调用此方法
        InstanceService instanceService = SpringContext.getBean(InstanceService.class);
        instanceService.recvMessage(messageItem.getTopic(), messageItem.getData());
    }
}
