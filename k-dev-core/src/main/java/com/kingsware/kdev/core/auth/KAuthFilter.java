package com.kingsware.kdev.core.auth;

import com.kingsware.kdev.core.bean.ApiDefine;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.cache.api.ApiInfo;
import com.kingsware.kdev.core.cache.api.ApiManager;
import com.kingsware.kdev.core.cache.controller.ControllerManager;
import com.kingsware.kdev.core.cache.license.LicenseManager;
import com.kingsware.kdev.core.cache.open.OpenAccountInfo;
import com.kingsware.kdev.core.cache.open.OpenApiManager;
import com.kingsware.kdev.core.cache.permssion.PermissionManager;
import com.kingsware.kdev.core.cache.session.SessionManager;
import com.kingsware.kdev.core.config.MyHttpServletRequestWrapper;
import com.kingsware.kdev.core.context.ClientInfo;
import com.kingsware.kdev.core.context.KClientContext;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.enums.RetEnum;
import com.kingsware.kdev.core.excel.ExcelWorker;
import com.kingsware.kdev.core.excel.KExcel;
import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.exception.ForbiddenException;
import com.kingsware.kdev.core.exception.LicenseException;
import com.kingsware.kdev.core.exception.UnauthorizedException;
import com.kingsware.kdev.core.i18n.I18n;
import com.kingsware.kdev.core.kflow.*;
import com.kingsware.kdev.core.kflow.bean.KdbFlowResult;
import com.kingsware.kdev.core.kflow.bean.KdbRetFile;
import com.kingsware.kdev.core.kmq.KmqMessageCenter;
import com.kingsware.kdev.core.model.SysLoginLog;
import com.kingsware.kdev.core.model.SysOperateLog;
import com.kingsware.kdev.core.orm.exception.OrmDbException;
import com.kingsware.kdev.core.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.*;

/**

 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/18 4:53 下午
 */
@Component
@Slf4j
public class KAuthFilter implements Filter {

    @Resource
    private KFlowProperties kFlowProperties;
    @Resource
    private AppAuthProperties appAuthProperties;
    @Resource
    private ControllerManager controllerManager;
    /** 忽略的接口 **/
    private static final String ignoreApi = ":open";
    /** 开放接口 **/
    private static final String openApiFlag = ":open:";
    /** 签名噪音 **/
    private final Set<String> signNonces = new TreeSet<String>();

    @Value("${app.auth.log-ignore-tags:websocket,ping,session}")
    private String logIgnoreTags;




    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        // 获取请求路径
        String url = request.getRequestURI();
//        if (1 ==1) {
//            filterChain.doFilter(request, response);
//            return;
//        }

        MyHttpServletRequestWrapper wrapperRequest = null;
        String requestBody = "{}";
        if (servletRequest != null) {
            wrapperRequest = new MyHttpServletRequestWrapper((HttpServletRequest) request);
            String contentType = request.getContentType();
            if (contentType == null || !contentType.toLowerCase().contains("multipart/form-data")) {
                wrapperRequest.getInputStream();
                requestBody = new String(wrapperRequest.getRequestBody(), StandardCharsets.UTF_8);
            }

        }
        ContentCachingResponseWrapper wrapperResponse = new ContentCachingResponseWrapper(response);

        // 获取请求方式
        String method = request.getMethod().toLowerCase();
        // 获取上下文路径
        String contextPath = request.getContextPath();
        String apiCode = "";
        // 接口信息
        ApiInfo api = null;
        ApiDefine apiDefine = null;
        int responseCode = 200;
        // 是否开放接口
        boolean isOpenApi = false;
        // 错误信息
        String errorMessage = "";
        // 请求参数
        Map<String, Object> argvMap = new HashMap<>();
        // 调用方式
        CallType callType = null;
        // 请求时间
        String now = DateUtils.getNow();
        long t1 = System.currentTimeMillis();
        try {
            if (url.contains("//")) {
                url = url.replaceAll("//", "/");
            }
            String apiUrlPrefix = request.getContextPath() + "/api";
            // 如果是静态文件
            if (!url.startsWith(apiUrlPrefix) ) {
                filterChain.doFilter(wrapperRequest, response);
                return;
            }
            if (url.startsWith(apiUrlPrefix))  {
                contextPath = apiUrlPrefix;
            }
            // 获取配置的接口信息
            String path = url.replaceFirst(contextPath, "");
            api = ApiManager.getInstance().getApi(method, path);
            apiDefine = getApiDefine(request, response);
            // 初始化青松上下文
            initContext(request, response);
            // 如果是openapi，表示是ignore
            boolean ignore = false;
            callType = CallType.CONTROLLER;
            argvMap = ServletUtil.getRequestParams(api, path, request, requestBody);
            // 流程调用方式
            if (api != null && api.getCallType() == 2 && kFlowProperties.isEnable()) {
                callType = CallType.KFLOW;
                apiCode = api.getApiCode();
                // 是否允许跳过权限
                ignore = StringUtils.isNotEmpty(api.getApiCode()) && apiCode.startsWith(ignoreApi);
            }
            else {
                if (apiDefine != null) {
                    apiCode = apiDefine.getApiCode();
                    ignore = apiDefine.isIgnore();
                }
                else {
                    filterChain.doFilter(wrapperRequest, response);
                    return;
                }
            }
            // 判断是否开放接口
            isOpenApi = StringUtils.isNotEmpty(apiCode) && apiCode.startsWith(openApiFlag) && api != null;
            if (isOpenApi) {
                // 处理请求变量
                this.checkOpenApi(api, argvMap);
            }
            else {
                this.checkPermission(request, response, ignore, apiCode);
            }
            // 校验license
            if (!ignore) {
                checkLicense();
            }
            // 根据不同的调用类型，进行调用相关处理
            if (callType == CallType.CONTROLLER) {
                filterChain.doFilter(wrapperRequest, wrapperResponse);
            }
            else {
                callByFlow(request, response, api, path, argvMap);
            }
        }
        catch (BusinessException e) {
            errorMessage = e.getMessage();
            responseCode = RetEnum.SERVICE_FAIL.getCode();
            ServletUtil.responseJson(response, BaseRet.failMessage(e.getMessage()));
        }
        catch (OrmDbException e) {
            errorMessage = e.getMessage();
            responseCode = RetEnum.SERVICE_FAIL.getCode();
            String devMode = SpringContext.getProperties("app.mode.dev", "true");
            if ("true".equals(devMode)) {
                ServletUtil.responseJson(response, BaseRet.failMessage(e.getMessage(), e.getKlog(), e.getExceptionTrace()));
            }
            else {
                ServletUtil.responseJson(response, BaseRet.failMessage(e.getMessage()));
            }

        }
        catch (UnauthorizedException e) {
            errorMessage = e.getMessage();
            responseCode = RetEnum.UNAUTHORIZED.getCode();
            log.error("用户未登录，接口路径:{}, 请求方法:{}， 异常信息:{}", url, method, e.getMessage());
            ServletUtil.responseJson(response, BaseRet.fail(e.getMessage(), RetEnum.UNAUTHORIZED.getCode()));
        }
        catch (LicenseException e) {
            errorMessage = e.getMessage();
            responseCode = RetEnum.LICENSE_FAIL.getCode();
            ServletUtil.responseJson(response, BaseRet.fail(e.getMessage(), RetEnum.LICENSE_FAIL.getCode()));
        }
        catch (ForbiddenException e) {
            errorMessage = e.getMessage();
            responseCode = RetEnum.FORBIDDEN.getCode();
            log.error("接口无权限，接口路径:{}, 请求方法:{}", url, method);
            String message = MessageFormat.format("很抱歉，您没有此接口的访问权限! 接口地址： {0}, 接口编码:{1}", url, apiCode);
            ServletUtil.responseJson(response, BaseRet.fail(message, RetEnum.FORBIDDEN.getCode()));
        }
        catch (ServletException e) {
            //文件下载的不作处理
        }
        catch (Exception e) {
            errorMessage = e.getMessage();
            responseCode = RetEnum.SERVICE_FAIL.getCode();
            log.error("error", e);
            ServletUtil.responseJson(response, BaseRet.failMessage(ExceptionUtils.getStackTrace(e)));
        }
        finally {
            int takeTime = (int)(System.currentTimeMillis()-t1);
            if (isOpenApi) {
                String accessId = argvMap.getOrDefault("accessId", "").toString();
                Map<String, Object> recordMap = new HashMap<>();
                recordMap.put("id", StringUtils.getUUID());
                recordMap.put("apiName", api.getApiName());
                recordMap.put("accessId", accessId);
                recordMap.put("requestParams", JsonUtil.toJson(argvMap));
                recordMap.put("requestTime", now);
                recordMap.put("useTime", System.currentTimeMillis() - t1);
                recordMap.put("success", StringUtils.isNotEmpty(errorMessage) ? 0: 1);
                recordMap.put("errorMessage", errorMessage);
                recordMap.put("requestIp", ServletUtil.getClientIp(request));
                KmqMessageCenter.getInstance().produce("openApiQueue",  JsonUtil.toJson(recordMap));
            }
            if (callType != null && !hasIgnoreTag(url)) {
                if ((callType == CallType.CONTROLLER && apiDefine != null) || callType == CallType.KFLOW) {

                    String opertator = KClientContext.getContext().getUserInfo() != null ? KClientContext.getContext().getUserInfo().getUsername() : "";
                    if (apiDefine != null &&"登录".equals(apiDefine.getName())) {
                        opertator = argvMap.get("username").toString();
                    }
                    // 保存操作日志
                    SysOperateLog operateLog = new SysOperateLog();
                    operateLog.setOperator(opertator);
                    operateLog.setAction(callType == CallType.CONTROLLER ? apiDefine.getName(): api.getApiName());
                    operateLog.setModule(callType == CallType.CONTROLLER ? apiDefine.getModule(): api.getApiTags());
                    operateLog.setIp(KClientContext.getContext().getIp());
                    operateLog.setTimes(takeTime);
                    operateLog.setUrl(KClientContext.getContext().getUrl());
                    operateLog.setResponseCode(responseCode);
                    operateLog.setResponseMessage(errorMessage);
                    operateLog.setOperateTime(new Timestamp(System.currentTimeMillis()));
                    operateLog.setMethod(callType == CallType.CONTROLLER ? apiDefine.getCallMethod() + "()": api.getApiName());
                    operateLog.setRequestMethod(KClientContext.getContext().getRequest().getMethod());
                    operateLog.setResponseBody(ServletUtil.getResponseBody(wrapperResponse));
                    operateLog.setRequestBody(requestBody);
                    try {
                        wrapperResponse.copyBodyToResponse();
                    }
                    catch (Exception ignored) {
                    }
                    KmqMessageCenter.getInstance().produce("t_operate_log", JsonUtil.toJson(operateLog) );
                    // 保存登录日志
                    if (callType == CallType.CONTROLLER && "登录".equals(apiDefine.getName())) {
                        // 获取表单信息
                        SysLoginLog loginLog = new SysLoginLog();
                        loginLog.setOperator(opertator);
                        loginLog.setIp(KClientContext.getContext().getIp());
                        loginLog.setResponseCode(responseCode);
                        loginLog.setTimes(takeTime);
                        loginLog.setResponseMessage(errorMessage);
                        loginLog.setOperateTime(new Timestamp(System.currentTimeMillis()));
                        KmqMessageCenter.getInstance().produce("t_login_log", JsonUtil.toJson(loginLog) );
                    }
                }

            }
        }

    }
    /**
     *  判断是否存在忽略的日志标签
     * @param url   url
     * @return      是/否
     */
    private boolean hasIgnoreTag(String url) {
        String[] arr = logIgnoreTags.split(",");
        for (String str: arr) {
            if (url.toLowerCase().contains(str.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    private void checkLicense() {
        int status = LicenseManager.getInstance().getStatus();
        if ( status == -1) {
            throw new LicenseException(I18n.t("license.error.-1", "非法授权"));
        }
        else if (status == 0) {
            throw new LicenseException(I18n.t("license.error.0", "许可证非授权"));
        }
        else if (status == 1) {
            throw new LicenseException(I18n.t("license.error.1", "许可证未生效"));
        }
        else if (status == 3) {
            throw new LicenseException(I18n.t("license.error.3", "许可证已过期"));
        }
    }



    private void  callByFlow(HttpServletRequest request, HttpServletResponse response, ApiInfo api, String path, Map<String, Object> argvMap) {
        // 获取视图模型
        KFlowContext context = KFlowContext.createBaseContext(StringUtils.isNotEmpty(api.getInArgv()) ? api.getInArgv() : "{}", StringUtils.isNotEmpty(api.getOutArgv()) ? api.getOutArgv() : "{}");
        // 调用流程
        KdbFlowResult result = KdbFlowExecutor.getInstance().execute(api.getApiFlowId(), api.getSubFlowIds(), argvMap, context, false);
        // 转为api格式
        switch (result.getType()) {
            case KFlowConstant.RESULT_JSON:
                ServletUtil.responseJson(response, FlowUtils.toJsonResult(result.getData(), result.getLog()));
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

    /**
     * 初始化青松上下文
     * @param request   请求
     * @param response  响应
     */
    private void initContext(HttpServletRequest request, HttpServletResponse response) {
        //获取用户信息
        String ip = ServletUtil.getClientIp(request);
        String token = TokenUtil.getTokenString(request);
        // 组装clientInfo
        ClientInfo clientInfo = new ClientInfo();
        clientInfo.setIp(ip);
        clientInfo.setToken(token);
        clientInfo.setRequest(request);
        clientInfo.setResponse(response);
        clientInfo.setUrl(request.getRequestURI());
        // 写到线程变量
        KClientContext.setContext(clientInfo);
    }

    /**
     * 检测权限
     * @param request   请求
     * @param response  响应
     */
    private void checkPermission(HttpServletRequest request, HttpServletResponse response, boolean ignore, String apiCode) {
        String token = TokenUtil.getTokenString(request);
        // 获取用户信息
        BaseUserInfo userInfo = null;
        // 如果启用权限拦截的，通过带验证的获取用户信息，这样如果权限验证有问题，就会自动抛出异常
        if (Boolean.TRUE.equals(appAuthProperties.getEnable()) && !ignore) {
            userInfo =  TokenUtil.getUserInfoByToken(token, appAuthProperties.getTokenSecret(), appAuthProperties.getIss(),
                    KClientContext.getContext().getIp(), appAuthProperties.getTokenExpireMinutes(), appAuthProperties.getMockSessionExpireMinutes());
            // 校验接口编码
            if(!PermissionManager.getInstance().hasPermission(userInfo.getRoleIds(),apiCode)) {
                throw new ForbiddenException(I18n.t("permission.api-forbidden", "接口无权限"));
            }

        }
        else {
            userInfo = TokenUtil.getUserInfoByToken(token, appAuthProperties.getTokenSecret());
        }
        // 保存用户信息
        KClientContext.getContext().setUserInfo(userInfo);
        if (userInfo != null) {
            // 检查是否只有一个会话
            if (appAuthProperties.getLoginSessionOne()) {
                if (!SessionManager.getInstance().checkSession(userInfo.getId(), KClientContext.getContext().getToken())) {
                    throw new UnauthorizedException(I18n.t("auth. unauthorized-e007", "用户已在别处登录"));
                }
            }
            // 是否要更新过期时间
            boolean updateExpired = !KClientContext.getContext().getUrl().endsWith("/ping");
            // 更新活动时间
            SessionManager.getInstance().updateActiveTime(userInfo.getId(), KClientContext.getContext().getToken(), appAuthProperties.getMockSessionExpireMinutes(), updateExpired);

        }

    }

    /**
     * 校验开放api权限
     * @param apiInfo   接口信息
     */
    public void checkOpenApi(ApiInfo apiInfo, Map<String, Object> params) {
        // 获取接入商id
        String accessId = params.getOrDefault("accessId", "").toString();
        if (StringUtils.isEmpty(accessId)) {
            throw BusinessException.serviceThrow("接入商ID为空！");
        }
        if (!OpenApiManager.getInstance().hasAccess(accessId)) {
            throw BusinessException.serviceThrow("接入商不存在！");
        }
        if (!OpenApiManager.getInstance().hasOpenApi(accessId, apiInfo.getApiCode())) {
            throw BusinessException.serviceThrow("接口未授权");
        }
        // 验签，如果需要验签，传输参数必须包括 timestamp(精确到毫秒)、sign(签名值 )、signNonce(签名噪音)
        // 签名算法
        // 假设参数有 a=1, b=2, c=3, timestamp=1654084663000 签名密钥:  signSecret = BSfNWCZrrSRphRhX
        // 第一步： 参数升序
        // 第二步： 参数拼接起来， 即 var str = "a=1&b=2&c=3&timestamp=1654084663000"
        // 第三步： 拼接签名密钥, 用@字符拼接起来  即  var str2 =  str1 + "@" +signSecret ,即a=1&b=2&c=3@BSfNWCZrrSRphRhX
        // 第四步， 使用md5进行计算签值 var sign = md5(str2)， 即 5802F5AC1AD0C5948CFE981481F0ADA7
        // 第五步， 在参数里加入 sign=5802F5AC1AD0C5948CFE981481F0ADA7


        OpenAccountInfo openAccountInfo = OpenApiManager.getInstance().getAccount(accessId);
        // 如果需要验签
        if (openAccountInfo.getValidateSign() == 1) {
            // 获取时间
            if (!params.containsKey("timestamp")) {
                throw BusinessException.serviceThrow("缺少时间戳参数: timestamp");
            }
            if (!params.containsKey("sign")) {
                throw BusinessException.serviceThrow("缺少签名值: sign");
            }
            if (!params.containsKey("signNonce")) {
                throw BusinessException.serviceThrow("缺少签名噪音: signNonce");
            }
            long timestamp = Long.parseLong(params.get("timestamp").toString());
            // 校验时间，超过5分钟的认为无效
            if (Math.abs(timestamp - System.currentTimeMillis()) > 1000*60*5)  {
                throw BusinessException.serviceThrow("请求时间已过期");
            }
            // 签名噪音
            String signNonce = params.get("signNonce").toString();
            if (signNonces.contains(signNonce)) {
                throw BusinessException.serviceThrow("签名噪音不能重复使用");
            }
            addSignNonce(signNonce);

            String sign = params.get("sign").toString();

            // 排序map
            Map<String, Object> toSignMap = new HashMap<>();
            params.forEach((k, v) -> {
                if (!k.equals("request") && !k.equals("sign")) {
                    toSignMap.put(k, v.toString());
                }
            });
            // 计算签名值
            String calcSign = SignUtil.getSign(toSignMap, openAccountInfo.getSignKey());
            // 对比签名
            if (!calcSign.equalsIgnoreCase(sign)) {
                throw BusinessException.serviceThrow("签名值不正确，传输的签名值:" + sign +", 计算值:" + calcSign);
            }

        }

    }

    /**
     * 添加签名噪音
     * @param nonce  噪音
     */
    private void addSignNonce(String nonce) {
        // 只存最新5000个, 达到5000时就清0
        if (signNonces.size()>=5000) {
            signNonces.clear();
        }
        signNonces.add(nonce);
    }

    /**
     * 获取控制器的api定义
     * @param request   请求
     * @param response  响应
     * @return  api定义
     */
    private ApiDefine getApiDefine(HttpServletRequest request, HttpServletResponse response) {
        // 获取请求路径
        String url = request.getRequestURI();
        // 获取请求方式
        String method = request.getMethod().toLowerCase();
        // 获取api
        return controllerManager.getApiDefine(method, url);
    }


}
