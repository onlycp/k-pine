package com.kingsware.kdev.core.db;

import java.util.List;

/**
 * SQL对象
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/21 5:41 下午
 */
public class SqlBean {
    /** sql语句 **/
    private String sql;
    /** 参数 **/
    private List<Object> params;

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<Object> getParams() {
        return params;
    }

    public void setParams(List<Object> params) {
        this.params = params;
    }
}
