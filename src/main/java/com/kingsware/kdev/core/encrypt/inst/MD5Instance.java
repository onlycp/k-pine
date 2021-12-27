package com.kingsware.kdev.core.encrypt.inst;

import com.kingsware.kdev.core.encrypt.EncryptInterface;
import com.kingsware.kdev.core.encrypt.config.AlgorithmConfig;
import com.kingsware.kdev.core.encrypt.config.MD5Config;
import com.kingsware.kdev.core.util.MD5Utils;
import com.kingsware.kdev.core.util.StringUtils;

/**
 * MD5实现类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/27 2:58 下午
 */
public class MD5Instance implements EncryptInterface {

    @Override
    public String encrypt(String source, AlgorithmConfig config) {
        MD5Config md5Config = (MD5Config) config;
        String encrypted = MD5Utils.md5(source);
        // 获取大小写定义
       String lowerUpper = getLowerUpper(md5Config);
        if ("lower".equals(lowerUpper)) {
            return encrypted.toLowerCase();
        }
        else {
            return encrypted.toUpperCase();
        }
    }

    @Override
    public boolean validate(String source, String encrypted, AlgorithmConfig config) {

        // 获取大小写定义
        String lowerUpper = getLowerUpper((MD5Config) config);
        // 加密源串
        String str1 = MD5Utils.md5(source);
        // 返回结果
        return str1.equalsIgnoreCase(lowerUpper);
    }

    /**
     * 获取大小写
     * @param md5Config 配置
     * @return  大小写
     */
    private String getLowerUpper(MD5Config md5Config) {
        String lowerUpper = "uppper";
        if (StringUtils.isNotEmpty(md5Config.getLowerUpper())) {
            lowerUpper = md5Config.getLowerUpper();
        }
        return lowerUpper;
    }
}
