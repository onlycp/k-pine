package com.kingsware.kdev.core.orm.exception;

/**
 * 数据库操作异常
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/22 3:39 下午
 */
public class OrmDbException extends RuntimeException {

    private String klog;
    /**
     * 默认构造函数
     * @param message    提示消息
     */
    public OrmDbException(String message) {
        super(message);
    }

    public OrmDbException(String message, String klog) {
        super(message);
        this.klog = klog;
    }

    public String getKlog() {
        return klog;
    }
}
