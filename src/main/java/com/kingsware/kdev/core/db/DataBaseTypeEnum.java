package com.kingsware.kdev.core.db;

/**
 * 数据库类型
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/21 2:10 下午
 */
public enum DataBaseTypeEnum {
    // kdb
    KDB("KDB"),
    // mysql
    MYSQL("MYSQL");

    /** 数据库类型名称 **/
    private String value;

    /**
     * 默认构造函数
     * @param value 数据库名称
     */
    DataBaseTypeEnum(String value) {
        this.value = value;
    }

    /**
     * 获取数据库类型名称
     * @return  数据库类型名称
     */
    public String getValue() {
        return value;
    }
}
