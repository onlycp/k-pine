package com.kingsware.kdev.core.encrypt.inst;

import com.kingsware.kdev.core.encrypt.EncryptInterface;
import com.kingsware.kdev.core.encrypt.config.AESConfig;
import com.kingsware.kdev.core.encrypt.config.AlgorithmConfig;
import com.kingsware.kdev.core.util.AESUtil;
import com.kingsware.kdev.core.util.StringUtils;

/**
 * AES加解密实例
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/27 3:15 下午
 */
public class AESInstance implements EncryptInterface {

    @Override
    public String encrypt(String source, AlgorithmConfig config) {
        AESConfig aesConfig = (AESConfig) config;
        // 获取密钥
        String secret = aesConfig.getSecret();
        // 获取补码方式
        String cipher = aesConfig.getCipher();
        // 如果补码方式为空
        if (StringUtils.isEmpty(cipher)) {
            return AESUtil.encrypt(source, secret);
        }
        else {
            return AESUtil.encrypt(source, secret, cipher);
        }
    }

    @Override
    public boolean validate(String source, String encrypted, AlgorithmConfig config) {
        // 加密源串
        String str1 =  encrypt(source, config);
        // 返回
        return str1.equalsIgnoreCase(encrypted);
    }
}
