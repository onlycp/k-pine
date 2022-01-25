package com.kingsware.kdev.core.exception;

import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.enums.RetEnum;
import com.kingsware.kdev.core.orm.exception.OrmDbException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 业务异常
     */
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public BaseRet<?> businessExceptionHandler(HttpServletRequest request, BusinessException e) {
        return BaseRet.failMessage(e.getMessage());
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(value = OrmDbException.class)
    @ResponseBody
    public BaseRet<?> ormExceptionHandler(HttpServletRequest request, OrmDbException e) {
        return BaseRet.failMessage(e.getMessage());
    }

    /**
     * 用户未登录或过期
     */
    @ExceptionHandler(value = UnauthorizedException.class)
    @ResponseBody
    public BaseRet<?> unauthorizedExceptionHandler(HttpServletRequest request, UnauthorizedException e) {
        return BaseRet.fail(e.getMessage(), RetEnum.UNAUTHORIZED.getCode());
    }

    /**
     * 用户未登录或过期
     */
    @ExceptionHandler(value = ForbiddenException.class)
    @ResponseBody
    public BaseRet<?> forbiddenExceptionHandler(HttpServletRequest request, ForbiddenException e) {
        return BaseRet.fail(e.getMessage(), RetEnum.FORBIDDEN.getCode());
    }



}

