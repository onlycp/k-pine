package com.kingsware.kdev.core.encrypt.inst;

import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.encrypt.EncryptInterface;
import com.kingsware.kdev.core.encrypt.config.AlgorithmConfig;
import com.kingsware.kdev.core.util.AESUtil;
import com.kingsware.kdev.core.util.Base64Utils;
import com.kingsware.kdev.core.util.MD5Utils;
import com.kingsware.kdev.core.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * Base64实例
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/27 2:50 下午
 */
@Slf4j
public class Base64Instance implements EncryptInterface {

    @Override
    public String encrypt(String source, AlgorithmConfig config, String slat) {
        String hash = calculateHash(Base64Utils.encodeToString(slat.getBytes(StandardCharsets.UTF_8)));
        String toEncryptStr = source + "|" + hash;
        String md5Value = MD5Utils.md5(toEncryptStr);
        String secret =  SpringContext.getProperties("encrypt.pwd.secret", "PsLZlazUBBUZYyPA");
        return "enc#" + AESUtil.encrypt(md5Value, secret);

    }

    @Override
    public boolean validate(String source, String encrypted, AlgorithmConfig config, String slat) {
        // 如果源串为空
        if (StringUtils.isEmpty(source)) {
            return false;
        }
        if (encrypted.startsWith("enc#")) {
            String str1 = encrypt(source, config, slat);
            return str1.equals(encrypted);
        }
        else {
            log.warn("密码存在安全问题，已禁用");
            return false;
        }
    }

    /**
     * 计算字符串的hash值
     * @param str 需要计算hash的字符串
     * @return hash值
     */
    private String calculateHash(String str) {
        if (StringUtils.isEmpty(str)) {
            return "";
        }

        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        int hash = 0;

        for (byte b : bytes) {
            hash = 31 * hash + (b & 0xff);
        }

        return String.valueOf(Math.abs(hash));
    }
}
