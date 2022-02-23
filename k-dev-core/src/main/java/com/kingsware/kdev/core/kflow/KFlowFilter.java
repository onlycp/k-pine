package com.kingsware.kdev.core.kflow;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.cache.api.ApiInfo;
import com.kingsware.kdev.core.cache.api.ApiManager;
import com.kingsware.kdev.core.util.DateUtils;
import com.kingsware.kdev.core.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
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

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 如果不启用流程
        if (!kFlowProperties.isEnable()) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        // 获取请求路径
        String url = request.getRequestURI();
        // 获取请求方式
        String method = request.getMethod().toLowerCase();
        // 获取上下文路径
        String contextPath = request.getContextPath();
        // 获取配置的接口信息
        String path = url.replace(contextPath, "");
        ApiInfo api = ApiManager.getInstance().getApi(method, path);
        // 如果找不到对应的接口配置
        if (api == null || api.getCallType() == 1) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        // 流程方式
        else if (api.getCallType() == 2) {
            // 获取视图模型

            KFlowContext context = KFlowContext.createBaseContext();
            // 处理请求变量
            Map<String, Object> argvMap = getRequestParams(api, path, request);
            if (argvMap.containsKey("page") && argvMap.containsKey("pageSize")) {
                int page = Integer.parseInt(argvMap.getOrDefault("page", "1").toString());
                int pageSize = Integer.parseInt(argvMap.getOrDefault("pageSize", "10").toString());
                argvMap.put("start", (page-1)*pageSize + "") ;
                argvMap.put("limit", pageSize + "");
            }
            // 加入Request信息
            argvMap.put("request.url", url);
            argvMap.put("request.time", DateUtils.getNow());
            // 处理类
            context.setHandleClass(api.getApiResultHandler());
            // 调用流程
            Object result = KdbFlowExecutor.getInstance().execute(api.getApiFlowId(), argvMap, context);
            // 转为api格式
            Object ret = KdbFlowExecutor.getInstance().toApiResult(result);
            responseJson(response, ret);
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);

    }

    /**
     * 获取请求参数
     * @param api       api信息
     * @param path      路径
     * @return          请求参数
     */
    private Map<String, Object> getRequestParams(ApiInfo api, String path, HttpServletRequest request) {

        Map<String, Object> params = new HashMap<>();
        // 获取query和form-data
        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            String value = request.getParameter(name);
            params.put(name, value);
        }
        // 获取body
        String body = getBody(request);
        if (StringUtils.isNotEmpty(body)) {
            try {
                Map<String, Object> argv = objectMapper.readValue(body, Map.class);
                params.putAll(argv);
            }
            catch (Exception e) {
                log.error("error", e);
            }
        }
        // 将body加到变量中
        params.put("request.body", body);
        // 获取path变量
        Map<String, Object> pathVariables = getPathVariables(path, api.getApiUrl());
        params.putAll(pathVariables);
        // 返回
        return params;
    }

    /**
     * 获取Body
     * @param request   Http请求
     * @return  返回body
     */
    private String getBody(HttpServletRequest request) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder("");
        try {
            br = request.getReader();
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
            br.close();
        }
        catch (IOException e) {
            log.error("error", e);
        }
        finally {
            if (null != br) {
                try {
                    br.close();
                }
                catch (IOException e) {
                    log.error("error", e);
                }
            }
        }
        return sb.toString();
    }

    /**
     * 输出json
     * @param object    对象
     */
    private void responseJson(HttpServletResponse response, Object object) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try (PrintWriter out = response.getWriter()) {
            out.append(objectMapper.writeValueAsString(object));
        } catch (IOException e) {
            log.error("error", e);
        }
    }

    /**
     *
     * @param path          路径
     * @param patternUrl    匹配路径
     * @return              返回路径变量
     */
    private Map<String, Object> getPathVariables(String path, String patternUrl) {
        Map<String, Object> variables = new HashMap<>();
        // 匹配路径
        String[] pUrls = patternUrl.split("/");
        // 前端传过来的路径
        String[] uiUrls = path.split("/");
        // 取最小长度
        int minLength = Math.min(pUrls.length, uiUrls.length);;
        for (int i = 0; i< minLength; i++) {
            String pVar = pUrls[i].trim();
            String uVar = uiUrls[i].trim();
            if (pVar.startsWith("{") && pVar.endsWith("}")) {
                String realName = pVar.substring(1, pVar.length()-1);
                variables.put(realName, uVar);
            }
        }
        return variables;
    }
}
