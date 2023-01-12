package com.kingsware.kdev.core.util;

/**
 * @author chenp
 * @date 2023/1/12
 */

import com.kingsware.kdev.core.context.SpringContext;

import java.util.HashMap;
import java.util.Map;

/**
 * uniops工具类
 */
public class UniOpsUtil {

    private UniOpsUtil() {}

    @SuppressWarnings("all")
    public static String getUniOpsToken() {
        String username = SpringContext.getProperties("uniops.user", "admin");
        String password = SpringContext.getProperties("uniops.pwd", "WzcwLDIwNiwxMTUsNTksNjUsMTk1LDIzMiw5OSwxMDksOTAsMTM3LDcyLDYsMTQxLDkxLDE1OF0=");
        String uniopsServer = SpringContext.getProperties("uniops.server", "http://localhost:3456");
        String url = uniopsServer + "/ops/system/login";
        // 组装参数
        Map<String, Object> params = new HashMap<>();
        params.put("currentCount", 1);
        params.put("userName", username);
        params.put("password", password);
        // 发起请求
        String responseBody = HttpUtil.postBody(url, JsonUtil.toJson(params), new HashMap<>());
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
