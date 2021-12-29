package com.kingsware.kdev.core.orm;

import com.kingsware.kdev.core.orm.expression.Op;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * SQL对象
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/21 5:41 下午
 */
@Data
public class SqlWrapper {
    /** sql语句 **/
    private String sql;
    /** 参数 **/
    private List<Object> params = new ArrayList<>();

    public SqlWrapper() {
    }

    public SqlWrapper(String sql) {
        this.sql = sql;
    }

    /**
     * 增加查询条件
     * @param columnName    列名
     * @param op            操作符
     * @param objects       参数
     */
    public void addCondition(String columnName, Op op, Object... objects) {
        sql += " and ";
        sql += (columnName + " ");
        sql += op.bind();
        params.addAll(Arrays.asList(objects));
    }

    /**
     * 增加查询条件
     * @param columnName    列名
     * @param bind            操作符
     * @param objects       参数
     */
    public void addCondition(String columnName, String bind, Object... objects) {
        sql += " and ";
        sql += (columnName + " ");
        sql += bind;
        params.addAll(Arrays.asList(objects));
    }

    /**
     * 增加查询条件
     * @param condition 条件语句
     * @param objects   参数
     */
    public void addCondition(String condition, Object... objects) {
        sql += " and ";
        sql += condition;
        params.addAll(Arrays.asList(objects));
    }
}
