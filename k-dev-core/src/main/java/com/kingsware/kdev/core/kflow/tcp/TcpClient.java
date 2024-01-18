package com.kingsware.kdev.core.kflow.tcp;

import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.core.util.ThreadUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Date;

/**
 * tcp客户端
 */
@Slf4j
public class TcpClient {

    /** 缓冲池大小 **/
    private static final int MAX_MSG_LEN = 1024 * 1024 * 255 + 100;

    /** ip **/
    private final String ip;
    /** 端口 **/
    private final int port;
    /** socket **/
    private Socket socket;
    /** 客户端id **/
    private final TcpClientContext context;
    /** 输出线程 **/
    private boolean working;
    /** 客户端id **/
    private final String id;
    /** 接收字节 **/
    private final ByteBuffer byteBuffer = ByteBuffer.allocate(MAX_MSG_LEN);
    /** 连接状态  **/
    private boolean connected = false;
    /** 心跳时间 **/
    private long heartTime = new Date().getTime();

    public TcpClient(String ip, int port, boolean autoConnect, TcpClientContext context, SocketHeadType socketHeadType) {
        this.id = StringUtils.getUUID();
        this.ip = ip;
        this.port = port;
        this.context = context;
        if (autoConnect) {
            new Thread(() -> {
                while (true) {
                    try {
                        if (!isConnected())  {
                            this.connect();
                            // 清理缓冲区
                            byteBuffer.clear();
                            // 发送注册
                            TReqMessage message = new TReqMessage(socketHeadType, "", "");
                            send(message);
                        }
                    }
                    catch (Exception ignored) {
                    }
                    finally {
                        try {
                            ThreadUtils.sleep(500);
                        }
                        catch (Exception ignored) {
                        }
                    }

                }
            }).start();
        }
        register();
    }


    public void heart() {
        if (socket.isConnected()) {
            TReqMessage message = new TReqMessage(SocketHeadType.HEART_REQUEST, "", "");
            send(message);
        }
    }

    public void register() {
        // 创建一个收消息
        working = true;
        new Thread(() -> {
            while (true) {
                if (socket != null && socket.isConnected()) {
                    try {
                        InputStream is = socket.getInputStream();
                        byte[] buf =new byte[4096];
                        int len = 0;
                        while ((len = is.read(buf) ) > 0) {
                            this.heartTime = new Date().getTime();
                            log.debug("tcp <= {}", ProtocolHelper.getPrintString(buf,len) );
                            for (int i = 0; i < len; i++) {
                                byteBuffer.put(buf[i]);
                                int pos = byteBuffer.position();
                                if(pos > 2 && byteBuffer.get(pos -2) == 0x0d && buf[i] == 0x0a ) {
                                    // 截取消息内容
                                    byte[] dst = new byte[pos-6];
                                    byteBuffer.position(4);
                                    byteBuffer.get(dst, 0, pos-6);
                                    // 清理缓冲区
                                    byteBuffer.clear();
                                    //处理消息
                                    TRspMessage resp = TRspMessage.parseFrom(dst);
                                    // 响应数据
                                    TMessage tMessage = new TMessage();
                                    tMessage.setClientId(id);
                                    tMessage.setBody(JsonUtil.toJson(resp));
                                    context.read(tMessage);
                                }
                            }
                        }

                    } catch (Exception e) {
                        try {
                            this.socket.close();
                        } catch (IOException ex) {
                            // throw new RuntimeException(ex);
                        }
                    }
                }
                ThreadUtils.sleep(500);
            }
        }).start();
    }

    public String getId() {
        return id;
    }

    /**
     * 发送消息
     * @param msg 消息内容
     */
    public void send(TReqMessage msg) {
        if (this.socket.isConnected()) {
            try {

                byte[] bodyBytes = msg.toByteArray();
                int len = bodyBytes.length;
                byte[] sendData =  new byte[len+2];
                System.arraycopy(bodyBytes, 0, sendData, 0, len);
                sendData[len] = 0x0d;
                sendData[len+1] = 0x0a;
                //写消息体
                this.socket.getOutputStream().write(sendData);
                log.debug("tcp => {}", ProtocolHelper.getPrintString(sendData, sendData.length) );
                // 写入换行
//                this.socket.getOutputStream().write(new byte[] {0x0d, 0x0a});
                this.socket.getOutputStream().flush();
            }
            catch (Exception e) {
                log.error("消息发送失败:" + e.getMessage());
            }

        }
    }

    /**
     * 断开连接
     */
    public void disConnect() {
        if (this.socket.isConnected()) {
            try {
                this.working = false;
                this.socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
     }

    /**
     * 是否连接状态
     * @return
     */
     public boolean isConnected() {
        if (socket == null) {
            return false;
        }
//         log.info("socket.isConnected() = {}, heart:{}", socket.isConnected(), (new Date().getTime() - this.heartTime));
         if (!socket.isConnected()) {
             return false;
         }
         if((new Date().getTime() - this.heartTime) > 1000*10) {
            disConnect();
            return false;
         }
         return true;
     }
    /**
     * 连接tcp服务端
     */
    public void connect()  {
        try {
            if(socket != null && socket.isConnected()) {
                disConnect();
            }
            working = true;
            socket = new Socket(ip, port);
            socket.setKeepAlive(true);
            log.info("TCP连接成功, IP:{}, Port:{}, 状态:{}", ip, port, socket.isConnected() ? "已连接": "未连接");
            this.connected = true;
            this.heartTime = new Date().getTime();
        } catch (Exception e) {
            log.error("服务端连接失败，IP:{}, 端口:{}, 错误信息:{}", ip, port, e.getMessage());
        }


    }






}
