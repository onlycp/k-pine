package com.kingsware.kdev.core.plugins;

import java.util.Map;

/**
 * 内容存储插件
 */
public interface FaasChannelPlugin {


    /**
     * 发送
     * @return  保存类型
     */
    String send(String apiUrl, String body, Map<String, String> headerMap);

    /**
     * 获取通道名称
     * @return
     */
    String name();
}
