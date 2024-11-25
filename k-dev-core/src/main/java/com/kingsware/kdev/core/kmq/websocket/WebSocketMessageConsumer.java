package com.kingsware.kdev.core.kmq.websocket;

import com.kingsware.kdev.core.cache.instance.InstanceManager;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.kmq.KmqConsumer;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.core.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


/**
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/5/27 5:44 PM
 */
@Slf4j
public class WebSocketMessageConsumer implements KmqConsumer {
    @Override
    public void onMessage(List<String> payload) throws Exception {

        //log.info("websocket message consumer: {}", JsonUtil.toJson(payload));
        MessageWebSocket messageWebSocket = SpringContext.getBean(MessageWebSocket.class);
        for (String message: payload) {
            WmMessageArgv wmMessageArgv = JsonUtil.toBean(message, WmMessageArgv.class);
            if (StringUtils.isNotEmpty(wmMessageArgv.getToken())) {
//                messageWebSocket.sendMessageByToken(wmMessageArgv.getToken(), wmMessageArgv.getMessage());
                // 广播所有节点，因为用户可能不在当前节点上
                InstanceManager.getInstance().broadMessage("send-by-user-token", message);
            }
            else if (StringUtils.isNotEmpty(wmMessageArgv.getUserId())) {
//                messageWebSocket.sendMessageByUserId(wmMessageArgv.getUserId(), wmMessageArgv.getMessage());
                // 广播所有节点，因为用户可能不在当前节点上
                InstanceManager.getInstance().broadMessage("send-by-user-id", message);
            }
            else {
//                messageWebSocket.broadMessage(wmMessageArgv.getMessage());
                // 广播所有节点
                WmMessage wmMessage = new WmMessage();
                wmMessage.setTopic("broadcast");
                wmMessage.setBody(wmMessageArgv.getMessage());
                InstanceManager.getInstance().broadMessage("node-ws-broadcast", JsonUtil.toJson(wmMessage));
            }
        }

    }

    @Override
    public String topic() {
        return WebsocketConstants.MQ_TO_WEBSOCKET;
    }
}
