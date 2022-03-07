package com.kingsware.kdev.core.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * servlet工具类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/3/4 3:53 下午
 */
public class ServletUtil {

    private ServletUtil() {
    }

    /**
     * 获取http请求
     * @return http请求
     */
    public static HttpServletRequest request() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    /**
     * 获取http请求
     * @return http响应
     */
    public static HttpServletResponse response() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
    }
}
