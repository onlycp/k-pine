package com.kingsware.kdev.core.util;

import com.kingsware.kdev.core.bean.SqlSegment;
import com.kingsware.kdev.core.kflow.FlowUtils;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.exception.OrmDbException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * SQL工具类，用于处理和解析SQL语句
 */
@Slf4j
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
        int startLine = -1;
        for (int i=0; i < lines.size(); i++) {
            if (startLine == -1) {
                startLine = i+1;
            }
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
                    segment.setLine(startLine);
                    startLine = -1;
                    sqlSegments.add(segment);
                    // 清空sql
                    sql.setLength(0);
                }
            }
        }
        return sqlSegments;
    }

    /**
     * 执行SQL语句，并处理重复项异常
     *
     * 本函数旨在执行给定的SQL语句如果在执行过程中出现表示重复项的异常（如duplicate或already exists），
     * 则函数将忽略这些异常并返回而不进行任何操作对于其他类型的异常，函数将记录错误信息并重新抛出异常
     *
     * @param sql 待执行的SQL语句
     * @throws OrmDbException 如果SQL执行失败且不为重复项异常，则抛出此异常
     */
    public static void executeSql(String sourceName, String sql) {

        try {
            // 使用FlowUtils构建CData SQL语句并执行
            DB.byName(sourceName).executeUpdateSql(FlowUtils.buildCDATASql(sql));
        } catch (Exception e) {
            // 检查运行时异常是否表示重复项或已存在的情况
            if (isDuplicateException(e)) {
                // 如果是重复项异常，则忽略并返回
                return;
            }
            // 记录并抛出运行时异常
            log.error("sql执行失败: {}, error: {}", sql, e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 判断异常是否由重复项引起
     *
     * @param e 异常对象
     * @return 如果异常信息包含重复项相关关键词，则返回true；否则返回false
     */
    private static boolean isDuplicateException(Exception e) {
        String message = e.getMessage().toLowerCase();
        String trace = ExceptionUtils.getStackTrace(e).toLowerCase();
        String exceptionTrace = ((OrmDbException) e).getExceptionTrace();
        return message.contains("duplicate") || trace.contains("duplicate") ||  exceptionTrace.contains("duplicate")
                || message.contains("already exists") || trace.contains("already exists") || exceptionTrace.contains("already exists")
                || message.contains("重复") || trace.contains("重复") ||  exceptionTrace.contains("重复")
                || message.contains("存在") || trace.contains("存在") ||  exceptionTrace.contains("存在");
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

