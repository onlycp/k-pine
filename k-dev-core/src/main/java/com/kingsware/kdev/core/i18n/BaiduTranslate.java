package com.kingsware.kdev.core.i18n;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.core.util.MD5Utils;
import com.kingsware.kdev.core.util.ThreadUtils;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 百度翻译
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/29 10:20 下午
 */
public class BaiduTranslate {

    /** 接口地址 **/
    private final static String url = "http://api.fanyi.baidu.com/api/trans/vip/translate";

    /** appid **/
    private final static String appid = "20200624000504919";

    /** 密钥 **/
    private final static String securityKey = "wUmVbXTCgte52AOc9PrV";

    @Data
    static class InnerTransResult {
        /** 原文 **/
        private String src;
        /** 译文 **/
        private String dst;
    }

    @Data
    public static class TranslateResult {
        /** 源语言 **/
        private String from;
        /** 目标语言	**/
        private String to;
        /** 响应码 **/
        @JsonProperty("error_code")
        private Integer errorCode;
        /** 翻译结果 **/
        @JsonProperty("trans_result")
        private List<InnerTransResult> transResult;

    }


    /**
     * 翻译
     * @param query
     * @param from
     * @param to
     * @return
     */
    public static String getTransResult(String query, String from, String to) {
        try {
            Map<String, String> params = new HashMap<String, String>();
            params.put("q", query);
            params.put("from", from);
            params.put("to", to);
            params.put("appid", appid);
            // 随机数
            String salt = String.valueOf(System.currentTimeMillis());
            params.put("salt", salt);
            // 签名
            String src = appid + query + salt + securityKey; // 加密前的原文
            params.put("sign", MD5Utils.md5(src));
            String dst = HttpGet.get(url, params);
            TranslateResult result = JsonUtil.toBean(dst, TranslateResult.class);
            return result.getTransResult().get(0).getDst();
        }
        catch (Exception e) {
            ThreadUtils.sleep(1001);
            return getTransResult(query, from, to);
        }

    }



}
