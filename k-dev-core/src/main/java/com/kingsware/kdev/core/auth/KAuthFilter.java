package com.kingsware.kdev.core.auth;

import com.kingsware.kdev.core.bean.*;
import com.kingsware.kdev.core.cache.TimedCache;
import com.kingsware.kdev.core.cache.api.ApiInfo;
import com.kingsware.kdev.core.cache.api.ApiManager;
import com.kingsware.kdev.core.cache.api.ApiResultCache;
import com.kingsware.kdev.core.cache.api.ApiResultCacheManager;
import com.kingsware.kdev.core.cache.controller.ControllerManager;
import com.kingsware.kdev.core.cache.license.LicenseManager;
import com.kingsware.kdev.core.cache.open.OpenAccountInfo;
import com.kingsware.kdev.core.cache.open.OpenApiManager;
import com.kingsware.kdev.core.cache.permssion.PermissionManager;
import com.kingsware.kdev.core.cache.refer.PageLoadManager;
import com.kingsware.kdev.core.cache.session.SessionManager;
import com.kingsware.kdev.core.config.MyHttpServletRequestWrapper;
import com.kingsware.kdev.core.config.UiConfig;
import com.kingsware.kdev.core.context.ClientInfo;
import com.kingsware.kdev.core.context.KClientContext;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.cron.DynamicTask;
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
import com.kingsware.kdev.core.mode.AppModeProperties;
import com.kingsware.kdev.core.model.SysLoginLog;
import com.kingsware.kdev.core.model.SysOperateLog;
import com.kingsware.kdev.core.model.SysTask;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.exception.OrmDbException;
import com.kingsware.kdev.core.util.*;
import com.kingsware.kdev.core.util.jWi.JWildcard;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

/**

 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/18 4:53 下午
 */
@Component
@Slf4j
public class KAuthFilter implements Filter {

    @Autowired
    private KflowProperties kflowProperties;
    @Autowired
    private AppAuthProperties appAuthProperties;
    @Autowired
    private ControllerManager controllerManager;
    @Autowired
    private UiConfig uiConfig;


    /** 忽略的接口 **/
    private static final String ignoreApi = ":open";
    /** 开放接口 **/
    private static final String openApiFlag = ":open:";
    /** 页面 **/
    private static final String kPageFlag = "/k-pages/";
    /** 签名噪音 **/
    private final Set<String> signNonces = new TreeSet<String>();

    @Value("${app.auth.log-ignore-tags:websocket,ping,session,executeTask}")
    private String logIgnoreTags;

    @Value("${app.mode.dev:false}")
    private boolean modeDev;

    @Value("#{'${app.ignore.urls:websocket;/eiac;/sys-tool-box}'.split(';')}")
    private List<String> ignoreUrls;

    @Value("#{'${app.cache.urls:/hellworld}'.split(';')}")
    private List<String> apiCacheUrls;

    // 噪点缓存
    private TimedCache<String, Long> noiseCache = new TimedCache<String, Long>();

    private TimedCache<String, KdbFlowResult> apiCache = new TimedCache<String, KdbFlowResult>();

    private RateLimiter rateLimiter = new RateLimiter();

    private final AtomicInteger currentInQueueCount = new AtomicInteger(0);




    /**
     * 判断url是否包括配置的url标识
     * @param url url
     * @return  是否
     */
    private boolean containUrl(HttpServletRequest request, String url) {
        for (String item: ignoreUrls) {
            if (url.contains(item)) {
                return true;
            }
        }
        return false;
    }

    private boolean isCacheUrl(String url) {
        for (String item: apiCacheUrls) {
            if (url.contains(item)) {
                return true;
            }
        }
        return false;
    }
    @Override
    /**
     * A filter function, which is used to intercept all requests.
     *
     * @param servletRequest The request object
     * @param servletResponse The response object of the servlet
     * @param filterChain The filter chain object, which is used to call the next filter in the chain.
     */
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        this.doMyFilter(servletRequest, servletResponse, filterChain);
    }


    public void doMyFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String url = request.getRequestURI();
        try {


            initContext(request, response);
            if (containUrl(request, url) ) {
                filterChain.doFilter(request, response);
                return;
            }
            PageLoadManager.getInstance().startCalculate(request, response);
            // 判断是否静态文件
            String url2 = url;
            if (url.startsWith(request.getContextPath())) {
                url2 = url.substring(request.getContextPath().length());
            }
            if (uiConfig.isStaticsResource(url2)) {
                filterChain.doFilter(request, response);
                ServletUtil.printResponseHeaders(request, response);
                return;
            }
            // 如果是前端路由，则直接返回首页
            if(uiConfig.isFrontRouter(url2, request) && !url.contains(kPageFlag)) {
                uiConfig.redirectToIndex(request, response);
                return;
            }
            String requestBody = "{}";

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
            MyHttpServletRequestWrapper wrapperRequest = null;
            ContentCachingResponseWrapper wrapperResponse = null;
            String path = "";
            // 处理限流
            int limit = SpringContext.getInt("app.rate-limit.max", 300);
            if (!rateLimiter.tryAcquire(limit)) {
                log.info("Too many requests at the moment. Please try again later. {} > {} ",  rateLimiter.getCount(), limit);
                ServletUtil.responseJson((HttpServletResponse)servletResponse, BaseRet.failMessage("Too many requests at the moment. Please try again later."));
                return;
            }
            try {
                // 计数器
                int requestInQueueMax = SpringContext.getInt("app.request-in-queue.max", 80);
                if (currentInQueueCount.incrementAndGet() > requestInQueueMax) {
                    log.info("The current request queue is too long. Please try again later. {} > {} ", currentInQueueCount.get(), requestInQueueMax);
                    ServletUtil.responseJson((HttpServletResponse)servletResponse, BaseRet.failMessage("The current request queue is too long. Please try again later."));
                    return;
                }

                if (url.contains("//")) {
                    url = url.replaceAll("//", "/");
                }
                // log.info("Take-{}, {}",1,  (System.currentTimeMillis()-tt0));
                String apiUrlPrefix = request.getContextPath() + "/api";
                // 如果是接口或者url文件
                if (url.startsWith(apiUrlPrefix) || url.contains(kPageFlag)) {

                    wrapperRequest = new MyHttpServletRequestWrapper(request);
                    String contentType = request.getContentType();
                    if (contentType == null || !contentType.toLowerCase().contains("multipart/form-data")) {
                        wrapperRequest.getInputStream();
                        requestBody = new String(wrapperRequest.getRequestBody(), StandardCharsets.UTF_8);
                    }
                    if (ServletUtil.isAjaxRequest(request)) {
                        wrapperResponse = new ContentCachingResponseWrapper(response);
                    }
                    if (url.startsWith(apiUrlPrefix)) {
                        contextPath = apiUrlPrefix;
                    }
                    // log.info("Take-{}, {}",2,  (System.currentTimeMillis()-tt0));
                    // 获取配置的接口信息
                    path = url.replaceFirst(contextPath, "");
                    api = ApiManager.getInstance().getApi(method, path);
                    apiDefine = getApiDefine(request, response);
                    // 初始化青松上下文
                    initContext(request, response);
                    // 如果是openapi，表示是ignore
                    boolean ignore = false;
                    // 是否开发
                    boolean dev = false;
                    callType = CallType.CONTROLLER;

                    // 接口定义不存在
                    if (api == null && apiDefine == null) {
                        ServletUtil.responseJson(response, BaseRet.fail(I18n.t("KAuthFilter.apiNotFound", "接口不存在"), RetEnum.SERVICE_FAIL.getCode()));
                        return;
                    }
                    // log.info("Take-{}, {}",3,  (System.currentTimeMillis()-tt0));
                    // 流程调用方式
                    if (api != null && api.getCallType() == 2 && kflowProperties.isEnable()) {
                        callType = CallType.KFLOW;
                        apiCode = api.getApiCode();
                        // 是否允许跳过权限
                        ignore = (StringUtils.isNotEmpty(api.getApiCode()) && apiCode.startsWith(ignoreApi)) || ServletUtil.isRefererRule(request);
                    } else {
                        if (apiDefine != null) {
                            apiCode = apiDefine.getApiCode();
                            ignore = apiDefine.isIgnore() || ServletUtil.isRefererRule(request);
                            dev = apiDefine.isDev() && ! ServletUtil.isRefererRule(request);
                        } else {
                            //                    log.info("上下文-2:{},路径:{}", contextPath, request.getRequestURI()  );
                            filterChain.doFilter(wrapperRequest, response);
                            return;
                        }
                    }
                    if ((!modeDev) && dev) {
                        ServletUtil.responseJson(response, BaseRet.fail(I18n.t("DevApiAspect.error1", "发布模式无权访问此接口"), RetEnum.ONLY_DEV.getCode()));
                        return;
                    }
                    // log.info("Take-{}, {}",4,  (System.currentTimeMillis()-tt0));
                    // 判断是否开放接口
                    isOpenApi = StringUtils.isNotEmpty(apiCode) && apiCode.startsWith(openApiFlag) && api != null;
                    if (isOpenApi) {
                        // 处理请求变量
                        argvMap = ServletUtil.getRequestParams(api, path, request, requestBody, false);
                        this.checkOpenApi(api, argvMap);
                    } else {
                        // 检验噪点
                        String noise = request.getHeader("X-Noise");
                        this.checkNoise(url, noise, TokenUtil.getTokenString(request));
                        this.checkPermission(request, response, ignore, apiCode);
                    }

                    // log.info("Take-{}, {}",5,  (System.currentTimeMillis()-tt0));
                    // 校验license
                    if (!ignore) {
                        checkLicense();
                    }
                    // log.info("Take-{}, {}",6,  (System.currentTimeMillis()-tt0));
                    // 根据不同的调用类型，进行调用相关处理

                    if (callType == CallType.CONTROLLER) {
                        if (wrapperResponse != null) {
                            filterChain.doFilter(wrapperRequest, wrapperResponse);
                        }
                        else {
                            filterChain.doFilter(request, response);
                        }
                    } else {
                        // log.info("Take-{}, {}",7,  (System.currentTimeMillis()-tt0));
                        if (argvMap.isEmpty()) {
                            argvMap = ServletUtil.getRequestParams(api, path, request, requestBody, true);
                        }
                        long my1 = System.currentTimeMillis();
                        long my2 = System.currentTimeMillis();
                        if (api.getApiFlowId().equalsIgnoreCase("a20fd82c126947f9ab3b599001df6126")) {
                            log.info("用时：1");
                        }
                        callByFlow(request, response, api, path, argvMap, requestBody);     if (api.getApiFlowId().equalsIgnoreCase("a20fd82c126947f9ab3b599001df6126")) {
                            log.info("用时：2");
                        }

                        // log.info("Take-{}, {}",8,  (System.currentTimeMillis()-tt0));
                    }

                } else {
                    filterChain.doFilter(request, response);
                }
            }
            catch (BusinessException e) {
                errorMessage = e.getMessage();
                responseCode = RetEnum.SERVICE_FAIL.getCode();
                ServletUtil.responseJson(response, BaseRet.failData(e.getMessage(), e.getData()));
            }
            catch (OrmDbException e) {
                errorMessage = e.getMessage();
                responseCode = RetEnum.SERVICE_FAIL.getCode();
                String devMode = SpringContext.getProperties("app.mode.dev", "true");
//            if ("true".equals(devMode)) {
//                ServletUtil.responseJson(response, BaseRet.failMessage(e.getMessage(), e.getKlog(), e.getExceptionTrace()));
//            }
//            else {
//                ServletUtil.responseJson(response, BaseRet.failMessage(e.getMessage()));
//            }
                if (argvMap.isEmpty()) {
                    argvMap = ServletUtil.getRequestParams(api, path, request, requestBody, false);
                }
                KClientContext.getContext().setArgv(argvMap);
                ServletUtil.responseJson(response, BaseRet.failMessage(e.getMessage(), e.getKlog(), e.getExceptionTrace()));

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
                String message = I18n.t("KAuthFilter.cannotAccess","很抱歉，您没有此接口的访问权限! 接口地址： {0}, 接口编码:{1}", url, apiCode);
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
                // 减数
                currentInQueueCount.decrementAndGet();
                int takeTime = (int)(System.currentTimeMillis()-t1);
                if (argvMap.isEmpty()) {
                    argvMap = ServletUtil.getRequestParams(api, path, request, requestBody, false);
                }
                if (isOpenApi) {
                    this.saveOpenApiLog(api, argvMap, takeTime);
                }
                if (callType != null && !hasIgnoreTag(url)) {
                    if ((callType == CallType.CONTROLLER && apiDefine != null) || callType == CallType.KFLOW) {
                        // 获取日志过滤配置
                        String operateFilter = SpringContext.getBootProperties("app.operate.log-filter","");
                        boolean saveLoginOn = false;
                        if (!StringUtils.isEmpty(operateFilter)) {
                            String[] opFilters = operateFilter.split(";");
                            for (String pattern: opFilters) {
                                if (Pattern.matches(JWildcard.wildcardToRegex(pattern), request.getRequestURI())) {
                                    saveLoginOn  = true;
                                    break;
                                }
                            }

                        } else {
                            saveLoginOn = true;
                        }
                        if (saveLoginOn) {
                            // 保存操作日志
                            String responseBody = wrapperResponse == null ? "" : ServletUtil.getResponseBody(wrapperResponse);

                            this.saveOperateLog(url, request.getMethod(), responseCode, errorMessage, takeTime, JsonUtil.toJson(argvMap), responseBody, callType, api, apiDefine, request);
                        }
                        // 保存登录日志
                        this.saveLoginLog(argvMap, apiDefine, callType, url, responseCode, errorMessage, takeTime);
                    }

                }
                try {
                    if (wrapperResponse != null) {
                        wrapperResponse.copyBodyToResponse();
                    }
                }
                catch (Exception ignored) {
                    ignored.printStackTrace();
                }
            }
        }
        finally {
            if (!containUrl(request, url)) {
                PageLoadManager.getInstance().endCalculate(request, response);
            }

        }



    }


    private void saveLoginLog(Map<String, Object> argvMap, ApiDefine apiDefine, CallType callType, String url, int responseCode,  String errorMessage, int takeTime) {
        String opertator = KClientContext.getContext().getUserInfo() != null ? KClientContext.getContext().getUserInfo().getUsername() : "";
        // 保存登录日志
        if ((callType == CallType.CONTROLLER && "登录".equals(apiDefine.getName()))
                || (url.contains("login") && argvMap.containsKey("username") && ServletUtil.request().getMethod().equalsIgnoreCase("POST"))
                || (url.contains("sys-sso") && ServletUtil.request().getMethod().equalsIgnoreCase("POST"))) {
            // 获取表单信息
            SysLoginLog loginLog = new SysLoginLog();
            if(StringUtils.isEmpty(opertator)) {
                opertator = getLoginUserId(argvMap);
            }
            loginLog.setOperator(opertator);
            boolean ipAddressQuery = SpringContext.getProperties("app.login-log-ip-address-query", "true").equals("true");
            String ip = getLoginIp(argvMap);
            if(StringUtils.isEmpty(ip)) {
                ip = KClientContext.getContext().getIp();
            }
            if (ipAddressQuery) {
                String address = IpUtils.getAddressByIp(ip);
                loginLog.setAddress(address);
            }
            loginLog.setIp(ip);
            loginLog.setResponseCode(responseCode);
            loginLog.setTimes(takeTime);
            if (StringUtils.isEmpty(errorMessage) && KClientContext.getContext() != null) {
                errorMessage = KClientContext.getContext().getErrorMessage();
            }
            loginLog.setResponseMessage(StringUtils.retrench(errorMessage, 1000));
            loginLog.setOperateTime(new Timestamp(System.currentTimeMillis()));
//            log.info("保存登录日志:{}, 请求参数:{}", JsonUtil.toJson(loginLog), JsonUtil.toJson(argvMap));

            KmqMessageCenter.getInstance().produce("t_login_log", JsonUtil.toJson(loginLog) );
        }
    }

    private void saveOpenApiLog(ApiInfo api, Map<String, Object> argvMap, int takeTime) {
        String accessId = argvMap.getOrDefault("accessId", "").toString();
        Map<String, Object> recordMap = new HashMap<>();
        recordMap.put("id", StringUtils.getUUID());
        recordMap.put("apiId", api.getId());
        recordMap.put("apiName", api.getApiName());
        recordMap.put("accessId", accessId);
        recordMap.put("requestParams", StringUtils.retrench(JsonUtil.toJson(argvMap), 1000));
        recordMap.put("requestTime", DateUtils.getNow());
        recordMap.put("useTime", takeTime);
        recordMap.put("success", StringUtils.isNotEmpty(KClientContext.getContext().getErrorMessage()) ? 0: 1);
        recordMap.put("errorMessage", StringUtils.retrench(KClientContext.getContext().getErrorMessage(), 255));
        recordMap.put("requestIp", ServletUtil.getClientIp(ServletUtil.request()));
        KmqMessageCenter.getInstance().produce("openApiQueue",  JsonUtil.toJson(recordMap));
    }

    private void saveOperateLog(String url, String requestMethod, int responseCode,  String errorMessage, int takeTime, String requestBody, String responseBody, CallType callType, ApiInfo api, ApiDefine apiDefine, HttpServletRequest request) {
        String opertator = KClientContext.getContext().getUserInfo() != null ? KClientContext.getContext().getUserInfo().getUsername() : "";
        // 保存操作日志
        SysOperateLog operateLog = new SysOperateLog();
        operateLog.setOperator(opertator);
        operateLog.setAction(callType == CallType.CONTROLLER ? apiDefine.getName(): api.getApiName());
        operateLog.setModule(callType == CallType.CONTROLLER ? apiDefine.getModule(): api.getApiTags());
        operateLog.setIp(KClientContext.getContext().getIp());
        operateLog.setTimes(takeTime);
        operateLog.setUrl(KClientContext.getContext().getUrl());
        operateLog.setResponseCode(responseCode);
        operateLog.setResponseMessage(StringUtils.retrench(errorMessage, 1000));
        operateLog.setOperateTime(new Timestamp(System.currentTimeMillis()));
        operateLog.setMethod(callType == CallType.CONTROLLER ? apiDefine.getCallMethod() + "()": api.getApiName());
        operateLog.setRequestMethod(KClientContext.getContext().getRequest().getMethod());
        operateLog.setAppId(KClientContext.getContext().getRequest().getHeader("appId"));
        if (ServletUtil.isAjaxRequest(request)) {
            String rBody = responseBody;
            rBody = changeBodyJson(rBody);
            operateLog.setResponseBody(StringUtils.retrench(rBody,100));
            AppModeProperties appModeProperties = SpringContext.getBean(AppModeProperties.class);
            if (appModeProperties.getDev()) {
                long warnResponseBodySize = Long.parseLong(SpringContext.getProperties("app.warn-response-body-size", "1048576"));
                String enableNotice = SpringContext.getProperties("app.warn-response-body-enable", "false");
                String receivers = SpringContext.getProperties("app.warn-response-body-receiver", "94123ca363dc4dfaa62a6bb5dcd3bf50,7aed8c297a6940f681c26eb6ab68893d");
                // 如果api存在，则发送给创建人和修改人员
                if (api != null) {
                    Set<String> developers = new HashSet<>();
                    if(StringUtils.isNotEmpty(api.getWhoCreated())) {
                        developers.add(api.getWhoCreated());
                    }
                    if(StringUtils.isNotEmpty(api.getWhoModified())) {
                        developers.add(api.getWhoModified());
                    }
                    receivers = StringUtils.joinToString(Arrays.asList(developers.toArray()), ",");
                }
                String warnResponseIgnores = SpringContext.getProperties("app.warn-response-url-ignores", "/v3/team/,sys-kdb-flow");
                boolean ignoreUrl = false;
                String[] ignores = warnResponseIgnores.split(",");
                for (String ig: ignores) {
                    if(url.contains(ig)) {
                        ignoreUrl = true;
                        break;
                    }
                }
                if (rBody.length() > warnResponseBodySize) {
                    String content = String.format("【响应警告】- 内容 请求地址：%s， 请求方法：%s，请求参数：%s， 响应内容大小:%d", url, requestMethod, StringUtils.retrench(responseBody,500), rBody.length());
                    log.warn(content);
                    if ("true".equalsIgnoreCase(enableNotice) && !ignoreUrl && StringUtils.isNotEmpty(receivers)) {
                        NoticeMessage noticeMessage = new NoticeMessage();
                        noticeMessage.setTitle("API请求响应内容长度预警");
                        noticeMessage.setContent(content);
                        noticeMessage.setToWhos(receivers);
                        noticeMessage.setFromWho("056fb0eeb9a44cb0953534b4c0ca01fa");
                        KmqMessageCenter.getInstance().produce("inbox", JsonUtil.toJson(noticeMessage) );
                    }
                }
                long warnResponseBodyTime = Long.parseLong(SpringContext.getProperties("app.warn-response-body-time", "10000"));
                if(takeTime > warnResponseBodyTime) {
                    String content = String.format("【响应警告】- 用时 请求地址：%s， 请求方法：%s，请求参数：%s， 响应用时:%d", url, requestMethod, StringUtils.retrench(responseBody,500), takeTime);
                    log.warn(content);
                    if ("true".equalsIgnoreCase(enableNotice) && !ignoreUrl && StringUtils.isNotEmpty(receivers)) {
                        NoticeMessage noticeMessage = new NoticeMessage();
                        noticeMessage.setTitle("API请求响应时长预警");
                        noticeMessage.setContent(content);
                        noticeMessage.setToWhos(receivers);
                        noticeMessage.setFromWho("056fb0eeb9a44cb0953534b4c0ca01fa");
                        KmqMessageCenter.getInstance().produce("inbox", JsonUtil.toJson(noticeMessage) );
                    }
                }
            }


        }
        operateLog.setRequestBody(StringUtils.retrench(requestBody, 1000));
//                        log.info("操作日志保存:{}, url:{}" , operateLog.getAction(), request.getRequ
//                        estURI());
        KmqMessageCenter.getInstance().produce("t_operate_log", JsonUtil.toJson(operateLog) );


    }

    private String getLoginIp(Map<String, Object> argvMap) {
        if (argvMap.containsKey("ip")) {
            return (String) argvMap.get("ip");
        }
        return KClientContext.getContext().getIp();
    }

    // 数据脱敏
    private String changeBodyJson(String json) {
        if (StringUtils.isEmpty(json)) {
            return json;
        }
        // 判断json里是否包含token
        if (json.contains("token")) {
            json = json.replaceAll("\"token\":\"[^\"]*\"", "\"token\":\"******\"");
        }
        return json;
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



    @SuppressWarnings("all")
    private void  callByFlow(HttpServletRequest request, HttpServletResponse response, ApiInfo api, String path, Map<String, Object> argvMap, String requestBody) {
        // 获取视图模型
        KFlowContext context = KFlowContext.createBaseContext(StringUtils.isNotEmpty(api.getInArgv()) ? api.getInArgv() : "{}", StringUtils.isNotEmpty(api.getOutArgv()) ? api.getOutArgv() : "{}", api.getI18nKeys(), api.getAppId());
        // 用于接口测试，指定id
        if (argvMap.containsKey("replaceBody")) {
            Map<String, Object> sysMap = JsonUtil.toMap(argvMap.get("replaceBody").toString());
            if (sysMap != null) {
                if (sysMap.containsKey("id")) {
                    Map<String, Object> contextMap = (Map<String, Object>)context.getSystemContext().get("sys");
                    contextMap.put("uuid",sysMap.get("id"));
                }
            }

        }

        // 加入应用id
        argvMap.put("_appId", api.getAppId());
//        String key = MD5Utils.md5(request.getRequestURI()) ;
        KdbFlowResult result = null;
        boolean disableCache = false;
        Object disableCacheObj = argvMap.get("_disableCache");
        if (disableCacheObj != null && (disableCacheObj.equals("true") || (disableCacheObj instanceof Boolean && ((Boolean) disableCacheObj == true)))) {
            disableCache = true;
        }
        if (api.getCacheEnable() != null && api.getCacheEnable() == 1 && disableCache == false) {
            String md5Key = ServletUtil.getRequestUuid(request.getRequestURI(), request.getQueryString(), requestBody, request);
            ApiResultCache res = ApiResultCacheManager.getInstance().get(md5Key);
            DynamicTask dynamicTask = SpringContext.getBean(DynamicTask.class);
            SysTask virtualTask = dynamicTask.getVirtualTask(md5Key);
            if (res != null && virtualTask != null) {
                dynamicTask.virtualHeart(md5Key);
                if (StringUtils.isNotEmpty(KClientContext.getContext().getToken())) {
                    res.getTokens().add(KClientContext.getContext().getToken());
                }
                result = res.getResult();
                // 同时发送通知
                dynamicTask.sendDataChange(virtualTask, res, true, res.getResult().getData());
                log.info("接口：{}, 参数:{} 命中缓存!", api.getApiName(), JsonUtil.toJson(argvMap));

            }
            else {

                if (!dynamicTask.hasVirtualTask(md5Key)) {
                    // 创建虚拟任务
                    SysTask task = new SysTask();
                    task.setId(md5Key);
                    task.setName("virtual-api-cache-" + api.getApiName());
                    task.setEnable(1);
                    task.setDistributed(0);
                    task.setWhenCreated(new Timestamp(System.currentTimeMillis()));
                    task.setWhenModified(new Timestamp(System.currentTimeMillis()));
                    task.setTaskType(2);
                    task.setCron(api.getCacheCron());
                    task.setTaskResourceId(api.getApiFlowId());
                    task.setNote(JsonUtil.toJson(context));
                    task.setLockStatus(api.getCacheExpireTime());
                    task.setTaskArgv(JsonUtil.toJson(argvMap));
                    task.setWhoCreated(KClientContext.getContext().getToken());
                    log.info("创建虚拟任务：{}, 参数:{}", task.getName(), JsonUtil.toJson(task));
                    dynamicTask.addVirtualTask(task);
                }
                else {
                    SysTask task = dynamicTask.getVirtualTask(md5Key);
                    if (task != null) {
                        task.setWhenModified(new Timestamp(System.currentTimeMillis()));
                    }
                }
                long t1 = System.currentTimeMillis();
                result = KdbFlowExecutor.getInstance().execute(api.getApiFlowId(), api.getSubFlowIds(), argvMap, context, false, false);
                long t2 = System.currentTimeMillis();
                ApiResultCache cache = ApiResultCache.create(result, (t2 - t1));
                if (StringUtils.isNotEmpty(KClientContext.getContext().getToken())) {
                    cache.getTokens().add(KClientContext.getContext().getToken());
                }
                ApiResultCacheManager.getInstance().put(md5Key, cache, (long)( api.getCacheExpireTime() * 1000));
            }

        }
        else {
            result = KdbFlowExecutor.getInstance().execute(api.getApiFlowId(), api.getSubFlowIds(), argvMap, context, false, false);
        }

        KdbRetFile kdbRetFile = null;
        KClientContext.getContext().setArgv(argvMap);
        KClientContext.getContext().setApiRspAdapter(api.getApiRspArgv());
        // 转为api格式
        switch (result.getType()) {
            case KFlowConstant.RESULT_JSON:
                if (api.getApiFlowId().equalsIgnoreCase("a20fd82c126947f9ab3b599001df6126")) {
                    log.info("用时：3");
                }
                if (StringUtils.isNotEmpty(api.getApiResultHandler()) && "user_json".equalsIgnoreCase(api.getApiResultHandler())) {
                    ServletUtil.responseJson(response, result.getData());
                }
                else {
                    ServletUtil.responseJson(response, FlowUtils.toJsonResult(result.getData(), result.getLog(), result.getExceptionStack()));
                }
                if (api.getApiFlowId().equalsIgnoreCase("a20fd82c126947f9ab3b599001df6126")) {
                    log.info("用时：4");
                }
                break;
            case KFlowConstant.RESULT_EXCEL:
                ExcelWorker.getInstance().writeToWeb(response, (KExcel) result.getData());
                break;
            case KFlowConstant.RESULT_FILE:
                kdbRetFile = (KdbRetFile) result.getData();
                ServletUtil.responseFile(response, kdbRetFile.getFileName(), kdbRetFile.getData());
                break;
            case KFlowConstant.RESULT_HTML:
                ServletUtil.responseHtml(response, result.getData().toString());
                break;
            case KFlowConstant.RESULT_BASE64_TO_FILE:
                kdbRetFile = (KdbRetFile) result.getData();
                ServletUtil.responseFile(response, kdbRetFile.getFileName(), Base64.getDecoder().decode(kdbRetFile.getData()));
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
        // 获取用户信息
        BaseUserInfo userInfo = TokenUtil.getUserInfoByToken(token, appAuthProperties.getTokenSecret());
        if (userInfo != null) {
            KClientContext.getContext().setUserInfo(userInfo);
        }

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
            String devMode = SpringContext.getProperties("app.mode.dev", "true");
            if (!"true".equalsIgnoreCase(devMode)) {
                if(!PermissionManager.getInstance().hasPermission(userInfo.getRoleIds(),apiCode)) {
                    throw new ForbiddenException(I18n.t("permission.api-forbidden", "接口无权限"));
                }
            }
            if (userInfo != null) {
                // 是否要更新过期时间
                boolean updateExpired = !KClientContext.getContext().getUrl().endsWith("/ping");
                // 更新活动时间
                SessionManager.getInstance().updateActiveTime(userInfo.getId(), KClientContext.getContext().getToken(), appAuthProperties.getMockSessionExpireMinutes(), updateExpired);

            }


        }
        else {
            userInfo = TokenUtil.getUserInfoByToken(token, appAuthProperties.getTokenSecret());
        }
        // 保存用户信息
        KClientContext.getContext().setUserInfo(userInfo);


    }

    /**
     * 校验开放api权限
     * @param apiInfo   接口信息
     */
    public void checkOpenApi(ApiInfo apiInfo, Map<String, Object> params) {
        // 获取接入商id
        String accessId = params.getOrDefault("accessId", "").toString();
        if (StringUtils.isEmpty(accessId)) {
            throw BusinessException.serviceThrow(I18n.t("KAuthFilter.openApi.empty", "接入商ID为空！"));
        }
        if (!OpenApiManager.getInstance().hasAccess(accessId)) {
            throw BusinessException.serviceThrow(I18n.t("KAuthFilter.openApi.hasnot", "接入商不存在！"));
        }
        if (!OpenApiManager.getInstance().hasOpenApi(accessId, apiInfo.getApiCode())) {
            throw BusinessException.serviceThrow(I18n.t("KAuthFilter.openApi.authFail","接口未授权"));
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
                throw BusinessException.serviceThrow(I18n.t("KAuthFilter.openApi.timestamp","缺少时间戳参数: timestamp"));
            }
            if (!params.containsKey("sign")) {
                throw BusinessException.serviceThrow(I18n.t("KAuthFilter.openApi.sign", "缺少签名值: sign"));
            }
            if (!params.containsKey("signNonce")) {
                throw BusinessException.serviceThrow(I18n.t("KAuthFilter.openApi.signNonce", "缺少签名噪音: signNonce"));
            }
            long timestamp = Long.parseLong(params.get("timestamp").toString());
            // 校验时间，超过5分钟的认为无效
            if (Math.abs(timestamp - System.currentTimeMillis()) > 1000*60*5)  {
                throw BusinessException.serviceThrow(I18n.t("KAuthFilter.openApi.timeout", "请求时间已过期"));
            }
            // 签名噪音
            String signNonce = params.get("signNonce").toString();
            if (signNonces.contains(signNonce)) {
                throw BusinessException.serviceThrow(I18n.t("KAuthFilter.openApi.repeat", "签名噪音不能重复使用"));
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
                throw BusinessException.serviceThrow(I18n.t("KAuthFilter.openApi.signError", "签名值不正确，传输的签名值:{0}, 计算值: {1}" , sign, calcSign));
            }

        }

    }

    private String getLoginUserId(Map<String, Object> argvMap) {
        String mode = argvMap.getOrDefault("mode", "pwd").toString();
        if (mode.equals("pwd")) {
            Object username = argvMap.get("username");
            if (username != null && StringUtils.isNotEmpty(username.toString())) {
                String str = username.toString();
                try {
                    if (Base64Utils.isBase64(str)) {
                        return new String(Base64.getDecoder().decode(str), StandardCharsets.UTF_8);
                    }
                    return str;

                }
                catch (Exception e) {
                    return username.toString();
                }

            }
        }
        else if (mode.equals("ldap")) {
            Object ldapUsername = argvMap.get("ldapUsername");
            if (ldapUsername != null && StringUtils.isNotEmpty(ldapUsername.toString())) {
                String str = ldapUsername.toString();
                return str;
            }
        }
        else if (mode.equals("sms")) {
            Object phoneNumber = argvMap.get("phoneNumber");
            if (phoneNumber != null && StringUtils.isNotEmpty(phoneNumber.toString())) {
                String uname = KClientContext.getContext().getUsername();
                return uname;
            }
        }

        Object uid = argvMap.get("uid");
        Object type = argvMap.get("type");
        if (type != null && uid != null) {
            if (1 == (Integer) type) {
                return uid.toString();
            }
            if (2 == (Integer) type) {
                BaseUserInfo userList = DB.findOne(BaseUserInfo.class, "select username from sys_user where id=?", uid);
                if (userList != null) {
                    return userList.getUsername();
                }
            }
        }

        return null;
    }

    /**
     * 判断一个字符串是否为base64字符串
     */
    private boolean isBase64(String str) {
        if (str == null || str.trim().length() == 0) {
            return false;
        }
        try {
            Base64.getDecoder().decode(str);
            return true;
        } catch (Exception e) {
            return false;
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
        String contextPath = request.getContextPath();
        if (StringUtils.isNotEmpty(contextPath) && !"/".equals(contextPath)) {
            url = url.substring(contextPath.length());
        }
        // 获取请求方式
        String method = request.getMethod().toLowerCase();
        // 获取api
        return controllerManager.getApiDefine(method, url);
    }

    /**
     * 解码噪音字符串
     * @param noiseString
     * @return
     */
    private String decodeNoiseString(String noiseString) {
        noiseString = noiseString.replaceAll("KAxBybA", "=");
        // 将字符数组拆分为交叉替换前的数组
        char[] charArray = noiseString.toCharArray();
        for (int i = 0; i < charArray.length - 1; i += 2) {
            char temp = charArray[i];
            charArray[i] = charArray[i + 1];
            charArray[i + 1] = temp;
        }

        // 将字符数组拼接为字符串
        String swappedString = new String(charArray);

        // Base64 解码
        byte[] decodedBytes = Base64.getDecoder().decode(swappedString);
        return new String(decodedBytes);
    }

    /**
     * 校验噪音
     * @param url
     * @param noise
     * @param token
     */
    public void checkNoise(String url, String noise, String token) {
        if(StringUtils.isEmpty(token)) {
            return;
        }
        String enableNoise = SpringContext.getProperties("app.auth.noise.enable", "false");
        if ("false".equalsIgnoreCase(enableNoise)) {
            return;
        }
        if (StringUtils.isEmpty(noise)) {
            throw BusinessException.serviceThrow(I18n.t("KAuthFilter.N004", "接口请求不合法，代码:N004"));
        }
        // 解密噪音
        String noiseDecrypt = decodeNoiseString(noise);
        if (StringUtils.isEmpty(noiseDecrypt)) {
            throw BusinessException.serviceThrow(I18n.t("KAuthFilter.N003", "接口请求不合法，代码:N003"));
        }
        String[] arr = noiseDecrypt.split("\\|");
        long createTime = Long.parseLong(arr[0]);
        long hash = Integer.parseInt(arr[1]);
        // 计算超时时间（5分钟超时）
        if (Math.abs(System.currentTimeMillis() - createTime) > 1000*60*5) {
            throw BusinessException.serviceThrow(I18n.t("KAuthFilter.N002", "接口请求不合法，代码:N002"));
        }
        // 计算令牌的hash值
        int tokenHash = calculateHash(token);
        if (tokenHash!= hash) {
            throw BusinessException.serviceThrow(I18n.t("KAuthFilter.N001", "接口请求不合法，代码:N001"));
        }
        if (noiseCache.containsKey(noise)) {
            throw BusinessException.serviceThrow(I18n.t("KAuthFilter.N005", "接口请求不合法，代码:N005"));
        }
        noiseCache.put(noise, System.currentTimeMillis(), 1000*60*5);

    }

    private int calculateHash(String input) {
        input = input.toLowerCase();
        int hash = 1315423911;

        for (int i = input.length() - 1; i >= 0; i--) {
            char ch = input.charAt(i);
            hash ^= ((hash << 5) + ch + (hash >> 2));
        }

        return hash & 0x7FFFFFFF;
    }
}
