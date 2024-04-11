package com.kingsware.kdev.core.kflow.tcp;

import com.kingsware.kdev.core.util.StringUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * tcp客户端
 */
@Slf4j
public class TcpClient {

    /** 缓冲池大小 **/
    private static final int MAX_MSG_LEN = 1024 * 1024 * 255 + 100;

    /** 客户端id **/
    private final TcpClientContext context;
    /** 客户端id **/
    private final String id;
    private String ip;
    private int port;
    /** 接收字节 **/
    private long heartTime = new Date().getTime();

    private NioEventLoopGroup workerGroup = new NioEventLoopGroup();
    private ChannelHandlerContext channelHandlerContext;
    private SocketHeadType socketHeadType;
    private Bootstrap b = new Bootstrap();

    private static final AtomicInteger CONNECT_COUNT = new AtomicInteger(0);


    public TcpClient(String ip, int port, boolean autoConnect, TcpClientContext context, SocketHeadType socketHeadType) {
        this.id = CONNECT_COUNT.getAndIncrement() + "";
        /** ip **/
        this.ip = ip;
        /** 端口 **/
        this.port = port;
        this.context = context;
        this.socketHeadType = socketHeadType;
        final TcpClient myThis = this;
        new Thread(() -> {
            try {

                b.group(workerGroup);
                b.channel(NioSocketChannel.class);
                b.option(ChannelOption.SO_KEEPALIVE, true);
                b.handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, Delimiters.lineDelimiter())); // 设置分隔符
                        ch.pipeline().addLast(new SimpleClientHandler(myThis));
                    }
                });
                // 等待连接关闭
                ChannelFuture f = connect(b);
                if (f.isSuccess()) {
                    log.info("客户端启动成功");
                }
            }
            catch (Exception e) {
                log.error("客户端启动失败", e);
            }
            finally {
                //workerGroup.shutdownGracefully();
            }
        }).start();
    }

    private ChannelFuture connect(Bootstrap b) throws InterruptedException {
        ChannelFuture f = b.connect(ip, port).addListener((GenericFutureListener<ChannelFuture>) future -> {
            if (!future.isSuccess()) {
                log.warn("连接失败，进行重连操作...");
                System.err.println("连接失败，进行重连操作...");
                future.channel().eventLoop().schedule(() -> connect(b), 5, TimeUnit.SECONDS);
            }
            else {
                log.info("TCP连接成功，ip:{}, port:{}", ip, port);
            }
        }).sync();
        f.channel().closeFuture().sync();
        return f;
    }

    public void iActive() {
        this.heartTime = new Date().getTime();
    }

    public TcpClientContext getContext() {
        return this.context;
    }

    public void setChannelHandlerContext(ChannelHandlerContext channelHandlerContext) {
        this.channelHandlerContext = channelHandlerContext;
        this.heartTime = new Date().getTime();
        // 发送注册
        TReqMessage message = new TReqMessage(socketHeadType, "", "");
        send(message);
    }


    public void heart() {
        if (isConnected()) {
            TReqMessage message = new TReqMessage(SocketHeadType.HEART_REQUEST, "", "");
            send(message);
        }
        else {
            try {
                connect(b);
            }
            catch (InterruptedException ignored) {
            }
        }

    }

    public void read(TMessage msg) {
//        this.heartTime = new Date().getTime();
        this.context.read(msg);
    }


    public String getId() {
        return id;
    }

    /**
     * 发送消息
     * @param msg 消息内容
     */
    public void send(TReqMessage msg) {
        try {

            byte[] bodyBytes = msg.toByteArray();
            int len = bodyBytes.length;
            byte[] sendData = new byte[len + 2];
            System.arraycopy(bodyBytes, 0, sendData, 0, len);
            sendData[len] = 0x0d;
            sendData[len + 1] = 0x0a;
            this.channelHandlerContext.writeAndFlush(channelHandlerContext.alloc().buffer().writeBytes(sendData));
            //log.info("tcp => {}", ProtocolHelper.getPrintString(sendData, sendData.length));
        }
        catch (Exception e) {
            log.error("消息发送失败:" + e.getMessage());
        }
    }

    /**
     * 断开连接
     */
    public void disConnect() {
        if (this.channelHandlerContext != null) {
            this.channelHandlerContext.close();
            //this.channelHandlerContext = null;
        }
     }

    /**
     * 是否连接状态
     * @return
     */
     public boolean isConnected() {
        if (channelHandlerContext == null) {
            return false;
        }
        if (channelHandlerContext.isRemoved()) {
            return false;
        }
        if((new Date().getTime() - this.heartTime) > 1000*10) {
            log.info("心跳超时，连接自动断开:{}", this.id);
            return false;
        }
         return true;
     }
}
