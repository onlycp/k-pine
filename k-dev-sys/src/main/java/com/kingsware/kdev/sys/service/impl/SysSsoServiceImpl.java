package com.kingsware.kdev.sys.service.impl;


import com.kingsware.kdev.core.context.KClientContext;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.i18n.I18n;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.core.util.ServletUtil;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.sys.argv.SysSsoArgv;
import com.kingsware.kdev.sys.argv.SysUserLoginArgv;
import com.kingsware.kdev.sys.model.SysUser;
import com.kingsware.kdev.sys.ret.SysUserLoginRet;
import com.kingsware.kdev.sys.service.SysSsoService;
import com.kingsware.kdev.sys.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Slf4j
@Service
public class SysSsoServiceImpl implements SysSsoService {

    private static final String SSO_LOGIN_LAN_ONLY_ENABLE_KEY = "sso.login.lan-only.enable";
    private static final String SSO_LOGIN_IP_WHITELIST_KEY = "sso.login.ip-whitelist";
    @SuppressWarnings("java:S1313") // secure baseline for LAN-only access; can be overridden by sso.login.ip-whitelist
    private static final String DEFAULT_SSO_LOGIN_IP_WHITELIST = "10.0.0.0/8,172.16.0.0/12,192.168.0.0/16,127.0.0.1,::1,fc00::/7";

    @Resource
    private SysUserService sysUserService;


    /**
     * 登录
     *
     * @param ssoArgv
     * @return
     */
    @Override
    public SysUserLoginRet doLogin(SysSsoArgv ssoArgv) {
        try {
            validateLoginIp();
            // 查询安全令牌
            String secretKey = SpringContext.getProperties("sso.key", "");
            if (StringUtils.isEmpty(secretKey)) {
                log.error("SSO login rejected, property sso.key is not configured.");
                throw BusinessException.serviceThrow(I18n.t("SysSsoServiceImpl.ssoKeyRequired", "系统未配置单点登录密钥"));
            }
            if (!secretKey.equals(ssoArgv.getSecretKey())) {
                throw new BusinessException(I18n.t("SysSsoServiceImpl.authFail", "认证不合法！"));
            }
            log.info("用户自动登录:{}", JsonUtil.toJson(ssoArgv));
            // 根据账号查询用户并进行登录
            SysUser user = null;
            if (ssoArgv.getType() == null || ssoArgv.getType() == 1) {
                user = DB.findOne(SysUser.class, "select username, password from sys_user where username=?", ssoArgv.getUid());
            }
            else {
                user = DB.findOne(SysUser.class, "select username, password from sys_user where id=?", ssoArgv.getUid());
            }
            SysUserLoginArgv loginArgv = new SysUserLoginArgv();
            loginArgv.setUsername(user.getUsername());
            loginArgv.setPassword(user.getPassword());
            // 调用登录接口
            if (KClientContext.getContext() != null) {
                KClientContext.getContext().setValidateCodeFlag(false);
                KClientContext.getContext().setValidatePassFlag(false);
            }
            SysUserLoginRet ret = sysUserService.login(JsonUtil.beanToMap(loginArgv));
            log.info("自动登录结果:"  + JsonUtil.toJson(ret));
            return ret;
        }
        catch (BusinessException businessException) {
            throw businessException;
        }
        catch (Exception e) {
            log.error("error", e);
            throw BusinessException.serviceThrow(I18n.t("SysSsoServiceImpl.autoLoginFail", "自动登录失败") +  ":" + e.getMessage());
        }
    }

    private void validateLoginIp() {
        boolean enableLanOnly = SpringContext.getBoolean(SSO_LOGIN_LAN_ONLY_ENABLE_KEY, true);
        if (!enableLanOnly) {
            return;
        }
        if (KClientContext.getContext() == null || KClientContext.getContext().getRequest() == null) {
            log.warn("SSO login rejected, request context is null.");
            throw new BusinessException(I18n.t("SysSsoServiceImpl.lanOnly", "仅允许局域网IP访问"));
        }
        HttpServletRequest request = KClientContext.getContext().getRequest();
        String clientIp = ServletUtil.getClientIp(request);
        String whitelistConfig = SpringContext.getProperties(SSO_LOGIN_IP_WHITELIST_KEY, DEFAULT_SSO_LOGIN_IP_WHITELIST);
        if (StringUtils.isEmpty(whitelistConfig)) {
            whitelistConfig = DEFAULT_SSO_LOGIN_IP_WHITELIST;
        }
        List<String> whitelist = parseWhitelist(whitelistConfig);
        if (!isIpAllowed(clientIp, whitelist)) {
            log.warn("SSO login rejected, clientIp={}, remoteAddr={}, xForwardedFor={}, xRealIp={}, whitelist={}",
                    clientIp, request.getRemoteAddr(), request.getHeader("x-forwarded-for"),
                    request.getHeader("X-Real-IP"), whitelistConfig);
            throw new BusinessException(I18n.t("SysSsoServiceImpl.lanOnly", "仅允许局域网IP访问"));
        }
    }

    private List<String> parseWhitelist(String whitelistConfig) {
        List<String> whitelist = new ArrayList<>();
        String[] rules = whitelistConfig.split("[,;\\s]+");
        for (String rule : rules) {
            if (StringUtils.isNotEmpty(rule)) {
                whitelist.add(rule.trim());
            }
        }
        return whitelist;
    }

    private boolean isIpAllowed(String ip, List<String> whitelist) {
        InetAddress clientAddress = parseIp(ip);
        if (clientAddress == null || whitelist == null || whitelist.isEmpty()) {
            return false;
        }
        for (String rule : whitelist) {
            if (isIpMatchRule(clientAddress, rule)) {
                return true;
            }
        }
        return false;
    }

    private boolean isIpMatchRule(InetAddress clientAddress, String rule) {
        if (StringUtils.isEmpty(rule)) {
            return false;
        }
        if ("*".equals(rule)) {
            return true;
        }
        String trimmedRule = rule.trim();
        if (trimmedRule.contains("/")) {
            return matchCidr(clientAddress, trimmedRule);
        }
        InetAddress allowedIp = parseIp(trimmedRule);
        return allowedIp != null && Arrays.equals(clientAddress.getAddress(), allowedIp.getAddress());
    }

    private InetAddress parseIp(String ip) {
        if (StringUtils.isEmpty(ip)) {
            return null;
        }
        String normalizedIp = ip.trim();
        int zoneIndex = normalizedIp.indexOf('%');
        if (zoneIndex > 0) {
            normalizedIp = normalizedIp.substring(0, zoneIndex);
        }
        try {
            return InetAddress.getByName(normalizedIp);
        }
        catch (Exception e) {
            return null;
        }
    }

    private boolean matchCidr(InetAddress clientAddress, String cidrRule) {
        String[] split = cidrRule.split("/");
        if (split.length != 2) {
            return false;
        }
        InetAddress networkAddress = parseIp(split[0]);
        if (networkAddress == null) {
            return false;
        }
        byte[] clientBytes = clientAddress.getAddress();
        byte[] networkBytes = networkAddress.getAddress();
        if (clientBytes.length != networkBytes.length) {
            return false;
        }
        int prefixLength;
        try {
            prefixLength = Integer.parseInt(split[1]);
        }
        catch (Exception e) {
            return false;
        }
        int totalBits = clientBytes.length * 8;
        if (prefixLength < 0 || prefixLength > totalBits) {
            return false;
        }
        BigInteger clientInt = new BigInteger(1, clientBytes);
        BigInteger networkInt = new BigInteger(1, networkBytes);
        int shiftBits = totalBits - prefixLength;
        if (shiftBits > 0) {
            clientInt = clientInt.shiftRight(shiftBits);
            networkInt = networkInt.shiftRight(shiftBits);
        }
        return clientInt.equals(networkInt);
    }


}
