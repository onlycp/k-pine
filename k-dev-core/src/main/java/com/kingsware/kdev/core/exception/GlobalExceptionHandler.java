package com.kingsware.kdev.core.exception;

import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.context.KClientContext;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.enums.RetEnum;
import com.kingsware.kdev.core.i18n.I18n;
import com.kingsware.kdev.core.orm.exception.OrmDbException;
import com.kingsware.kdev.core.orm.exception.TransactionException;
import com.kingsware.kdev.core.util.ExceptionUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;



@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 业务异常
     */
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public BaseRet<Void> businessExceptionHandler(HttpServletRequest request, BusinessException e) {
        KClientContext.getContext().setErrorMessage(e.getMessage());
        return BaseRet.failData(e.getMessage(), e.getData());
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(value = OrmDbException.class)
    @ResponseBody
    public BaseRet<Void> ormExceptionHandler(HttpServletRequest request, OrmDbException e) {
        String devMode = SpringContext.getProperties("app.mode.dev", "true");
        String message = I18n.t("GlobalExceptionHandler.error1","数据库操作异常:{0}", e.getMessage());
        if ("true".equals(devMode)) {
            return BaseRet.failMessage(message, e.getKlog(), e.getExceptionTrace());
        }
        else {
            return BaseRet.failMessage(message);
        }

    }

    /**
     * 业务异常
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public BaseRet<Void> exceptionHandler(HttpServletRequest request, Exception e) {
        String devMode = SpringContext.getProperties("app.mode.dev", "true");
        log.error("系统内部异常:", e);
        String message = I18n.t("GlobalExceptionHandler.error2","系统内部异常:{0}", e.getMessage());
        if ("true".equals(devMode)) {
            return BaseRet.failMessage(message, null, ExceptionUtils.getStackTrace(e));
        }
        else {
            return BaseRet.failMessage(message);
        }

    }

    /**
     * 业务异常
     */
    @ExceptionHandler(value = TransactionException.class)
    @ResponseBody
    public BaseRet<Void> transactionExceptionHandler(HttpServletRequest request, TransactionException e) {
        String devMode = SpringContext.getProperties("app.mode.dev", "true");
        if ("true".equals(devMode)) {
            return BaseRet.failMessage(e.getMessage(), e.getKlog(), e.getExceptionTrace());
        }
        else {
            return BaseRet.failMessage(e.getMessage());
        }

    }

    /**
     * 用户未登录或过期
     */
    @ExceptionHandler(value = UnauthorizedException.class)
    @ResponseBody
    public BaseRet<Void> unauthorizedExceptionHandler(HttpServletRequest request, UnauthorizedException e) {
        return BaseRet.fail(e.getMessage(), RetEnum.UNAUTHORIZED.getCode());
    }

    /**
     * 用户未登录或过期
     */
    @ExceptionHandler(value = ForbiddenException.class)
    @ResponseBody
    public BaseRet<Void> forbiddenExceptionHandler(HttpServletRequest request, ForbiddenException e) {
        return BaseRet.fail(e.getMessage(), RetEnum.FORBIDDEN.getCode());
    }



}

