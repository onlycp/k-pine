package com.kingsware.kdev.core.kmq.websocket;

import com.kingsware.kdev.core.auth.AppAuthProperties;
import com.kingsware.kdev.core.auth.AuthToken;
import com.kingsware.kdev.core.auth.TokenUtil;
import com.kingsware.kdev.core.cache.session.SessionManager;
import com.kingsware.kdev.core.context.KClientContext;
import com.kingsware.kdev.core.cache.instance.InstanceManager;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.cron.DynamicTask;
import com.kingsware.kdev.core.kmq.KmqMessageCenter;
import com.kingsware.kdev.core.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
    private static final Map<Session, Long> allSessionSet = new HashMap<>();

    private static final ScheduledExecutorService expirationScheduler = Executors.newScheduledThreadPool(1);

    public MessageWebSocket() {
        expirationScheduler.scheduleAtFixedRate(this::clearExpireSessions, 0, 5, TimeUnit.SECONDS);
    }


    /**
     * 连接建立成功调用的方法 */
    @OnOpen
    public void onOpen(Session session) {
//        logger.info("WebSocket连接成功, sessionId:{}", session.getId());
        sendMessage(session, JsonUtil.toJson(new WmMessage("welcome", "Hello world!")));
        allSessionSet.put(session, System.currentTimeMillis());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void  onClose(Session session) {
        removeSession(session);
    }
    /**

     *
     */
    @OnError
    public void onError(Session session, Throwable error) {
        // 发生错误时
        removeSession(session);
    }

    /**
     * 移除会话
     * @param session  会话
     */
    private void removeSession(Session session) {
        // 查找session
        try {
            logger.info("用户:【sessionId={}】下线，当前在线人数为:{} ",  session.getId(), sessionTokenSet.size());
            // 从所有会话中移移
            allSessionSet.remove(session);
            Set<SessionToken> copiedSet = new HashSet<>(sessionTokenSet);
            Optional<SessionToken> optional = copiedSet.stream().filter(it -> it.getSession().getId().equalsIgnoreCase(session.getId())).findFirst();
            // logger.info("用户:【userId={}, token={}】退出，当前在线人数为:{} ", optional.get().getUserId(), optional.get().getToken(), sessionTokenSet.size());
            optional.ifPresent(sessionTokenSet::remove);
        }
        catch (Exception ignored) {

        }

    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {

        try {
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
                    WmMessage replyMessage = new WmMessage("error", "token不存在或已过时");
                    this.sendMessage(session, JsonUtil.toJson(replyMessage));
                    try {
                        session.close();
                    }
                    catch (Exception e) {
                        logger.error("关闭连接失败", e);
                    }

                    return;
                }
                SessionToken sessionToken = new SessionToken();
                sessionToken.setToken(token);
                sessionToken.setUserId(authToken.getUserInfo().getId());
                sessionToken.setSession(session);
                sessionToken.setHeartTime(System.currentTimeMillis());
                sessionTokenSet.add(sessionToken);
                logger.info("用户:【userId={}, sessionId={}】上线，当前在线人数为:{} ", authToken.getUserInfo().getId(), session.getId(), sessionTokenSet.size());

            }
            else if ("ping".equalsIgnoreCase(wmMessage.getTopic())) {
                WmMessage replyMessage = new WmMessage("pong", "");
                this.sendMessage(session, JsonUtil.toJson(replyMessage));
                // 获取令牌
                SessionToken sessionToken = getSessionToken(session);
                if (sessionToken != null) {
                    sessionToken.setHeartTime(System.currentTimeMillis());
                    // 更新活动时间
                    AppAuthProperties appAuthProperties = SpringContext.getBean(AppAuthProperties.class);
                    // logger.info("更新活动时间");
                    SessionManager.getInstance().updateActiveTime(sessionToken.getUserId(), sessionToken.getToken(), appAuthProperties.getMockSessionExpireMinutes(), appAuthProperties.getSessionUpdateByPing());
                }
                // 更新心跳时间
                allSessionSet.put(session, System.currentTimeMillis());
                // 更新缓存

            }

        // 如果是广播
        else if ("broadcast".equalsIgnoreCase(wmMessage.getTopic())) {
            try {
                // broadcast代表广播所有节点，是由消息发起方明确指定的
                // 而node-ws-broadcast仅仅只是一个内部标识，表示broadcast消息传播到每个节点后，在当前节点的消息表示
                InstanceManager.getInstance().broadMessage("node-ws-broadcast", message);

            }
            catch (Exception e) {
                logger.error("广播失败", e);
            }

        }
        else if ("refresh-api-data".equalsIgnoreCase(wmMessage.getTopic())) {
            // 需要广播所有节点进行修改时间的刷新
            InstanceManager.getInstance().broadMessage("refresh-api-data", message);

            // 获取令牌
            SessionToken sessionToken = getSessionToken(session);
            if (sessionToken != null) {
                sessionToken.setHeartTime(System.currentTimeMillis());
                // 更新活动时间
                AppAuthProperties appAuthProperties = SpringContext.getBean(AppAuthProperties.class);
                // logger.info("更新活动时间");
                SessionManager.getInstance().updateActiveTime(sessionToken.getUserId(), sessionToken.getToken(), appAuthProperties.getMockSessionExpireMinutes(), true);
            }
            // 更新心跳时间
            allSessionSet.put(session, System.currentTimeMillis());
        }
        else {
            // 获取令牌
            SessionToken sessionToken = getSessionToken(session);
            if (sessionToken != null) {
                Wm2MqMessage wm2MqMessage = new Wm2MqMessage();
                wm2MqMessage.setToken(sessionToken.getToken());
                wm2MqMessage.setWmMessage(wmMessage);
                KmqMessageCenter.getInstance().produce(MQ_FROM_WEBSOCKET, JsonUtil.toJson(wm2MqMessage));
            }

            }
        }
        catch (Exception e) {
            logger.error("接收websocket消息失败", e);
        }

    }


    public void clearExpireSessions() {

        // 移除过时的sessionToken
        try {
            Set<SessionToken> removeSessions = sessionTokenSet.stream().filter(it-> ((it.getHeartTime() + 30000) <= System.currentTimeMillis())).collect(Collectors.toSet());
            removeSessions.forEach(it -> {
                try {
                    logger.info("移除过时的session: {}", it.getSession().getId());
                    sessionTokenSet.remove(it);
                    it.getSession().close();
                }
                catch (Exception e) {
                    logger.error("移除过时的sessionToken失败", e);
                }


            });
            sessionTokenSet.removeAll(removeSessions);
        }
        catch (Exception ignored) {
            ignored.printStackTrace();
        }
        //  移除过时的session
        try {
          List<Session> removeSessions = allSessionSet.entrySet().stream().filter(it -> ((it.getValue() + 30000) <= System.currentTimeMillis())).map(Map.Entry::getKey).collect(Collectors.toList());
          removeSessions.forEach(it -> {
              try {
                  WmMessage exitMessage = new WmMessage("exit", "心跳超时，会话将被关闭");
                  it.getBasicRemote().sendText(JsonUtil.toJson(exitMessage));
                  logger.info("移除过时的session: {}", it.getId());
                  it.close();
              }
              catch (Exception e) {
                  logger.error("移除过时的session失败", e);
              }

          });
          allSessionSet.keySet().removeAll(removeSessions);
        }
        catch (Exception ignored) {

        }
    }

    public SessionToken getSessionToken(Session session) {
        Set<SessionToken> copiedSet = new HashSet<>(sessionTokenSet);
        Optional<SessionToken> sessionToken = copiedSet.stream().filter(it -> it.getSession().getId().equals(session.getId())).findFirst();
        return sessionToken.orElse(null);
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
//            logger.error("消息发送失败: {}", message);
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

    /**
     * 判断是否存在会话
     * @param token
     * @return
     */
    public boolean hasSessionByToken(String token) {
        return sessionTokenSet.stream().anyMatch(it -> it.getToken().equals(token));
    }
}
