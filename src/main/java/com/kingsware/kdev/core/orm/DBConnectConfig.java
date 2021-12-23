package com.kingsware.kdev.core.orm;

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
    /** 通道名 **/
    private String channel;
    /** 所用数据库 **/
    private String innerType;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(String databaseType) {
        this.databaseType = databaseType;
    }

    public String getInnerType() {
        return innerType;
    }

    public void setInnerType(String innerType) {
        this.innerType = innerType;
    }
}
