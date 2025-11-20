package com.kingsware.kdev.sys.argv;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class SysUserResetPasswordArgv {

    private String userId;

    private String password;

    private String rePassword;

}
