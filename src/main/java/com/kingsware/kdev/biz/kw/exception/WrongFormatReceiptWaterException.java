package com.kingsware.kdev.biz.kw.exception;

/**
 * 错误的回单流水格式异常
 */
public class WrongFormatReceiptWaterException extends RuntimeException{
    public WrongFormatReceiptWaterException(String message) {
        super(message);
    }

    public WrongFormatReceiptWaterException(String message, Throwable cause) {
        super(message, cause);
    }
}
