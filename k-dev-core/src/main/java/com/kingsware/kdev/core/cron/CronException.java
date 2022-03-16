package com.kingsware.kdev.core.cron;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Cron 定时任务异常
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/3/16 4:18 下午
 */
public class CronException extends RuntimeException{
    /** 1: class不存在, 2: 流程不存在, 3:其他异常 **/
    private int errorCode;

    public CronException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
