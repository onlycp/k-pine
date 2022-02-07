package com.kingsware.kdev.core.encrypt.config;

/**
 * AES加密配置
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/27 2:28 下午
 */
public class AESConfig extends AlgorithmConfig {
    /** 密钥 **/
    private String secret;
    /** 算法/模式/补码方式 **/
    private String cipher;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getCipher() {
        return cipher;
    }

    public void setCipher(String cipher) {
        this.cipher = cipher;
    }
}
