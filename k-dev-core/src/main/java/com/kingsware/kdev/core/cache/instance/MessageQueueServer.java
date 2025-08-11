package com.kingsware.kdev.core.cache.instance;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.HashSet;
import java.util.Set;

/**
 * @author chenp
 * @date 2024/4/24
 */
public class MessageQueueServer extends Thread {


    /** 端口 */
    private final int port;
    private EventLoopGroup bossGroup = new NioEventLoopGroup();
    private EventLoopGroup workerGroup = new NioEventLoopGroup();
    private Set<ChannelHandlerContext> myClientContexts = new HashSet<>();




    public MessageQueueServer(int port) {
        this.port = port;
    }

    public Set<ChannelHandlerContext> getMyClientContexts() {
        return myClientContexts;
    }

    public void run() {
        final MessageQueueServer myThis = this;
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_RCVBUF, 1024*8)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new MessageEncoder());
                            ch.pipeline().addLast(new MessageDecoder());
                            ch.pipeline().addLast(new MessageQueueServerHandler(myThis));
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind(port).sync();

            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully shut down your server.
            f.channel().closeFuture().sync();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
//            workerGroup.shutdownGracefully();
//            bossGroup.shutdownGracefully();
        }
    }

    public void addClientContext(ChannelHandlerContext ctx) {
        myClientContexts.add(ctx);
    }

    public void removeClientContext(ChannelHandlerContext ctx) {
        myClientContexts.remove(ctx);
    }

    // 添加一个停止服务器的方法
    public void close() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}
