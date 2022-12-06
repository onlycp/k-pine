package com.kingsware.kdev.core.cron;

/**
 * 启动处理类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/4/27 5:13 PM
 */
public interface KRunner {

    /**
     *  马上运行
     */
    void runNow() throws Exception;
}
