package com.kingsware.kdev.core.config;

import com.kingsware.kdev.core.cache.api.ApiManager;
import com.kingsware.kdev.core.context.KClientContext;
import com.kingsware.kdev.core.kflow.KFlowContext;
import com.kingsware.kdev.core.kflow.KdbFlowExecutor;
import com.kingsware.kdev.core.util.DateUtils;
import com.kingsware.kdev.sys.model.SysApi;
import org.springframework.stereotype.Component;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;

/**

 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/18 4:53 下午
 */
@Component
public class KFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        // 获取请求路径
        String url = request.getRequestURI();
        // 获取请求方式
        String method = request.getMethod().toLowerCase();
        // 获取上下文路径
        String contextPath = request.getContextPath();
        // 获取配置的接口信息
        SysApi api = ApiManager.getInstance().getApi(method, url, contextPath);
        // 如果找不到对应的接口配置
        if (api == null || api.getCallType() == 1) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        // 流程方式
        else if (api.getCallType() == 2) {
            KFlowContext context = new KFlowContext();
            context.getSystemContext().put("who",  KClientContext.getContext() != null && KClientContext.getContext().getUserInfo()!= null ? KClientContext.getContext().getUserInfo().getId() : "");
            context.getSystemContext().put("username",  KClientContext.getContext() != null && KClientContext.getContext().getUserInfo()!= null ? KClientContext.getContext().getUserInfo().getUsername() : "");
            context.getSystemContext().put("when", DateUtils.getNow());
            KdbFlowExecutor.getInstance().execute(api.getApiFlowId(), new HashMap<>(), context);
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);


    }
}
