package com.kingsware.kdev.biz.kw.exception;

/**
 * 每日录入回单流水异常
 */
public class DailyReceiptWaterException extends RuntimeException {

    public DailyReceiptWaterException(String message) {
        super(message);
    }

    public DailyReceiptWaterException(String message, Throwable cause) {
        super(message, cause);
    }
}