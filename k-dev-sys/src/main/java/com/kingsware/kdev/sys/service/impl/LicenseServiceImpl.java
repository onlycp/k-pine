package com.kingsware.kdev.sys.service.impl;

import com.kingsware.kdev.core.cache.license.LicenseManager;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.kdb.DataSourceInfo;
import com.kingsware.kdev.core.orm.kdb.DataSourceQueryArgv;
import com.kingsware.kdev.core.util.FileUtils;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.core.util.RSAUtils;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.core.cache.license.License;
import com.kingsware.kdev.sys.ret.LicenseRet;
import com.kingsware.kdev.sys.service.LicenseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Optional;

/**
 * License业务实现类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/5/5 4:03 PM
 */
@Service
@Slf4j
public class LicenseServiceImpl implements LicenseService {


    @Override
    public LicenseRet getLicense() {

        LicenseRet ret = new LicenseRet();
        // 读取文件
        License license = LicenseManager.getInstance().getLicense();
        ret.setStatus(LicenseManager.getInstance().getStatus());
        if (license != null) {
            ret.setCustomer(license.getCustomer());
            ret.setValidDate(license.getValidDate());
            ret.setInvalidDate(license.getInvalidDate());
        }
        return ret;
    }
}
