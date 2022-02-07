package com.kingsware.kdev.sys.argv;

import lombok.Data;

/**
 * 新增流程实体
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/5 3:06 下午
 */
@Data
public class SysKdbDataSourceArgv {
    /** 数据源名称 **/
    private String id;
    /** 驱动类 **/
    private String driverClass;
    /** url **/
    private String jdbcUrl;
    /** 用户名 **/
    private String username;
    /** 密码 **/
    private String password;
}
