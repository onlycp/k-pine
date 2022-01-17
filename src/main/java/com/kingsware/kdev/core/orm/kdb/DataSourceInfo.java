package com.kingsware.kdev.core.orm.kdb;

import lombok.Data;

/**
 * 数据源信息.
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/14 1:59 下午
 */
@Data
public class DataSourceInfo {
    /** 数据源名称 **/
    private String sourceName;
    /** 驱动类 **/
    private String driverClass;
    /** url **/
    private String jdbcUrl;
    /** 用户名 **/
    private String userName;
    /** 密码 **/
    private String password;
}
