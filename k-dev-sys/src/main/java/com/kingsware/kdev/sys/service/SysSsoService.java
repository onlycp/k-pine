package com.kingsware.kdev.sys.service;

import com.kingsware.kdev.sys.argv.SysAutoLoginArgv;
import com.kingsware.kdev.sys.argv.SysSsoArgv;
import com.kingsware.kdev.sys.ret.SysUserLoginRet;

public interface SysSsoService {

    /**
     * 登录
     * @return
     */
    SysUserLoginRet doLogin(SysSsoArgv ssoArgv);



}
