package com.kingsware.kdev.core.orm.kdb;

import com.kingsware.kdev.core.orm.DBConnectConfig;

/**
 * KDB配置文件
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/21 2:51 下午
 */
public class KDBConnectConfig extends DBConnectConfig {
    /** 服务器地址 **/
    private String server;
    /** 数据库名称 **/
    private String dbName;

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
}
