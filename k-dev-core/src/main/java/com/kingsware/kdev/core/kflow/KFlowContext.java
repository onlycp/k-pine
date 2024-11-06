package com.kingsware.kdev.core.kflow;

import com.kingsware.kdev.core.auth.BaseUserInfo;
import com.kingsware.kdev.core.bean.ExceptionLog;
import com.kingsware.kdev.core.cache.access.AccessManager;
import com.kingsware.kdev.core.cache.instance.HostInfo;
import com.kingsware.kdev.core.cache.license.LicenseManager;
import com.kingsware.kdev.core.context.KClientContext;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.mode.AppModeProperties;
import com.kingsware.kdev.core.util.*;
import lombok.Data;
import net.minidev.json.JSONObject;

import java.net.URLDecoder;
import java.util.*;

/**
 * 流程环境变量
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/18 2:23 下午
 */
@Data
public class KFlowContext {

    /** 当前系统变量 **/
    private final Map<String, Object> systemContext = new HashMap<>();
    /** 处理类 **/
    private String handleClass;
    /** 输入参数定义 **/
    private String inArgv;
    /** 输出参数定义 **/
    private String outArgv;
    /** 应用id **/
    private String appId;
    /** 自动国际化键 **/
    private Set<String> i18nKeys = new HashSet<>();


    /**
     * 创建基本上下文
     * @param inArgv 输入参数
     * @param outArgv 输出参数
     * @return KFlowContext 返回流程上下文对象
     */
    public static KFlowContext createBaseContext(String inArgv, String outArgv, String i18nKeys) {
        String encodeDebugUserInfo = ServletUtil.request().getHeader("Debug-User-Info");
        KFlowContext context = new KFlowContext();
        Map<String, Object> sysMap = new HashMap<>();

        // 处理系统变量
        sysMap.put("who",  KClientContext.getContext() != null && KClientContext.getContext().getUserInfo()!= null ? KClientContext.getContext().getUserInfo().getId() : "");
        sysMap.put("username",  KClientContext.getContext() != null && KClientContext.getContext().getUserInfo()!= null ? KClientContext.getContext().getUserInfo().getUsername() : "");
        sysMap.put("when", DateUtils.getNow());
        sysMap.put("uuid", StringUtils.getUUID());
        sysMap.put("avatar", KClientContext.getContext() != null && KClientContext.getContext().getUserInfo()!= null ? KClientContext.getContext().getUserInfo().getAvatar() : "");
        sysMap.put("realName",  KClientContext.getContext() != null && KClientContext.getContext().getUserInfo()!= null ? KClientContext.getContext().getUserInfo().getRealName() : "");
        sysMap.put("mobile",  KClientContext.getContext() != null && KClientContext.getContext().getUserInfo()!= null ? KClientContext.getContext().getUserInfo().getMobile() : "");
        sysMap.put("email",  KClientContext.getContext() != null && KClientContext.getContext().getUserInfo()!= null ? KClientContext.getContext().getUserInfo().getEmail() : "");
        sysMap.put("roleIds",  KClientContext.getContext() != null && KClientContext.getContext().getUserInfo()!= null ? KClientContext.getContext().getUserInfo().getRoleIds() : "");
        sysMap.put("roleCodes",  KClientContext.getContext() != null && KClientContext.getContext().getUserInfo()!= null ? KClientContext.getContext().getUserInfo().getRoleCodes() : "");
        sysMap.put("roleNames",  KClientContext.getContext() != null && KClientContext.getContext().getUserInfo()!= null ? KClientContext.getContext().getUserInfo().getRoleNames() : "");
        sysMap.put("sysUnitIds",  KClientContext.getContext() != null && KClientContext.getContext().getUserInfo()!= null ? KClientContext.getContext().getUserInfo().getSysUnitIds() : "");
        sysMap.put("sysUnitNames",  KClientContext.getContext() != null && KClientContext.getContext().getUserInfo()!= null ? KClientContext.getContext().getUserInfo().getSysUnitNames() : "");

        if(isDevMode() && encodeDebugUserInfo != null && StringUtils.isNotEmpty(encodeDebugUserInfo))   {
            try {
                // 将解码后的字节转换成字符串
                String decodedString = URLDecoder.decode(encodeDebugUserInfo,"UTF-8");
                Map<String, Object> userMap = JsonUtil.toMap(decodedString);
                if (userMap != null && userMap.size() > 0) {
                    if (userMap.get("id") != null) {
                        sysMap.put("who",  (String) userMap.get("id"));
                    }
                    if (userMap.get("username") != null) {
                        sysMap.put("username",  (String) userMap.get("username"));
                    }
                    if (userMap.get("avatar") != null) {
                        sysMap.put("avatar",  (String) userMap.get("avatar"));
                    }
                    if (userMap.get("realName") != null) {
                        sysMap.put("realName",  (String) userMap.get("realName"));
                    }
                    if (userMap.get("mobile") != null) {
                        sysMap.put("mobile",  (String) userMap.get("mobile"));
                    }
                    if (userMap.get("email") != null) {
                        sysMap.put("email",  (String) userMap.get("email"));
                    }
                    if (userMap.get("roleIds") != null) {
                        sysMap.put("roleIds",  (String) userMap.get("roleIds"));
                    }
                    if (userMap.get("roleCodes") != null) {
                        sysMap.put("roleCodes",  (String) userMap.get("roleCodes"));
                    }
                    if (userMap.get("roleNames") != null) {
                        sysMap.put("roleNames",  (String) userMap.get("roleNames"));
                    }
                    if (userMap.get("sysUnitIds") != null) {
                        sysMap.put("sysUnitIds",  (String) userMap.get("sysUnitIds"));
                    }
                    if (userMap.get("sysUnitNames") != null) {
                        sysMap.put("sysUnitNames",  (String) userMap.get("sysUnitNames"));
                    }
                }
            } catch (Exception e) {
               ExceptionUtils.getStackTrace(e);
            }
       }

        sysMap.put("isAdmin",  isAdmin());
        // 是否uniops
        sysMap.put("isUniops", LicenseManager.getInstance().isUniopsApp());
        HostInfo hostInfo = SystemUtil.getHost();
        String baseUrl = "http://" + hostInfo.getHostName() + ":" + hostInfo.getPort() + ServletUtil.getContextPath();
        if (baseUrl.endsWith("/")) {
            baseUrl = baseUrl.substring(0, baseUrl.length()-1);
        }
        sysMap.put("baseUrl", baseUrl);
        context.getSystemContext().put("sys", sysMap);
        context.getSystemContext().put("devMode", isDevMode());
        // 设置输入参数
        context.inArgv = StringUtils.isEmpty(inArgv) ? "{}" : inArgv;
        context.outArgv = StringUtils.isEmpty(outArgv) ? "{}" : outArgv;
        if (StringUtils.isNotEmpty(i18nKeys)) {
            String[] sets = i18nKeys.split(",");
            context.i18nKeys.addAll(Arrays.asList(sets));
        }
        return context;
    }

    /**
     * 创建基本上下文
     * @param inArgv 输入参数
     * @param outArgv 输出参数
     * @return KFlowContext 返回流程上下文对象
     */
    public static KFlowContext createBaseContext(String inArgv, String outArgv, String i18nKeys, String appId) {
        KFlowContext context = createBaseContext(inArgv, outArgv, i18nKeys);
        context.appId = appId;
        return context;
    }

    public static boolean isDevMode() {
        AppModeProperties appModeProperties = SpringContext.getBean("appModeProperties");
        return appModeProperties.getDev();
    }

    public static boolean isAdmin() {
        if (KClientContext.getContext() == null) {
            return false;
        }
        // 获取用户信息
        BaseUserInfo userInfo = KClientContext.getContext().getUserInfo();
        // 如果不是web登录或者不登录
        if (userInfo == null) {
            return false;
        }
        return AccessManager.getInstance().isSupperAdmin(userInfo.getRoleIds());
    }
}
