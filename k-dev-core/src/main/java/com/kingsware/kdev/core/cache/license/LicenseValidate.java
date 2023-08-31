package com.kingsware.kdev.core.cache.license;

/**
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2023/8/31 17:28
 */
public interface LicenseValidate {
    /**
     * 验证key
     * @return 返回key
     */
    String key();

    /**
     * 错误信息
     * @return 错误信息
     */
    String errorMessage();

    /**
     * 验证
     * @param data 消息内容
     * @return 返回验证结果
     */
    boolean execute(String data);
}
