package com.kingsware.kdev.core.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2024/9/23 10:54
 */
@Component
@Slf4j
public class DynamicUrlFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取原始请求 URL
        String originalUrl = request.getRequestURI();
        //log.info("Original URL: {}", originalUrl);

        // 例如：根据某个规则重写 URL
        // 这里可以根据实际业务逻辑修改为您想要的 URL
        String rewrittenUrl =  originalUrl; // 示例：将原始 URL 前面加上 "/new/path"
        //log.info("Rewritten URL: {}", rewrittenUrl);

        // 使用 HttpServletRequestWrapper 包装原始请求，重写 getRequestURI() 方法
        HttpServletRequestWrapper wrappedRequest = new HttpServletRequestWrapper(request) {
            @Override
            public String getRequestURI() {
                return rewrittenUrl; // 返回修改后的 URL
            }
        };
        // 将包装后的请求传递给过滤器链
        filterChain.doFilter(wrappedRequest, response);
    }
}
