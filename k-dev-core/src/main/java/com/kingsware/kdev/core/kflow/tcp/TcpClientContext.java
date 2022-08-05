package com.kingsware.kdev.core.kflow.tcp;

import com.kingsware.kdev.core.cache.dict.DictManager;
import com.kingsware.kdev.core.kmq.KmqMessageCenter;
import com.kingsware.kdev.core.kmq.websocket.WebsocketConstants;
import com.kingsware.kdev.core.kmq.websocket.WmMessageArgv;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.core.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * tcp客户端客户器
 */
@Slf4j
public class TcpClientContext {

    private static TcpClientContext instance;
    /**
     * tcp客户端集合
     */
    private Set<TcpClient> clients = Collections.synchronizedSet(new HashSet<>());

    /** 输入队列-收**/
    private final BlockingDeque<TMessage> recvBlockQueue =  new LinkedBlockingDeque<>(1024);
    /** 输出队列-发 **/
    private final BlockingDeque<TMessage> sendBlockQueue =  new LinkedBlockingDeque<>(1024);

    public static TcpClientContext getInstance() {
        if (instance == null) {
            synchronized (TcpClientContext.class) {
                if (instance == null) {
                    instance = new TcpClientContext();
                }
            }
        }
        return instance;
    }

    private TcpClientContext() {
        initHandler();
    }

    /**
     * 增加客户端
     * @param ip    ip
     * @param port  端口
     */
    public void addClient(String ip, int port) {
        TcpClient client = new TcpClient(ip, port, true, this);
        clients.add(client);

    }

    /**
     * 初始化处理器
     */
    private void initHandler() {
        new Thread(() -> {
            while (true) {
                try {
                    TMessage tMessage = recvBlockQueue.take();
                    ExchangeMessage exchangeMessage = JsonUtil.toBean(tMessage.getBody(), ExchangeMessage.class);
                    log.info("接收数据:" + JsonUtil.toJson(tMessage));
                    if ("faasDebug".equals(exchangeMessage.getType())) {
                        WmMessageArgv wmMessageArgv = new WmMessageArgv();
                        wmMessageArgv.setMessage(tMessage.getBody());
                        KmqMessageCenter.getInstance().produce(WebsocketConstants.MQ_TO_WEBSOCKET, JsonUtil.toJson(wmMessageArgv));
                    }


                }
                catch (Exception e) {
                    log.error("error", e);
                }

            }
        }).start();
        new Thread(() -> {
            while (true) {
                try {
                    TMessage tMessage = sendBlockQueue.take();
                    // 如果消息id为空，那就广播
                    if (StringUtils.isEmpty(tMessage.getClientId())) {
                        for (TcpClient client: clients) {
                            client.send(tMessage.getBody());
                        }
                    }
                    else {
                        Optional<TcpClient> optional = clients.stream().filter(it -> it.getId().equals(tMessage.getClientId())).findFirst();
                        optional.ifPresent(client -> client.send(tMessage.getBody()));
                    }
                }
                catch (Exception e) {
                    log.info("消息发送失败", e);
                }
            }
        }).start(); ;

    }


    /**
     * 接收消息
     * @param msg       消息
     */
    public void read(TMessage msg) {
        recvBlockQueue.add(msg);
        this.write("reply:" + msg.getBody());
    }

    /**
     * 发送消息
     * @param msg   消息
     */
    public void write(String msg) {
        TMessage message = new TMessage();
        message.setBody(msg);
        message.setClientId(null);
        sendBlockQueue.add(message);
    }




}
