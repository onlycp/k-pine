package com.kingsware.kdev.core.orm.exception;

/**
 * 事务异常
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/22 3:39 下午
 */
public class TransactionException extends RuntimeException {

    private String klog;

    private String exceptionTrace;
    /**
     * 默认构造函数
     * @param message    提示消息
     */
    public TransactionException(String message) {
        super(message);
    }

    public TransactionException(String message, String klog, String exceptionTrace) {
        super(message);
        this.klog = klog;
        this.exceptionTrace = exceptionTrace;
    }

    public String getKlog() {
        return klog;
    }

    public String getExceptionTrace() {
        return exceptionTrace;
    }

}
