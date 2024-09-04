package com.kingsware.kdev.sys.service;

import com.kingsware.kdev.sys.model.DevModelSql;

/**
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2024/9/3 20:06
 */
public interface DevModelSqlService {

    /**
     * 执行指定应用的某个操作
     *
     * @param appId 应用的唯一标识符
     */
    void execute(String appId);

    /**
     * 执行指定应用的特定来源的操作
     *
     * @param appId 应用的唯一标识符
     * @param sourceName 操作的来源名称
     */
    void execute(String appId, String sourceName);

    /**
     * 执行给定的开发模型SQL语句
     *
     * @param devModelSql 要执行的SQL语句，可能包含复杂的查询或操作
     */
    void execute(DevModelSql devModelSql);
}
