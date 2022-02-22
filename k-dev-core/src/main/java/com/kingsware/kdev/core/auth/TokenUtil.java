package com.kingsware.kdev.core.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingsware.kdev.core.exception.UnauthorizedException;
import com.kingsware.kdev.core.i18n.I18n;
import com.kingsware.kdev.core.util.AESUtil;
import com.kingsware.kdev.core.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 令牌工具类，用于生成令牌及令牌验证
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/20 2:07 下午
 */
public class TokenUtil {

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
    public static String createToken(String dataSecret, String iss, String ip, BaseUserInfo userInfo) {
        // 实例一个令牌对象
        AuthToken authToken = new AuthToken();
        authToken.setIp(ip);
        authToken.setIss(iss);
        authToken.setWhenCreated(System.currentTimeMillis());
        authToken.setUserInfo(userInfo);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String token = objectMapper.writeValueAsString(authToken);
            // 将令牌字符串使用AES加密（128位)
            return AESUtil.encrypt(token, dataSecret);
        } catch (JsonProcessingException e) {
            logger.warn("生成Token失败, 源串:{}", userInfo);
            return null;
        }
    }

    /**
     * 通过token获取当前的用户信息
     * @param token         令牌
     * @param dataSecret    数据密钥
     * @param iss           发行机构
     * @param ip            ip
     * @param tokenExpireMinutes    令牌有效时长（分)）
     */
    public static BaseUserInfo getUserInfoByToken(String token, String dataSecret,  String iss, String ip, int tokenExpireMinutes) {
        // 如果令牌为空
        if (StringUtils.isEmpty(token)) {
            throw new UnauthorizedException(I18n.t("auth. unauthorized-e001", "用户未登录，错误码: E001"));
        }
        // 解密令牌
        String decryptToken = AESUtil.decrypt(token, dataSecret);
        // 如果令牌无法解密
        if (StringUtils.isEmpty(decryptToken)) {
            throw new UnauthorizedException(I18n.t("auth. unauthorized-e002", "用户未登录，错误码: E002"));
        }
        AuthToken authToken;
        try {
            authToken = new ObjectMapper().readValue(decryptToken, AuthToken.class);
        } catch (JsonProcessingException e) {
            // 如果令牌无法转为实体
            throw new UnauthorizedException(I18n.t("auth. unauthorized-e003", "用户未登录，错误码: E003"));
        }
        // 校验发行机构
        if (!iss.equals(authToken.getIss())) {
            throw new UnauthorizedException(I18n.t("auth. unauthorized-e004", "用户未登录，错误码: E004"));
        }
        // 校验ip
        if (!ip.equals(authToken.getIp())) {
            throw new UnauthorizedException(I18n.t("auth. unauthorized-e005", "用户未登录，错误码: E005"));
        }
        // 校验令牌有效性
        long expireTime = authToken.getWhenCreated() + ((long) tokenExpireMinutes * 60 * 1000);
        if (expireTime < System.currentTimeMillis()) {
            throw new UnauthorizedException(I18n.t("auth. unauthorized-e006", "登录已失效"));
        }
        // 返回用户信息
        return authToken.getUserInfo();
    }
}
