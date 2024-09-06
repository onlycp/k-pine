package com.kingsware.kdev.core.orm.kdb;

import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据源信息.
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/14 1:59 下午
 */
@Data
@FieldNameConstants
public class DataSourceInfo {
    /** 数据源名称 **/
    private String sourceName;
    /** 驱动类 **/
    private String driverClass;
    /** url **/
    private String jdbcUrl;
    /** 数据源所属应用 **/
    private String appId;
    /** 用户名 **/
    private String userName;
    /** 密码 **/
    private String password;
    /** 其他配置参数 **/
    private String json;
}
