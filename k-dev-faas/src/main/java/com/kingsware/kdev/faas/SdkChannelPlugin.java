package com.kingsware.kdev.faas;

import com.alibaba.fastjson.JSONObject;
import com.kingsware.catalina.Bootstrap;
import com.kingsware.catalina.sdk.FaasSdk;
import com.kingsware.kdev.core.cache.license.LicenseManager;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.exception.HttpClientException;
import com.kingsware.kdev.core.plugins.FaasChannelPlugin;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.tools.FEnv;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
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


    public void init() {
        if (inited) {
            return;
        }
        // 如果是uniops，那么就调整目录
        if (LicenseManager.getInstance().isUniopsApp()) {
            log.info("当前为uniops应用");
            JSONObject config = new JSONObject();
            config.put("httpPort", 10081);
            String extPath =  "faas/ext";
            String libPath =  "faas/lib";
            config.put("ext.path", extPath);
            config.put("lib.path", libPath);
            // 如果目录不存在，则创建
            if (!new File(extPath).exists())  {
                new File(extPath).mkdirs();
            }
            if (!new File(libPath).exists())  {
                new File(libPath).mkdirs();
            }
            log.info("当前为k-uniops模式，faas-ext:{}, faas-lib:{}", extPath, libPath);
            FEnv.setConfig(config);
        }
        log.info("插件加载准备:{}",  name());
        Bootstrap.main(null);
        log.info("插件加载完成:{}",  name());
        inited = true;
    }



    @Override
    public String send(String apiUrl, String body, Map<String, String> headerMap) {
        this.init();
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
