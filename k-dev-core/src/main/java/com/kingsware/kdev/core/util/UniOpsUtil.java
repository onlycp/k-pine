package com.kingsware.kdev.core.util;

/**
 * @author chenp
 * @date 2023/1/12
 */

import com.kingsware.kdev.core.context.SpringContext;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * uniops工具类
 */
@Slf4j
public class UniOpsUtil {

    private UniOpsUtil() {}

    @SuppressWarnings("all")
    public static String getUniOpsToken(String server, String user, String pwd) {
        String username = user;
        String password = pwd;
        String uniopsServer = server;
        String url = uniopsServer + "/ops/system/login";
        // 组装参数
        Map<String, Object> params = new HashMap<>();
        params.put("currentCount", 1);
        params.put("userName", username);
        params.put("password", password);
        // 发起请求
        String responseBody = HttpUtil.callHttp(url, JsonUtil.toJson(params), new HashMap<>());
        log.info("uniops令牌请求:{}", responseBody);
        Map<String, Object> retMap = JsonUtil.toMap(responseBody);
        int errorCode = (int)retMap.get("errorCode");
        if (errorCode == 0) {
            Map<String, Object> responseBodyMap = (Map<String, Object> )retMap.get("responseBody");
            String token = responseBodyMap.get("token").toString();
            return token;
        }
        return null;

    }
}
