package com.kingsware.kdev.sys.service.impl;

import com.kingsware.kdev.core.bean.ExceptionLog;
import com.kingsware.kdev.core.cache.api.ApiInfo;
import com.kingsware.kdev.core.cache.api.ApiManager;
import com.kingsware.kdev.core.cache.session.SessionManager;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.cache.session.TokenSession;
import com.kingsware.kdev.core.cron.DynamicTask;
import com.kingsware.kdev.core.exception.ExceptionLogManager;
import com.kingsware.kdev.core.kmq.websocket.MessageWebSocket;
import com.kingsware.kdev.core.kmq.websocket.WmMessage;
import com.kingsware.kdev.core.kmq.websocket.WmMessageArgv;
import com.kingsware.kdev.core.model.SysOnlineUser;
import com.kingsware.kdev.core.model.SysTask;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.core.cache.instance.InstanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2023/7/12 11:03
 */
@Service
@Slf4j
public class InstanceServiceImpl implements InstanceService {

    @Resource
    private DynamicTask dynamicTask;
    @Resource
    private MessageWebSocket messageWebSocket;

    @Override
    public void recvMessage(String topic, String message) {

        // 任务执行
        if ("task-execute".equalsIgnoreCase(topic)) {
            SysTask task = JsonUtil.toBean(message, SysTask.class);
            new Thread(() -> dynamicTask.executeTask(task)).start();
        }
        // 新增或编辑api
        else if ("api-add-update".equalsIgnoreCase(topic)) {
            ApiInfo apiInfo = JsonUtil.toBean(message, ApiInfo.class);
            ApiManager.getInstance().addOrUpdateApi(apiInfo);
        }
        // 删除api
        else if ("api-delete".equalsIgnoreCase(topic)) {
            ApiManager.getInstance().removeApi(message);
        }
        // 增加会话
        else if ("session-add".equalsIgnoreCase(topic)) {
            SysOnlineUser onlineUser = JsonUtil.toBean(message, SysOnlineUser.class);
            SessionManager.getInstance().addSession(onlineUser);
        }
        // 更新会话
        else if ("session-update".equalsIgnoreCase(topic)) {
            TokenSession tokenSession = JsonUtil.toBean(message, TokenSession.class);
            SessionManager.getInstance().updateSession(tokenSession);
        }
        // 删除会话
        else if ("session-remove".equalsIgnoreCase(topic)) {
            SysOnlineUser onlineUser = JsonUtil.toBean(message, SysOnlineUser.class);
            SessionManager.getInstance().removeSession(onlineUser.getUserId(), onlineUser.getLoginToken());
        }
        // 写入异常日志
        else if ("exception-write-log".equalsIgnoreCase(topic)) {
            ExceptionLog exceptionLog = JsonUtil.toBean(message, ExceptionLog.class);
            ExceptionLogManager.getInstance().write(exceptionLog);
        }
        // 广播消息
        else if("node-ws-broadcast".equalsIgnoreCase(topic)){
            WmMessage wmMessage = JsonUtil.toBean(message, WmMessage.class);
            messageWebSocket.broadMessage(wmMessage.getBody());
        }
        // 根据用户ID发送消息
        else if("send-by-user-id".equalsIgnoreCase(topic)){
            WmMessageArgv wmMessageArgv = JsonUtil.toBean(message, WmMessageArgv.class);
            messageWebSocket.sendMessageByUserId(wmMessageArgv.getUserId(), wmMessageArgv.getMessage());
        }
        // 根据用户token发送消息
        else if("send-by-user-token".equalsIgnoreCase(topic)){
            WmMessageArgv wmMessageArgv = JsonUtil.toBean(message, WmMessageArgv.class);
            messageWebSocket.sendMessageByToken(wmMessageArgv.getToken(), wmMessageArgv.getMessage());
        }
        else if ("broad-to-sessions".equalsIgnoreCase(topic)) {
            WmMessageArgv wmMessageArgv = JsonUtil.toBean(message, WmMessageArgv.class);
            messageWebSocket.broadMessageToAllSessions(wmMessageArgv.getMessage());

        }
        // 刷新api数据
        else if("refresh-api-data".equalsIgnoreCase(topic)){
            WmMessage wmMessage = JsonUtil.toBean(message, WmMessage.class);
            DynamicTask dynamicTask = SpringContext.getBean(DynamicTask.class);
            dynamicTask.virtualHeart(wmMessage.getBody());
        }
    }
}
