package com.kingsware.kdev.sys.argv;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class SysUserLoginArgv {

    private String token;

    private String username;

    private String password;

    private String ip;

}
