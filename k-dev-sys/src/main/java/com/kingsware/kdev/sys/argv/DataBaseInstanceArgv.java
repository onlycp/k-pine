package com.kingsware.kdev.sys.argv;

import lombok.Data;

@Data
public class DataBaseInstanceArgv {

    /** url **/
    private String jdbcUrl;
    /** 用户名 **/
    private String userName;
    /** 密码 **/
    private String password;
}
