package com.kingsware.kdev.core.orm.kdb;

import lombok.Data;

/**
 * kdb响应结果
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/24 3:10 下午
 */
@Data
public class KdbRet<T> {
    /** 响应码 **/
    private int errorCode;
    /** 异常堆栈信息 **/
    private String stackTrace;
    /** 信息 **/
    private String message;
    /** 响应体 **/
    private T responseBody;
    /** log **/
    private String klog;
}
