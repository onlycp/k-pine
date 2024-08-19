package com.kingsware.kdev.core.cron;

/**
 * 定时任务接口
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/11 11:34 上午
 */
public interface KTask {

    /** 执行任务 **/
    void execute() throws Exception;
    /** 表达式 **/
    String cron();
    /** 名称 **/
    String name();
    /** 描述, 默认值为空字符串 **/
    default String note() { return "";}
}
