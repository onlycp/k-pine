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
        }
        // 流程方式
        else {
            // 获取视图模型
            KFlowContext context = KFlowContext.createBaseContext(StringUtils.isNotEmpty(api.getInArgv()) ? api.getInArgv() : "{}", StringUtils.isNotEmpty(api.getOutArgv()) ? api.getOutArgv() : "{}");
            // 处理请求变量
            Map<String, Object> argvMap = getRequestParams(api, path, request);
//            // 加入Req
            // 处理类
            context.setHandleClass(api.getApiResultHandler());
            // 调用流程
            KdbFlowResult result = KdbFlowExecutor.getInstance().execute(api.getApiFlowId(), api.getSubFlowIds(), argvMap, context);
            // 转为api格式
            if (result.getType().equals(KFlowConstant.RESULT_JSON)) {
                responseJson(response, FlowUtils.toJsonResult(result.getData()));
            }
            else if (result.getType().equals(KFlowConstant.RESULT_EXCEL)) {
                ExcelWorker.getInstance().writeToWeb((KExcel) result.getData());
            }
            else if (result.getType().equals(KFlowConstant.RESULT_FILE)) {
                KdbRetFile kdbRetFile = (KdbRetFile) result.getData();
                ServletUtil.responseFile(kdbRetFile.getFileName(), kdbRetFile.getData());
            }
        }
    }

    /**
     * 获取请求参数
     * @param api       api信息
     * @param path      路径
     * @return          请求参数
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> getRequestParams(ApiInfo api, String path, HttpServletRequest request) {

        Map<String, Object> params = new HashMap<>();
        // 获取query和form-data
        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            String value = request.getParameter(name);
            params.put(name, value);
        }
        // 获取path变量
        Map<String, Object> pathVariables = getPathVariables(path, api.getApiUrl());
        params.putAll(pathVariables);
        // 判断是文件还是raw提交
        String contentType = request.getContentType();

        // 获取文件
        if (StringUtils.isNotEmpty(contentType) && contentType.toLowerCase().contains("multipart/form-data")) {
            MultipartResolver resolver = new StandardServletMultipartResolver();
            MultipartHttpServletRequest multipartHttpServletRequest = resolver.resolveMultipart(request);
            // 获取所有文件
            Map<String, MultipartFile> fileMap = multipartHttpServletRequest.getFileMap();
            for (Map.Entry<String, MultipartFile> multipartFileEntry: fileMap.entrySet()) {
                KFlowUploadFile uploadFile = new KFlowUploadFile();
                Base64.getEncoder().encodeToString("1".getBytes());
                // 原始文件名
                uploadFile.setOriginFileName(multipartFileEntry.getValue().getOriginalFilename());
                // 文件大小
                uploadFile.setFileSize(multipartFileEntry.getValue().getSize());
                // 名称
                uploadFile.setName(multipartFileEntry.getValue().getName());
                // content type
                uploadFile.setContentType(multipartFileEntry.getValue().getContentType());
                // 文件内容
                try {
                    uploadFile.setFileContent(Base64Utils.encodeToString(multipartFileEntry.getValue().getBytes()));
                }
                catch (IOException e) {
                    log.error("文件转换成功，原始文件名:{}，名称:{}，{}", uploadFile.getOriginFileName(), uploadFile.getName(), e );
                }
                // 将文件加入到流程变量中
                params.put(multipartFileEntry.getKey(), uploadFile);
            }
        }
        else {
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
            Map<String, Object> requestMap = new HashMap<>();
            requestMap.put("body", body);
            params.put("request", requestMap);
        }

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
