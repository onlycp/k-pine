package com.kingsware.kdev.core.exception;

/**
 * 接口或资源未授权
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/21 9:17 上午
 */
public class ForbiddenException extends RuntimeException{
    /**
     * 默认构造函数
     * @param message    提示消息
     */
    public ForbiddenException(String message) {
        super(message);
    }
}
