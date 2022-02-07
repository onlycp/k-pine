package com.kingsware.kdev.core.exception;

/**
 * 用户未授权异常
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/21 9:17 上午
 */
public class UnauthorizedException extends RuntimeException{
    /**
     * 默认构造函数
     * @param message  提示消息
     */
    public UnauthorizedException(String message) {
        super(message);
    }
}
