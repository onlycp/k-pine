package com.kingsware.kdev.sys.mq;

import com.kingsware.kdev.core.auth.AuthToken;
import com.kingsware.kdev.core.auth.TokenUtil;
import com.kingsware.kdev.core.cache.session.SessionManager;
import com.kingsware.kdev.core.context.KClientContext;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.kflow.tcp.TcpClientContext;
import com.kingsware.kdev.core.kmq.KmqConsumer;
import com.kingsware.kdev.core.kmq.KmqMessageCenter;
import com.kingsware.kdev.core.kmq.websocket.*;
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
public class FromWebsocketConsumer implements KmqConsumer {
    @Override
    public void onMessage(List<String> payload) throws Exception {

        for (String message: payload) {
            Wm2MqMessage wm2MqMessage = JsonUtil.toBean(message, Wm2MqMessage.class);
            if ("faasDebug".equals(wm2MqMessage.getWmMessage().getTopic())) {
                // TcpClientContext.getInstance().write(JsonUtil.toJson(wm2MqMessage.getWmMessage()));
            }

        }

    }

    @Override
    public String topic() {
        return WebsocketConstants.MQ_FROM_WEBSOCKET;
    }
}
