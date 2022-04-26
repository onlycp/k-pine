package com.kingsware.kdev.core.kflow;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingsware.kdev.core.auth.AppAuthProperties;
import com.kingsware.kdev.core.auth.BaseUserInfo;
import com.kingsware.kdev.core.auth.TokenUtil;
import com.kingsware.kdev.core.cache.api.ApiInfo;
import com.kingsware.kdev.core.cache.api.ApiManager;
import com.kingsware.kdev.core.context.ClientInfo;
import com.kingsware.kdev.core.context.KClientContext;
import com.kingsware.kdev.core.excel.ExcelWorker;
import com.kingsware.kdev.core.excel.KExcel;
import com.kingsware.kdev.core.kflow.bean.KFlowUploadFile;
import com.kingsware.kdev.core.kflow.bean.KdbFlowResult;
import com.kingsware.kdev.core.kflow.bean.KdbRetFile;
import com.kingsware.kdev.core.util.ServletUtil;
import com.kingsware.kdev.core.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**

 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/18 4:53 下午
 */
@Component
@Slf4j
public class KFlowFilter implements Filter {

    private static final String RET_TYPE_KEY = "ret@type";

    @Resource
    private ObjectMapper objectMapper;
    @Resource
    private KFlowProperties kFlowProperties;
    @Resource
    private AppAuthProperties appAuthProperties;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //获取用户信息
        String ip = ServletUtil.getClientIp(request);
        String token = TokenUtil.getTokenString(request);
        // 获取用户信息
        BaseUserInfo userInfo = TokenUtil.getUserInfoByToken(token, appAuthProperties.getTokenSecret());
        // 组装clientInfo
        ClientInfo clientInfo = new ClientInfo();
        clientInfo.setIp(ip);
        clientInfo.setToken(token);
        clientInfo.setRequest(request);
        clientInfo.setResponse(response);
        clientInfo.setUrl(request.getRequestURI());
        clientInfo.setUserInfo(userInfo);
        // 写到线程变量
        KClientContext.setContext(clientInfo);
        // 如果不启用流程
        if (!kFlowProperties.isEnable()) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        // 获取请求路径
        String url = request.getRequestURI();
        // 获取请求方式
        String method = request.getMethod().toLowerCase();
        // 获取上下文路径
        String contextPath = request.getContextPath();
        if (url.startsWith("/api"))  {
            contextPath = "/api";
        }
        // 获取配置的接口信息
        String path = url.replace(contextPath, "");
        ApiInfo api = ApiManager.getInstance().getApi(method, path);
        // 如果找不到对应的接口配置
        if (api == null || api.getCallType() == 1) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        // 获取视图模型
        KFlowContext context = KFlowContext.createBaseContext(StringUtils.isNotEmpty(api.getInArgv()) ? api.getInArgv() : "{}", StringUtils.isNotEmpty(api.getOutArgv()) ? api.getOutArgv() : "{}");
        // 处理请求变量
        Map<String, Object> argvMap = ServletUtil.getRequestParams(api, path, request);
        // 加入Req
        // 处理类
        context.setHandleClass(api.getApiResultHandler());
        // 调用流程
        KdbFlowResult result = KdbFlowExecutor.getInstance().execute(api.getApiFlowId(), api.getSubFlowIds(), argvMap, context);
        // 转为api格式
        switch (result.getType()) {
            case KFlowConstant.RESULT_JSON:
                ServletUtil.responseJson(response, FlowUtils.toJsonResult(result.getData()));
                break;
            case KFlowConstant.RESULT_EXCEL:
                ExcelWorker.getInstance().writeToWeb(response, (KExcel) result.getData());
                break;
            case KFlowConstant.RESULT_FILE:
                KdbRetFile kdbRetFile = (KdbRetFile) result.getData();
                ServletUtil.responseFile(response, kdbRetFile.getFileName(), kdbRetFile.getData());
                break;
        }
    }

    private void initContext(ServletRequest servletRequest, ServletResponse servletResponse) {

    }
}
