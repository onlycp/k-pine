package com.kingsware.kdev.core.excel.format;

import com.kingsware.kdev.core.util.DateUtils;
import com.kingsware.kdev.core.util.StringUtils;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 格式化日期
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/6 8:56 上午
 */
public class RegionDateFormat implements RegionFormat{

    @Override
    public Object format(Object value) {
        // 值为空，就返回空值
        if (value == null) {
            return null;
        }
        // 如果是TimeStamp
        if (value instanceof Timestamp) {
            return DateUtils.formatDate((new Date(((Timestamp) value).getTime())),DateUtils.DATE);
        }
        // 如果是Date
        if (value instanceof Date) {
            return DateUtils.formatDate((Date)value, DateUtils.DATE);
        }
        // 如果是long
        if (value instanceof Long) {
            return DateUtils.formatDate(new Date((Long)value), DateUtils.DATE);
        }
        // 如果是字符串，时间戳
        if (value instanceof String) {
            if (StringUtils.isNotEmpty(value.toString())) {
                return DateUtils.formatDate(new Date(Long.parseLong(value.toString())), DateUtils.DATE);
            }
        }
        return null;
    }
}
