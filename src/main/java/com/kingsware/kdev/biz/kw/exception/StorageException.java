package com.kingsware.kdev.biz.kw.exception;

/**
 * 系统存储异常
 */
public class StorageException extends RuntimeException {

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }

}
