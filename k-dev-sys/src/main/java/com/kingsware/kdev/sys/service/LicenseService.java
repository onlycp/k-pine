package com.kingsware.kdev.sys.service;

import com.kingsware.kdev.core.cache.license.License;
import com.kingsware.kdev.sys.argv.SysLicenseActive;
import com.kingsware.kdev.sys.ret.LicenseRet;

/**
 * License业务
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/5/5 3:56 PM
 */
public interface LicenseService {

    /**
     * 获取license
     * @return  返回license信息
     */
    LicenseRet getLicense();

    /**
     * 激活
     * @param licenseActive license激活信息
     */
    LicenseRet active(SysLicenseActive licenseActive);
}
