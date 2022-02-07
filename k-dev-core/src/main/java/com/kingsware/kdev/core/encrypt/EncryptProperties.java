package com.kingsware.kdev.core.encrypt;

import com.kingsware.kdev.core.encrypt.config.AESConfig;
import com.kingsware.kdev.core.encrypt.config.Base64Config;
import com.kingsware.kdev.core.encrypt.config.MD5Config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 加解密配置文件
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/27 2:23 下午
 */
@Component
@ConfigurationProperties(prefix = "encrypt")
public class EncryptProperties {
    /** 加密方式 **/
    private String mode;

    /** AES配置 **/
    private AESConfig aes;

    /** Base64配置 **/
    private Base64Config base64;

    /** md5配置 **/
    private MD5Config md5;

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public AESConfig getAes() {
        return aes;
    }

    public void setAes(AESConfig aes) {
        this.aes = aes;
    }

    public Base64Config getBase64() {
        return base64;
    }

    public void setBase64(Base64Config base64) {
        this.base64 = base64;
    }

    public MD5Config getMd5() {
        return md5;
    }

    public void setMd5(MD5Config md5) {
        this.md5 = md5;
    }
}
