package com.kingsware.kdev.core.kflow.bean;

import lombok.Data;

/**
 * kdb响应结果
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/3/3 3:33 下午
 */
@Data
public class KdbFlowResult {
    /** 结果类型 json、file **/
    private String type;
    /** 处理之后的结束 **/
    private Object data;
    /** 日志信息 **/
    private String log;
    /** 异常堆栈 **/
    private String exceptionStack;
}
