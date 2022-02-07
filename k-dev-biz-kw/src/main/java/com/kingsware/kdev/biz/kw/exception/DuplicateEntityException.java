package com.kingsware.kdev.biz.kw.exception;

/**
 * 重复的记录异常
 */
public class DuplicateEntityException extends RuntimeException{
    public DuplicateEntityException(String message) {
        super(message);
    }

    public DuplicateEntityException(String message, Throwable cause) {
        super(message, cause);
    }
}