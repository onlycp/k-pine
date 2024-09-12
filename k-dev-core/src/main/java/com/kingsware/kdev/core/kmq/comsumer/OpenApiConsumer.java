package com.kingsware.kdev.core.kmq.comsumer;

import com.kingsware.kdev.core.kflow.KFlowContext;
import com.kingsware.kdev.core.kflow.KdbFlowExecutor;
import com.kingsware.kdev.core.kflow.bean.KdbFlowResult;
import com.kingsware.kdev.core.kmq.KmqConsumer;
import com.kingsware.kdev.core.util.JsonUtil;

import java.util.List;
import java.util.Map;

public class OpenApiConsumer implements KmqConsumer {
    /**
     * 消费消息
     *
     * @param payloads 消息
     * @throws Exception 业务消费失败时，将放到失败队列文件日志
     */
    @Override
    public void onMessage(List<String> payloads) throws Exception {
        KFlowContext ic = KFlowContext.createBaseContext("{}", "{}", null);
        for (String payload: payloads) {
            Map<String, Object> recordMap = JsonUtil.toMap(payload);
            KdbFlowResult rs = KdbFlowExecutor.getInstance().execute("72caaa23e3744781b8e5d7565a6e23f7", "", recordMap, ic, false, true);
            System.out.println(JsonUtil.toJson(rs));
        }
    }

    /**
     * 获取主题
     *
     * @return 队列名
     */
    @Override
    public String topic() {
        return "openApiQueue";
    }
}
