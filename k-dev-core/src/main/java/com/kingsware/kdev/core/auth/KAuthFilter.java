package com.kingsware.kdev.core.auth;

import com.kingsware.kdev.core.bean.ApiDefine;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.cache.api.ApiInfo;
import com.kingsware.kdev.core.cache.api.ApiManager;
import com.kingsware.kdev.core.cache.controller.ControllerManager;
import com.kingsware.kdev.core.cache.license.LicenseManager;
import com.kingsware.kdev.core.cache.open.OpenApiManager;
import com.kingsware.kdev.core.cache.permssion.PermissionManager;
import com.kingsware.kdev.core.cache.session.SessionManager;
import com.kingsware.kdev.core.context.ClientInfo;
import com.kingsware.kdev.core.context.KClientContext;
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
import com.kingsware.kdev.core.orm.exception.OrmDbException;
import com.kingsware.kdev.core.util.ExceptionUtils;
import com.kingsware.kdev.core.util.ServletUtil;
import com.kingsware.kdev.core.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;
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


    @ApiIgnore
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
        String apiCode = "";
        try {
            // 如果是静态文件
            if (!url.startsWith("/api") &&  StringUtils.isEmpty(contextPath)) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
            if (url.startsWith("/api"))  {
                contextPath = "/api";
            }


            // 获取配置的接口信息
            String path = url.replaceFirst(contextPath, "");
            ApiInfo api = ApiManager.getInstance().getApi(method, path);
            ApiDefine apiDefine = getApiDefine(request, response);
            // 初始化青松上下文
            initContext(request, response);
            // 如果是openapi，表示是ignore
            boolean ignore = false;
            // 调用方式
            int callType = 1;

            // 流程调用方式
            if (api != null && api.getCallType() == 2 && kFlowProperties.isEnable()) {
                callType = 2;
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
                    apiCode = "";
                }
            }
            // 判断是否开放接口
            boolean isOpenApi = StringUtils.isNotEmpty(apiCode) && apiCode.startsWith(openApiFlag) && api != null;
            Map<String, Object> argvMap = new HashMap<>();
            // 只有在是流程调用时才获取参数，否则会破坏RequestBody
            if (callType == 2) {
                argvMap = ServletUtil.getRequestParams(api, path, request);
            }
            if (isOpenApi) {
                // 处理请求变量
                checkOpenApi(api, argvMap);
            }
            else {
                checkPermission(request, response, ignore, apiCode);
            }
            // 校验license
            if (!ignore) {
                checkLicense();
            }

            // 根据不同的调用类型，进行调用相关处理
            if (callType == 1) filterChain.doFilter(servletRequest, servletResponse); else callByFlow(request, response, api, path, argvMap);
        }
        catch (BusinessException | OrmDbException e) {
            ServletUtil.responseJson(response, BaseRet.failMessage(e.getMessage()));
        }
        catch (UnauthorizedException e) {
            log.error("用户未登录，接口路径:{}, 请求方法:{}", url, method);
            ServletUtil.responseJson(response, BaseRet.fail(e.getMessage(), RetEnum.UNAUTHORIZED.getCode()));
        }
        catch (LicenseException e) {
            ServletUtil.responseJson(response, BaseRet.fail(e.getMessage(), RetEnum.LICENSE_FAIL.getCode()));
        }
        catch (ForbiddenException e) {
            log.error("接口无权限，接口路径:{}, 请求方法:{}", url, method);
            String message = MessageFormat.format("很抱歉，您没有此接口的访问权限! 接口地址： {0}, 接口编码:{1}", url, apiCode);
            ServletUtil.responseJson(response, BaseRet.fail(message, RetEnum.FORBIDDEN.getCode()));
        }
        catch (Exception e) {
            log.error("error", e);
            ServletUtil.responseJson(response, BaseRet.failMessage(ExceptionUtils.getStackTrace(e)));
        }

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
        KdbFlowResult result = KdbFlowExecutor.getInstance().execute(api.getApiFlowId(), api.getSubFlowIds(), argvMap, context);
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
