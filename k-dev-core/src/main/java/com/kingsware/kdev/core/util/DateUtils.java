package com.kingsware.kdev.core.util;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/22 10:50 上午
 */
@Slf4j
public class DateUtils {
    // 时间日期
    public static final String DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    // 时间日期
    public static final String TIME = "HH:mm:ss";
    // 时间日期
    public static final String DATE_TIME_MS = "yyyy-MM-dd HH:mm:ss SSS";
    // 日期
    public static final String DATE = "yyyy-MM-dd";

    private static final String templates[] = {"yyyy-MM-dd", "yyyy-MM-d", "yyyy-M-dd", "yyyy-M-d", "yyyyMMdd", "yyyyMMd", "yyyyMdd", "yyyyMd", "yyyy年MM月dd日", "yyyy年MM月d日", "yyyy年M月dd日", "yyyy年M月d日", "yyyy/MM/dd", "yyyy/MM/d", "yyyy/M/dd", "yyyy/M/d", "yyyy.MM.dd", "yyyy.MM.d", "yyyy.M.dd", "yyyy.M.d"};

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

    /**
     * 格式化日期
     * @param date      日期
     * @param format    格式
     * @return
     */
    public static String formatDate(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 将字符串转为日期
     * @param dateString    时间字符串
     * @param format        格式
     * @return              日期时间
     */
    public static Date toDate(String dateString, String format) {
        if (StringUtils.isEmpty(dateString)) {
            return null;
        }
        SimpleDateFormat sdf =   new SimpleDateFormat(format);
        try {
            return sdf.parse(dateString);
        } catch (ParseException e) {
            log.warn("字符串转日期失败，原始字符串：{}, 格式:{}", dateString, format);
            return null;
        }
    }

    /**
     * 在不知道日期格式的情况下转日期
     * @param dateString
     * @return
     */
    public static Date toDateUnknowFormat(String dateString) {
        if (StringUtils.isEmpty(dateString)) {
            return null;
        }

        Date parse = null;
        boolean isFlag = false;
        for (int i = 0, size = templates.length; i < size; i++) {
            String templatePattern = templates[i];
            try {
                SimpleDateFormat format = new SimpleDateFormat(templatePattern);
                parse = format.parse(dateString);
            } catch (Exception e) {
                continue;
            }
            isFlag = true;
            break;
        }
        return parse;
    }
}
