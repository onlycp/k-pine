package com.kingsware.kdev.biz.kw.exception;

/**
 * 错误的流水顺序异常
 */
public class CurreptWaterIndexExceiption extends RuntimeException{
    public CurreptWaterIndexExceiption(String message) {
        super(message);
    }

    public CurreptWaterIndexExceiption(String message, Throwable cause) {
        super(message, cause);
    }
}
