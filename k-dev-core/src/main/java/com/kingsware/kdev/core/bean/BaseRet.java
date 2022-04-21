package com.kingsware.kdev.core.bean;

import com.kingsware.kdev.core.enums.RetEnum;
import com.kingsware.kdev.core.i18n.I18n;

import java.io.Serializable;

/**
 * 协议输出基础类
 * @author chenpeng
 * @date  2021-11-15
 */
public class BaseRet<T> implements Serializable {
    /** 业务响应码 **/
    private int code;
    /** 提示信息 **/
    private String message;
    /** 数据 **/
    private T data;
    /** 日志 **/
    private String log;

    public BaseRet() {
    }

    public BaseRet(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    /**
     * 成功返回
     * @param message  提示信息
     * @param <T>   泛型
     * @return      返回成功结果
     */
    public static <T> BaseRet<T> successMessage(String message) {
        return success(null, message);
    }

    /**
     * 业务异常时返回结果体
     * @param message  提示信息
     * @param <T>   泛型
     * @return      返回成功结果
     */
    public static <T> BaseRet<T> failMessage(String message) {
        return new BaseRet<>(RetEnum.SERVICE_FAIL.getCode(), message, null);
    }

    /**
     * 业务异常时返回结果体
     * @param message  提示信息
     * @param <T>   泛型
     * @return      返回响应
     */
    public static <T> BaseRet<T> fail(String message, int code) {
        return new BaseRet<>(code, message, null);
    }


    /**
     * 成功返回
     * @param data  数据
     * @param <T>   泛型
     * @return      返回成功结果
     */
    public static <T> BaseRet<T> success(T data) {
        return success(data, I18n.t("common.ok", "OK"));
    }

    /**
     * 成功返回
     * @return      返回成功结果
     */
    public static  BaseRet<?> success() {
        return success(null, I18n.t("common.ok", "OK"));
    }

    /**
     * 成功返回
     * @param data  数据
     * @param message  提示信息
     * @param <T>   泛型
     * @return      返回成功结果
     */
    public static <T> BaseRet<T> success(T data ,String message) {
        return new BaseRet<>(RetEnum.OK.getCode(), message, data);
    }
}
