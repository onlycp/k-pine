package com.kingsware.kdev.core.exception;

/**
 * http接口请求异常
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/21 9:17 上午
 */
public class HttpClientException extends RuntimeException{

    /** 响应码 **/
    private int code;
    /** url **/
    private String url;
    /** 请求参数 **/
    private String params;

    public HttpClientException(String message, int code, String url, String params) {
        super(message);
        this.code = code;
        this.url = url;
        this.params = params;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
}
