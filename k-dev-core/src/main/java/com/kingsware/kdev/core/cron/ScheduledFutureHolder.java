package com.kingsware.kdev.core.cron;

import com.kingsware.kdev.core.model.SysTask;
import lombok.Data;

import java.util.concurrent.ScheduledFuture;

/**
 * 定时任务
 * @author chen peng
 * @version 1.0.0
 * @date 2022/4/27 7:24 PM
 */
@Data
public class ScheduledFutureHolder {

    /** 任务调度器 **/
    private ScheduledFuture<?> scheduledFuture;
    /**  任务 **/
    private String cron;
    /** cron key **/
    private String cronKey;
}
