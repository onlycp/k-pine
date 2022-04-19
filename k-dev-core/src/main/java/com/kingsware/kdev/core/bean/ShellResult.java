package com.kingsware.kdev.core.bean;

/**
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/4/7 4:35 下午
 */
public class ShellResult {
    /** 是否成功 **/
    private boolean success;
    /** 提示信息 **/
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static ShellResult success(String message) {
        ShellResult shellResult = new ShellResult();
        shellResult.setMessage(message);
        shellResult.setSuccess(true);
        return shellResult;
    }

    public static ShellResult fail(String message) {
        ShellResult shellResult = new ShellResult();
        shellResult.setMessage(message);
        shellResult.setSuccess(false);
        return shellResult;
    }

}
