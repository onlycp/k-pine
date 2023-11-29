package com.kingsware.kdev.core.auth;

import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.enums.RetEnum;
import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.util.ServletUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
@Order(3)
public class DevApiAspect {
    @Value("${app.mode.dev:false}")
    private boolean modeDev;

    @Around("execution(public * com.kingsware..web.*.*(..)) && @annotation(dev)")
    public Object process(ProceedingJoinPoint pjd, Dev dev) throws Throwable {
        if (modeDev || ServletUtil.isRefererRule(ServletUtil.request())) {
            return pjd.proceed();
        }
        else {
            throw BusinessException.serviceThrow("发布模式无权访问此接口");
        }
    }
}
