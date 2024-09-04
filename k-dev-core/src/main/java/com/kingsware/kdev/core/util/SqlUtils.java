package com.kingsware.kdev.core.util;

import com.kingsware.kdev.core.bean.SqlSegment;

import java.util.ArrayList;
import java.util.List;

/**
 * SQL工具类，用于处理和解析SQL语句
 */
public class SqlUtils {

    /**
     * 解析给定的SQL语句列表，将其拆分为单独的SQL段
     *
     * @param lines 包含SQL语句的字符串列表
     * @return 包含解析后SQL段的列表
     */
    public static List<SqlSegment> parseSql(List<String> lines) {
        List<SqlSegment> sqlSegments = new ArrayList<>();
        StringBuffer sql = new StringBuffer();
        for (int i=0; i < lines.size(); i++) {
            String line = lines.get(i);
            // 去掉空格
            String cleanLine = line.trim();
            // 如果不是空才处理
            if (isSql(cleanLine)) {
                sql.append(cleanLine).append("\n");
                if (cleanLine.endsWith(";")) {
                    SqlSegment segment = new SqlSegment();
                    String isql = sql.toString();
                    isql = isql.trim();
                    if (isql.endsWith(";")) {
                        isql = isql.substring(0, sql.length()-1);
                    }
                    segment.setSql(isql);
                    segment.setLine(i+1);
                    sqlSegments.add(segment);
                    // 清空sql
                    sql.setLength(0);
                }
            }
        }
        return sqlSegments;
    }


    /**
     * 判断给定的字符串是否是一个有效的SQL语句起始
     *
     * @param content 待判断的字符串
     * @return 如果是有效的SQL语句起始，返回true；否则返回false
     */
    public static boolean isSql(String content) {

        if (content == null) {
            return false;
        }
        if ("".equals(content.trim())) {
            return false;
        }
        if (content.startsWith("--")) {
            return false;
        }
        if (content.startsWith("/*")) {
            return false;
        }

        return true;
    }
}

