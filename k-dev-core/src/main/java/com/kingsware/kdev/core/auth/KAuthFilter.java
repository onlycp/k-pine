package com.kingsware.kdev.core.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingsware.kdev.core.base.BaseController;
import com.kingsware.kdev.core.bean.ApiDefine;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.cache.api.ApiInfo;
import com.kingsware.kdev.core.cache.api.ApiManager;
import com.kingsware.kdev.core.cache.controller.ControllerManager;
import com.kingsware.kdev.core.cache.permssion.PermissionManager;
import com.kingsware.kdev.core.cache.session.SessionManager;
import com.kingsware.kdev.core.context.ClientInfo;
import com.kingsware.kdev.core.context.KClientContext;
import com.kingsware.kdev.core.enums.RetEnum;
import com.kingsware.kdev.core.excel.ExcelWorker;
import com.kingsware.kdev.core.excel.KExcel;
import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.exception.ForbiddenException;
import com.kingsware.kdev.core.exception.UnauthorizedException;
import com.kingsware.kdev.core.i18n.I18n;
import com.kingsware.kdev.core.kflow.*;
import com.kingsware.kdev.core.kflow.bean.KdbFlowResult;
import com.kingsware.kdev.core.kflow.bean.KdbRetFile;
import com.kingsware.kdev.core.orm.exception.OrmDbException;
import com.kingsware.kdev.core.util.ClassUtils;
import com.kingsware.kdev.core.util.ExceptionUtils;
import com.kingsware.kdev.core.util.ServletUtil;
import com.kingsware.kdev.core.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
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
    private ObjectMapper objectMapper;
    @Resource
    private KFlowProperties kFlowProperties;
    @Resource
    private AppAuthProperties appAuthProperties;
    @Resource
    private ControllerManager controllerManager;
    /** 开放api接口 **/
    private String openApi = ":open";


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

        if (url.startsWith("/api"))  {
            contextPath = "/api";
        }
        String apiCode = "";
        try {

            // 获取配置的接口信息
            String path = url.replace(contextPath, "");
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
                ignore = StringUtils.isNotEmpty(api.getApiCode()) && apiCode.startsWith(openApi);
            }
            else {
                apiCode = apiDefine.getApiCode();
                ignore = apiDefine.isIgnore();
            }
            // 检验权限
            checkPermission(request, response, ignore, apiCode);
            if (callType == 1) {
                filterChain.doFilter(servletRequest, servletResponse);
            }
            else {
                // 调用流程
                callByFlow(request, response, api, path);
            }
        }
        catch (BusinessException | OrmDbException e) {
            ServletUtil.responseJson(response, BaseRet.failMessage(e.getMessage()));
        }
        catch (UnauthorizedException e) {
            log.error("用户未登录，接口路径:{}, 请求方法:{}", url, method);
            ServletUtil.responseJson(response, BaseRet.fail(e.getMessage(), RetEnum.UNAUTHORIZED.getCode()));
        }
        catch (ForbiddenException e) {
            log.error("接口无权限，接口路径:{}, 请求方法:{}", url, method);
            String message = MessageFormat.format("很抱歉，您没有此接口的访问权限! 接口地址： {0}, 接口编码:{1}", url, apiCode);
            ServletUtil.responseJson(response, BaseRet.fail(message, RetEnum.FORBIDDEN.getCode()));
        }
        catch (Exception e) {
            e.printStackTrace();
            ServletUtil.responseJson(response, BaseRet.failMessage(ExceptionUtils.getStackTrace(e)));
        }





    }

    private void  callByFlow(HttpServletRequest request, HttpServletResponse response, ApiInfo api, String path) {
        // 获取视图模型
        KFlowContext context = KFlowContext.createBaseContext(StringUtils.isNotEmpty(api.getInArgv()) ? api.getInArgv() : "{}", StringUtils.isNotEmpty(api.getOutArgv()) ? api.getOutArgv() : "{}");
        // 处理请求变量
        Map<String, Object> argvMap = ServletUtil.getRequestParams(api, path, request);
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
