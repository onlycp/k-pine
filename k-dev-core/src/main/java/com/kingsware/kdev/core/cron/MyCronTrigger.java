package com.kingsware.kdev.core.cron;

import lombok.Data;
import org.springframework.scheduling.support.CronTrigger;

import java.time.ZoneId;
import java.util.TimeZone;

/**
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/4/27 7:05 PM
 */
public class MyCronTrigger extends CronTrigger {
    /** 任务名称 **/
    private String taskName;

    public MyCronTrigger(String expression) {
        super(expression);
    }

    public MyCronTrigger(String expression, String taskName) {
        super(expression);
        this.taskName = taskName;
    }

    public MyCronTrigger(String expression, TimeZone timeZone, String taskName) {
        super(expression, timeZone);
        this.taskName = taskName;
    }

    public MyCronTrigger(String expression, ZoneId zoneId, String taskName) {
        super(expression, zoneId);
        this.taskName = taskName;
    }

    public String getTaskName() {
        return this.taskName;
    }
}
