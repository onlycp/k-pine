package com.kingsware.kdev.sys.mq;

import com.kingsware.kdev.core.kflow.tcp.TcpClientContext;
import com.kingsware.kdev.core.kmq.KmqConsumer;
import com.kingsware.kdev.core.kmq.websocket.WebsocketConstants;
import com.kingsware.kdev.core.kmq.websocket.Wm2MqMessage;
import com.kingsware.kdev.core.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


/**
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/5/27 5:44 PM
 */
@Slf4j
public class FromWebsocketConsumer implements KmqConsumer {
    @Override
    public void onMessage(List<String> payload) throws Exception {

        for (String message: payload) {
            Wm2MqMessage wm2MqMessage = JsonUtil.toBean(message, Wm2MqMessage.class);
            if ("faasDebug".equals(wm2MqMessage.getWmMessage().getTopic())) {
                TcpClientContext.getInstance().write(JsonUtil.toJson(wm2MqMessage.getWmMessage()));
            }

        }

    }

    @Override
    public String topic() {
        return WebsocketConstants.MQ_FROM_WEBSOCKET;
    }
}
