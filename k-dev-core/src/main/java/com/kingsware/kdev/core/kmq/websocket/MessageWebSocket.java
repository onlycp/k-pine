package com.kingsware.kdev.core.kmq.websocket;

import com.kingsware.kdev.core.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * websocket端点
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 3:48 下午
 */
@ConditionalOnProperty(prefix = "app.websocket", name = {"enable"}, havingValue = "true")
@ServerEndpoint(value = "/websocket")
@Component
public class MessageWebSocket {
    /** 日志打印 **/
    private static final Logger logger = LoggerFactory.getLogger(MessageWebSocket.class);
    /** 存储当前所有的session **/
    private static final Set<SessionToken> sessionTokenSet = Collections.synchronizedSet(new HashSet<>());


    /**
     * 连接建立成功调用的方法 */
    @OnOpen
    public void onOpen(Session session) {
        logger.info("WebSocket连接成功, sessionId:{}", session.getId());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void  onClose(Session session) {
        // 查找session
        Optional<SessionToken> optional = sessionTokenSet.stream().filter(it -> it.getSession().getId().equalsIgnoreCase(session.getId())).findFirst();
        if (optional.isPresent()) {
            sessionTokenSet.remove(optional.get());
            logger.info("用户:【userId={}, token={}】退出，当前在线人数为:{} ", optional.get().getUserId(), optional.get().getToken(), sessionTokenSet.size());
        }
    }
    /**
     * 发生错误时调用
     *
     */
    @OnError
    public void onError(Session session, Throwable error) {
        logger.error("error", error);
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {

        logger.info("接收websocket消息: {}", message);
        WmMessage wmMessage = JsonUtil.toBean(message, WmMessage.class);
        if (wmMessage == null) {
            logger.info("websocket不合法，无法解析: {}", message);
            return;
        }
        // 如果是系统主题，里面主要有心跳
        if ("system".equalsIgnoreCase(wmMessage.getTopic())) {
            // 心跳
            if ("ping".equalsIgnoreCase(wmMessage.getType())) {
                WmMessage replyMessage = new WmMessage(WebsocketConstants.TOPIC_SYSTEM, WebsocketConstants.TYPE_PING, "pong", 0);
                sendMessage(session, JsonUtil.toJson(replyMessage));
            }
            else if ("token".equalsIgnoreCase(wmMessage.getType())) {

            }
        }
    }

    /**
     * 发送消息
     * @param session   会话
     * @param message   消息内容
     */
    public void sendMessage(Session session, String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            logger.info("websocket不合法，无法解析: {}", message);
        }
    }
}
