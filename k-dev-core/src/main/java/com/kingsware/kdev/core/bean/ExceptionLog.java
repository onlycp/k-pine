package com.kingsware.kdev.core.bean;

import lombok.Data;

import java.util.Map;


/**
 * 异常日志类，用于记录异常信息。
 *
 * @param id 异常日志的唯一标识符。
 * @param stackTrace 异常的堆栈跟踪信息。
 * @param klog 关联的日志信息，可能包含错误前的上下文信息。
 */
@Data
public class ExceptionLog {

    private String id; // 异常日志的唯一标识
    private String stackTrace; // 异常的堆栈跟踪信息
    private String klog; // 关联的日志信息
    private Map<String, Object> argv; // 输入参数
}
