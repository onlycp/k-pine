package com.kingsware.kdev.core.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/22 10:50 上午
 */
public class DateUtils {
    // 时间日期
    public static final String DATA_TIME = "yyyy-MM-dd HH:mm:ss";
    // 日期
    public static final String DATA = "yyyy-MM-dd";

    /**
     * 私有构造
     */
    private DateUtils(){}


    /**
     * 获取当前时间
     * @return  当前时间
     */
    public static String getNow() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
}
