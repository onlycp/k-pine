package com.kingsware.kdev.core.enums;

/**
 * 响应
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/20 9:50 上午
 */
public enum RetEnum {
    // 成功
    OK(200),
    // 未授权 (用来处理用户是否已登录)
    UNAUTHORIZED(401),
    // 接口或资源未授权
    FORBIDDEN(403),
    // 系统异常
    EXCEPTION(500),
    // 业务异常
    SERVICE_FAIL(600);


    /** 响应码 **/
    private final int code;

    /**
     * 默认构造函数
     * @param code  响应码
     */
    RetEnum(int code) {
        this.code = code;
    }

    /**
     * 获取响应码
     * @return  响应码
     */
    public int getCode() {
        return this.code;
    }
}
