package com.kingsware.kdev.core.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 异常工具类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/3/25 10:17 上午
 */
public class ExceptionUtils {

    /**
     * 获取异常信息
     * @param t 异常
     * @return  详细信息
     */
    public static String getStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw)) {
            t.printStackTrace(pw);
            return sw.toString();
        }
    }
}
