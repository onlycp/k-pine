package com.kingsware.kdev.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 线程工具类
 * @author chen peng
 * @version %I%, %G%
 * @date 2021/12/17 5:44 下午
 */
public class ThreadUtils {

    /**
     * 私有构造函数
     */
    private ThreadUtils(){}

    /**
     * 线程休眠
     * @param t 休眠时长，单位为毫秒
     */
    public static void sleep(long t) {
        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {
        }
    }
}
