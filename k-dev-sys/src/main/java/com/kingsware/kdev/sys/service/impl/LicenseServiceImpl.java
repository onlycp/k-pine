package com.kingsware.kdev.sys.service.impl;

import com.kingsware.kdev.core.cache.license.LicenseManager;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.exception.LicenseException;
import com.kingsware.kdev.core.i18n.I18n;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.kdb.DataSourceInfo;
import com.kingsware.kdev.core.orm.kdb.DataSourceQueryArgv;
import com.kingsware.kdev.core.util.FileUtils;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.core.util.RSAUtils;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.core.cache.license.License;
import com.kingsware.kdev.sys.argv.SysLicenseActive;
import com.kingsware.kdev.sys.ret.LicenseRet;
import com.kingsware.kdev.sys.service.LicenseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.nio.charset.StandardCharsets;
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
        if (ret.getStatus() != 2 && ret.getStatus() != 2) {
            ret.setMac(LicenseManager.getInstance().getMac());
        }
        return ret;
    }

    @Override
    public LicenseRet active(SysLicenseActive licenseActive) {
        License license = LicenseManager.getInstance().parseLicense(licenseActive.getLicense());
        int status = LicenseManager.getInstance().getStatus(license);
        if ( status == -1) {
            throw BusinessException.serviceThrow(I18n.t("license.error.-1", "非法授权"));
        }

        else if (status == 3) {
            throw BusinessException.serviceThrow(I18n.t("license.error.3", "许可证已过期"));
        }
        // 保存license
        // license目录
        String licenseDir = SpringContext.getProperties("license.dir", ".");
        // 读取文件
        String licenseFilePath = licenseDir + "/pine.license";
        // 保存文件License
        FileUtils.writeToFile(new File(licenseFilePath), licenseActive.getLicense().getBytes(StandardCharsets.UTF_8));
        // 返回license
        return this.getLicense();
    }
}
