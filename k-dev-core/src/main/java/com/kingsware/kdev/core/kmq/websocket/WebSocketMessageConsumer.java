package com.kingsware.kdev.core.kmq.websocket;

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

        log.info("websocket message consumer: {}", JsonUtil.toJson(payload));
        MessageWebSocket messageWebSocket = SpringContext.getBean(MessageWebSocket.class);
        for (String message: payload) {
            WmMessageArgv wmMessageArgv = JsonUtil.toBean(message, WmMessageArgv.class);
            if (StringUtils.isNotEmpty(wmMessageArgv.getToken())) {
                messageWebSocket.sendMessageByToken(wmMessageArgv.getToken(), wmMessageArgv.getMessage());
            }
            else if (StringUtils.isNotEmpty(wmMessageArgv.getUserId())) {
                messageWebSocket.sendMessageByUserId(wmMessageArgv.getUserId(), wmMessageArgv.getMessage());
            }
            else {
                messageWebSocket.broadMessage(wmMessageArgv.getMessage());
            }
        }

    }

    @Override
    public String topic() {
        return WebsocketConstants.MQ_TO_WEBSOCKET;
    }
}
