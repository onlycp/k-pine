package com.kingsware.kdev.core.db;

/**
 * 数据库连接配置基类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/21 2:51 下午
 */
public class DBConnectConfig {
    /** 数据库类型 **/
    private String databaseType;

    public String getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(String databaseType) {
        this.databaseType = databaseType;
    }
}
