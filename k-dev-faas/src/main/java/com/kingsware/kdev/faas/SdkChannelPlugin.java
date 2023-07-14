package com.kingsware.kdev.faas;

import com.alibaba.fastjson.JSONObject;
import com.kingsware.catalina.Bootstrap;
import com.kingsware.catalina.sdk.FaasSdk;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.exception.HttpClientException;
import com.kingsware.kdev.core.plugins.FaasChannelPlugin;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.tools.FEnv;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
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

    /** 是否已经初始化 **/
    private static boolean inited = false;

    @Value("${faas.ext-path:../faas/ext}")
    private String extPath;

    @Value("${faas.lib-path:../faas/lib}")
    private String libPath;


    /**
     * 初始化sdk
     */
    private void initSdk() {
        if (inited) {
            return;
        }
        inited = true;
        JSONObject config = new JSONObject();
        config.put("mode", "sdk");
        config.put("ext.path", extPath);
        config.put("lib.path", libPath);
        config.put("profiler.open", false);
        FEnv.setConfig(config);
        log.info("插件加载准备:{}",  name());
        Bootstrap.main(null);
        log.info("插件加载完成:{}",  name());

    }



    @Override
    public String send(String apiUrl, String body, Map<String, String> headerMap) {
        this.initSdk();
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
