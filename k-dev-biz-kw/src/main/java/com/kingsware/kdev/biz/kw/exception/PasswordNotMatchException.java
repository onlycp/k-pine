package com.kingsware.kdev.biz.kw.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 密码错误异常
 */
@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Passowrd is not match")
public class PasswordNotMatchException extends RuntimeException {
    public PasswordNotMatchException(String message){
        super(message);
    }
}
