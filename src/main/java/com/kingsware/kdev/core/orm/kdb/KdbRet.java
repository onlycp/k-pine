package com.kingsware.kdev.core.orm.kdb;

/**
 * kdb响应结果
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/24 3:10 下午
 */
public class KdbRet {
    /** 响应码 **/
    private int errorCode;
    /** 信息 **/
    private String message;
    /** 响应体 **/
    private String responseBody;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }
}
