package com.kingsware.kdev.core.encrypt.inst;

import com.kingsware.kdev.core.encrypt.EncryptInterface;
import com.kingsware.kdev.core.encrypt.config.AlgorithmConfig;
import com.kingsware.kdev.core.encrypt.config.Base64Config;
import com.kingsware.kdev.core.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Base64实例
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/27 2:50 下午
 */
public class Base64Instance implements EncryptInterface {

    @Override
    public String encrypt(String source, AlgorithmConfig config) {
        return new String(Base64.getEncoder().encode(source.getBytes(StandardCharsets.UTF_8)));

    }

    @Override
    public boolean validate(String source, String encrypted, AlgorithmConfig config) {
        // 如果源串为空
        if (StringUtils.isEmpty(source)) {
            return false;
        }
        String str1 = encrypt(source, config);
        return str1.equals(encrypted);

    }
}
