package com.kingsware.kdev.core.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingsware.kdev.core.cache.instance.InstanceManager;
import com.kingsware.kdev.core.cache.session.SessionManager;
import com.kingsware.kdev.core.cache.session.TokenSession;
import com.kingsware.kdev.core.context.KClientContext;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.exception.UnauthorizedException;
import com.kingsware.kdev.core.i18n.I18n;
import com.kingsware.kdev.core.model.SysOnlineUser;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.expression.Expr;
import com.kingsware.kdev.core.util.*;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 令牌工具类，用于生成令牌及令牌验证
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/20 2:07 下午
 */
public class TokenUtil {

    /** 令牌请求头**/
    public static final String AUTH_HEADER = "Authorization";
    /** 令牌前缀**/
    public static final String AUTH_PREFIX = "Bearer ";

    public static final Set<String> tokenFreshRecords = new HashSet<>();


    private TokenUtil() {

    }

    /** 日志打印 **/
    private static final Logger logger  = LoggerFactory.getLogger(TokenUtil.class);



    /**
     * 生成令牌
     * @param dataSecret    数据密钥(AES)
     * @param iss           发行机构
     * @param userInfo      用户信息
     * @param ip            客户端ip
     * @return              生成后的令牌
     */
    public static TokenPair createToken(String dataSecret, String iss, String ip, String sessionId,  BaseUserInfo userInfo) {
        // 实例一个令牌对象
        AuthToken authToken = new AuthToken();
        authToken.setIp(ip);
//        authToken.setIss(iss);
        authToken.setWhenCreated(System.currentTimeMillis());
        authToken.setUserInfo(userInfo);
        authToken.setKSessionId(sessionId);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String str = objectMapper.writeValueAsString(authToken);
            // 将令牌字符串使用AES加密（128位)
            String token = AESUtil.encrypt(str, dataSecret);
            String md5 = MD5Utils.md5(token);
            return new TokenPair(token, md5);

        } catch (JsonProcessingException e) {
            logger.warn("生成Token失败, 源串:{}", userInfo);
            return null;
        }
    }

    /**
     * 生成api_key
     * @param appId 应用id
     * @param userId  用户id
     * @param username  用户名
     * @param expireTime
     * @return
     */
    public static String createApiKey(String dataSecret, String appId, String host, String userId, String username, long expireTime) {
        ApiKey apiKey = new ApiKey();
        apiKey.setAppId(appId);
        apiKey.setUserId(userId);
        apiKey.setHost(host);
        apiKey.setUsername(username);
        apiKey.setExpireTime(expireTime);
        String data = JsonUtil.toJson(apiKey);
        return AESUtil.encrypt(data, dataSecret);
    }

    /**
     * 获取api key
     * @param dataSecret
     * @param apiKey
     * @return
     */
    public static ApiKey parseApiKey(String dataSecret, String apiKey) {
        String content = AESUtil.decrypt(apiKey, dataSecret);
        return JsonUtil.toBean(content, ApiKey.class);
    }

    /**
     * 通过token获取当前的用户信息
     * @param token         令牌
     * @param dataSecret    数据密钥
     * @param iss           发行机构
     * @param ip            ip
     * @param tokenExpireMinutes    令牌有效时长（分)）
     */
    public static BaseUserInfo getUserInfoByToken(String token, String dataSecret,  String iss, String ip, int tokenExpireMinutes, int mockSessionExpireMinutes) {
        // 如果令牌为空
        if (StringUtils.isEmpty(token)) {
            logger.info("token: {}", token);
            throw new UnauthorizedException(I18n.t("auth. unauthorized-e001", "用户未登录，代码: E001"));
        }
        token = autoGetToken(token);
        // 解密令牌
        String decryptToken = AESUtil.decrypt(token, dataSecret);
        // 如果令牌无法解密
        if (StringUtils.isEmpty(decryptToken)) {
            logger.info("token: {}", token);
            throw new UnauthorizedException(I18n.t("auth. unauthorized-e002", "用户未登录，代码: E002"));
        }
        AuthToken authToken;
        try {
            authToken = new ObjectMapper().readValue(decryptToken, AuthToken.class);
        } catch (JsonProcessingException e) {
            // 如果令牌无法转为实体
            throw new UnauthorizedException(I18n.t("auth. unauthorized-e003", "用户未登录，代码: E003"));
        }
        // 校验发行机构
//        if (!iss.equals(authToken.getIss())) {
//            throw new UnauthorizedException(I18n.t("auth. unauthorized-e004", "用户未登录，错误码: E004"));
//        }
        // 校验ip
        if (!ip.equals(authToken.getIp())) {
            logger.warn("current's Ip:{}, token's IP:{}", ip, authToken.getIp());
            //throw new UnauthorizedException(I18n.t("auth. unauthorized-e005", "用户未登录，错误码: E005"));
        }
        // 当模拟session的有效时间小于0时，走jwt的校验, 或者登录30秒之内
        if (mockSessionExpireMinutes <= 0 || (authToken.getWhenCreated() + 1000*30) > System.currentTimeMillis()) {
            // 校验令牌有效性
            long expireTime = authToken.getWhenCreated() + ((long) tokenExpireMinutes * 60 * 1000);
            if (expireTime < System.currentTimeMillis()) {
                logger.info("token: {}", token);
                throw new UnauthorizedException(I18n.t("auth. unauthorized-e006", "登录已失效"));
            }
        }
        // 否则，走传统的session方案
        else {
            TokenSession ts = SessionManager.getInstance().getByToken(authToken.getUserInfo().getId(), token);
            if (ts == null) {
                logger.info("token: {}", token);
                throw new UnauthorizedException(I18n.t("auth. unauthorized-e007", "登录会话不存在，请重新登录"));
            }
            long expireTime = ts.getActiveTime().getTime() + ((long) mockSessionExpireMinutes * 60 * 1000);
            logger.info("session过期时间:{}", DateUtils.formatDate(new Date(expireTime), "yyyy-MM-dd HH:mm:ss"));
            if (expireTime < System.currentTimeMillis()) {
                // 删除登录会话
                SysOnlineUser onlineUser = DB.findOne(SysOnlineUser.class, Expr.builder().add("loginToken", "=", token).build());
                if (onlineUser != null) {
                    DB.delete(onlineUser);
                    InstanceManager.getInstance().broadMessage("session-remove", JsonUtil.toJson(onlineUser));
                    SessionManager.getInstance().removeSession(onlineUser.getUserId(), onlineUser.getLoginToken());
                }
                logger.info("token: {}", token);
                throw new UnauthorizedException(I18n.t("auth. unauthorized-e006", "登录已失效"));
            }
        }
        AppAuthProperties appAuthProperties = SpringContext.getBean(AppAuthProperties.class);
        // 检查是否只有一个会话
        if (appAuthProperties.getLoginSessionOne()) {
            if(((authToken.getWhenCreated() + 1000*60) > System.currentTimeMillis())) {
                if (!tokenFreshRecords.contains(token)) {
                    SessionManager.getInstance().reloadSessions();
                    tokenFreshRecords.add(token);
                }
                else {
                    tokenFreshRecords.remove(token);
                }

            }
            if (!SessionManager.getInstance().checkSession(authToken.getUserInfo().getId(), token)) {
                throw new UnauthorizedException(I18n.t("auth. unauthorized-e007", "用户已在别处登录"));
            }
        }

        // 返回用户信息
        return authToken.getUserInfo();
    }


    public static BaseUserInfo getUserInfoByToken(String token, String dataSecret) {
        try {
            // 解密令牌
            String decryptToken = AESUtil.decrypt(autoGetToken(token), dataSecret);
            AuthToken authToken = new ObjectMapper().readValue(decryptToken, AuthToken.class);
            return authToken.getUserInfo();
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取令牌信息
     * @param token 令牌
     * @return  令牌信息
     */
    public static AuthToken getAuthToken(String token) {
        try {
            AppAuthProperties appAuthProperties = SpringContext.getBean(AppAuthProperties.class);
            // 解密令牌
            String decryptToken = AESUtil.decrypt(autoGetToken(token), appAuthProperties.getTokenSecret());
            return new ObjectMapper().readValue(decryptToken, AuthToken.class);
        }
        catch (Exception e) {
            return null;
        }
    }

    public static String autoGetToken(String token) {
        if (StringUtils.isEmpty(token)){
            return token;
        }
        if (token.length() == 32) {
            return SessionManager.getInstance().getTokenByMd5(token);
        }
        else {
            return token;
        }
    }

    /**
     * 获取令牌字符串
     * @return 令牌
     */
    public static String getTokenString(HttpServletRequest request) {
        // 获取对应的请求头
        String auth = request.getHeader(AUTH_HEADER);
        // 如果auth为空，则提示用户登录
        if (StringUtils.isEmpty(auth)) {
            auth = request.getParameter("token");
            if (StringUtils.isEmpty(auth)) {
                // 从cookie中获取
                auth = ServletUtil.getCookie(request,"pineToken", "");
            }

        }
        // 获取当前令牌
        return auth.replace(AUTH_PREFIX, "").replaceAll(" ", "+");
    }

}
