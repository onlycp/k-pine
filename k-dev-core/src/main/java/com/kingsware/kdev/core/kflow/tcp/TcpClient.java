package com.kingsware.kdev.core.kflow.tcp;

import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.core.util.ThreadUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * tcp客户端
 */
@Slf4j
public class TcpClient {

    /** ip **/
    private String ip;
    /** 端口 **/
    private int port;
    /** socket **/
    private Socket socket;
    /** 客户端id **/
    private final TcpClientContext context;
    /** 输出线程 **/
    private boolean working;
    /** 客户端id **/
    private String id;

    private  boolean autoConnect;

    public TcpClient(String ip, int port, boolean autoConnect, TcpClientContext context) {
        this.id = StringUtils.getUUID();
        this.ip = ip;
        this.port = port;
        this.context = context;
        this.autoConnect = autoConnect;
        if (autoConnect) {
            new Thread(() -> {
                while (true) {
                    try {
                        if (socket == null || !socket.isConnected()) {
                            this.connect();
                        }
                    }
                    catch (Exception ignored) {
                    }
                    finally {
                        try {
                            ThreadUtils.sleep(5000);
                        }
                        catch (Exception ignored) {
                        }
                    }

                }
            }).start();
        }
        init();
    }

    public void init() {
        // 创建一个收消息
        working = true;
        new Thread(() -> {
            while (working) {
                if (socket != null && socket.isConnected()) {
                    try {
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                        String message;
                        while ((message = in.readLine()) != null) {
                            TMessage tMessage = new TMessage();
                            tMessage.setClientId(id);
                            tMessage.setBody(message);
                            context.read(tMessage);
                        }

                    } catch (IOException e) {
                        try {
                            this.socket.close();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }

                }
                ThreadUtils.sleep(2000);

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
    public void send(String msg) {
        if (this.socket.isConnected()) {
            try {
                this.socket.getOutputStream().write(msg.getBytes(StandardCharsets.UTF_8));
                this.socket.getOutputStream().write(new byte[] {0x0d, 0x0a});
                this.socket.getOutputStream().flush();
            }
            catch (Exception e) {
//                log.error("消息发送消息");
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
        return socket.isConnected();
     }
    /**
     * 连接tcp服务端
     */
    public void connect()  {
        try {
            working = true;
            socket = new Socket(ip, port);
            socket.setKeepAlive(true);
            log.info("TCP连接成功, IP:{}, Port:{}, 状态:{}", ip, port, socket.isConnected() ? "已连接": "未连接");
        } catch (Exception e) {
            log.error("服务端连接失败，IP:{}, 端口:{}, 错误信息:{}", ip, port, e.getMessage());
        }


    }






}
