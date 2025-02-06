package com.kingsware.kdev.core.context;

/**
 * 客户端上下文字
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/21 9:52 上午
 */
public class KClientContext {

    private static final ThreadLocal<ClientInfo> clientInfoThreadLocal = new ThreadLocal<>();

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


    public static void clear() {
        clientInfoThreadLocal.remove();
    }


    /**
     * 获取当前应用的标识
     *
     * @return 当前应用的标识，如果上下文为空则返回null
     */
    public static String getCurrentAppId() {
        // 如果上下文为空，则直接返回null
        if (getContext() == null) {
            return null;
        }
        // 从请求头中获取应用标识
        return getContext().getRequest().getHeader("_request_app");
    }

}
