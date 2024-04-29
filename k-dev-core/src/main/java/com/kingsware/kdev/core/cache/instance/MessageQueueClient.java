package com.kingsware.kdev.core.cache.instance;

import com.kingsware.kdev.core.util.ThreadUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author chenp
 * @date 2024/4/24
 */
@Slf4j
public class MessageQueueClient extends Thread {

    private final String host;
    private final int port;

    private EventLoopGroup group;
    private Bootstrap bootstrap;
    private Channel channel;

    private long heartTime = new Date().getTime();

    private ChannelHandlerContext channelHandlerContext;

    public MessageQueueClient(String host, int port) {
        this.host = host;
        this.port = port;
        this.group = new NioEventLoopGroup();
        this.bootstrap = new Bootstrap();
        MessageQueueClient myThis = this;
        this.bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ch.pipeline().addLast(new MessageEncoder());
                        ch.pipeline().addLast(new MessageDecoder());
                        ch.pipeline().addLast(new MessageQueueClientHandler(myThis));
                    }
                });
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public ChannelHandlerContext getChannelHandlerContext() {
        return channelHandlerContext;
    }

    public void setChannelHandlerContext(ChannelHandlerContext channelHandlerContext) {
        this.channelHandlerContext = channelHandlerContext;
    }

    public void run() {
        while (true) {
            if (!isConnected()) {
                try {
                    if (channelHandlerContext != null) {
                        channelHandlerContext.close();
                    }
                    // 连接服务器
                    ChannelFuture f = bootstrap.connect(host, port).sync();
                    channel = f.channel();
                    // 等待连接关闭
                    f.channel().closeFuture().sync();
                } catch (InterruptedException ignored) {

                }
            }
            ThreadUtils.sleep(1000);
        }
    }

    public boolean isConnected() {
        return channelHandlerContext != null  && (new Date().getTime() - heartTime < 10000);
    }

    /**
     * 发送消息。
     * 该方法通过提供的channelHandlerContext将消息写入并刷新到连接的另一端。
     * 如果channelHandlerContext为null，则不执行任何操作。
     *
     * @param msg 要发送的消息项。
     */
    public void send(MessageItem msg) {
        // 判断channelHandlerContext是否已设置，若已设置则发送消息
        if (channelHandlerContext != null) {
            channel.writeAndFlush(msg);
        }
    }


    // 添加处理连接断开的方法

    public void heart() {

        if (channelHandlerContext != null) {
            MessageItem msg = new MessageItem();
            msg.setTopic("ping");
            msg.setData("heart");
            this.send(msg);
        }

    }

    public void iActive() {
        heartTime = new Date().getTime();
    }



    // 添加一个停止客户端的方法
    public void close() {
        group.shutdownGracefully();
    }
}
