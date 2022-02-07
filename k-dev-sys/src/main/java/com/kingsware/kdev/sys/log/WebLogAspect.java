package com.kingsware.kdev.sys.log;

import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.context.KClientContext;
import com.kingsware.kdev.core.enums.RetEnum;
import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.kmq.KmqMessageCenter;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.sys.argv.SysUserLoginArgv;
import com.kingsware.kdev.sys.model.SysLoginLog;
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

    /** 操作日志log的topic **/
    public static final String TOPIC_OPERATE_LOG = "t_operate_log";
    /** 操作日志log的topic **/
    public static final String TOPIC_LOGIN_LOG = "t_login_log";

    @Around("execution(public * com.kingsware..web.*.*(..)) && @annotation(apiOperation)")
    public Object process(ProceedingJoinPoint pjd, ApiOperation apiOperation) throws Throwable {

        // 执行并获取结果
        long t1 = System.currentTimeMillis();
        // 耗时
        long takeTime = 0;
        // 异常
        Exception exception = null;
        // 结果
        Object result = null;
        // 响应码
        int responseCode = -1;
        // 响应信息
        String responseMessage = "";
        try {
            // 执行方法
            result = pjd.proceed();
            // 处理结果
            if (result instanceof BaseRet) {
                BaseRet<?> ret = (BaseRet<?>) result;
                responseCode = ret.getCode();
                responseMessage = ret.getMessage();
            }
        }
        catch (Exception e) {
            // 暂存异常
            exception = e;
            // 如果业务异常
            if (e instanceof BusinessException) {
                BusinessException businessException = (BusinessException) e;
                responseCode = RetEnum.EXCEPTION.getCode();
                responseMessage = businessException.getMessage();
            }
        }
        finally {
            takeTime = System.currentTimeMillis() - t1;
        }

        // 保存日志
        try {
            Class<? extends Object> targetClass = pjd.getTarget().getClass();
            if (pjd.getTarget().getClass().isAnnotationPresent(Api.class)) {
                // 获取模块注解
                Api m = targetClass.getAnnotation(Api.class);
                // 生成操作日志
                SysOperateLog operateLog = new SysOperateLog();
                operateLog.setOperator(KClientContext.getContext().getUserInfo() != null ? KClientContext.getContext().getUserInfo().getUsername() : "");
                operateLog.setAction(apiOperation.value());
                operateLog.setModule(m.value());
                operateLog.setIp(KClientContext.getContext().getIp());
                operateLog.setTimes((int)(takeTime));
                operateLog.setUrl(KClientContext.getContext().getUrl());
                operateLog.setResponseCode(responseCode);
                operateLog.setResponseMessage(responseMessage);
                operateLog.setOperateTime(new Timestamp(System.currentTimeMillis()));
                operateLog.setRequestBody(JsonUtil.toJson(pjd.getArgs()));
                KmqMessageCenter.getInstance().produce(TOPIC_OPERATE_LOG, JsonUtil.toJson(operateLog) );

                //  如果是登录，则同时生成一个登录日志
                String operate = apiOperation.value().trim();
                if ("登录".equals(operate)) {
                    // 获取表单信息
                    SysUserLoginArgv loginArgv = (SysUserLoginArgv)pjd.getArgs()[0];
                    SysLoginLog loginLog = new SysLoginLog();
                    loginLog.setOperator(loginArgv.getUsername());
                    loginLog.setIp(KClientContext.getContext().getIp());
                    loginLog.setResponseCode(responseCode);
                    loginLog.setTimes((int)takeTime);
                    loginLog.setResponseMessage(responseMessage);
                    loginLog.setOperateTime(new Timestamp(System.currentTimeMillis()));
                    KmqMessageCenter.getInstance().produce(TOPIC_LOGIN_LOG, JsonUtil.toJson(loginLog) );
                }

            }
        }
        catch (Exception e) {
            log.error("日志保存异常，异常信息:{}", e.getMessage());
        }
        if (exception != null) {
            throw exception;
        }
        return result;
    }
}
