package com.kingsware.kdev.core.auth;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 权限配置类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/20 1:55 下午
 */
@Component
@ConfigurationProperties(prefix = "app.auth")
public class AppAuthProperties {
    /** 是否启动权限认证 **/
    private Boolean enable = true;
    /** 令牌发行机构 **/
    private String iss = "kingsware";
    /** 令牌有效时长 **/
    private int tokenExpireMinutes = 120;
    /** 加密密钥 **/
    private String tokenSecret;

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getIss() {
        return iss;
    }

    public void setIss(String iss) {
        this.iss = iss;
    }

    public int getTokenExpireMinutes() {
        return tokenExpireMinutes;
    }

    public void setTokenExpireMinutes(int tokenExpireMinutes) {
        this.tokenExpireMinutes = tokenExpireMinutes;
    }

    public String getTokenSecret() {
        return tokenSecret;
    }

    public void setTokenSecret(String tokenSecret) {
        this.tokenSecret = tokenSecret;
    }
}
