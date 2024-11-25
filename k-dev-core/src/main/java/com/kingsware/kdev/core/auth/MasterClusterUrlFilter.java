package com.kingsware.kdev.core.auth;

import com.kingsware.kdev.core.cache.instance.InstanceManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
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
@Order(1)
@Slf4j
public class MasterClusterUrlFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 如果不是主节点，则转发到/api/v1/kubbo/cluster页面
        if (!InstanceManager.getInstance().isActiveCluster() && !request.getRequestURI().contains("/api/v1/kubbo/cluster")) {
            response.sendRedirect("/api/v1/kubbo/cluster");
            return;
        }
        // 调用下一个过滤器
        filterChain.doFilter(request, response);
    }
}
