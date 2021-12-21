package com.kingsware.kdev.core.context;

import com.kingsware.kdev.core.auth.BaseUserInfo;

/**
 * 客户端上下文字
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/21 9:52 上午
 */
public class KClientContext {

    private static ThreadLocal<ClientInfo> clientInfoThreadLocal = new ThreadLocal<>();

    /**
     * 获取客户端信息
     * @return  客户端信息
     */
    public static ClientInfo getContext() {
        return clientInfoThreadLocal.get();
    }

    /**
     * 设置客户端信息
     * @param clientInfo    客户端信息
     */
    public static void setContext(ClientInfo clientInfo) {
        clientInfoThreadLocal.set(clientInfo);
    }

}
