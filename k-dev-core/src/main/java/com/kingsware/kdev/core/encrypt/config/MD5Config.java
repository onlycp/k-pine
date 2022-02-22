package com.kingsware.kdev.core.encrypt.config;

/**
 * MD5加密配置
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/27 2:28 下午
 */
public class MD5Config implements AlgorithmConfig{
    /** 结果大小写 **/
    private String lowerUpper;

    public String getLowerUpper() {
        return lowerUpper;
    }

    public void setLowerUpper(String lowerUpper) {
        this.lowerUpper = lowerUpper;
    }
}
