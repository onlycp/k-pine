package com.kingsware.kdev.faas;

import com.kingsware.catalina.Bootstrap;
import com.kingsware.catalina.sdk.FaasSdk;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.exception.HttpClientException;
import com.kingsware.kdev.core.plugins.FaasChannelPlugin;
import com.kingsware.kdev.core.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * FAAS通道
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2023/2/16 18:00
 */
@Component
@Slf4j
public class SdkChannelPlugin implements FaasChannelPlugin {

    @PostConstruct
    public void init() {
//        String faasCallMode = SpringContext.getProperties("app.k-flow.call-model", "http");
//        if (name().equalsIgnoreCase(faasCallMode)) {
//
//        }
        Bootstrap.main(null);
        log.info("插件加塞完成:{}",  name());
    }



    @Override
    public String send(String apiUrl, String body, Map<String, String> headerMap) {
        /**
         * {
         *     "method": "POST",
         *     "postInfo": {
         *         "flowID": "e91cb405d1914e21809a6f01cb44ef2f",
         *         "script": {},
         *         "variables": {
         *             "id": "7a8f44729b8a485cb8c9f750096f4fa0"
         *         }
         *     },
         *     "url": "/api/execute"
         * }
         */
        Map<String, Object> callParams = new HashMap<>();
        callParams.put("method", "POST");
        callParams.put("url", apiUrl.replaceAll(SpringContext.getProperties("database.sources.db.server", ""), ""));
        callParams.put("postInfo", JsonUtil.toMap(body));
        // 调用sdk返回数据
        try {
            return FaasSdk.invoke(JsonUtil.toJson(callParams));
        }
        catch (Exception e) {
            throw new HttpClientException(e.getMessage(), -1, apiUrl, body);
        }
    }

    @Override
    public String name() {
        return "sdk";
    }
}
