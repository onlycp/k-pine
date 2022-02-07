package com.kingsware.kdev.core.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @author andyzheng
 * @version 1.0.0
 * @description: ip 地址处理工具类
 * @date 2021/12/29 17:52
 */
public class IpAddressUtils {

    private IpAddressUtils() { }

    /**
     * 通过Request获取ip
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String remoteAddr = "";

        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }

        return remoteAddr;
    }
}
