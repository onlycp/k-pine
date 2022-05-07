package com.kingsware.kdev.core.exception;

/**
 * license异常
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/21 9:17 上午
 */
public class LicenseException extends RuntimeException{
    /**
     * 默认构造函数
     * @param message   提示消息
     */
    public LicenseException(String message) {
        super(message);
    }

    /**
     * 业务异常
     * @param message 提示消息
     * @return  异常
     */
    public static LicenseException serviceThrow(String message) {
        return new LicenseException(message);
    }
}
