package com.kingsware.kdev.core.exception;

/**
 * 用户未授权异常
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/21 9:17 上午
 */
public class BusinessException extends RuntimeException{
    /**
     * 默认构造函数
     * @param message   提示消息
     */
    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Object data) {
        super(message);
        this.data = data;
    }

    public BusinessException(Throwable cause, Object data) {
        super(cause);
        this.data = data;
    }



    private Object data;

    /**
     * 获取数据
     * @return
     */
    public Object getData() {
        return data;
    }


    /**
     * 业务异常
     * @param message 提示消息
     * @return  异常
     */
    public static BusinessException serviceThrow(String message) {
        return new BusinessException(message);
    }

    public static BusinessException serviceThrow(String message, Object data) {
        return new BusinessException(message, data);
    }

}
