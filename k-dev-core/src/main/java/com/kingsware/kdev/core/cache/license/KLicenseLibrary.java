package com.kingsware.kdev.core.cache.license;

import com.kingsware.kdev.core.auth.KAuthFilter;

/**
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2023/8/29 10:10
 */
public interface KLicenseLibrary extends com.sun.jna.Library {



    /**
     * 权限机器码
     * @return 返回机器码
     */
    String GetMachineSN();

    /**
     * 验证license
     * @param license license
     * @param port  端口
     * @return
     */
    int ValidateLicense(String license, String port);

    /**
     * 返回license有效期,
     * @return 生效时间|失效时间|选项信息
     */
    String GetLicenseData(String license);
}
