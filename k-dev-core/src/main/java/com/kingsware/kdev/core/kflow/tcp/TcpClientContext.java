package com.kingsware.kdev.core.kflow.tcp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingsware.kdev.core.kmq.KmqMessageCenter;
import com.kingsware.kdev.core.kmq.websocket.WebsocketConstants;
import com.kingsware.kdev.core.kmq.websocket.WmMessage;
import com.kingsware.kdev.core.kmq.websocket.WmMessageArgv;
import com.kingsware.kdev.core.model.SysNoticeRecord;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.util.*;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

import static com.kingsware.kdev.core.kflow.tcp.SocketHeadType.*;

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
    private final BlockingDeque<TMessage> recvBlockQueue =  new LinkedBlockingDeque<>(102400);
    /** 输出队列-发 **/
    private final BlockingDeque<TMessage> sendBlockQueue =  new LinkedBlockingDeque<>(102400);

    /** 调试会话ID和令牌缓存 **/
    private final Map<String, String> faasLogSessions = new ConcurrentHashMap<>();

    public static TcpClientContext getInstance() {
        if (instance == null) {
            synchronized (TcpClientContext.class) {
                if (instance == null) {
                    instance = new TcpClientContext();
                    instance.heart();
                }
            }
        }
        return instance;
    }

    private TcpClientContext() {
        initHandler();
    }

    public void putSession(String sessionId, String token) {
        faasLogSessions.put(sessionId, token);
    }

    public void removeSession(String sessionId) {
        faasLogSessions.remove(sessionId);
    }

    /**
     * 增加客户端
     * @param ip    ip
     * @param port  端口
     */
    public void addClient(String ip, int port) {
        TcpClient kLogClient = new TcpClient(ip, port, true, this, SocketHeadType.PINE);
        clients.add(kLogClient);
    }

    public void heart() {
        new Thread(() -> {
            while (true) {
                for (TcpClient client: clients) {
                    client.heart();
                }
                ThreadUtils.sleep(2000);
            }

        }).start();
    }

    private void  sendToWs(String toUserId, String body) {
        WmMessageArgv wmMessageArgv = new WmMessageArgv();
        wmMessageArgv.setMessage(body);
        wmMessageArgv.setUserId(toUserId);
        KmqMessageCenter.getInstance().produce(WebsocketConstants.MQ_TO_WEBSOCKET, JsonUtil.toJson(wmMessageArgv));
    }

    private void sendWsByToken(String toToken, String body) {
        WmMessageArgv wmMessageArgv = new WmMessageArgv();
        wmMessageArgv.setMessage(body);
        wmMessageArgv.setToken(toToken);
        KmqMessageCenter.getInstance().produce(WebsocketConstants.MQ_TO_WEBSOCKET, JsonUtil.toJson(wmMessageArgv));
    }

    private void sendWs(String toToken, String userId, String body) {
        WmMessageArgv wmMessageArgv = new WmMessageArgv();
        wmMessageArgv.setMessage(body);
        wmMessageArgv.setToken(toToken);
        wmMessageArgv.setUserId(userId);
        KmqMessageCenter.getInstance().produce(WebsocketConstants.MQ_TO_WEBSOCKET, JsonUtil.toJson(wmMessageArgv));
    }
    /**
     * 初始化处理器
     */
    private void initHandler() {
        new Thread(() -> {
            while (true) {
                try {
                    TMessage tMessage = recvBlockQueue.take();
                    log.info("接收数据:" + JsonUtil.toJson(tMessage));
                    TRspMessage tRspMessage = JsonUtil.toBean(tMessage.getBody(), TRspMessage.class);
                    // klog响应
                    if (tRspMessage.getType() == (short)KLOG_RESPONSE.getNumber()) {
                        KLogData kLogData = JsonUtil.toBean(tRspMessage.getData(), KLogData.class);
//                        kLogData.setSessionID("system");
//                        if (StringUtils.isNotEmpty(kLogData.getSessionID())) {
                        if (StringUtils.isNotEmpty(kLogData.getSessionID()) && faasLogSessions.containsKey(kLogData.getSessionID())) {

                            String sessionArgv = faasLogSessions.get(kLogData.getSessionID());
                            String[] arr = sessionArgv.split(";");
                            String token = arr[0];
                            String windowId = arr[1];
                            kLogData.setT(DateUtils.getNow());
                            kLogData.setWindowId(windowId);
                            // 组装前端用的消息体
                            WmMessage toC = new WmMessage();
                            toC.setTopic("klog");
                            toC.setBody(JsonUtil.toJson(kLogData));
                            // 发送到前端
                            sendWsByToken(token, JsonUtil.toJson(toC));


                        }
                    }
                    //
                    else if (tRspMessage.getType() == (short)USER_CUSTOM_RESPONSE.getNumber()) {
                        TcpCustomMessage customMessage = JsonUtil.toBean(tRspMessage.getData(), TcpCustomMessage.class);
                        // 站内通知
                        if ("notice".equalsIgnoreCase(customMessage.getType())) {
                            TcpNoticeMessage tcpNoticeMessage = JsonUtil.toBean(customMessage.getData(), TcpNoticeMessage.class);
                            SysNoticeRecord modelRecord = new SysNoticeRecord();
                            modelRecord.setFromWho(tcpNoticeMessage.getFromWho());
                            String fromWhoName = DB.findSingleAttribute(String.class, "select real_name from sys_user where id=?", tcpNoticeMessage.getFromWho());
                            modelRecord.setFromWhoName(fromWhoName);
                            modelRecord.setToWho(tcpNoticeMessage.getToWho());
                            String toWhoName = DB.findSingleAttribute(String.class, "select real_name from sys_user where id=?", tcpNoticeMessage.getToWho());
                            modelRecord.setToWhoName(toWhoName);
                            modelRecord.setIsRead(0);
                            modelRecord.setNoticeTime(Timestamp.valueOf(DateUtils.getNow()));
                            modelRecord.setTitle(tcpNoticeMessage.getTitle());
                            modelRecord.setContent(tcpNoticeMessage.getMessage());
                            DB.save(modelRecord);

                            // websocket推送给前端
                            try {
                                ObjectMapper mapper = new ObjectMapper();
                                String jsonString = null;
                                jsonString = mapper.writeValueAsString(modelRecord);
                                System.out.println(jsonString);
                                KmqMessageCenter.getInstance().produceWebsocketMessageToUser(tcpNoticeMessage.getToWho(), "notice-center", jsonString);
                            } catch (JsonProcessingException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        else if ("websocket".equalsIgnoreCase(customMessage.getType())) {
                            TcpWmMessage wmMessage = JsonUtil.toBean(customMessage.getData(), TcpWmMessage.class);
                            // 组装前端用的消息体
                            WmMessage toC = new WmMessage();
                            toC.setTopic(wmMessage.getTopic());
                            toC.setBody(wmMessage.getBody());
                            // 发送到前端
                            sendWs(wmMessage.getToken(), wmMessage.getUserId(),  JsonUtil.toJson(toC));
                        }
                    }

                }
                catch (Exception e) {
                    log.error("error", e);
                }

            }
        }).start();
//        new Thread(() -> {
//            while (true) {
//                try {
//                    TMessage tMessage = sendBlockQueue.take();
//                    // 如果消息id为空，那就广播
//                    if (StringUtils.isEmpty(tMessage.getClientId())) {
//                        for (TcpClient client: clients) {
//                            client.send(tMessage.getBody());
//                        }
//                    }
//                    else {
//                        Optional<TcpClient> optional = clients.stream().filter(it -> it.getId().equals(tMessage.getClientId())).findFirst();
//                        optional.ifPresent(client -> client.send(tMessage.getBody()));
//                    }
//                }
//                catch (Exception e) {
////                    log.info("消息发送失败", e);
//                }
//            }
//        }).start(); ;

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
