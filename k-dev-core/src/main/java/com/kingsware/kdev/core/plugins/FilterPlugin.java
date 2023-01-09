package com.kingsware.kdev.core.plugins;

import com.kingsware.kdev.core.config.MyHttpServletRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * sso插件
 * @author chenp
 * @date 2023-01-05
 */
public interface FilterPlugin {


    /**
     * This function is called by the servlet container when the filter is invoked.
     *
     * @param requestWrapper A wrapper around the original request object.
     * @param responseWrapper This is a wrapper around the response object. It is used to cache the response body.
     */
    void doFilter(MyHttpServletRequestWrapper requestWrapper, ContentCachingResponseWrapper responseWrapper);

}
