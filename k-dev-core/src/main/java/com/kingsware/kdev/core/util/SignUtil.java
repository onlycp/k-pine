package com.kingsware.kdev.core.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 签名工具类
 */
public class SignUtil {

    private SignUtil(){};

    /**
     * 获取签名值
     * @param params 参数map
     * @return  返回签名值
     */
    public static String getSign(Map<String, Object> params, String signKey) {
        // 排序map
        TreeMap<String, String> sortedMap = new TreeMap<>();
        params.forEach((k, v) -> sortedMap.put(k, v.toString()));
        // 拼接拼接字符
        List<String> queryStrings = new ArrayList<>();
        sortedMap.forEach((k, v) -> queryStrings.add(String.format("%s=%s", k,v)));
        String str1 = StringUtils.joinToString(queryStrings, "&");
        // 加上签名密钥
        String str2 = str1 + "@" + signKey;
        // 计算签名值
        return MD5Utils.md5(str2);
    }
}
