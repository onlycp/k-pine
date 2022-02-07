package com.kingsware.kdev.biz.kw.exception;

/**
 * 浦发接口发送异常
 */
public class MbsReqException extends RuntimeException{
    public MbsReqException(String message){
        super(message);
    }
}
