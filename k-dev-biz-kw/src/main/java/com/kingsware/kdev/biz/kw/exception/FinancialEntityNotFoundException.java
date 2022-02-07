package com.kingsware.kdev.biz.kw.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 记录没找到异常
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Entity not found")
public class FinancialEntityNotFoundException extends RuntimeException {

    public FinancialEntityNotFoundException(String message){
        super(message);
    }
}
