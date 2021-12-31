package com.kingsware.kdev.core.log;

import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.context.KClientContext;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.sys.model.SysOperateLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * 平台日志
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/31 10:39 上午
 */
@Aspect
@Slf4j
@Component
@Order(3)
public class WebLogAspect {

    @Around("execution(public * com.kingsware..web.*.*(..)) && @annotation(apiOperation)")
    public Object process(ProceedingJoinPoint pjd, ApiOperation apiOperation) throws Throwable {

        // 执行并获取结果
        long t1 = System.currentTimeMillis();
        Object result = pjd.proceed();
        long t2 = System.currentTimeMillis();
        // 保存日志
        try {
            Class targetClass = pjd.getTarget().getClass();
            if (pjd.getTarget().getClass().isAnnotationPresent(Api.class)) {
                // 获取模块注解
                Api m = (Api) targetClass.getAnnotation(Api.class);
                if (result instanceof BaseRet) {
                    BaseRet<?> ret = (BaseRet<?>) result;
                    // 生成日志
                    SysOperateLog operateLog = new SysOperateLog();
                    operateLog.setOperator(KClientContext.getContext().getUserInfo() != null ? KClientContext.getContext().getUserInfo().getUsername() : "");
                    operateLog.setAction(apiOperation.value());
                    operateLog.setModule(m.value());
                    operateLog.setIp(KClientContext.getContext().getIp());
                    operateLog.setTimes((int)(t2 - t1));
                    operateLog.setUrl(KClientContext.getContext().getUrl());
                    operateLog.setResponseCode(ret.getCode());
                    operateLog.setResponseMessage(ret.getMessage());
                    operateLog.setOperateTime(new Timestamp(System.currentTimeMillis()));
                    operateLog.setRequestBody(JsonUtil.toJson(pjd.getArgs()));
                    DB.save(operateLog);
                }

            }
        }
        catch (Exception e) {
            log.error("日志保存异常，异常信息:{}", e.getMessage());
        }
        return result;
    }
}
