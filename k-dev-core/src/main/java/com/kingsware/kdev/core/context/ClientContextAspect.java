package com.kingsware.kdev.core.context;

import com.kingsware.kdev.core.util.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * 环境变量拦截器
 * 将相关的环境信息保存到上下文中
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/21 10:09 上午
 */
@Aspect
@Component
@Order(0)
public class ClientContextAspect {

    /** 令牌请求头**/
    public static final String AUTH_HEADER = "Authorization";
    /** 令牌前缀**/
    public static final String AUTH_PREFIX = "Bearer ";


    @Around("execution(public * com.kingsware..web.*.*(..))")
    public Object process(ProceedingJoinPoint pjd) throws Throwable {
        // request
        HttpServletRequest request =  ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        // response
        HttpServletResponse response =  ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
        // 获取令牌
        String token = getTokenString(request);
        // 获取ip
        String ip = getClientIp(request);
        // 组装clientInfo
        ClientInfo clientInfo = new ClientInfo();
        clientInfo.setIp(ip);
        clientInfo.setToken(token);
        clientInfo.setRequest(request);
        clientInfo.setResponse(response);
        clientInfo.setUrl(request.getRequestURI());
        // 写到线程变量
        KClientContext.setContext(clientInfo);
        // 放开
        return pjd.proceed();
    }

    /**
     * 获取访问者IP
     * @return  ip
     */
    public static String getClientIp(HttpServletRequest request) {
        String ipFromNginx = request.getHeader("X-Real-IP");
        return StringUtils.isEmpty(ipFromNginx) ? request.getRemoteAddr() : ipFromNginx;
    }

    /**
     * 获取令牌字符串
     * @return 令牌
     */
    public static String getTokenString(HttpServletRequest request) {
        // 获取对应的请求头
        String auth = request.getHeader(AUTH_HEADER);
        // 如果auth为空，则提示用户登录
        if (StringUtils.isEmpty(auth)) {
            return "";
        }
        // 获取当前令牌
        return auth.replace(AUTH_PREFIX, "");
    }

}
