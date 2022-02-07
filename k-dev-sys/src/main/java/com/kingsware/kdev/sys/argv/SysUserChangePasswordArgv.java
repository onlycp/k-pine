package com.kingsware.kdev.sys.argv;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class SysUserChangePasswordArgv {

    private String oldPassword;

    private String newPassword;

}
