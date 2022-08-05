package com.kingsware.kdev.core.kmq.websocket;

import com.kingsware.kdev.core.auth.AuthToken;
import com.kingsware.kdev.core.auth.TokenUtil;
import com.kingsware.kdev.core.kmq.KmqMessageCenter;
import com.kingsware.kdev.core.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.kingsware.kdev.core.kmq.websocket.WebsocketConstants.MQ_FROM_WEBSOCKET;

/**
 * websocket端点
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 3:48 下午
 */
//@ConditionalOnProperty(prefix = "app.websocket", name = {"enable"}, havingValue = "true")
@ServerEndpoint(value = "/api/v1/websocket")
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
        sendMessage(session, JsonUtil.toJson(new WmMessage("welcome", "Hello world!")));
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void  onClose(Session session) {
        removeSession(session);
    }
    /**
     * 发生错误时调用
     *
     */
    @OnError
    public void onError(Session session, Throwable error) {
        // 发生错误时
        logger.error("error", error);
        removeSession(session);
    }

    /**
     * 移除会话
     * @param session  会话
     */
    private void removeSession(Session session) {
        // 查找session
        Optional<SessionToken> optional = sessionTokenSet.stream().filter(it -> it.getSession().getId().equalsIgnoreCase(session.getId())).findFirst();
        if (optional.isPresent()) {
            sessionTokenSet.remove(optional.get());
            logger.info("用户:【userId={}, token={}】退出，当前在线人数为:{} ", optional.get().getUserId(), optional.get().getToken(), sessionTokenSet.size());
        }
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {

//        logger.info("接收websocket消息: {}", message);
        WmMessage wmMessage = JsonUtil.toBean(message, WmMessage.class);
        if (wmMessage == null) {
            logger.info("websocket不合法，无法解析: {}", message);
            return;
        }
        if ("whoami".equalsIgnoreCase(wmMessage.getTopic())) {
            // 将会话信息保存起来
            String token = wmMessage.getBody();
            AuthToken authToken = TokenUtil.getAuthToken(token);
            if (authToken == null) {
                return;
            }
            SessionToken sessionToken = new SessionToken();
            sessionToken.setToken(token);
            sessionToken.setUserId(authToken.getUserInfo().getId());
            sessionToken.setSession(session);
            sessionTokenSet.add(sessionToken);

        }
        else {
            // 获取令牌
            Optional<SessionToken> sessionToken = sessionTokenSet.stream().filter(it -> it.getSession().getId().equals(session.getId())).findFirst();
            if (sessionToken.isPresent()) {
                Wm2MqMessage wm2MqMessage = new Wm2MqMessage();
                wm2MqMessage.setToken(sessionToken.get().getToken());
                wm2MqMessage.setWmMessage(wmMessage);
                KmqMessageCenter.getInstance().produce(MQ_FROM_WEBSOCKET, JsonUtil.toJson(wm2MqMessage));
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
//            logger.info("发送消息:" + JsonUtil.toJson(message));
        }
        catch (Exception e) {
            logger.error("消息发送失败: {}", message);
        }
    }

    /**
     * 广播消息
     */
    public void broadMessage(String message) {
        sessionTokenSet.forEach(it -> sendMessage(it.getSession(), message));
    }

    /**
     * 发送消息
     * @param token 令牌
     * @param message   消息
     */
    public void sendMessageByToken(String token, String message) {
        sessionTokenSet.stream().filter(it -> it.getToken().equals(token)).forEach(it -> sendMessage(it.getSession(), message));
    }

    /**
     * 发送消息
     * @param userId    用户id
     * @param message   消息
     */
    public void sendMessageByUserId(String userId, String message) {
        sessionTokenSet.stream().filter(it -> it.getUserId().equals(userId)).forEach(it -> sendMessage(it.getSession(), message));
    }
}
