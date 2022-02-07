package com.kingsware.kdev.biz.kw.exception;

/**
 * 重复的回单流水异常
 */
public class DuplicateReceiptWaterException extends RuntimeException{
    public DuplicateReceiptWaterException(String message) {
        super(message);
    }

    public DuplicateReceiptWaterException(String message, Throwable cause) {
        super(message, cause);
    }
}
