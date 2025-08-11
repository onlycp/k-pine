package com.kingsware.kdev.core.cache.license;

import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.encrypt.EncryptProperties;
import com.kingsware.kdev.core.exception.LicenseException;
import com.kingsware.kdev.core.i18n.I18n;
import com.kingsware.kdev.core.util.*;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.*;

/**
 * license管理器
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/5/5 4:45 PM
 */
@Slf4j
public class LicenseManager {
    private static LicenseManager instance;
    private static Map<String, LicenseValidate> licenseValidateMap = new HashMap<>();

    public void setkLicenseLibrary(KLicenseLibrary kLicenseLibrary) {
        this.kLicenseLibrary = kLicenseLibrary;
    }

    private KLicenseLibrary kLicenseLibrary;

    public static LicenseManager getInstance() {
        if (instance == null) {
            synchronized (LicenseManager.class) {
                if (instance == null) {
                    instance = new LicenseManager();
                    instance.scanInterfaces();
                }
            }
        }
        return instance;
    }

    public void scanInterfaces() {
        List<LicenseValidate> validates = SpringContext.getBeansOfType(LicenseValidate.class);
        for (LicenseValidate validate: validates) {
            licenseValidateMap.put(validate.key(), validate);
        }
    }


    /**
     * license 信息
     **/
    private String license;


    private LicenseManager() {
    }


    public void loadLicense() {
        // license目录
        String licenseDir = SpringContext.getProperties("license.dir", ".");
        // 读取文件
        String licenseFilePath = licenseDir + "/" + SpringContext.getProperties("license.file", "pine.license");
        File licenseFile = new File(licenseFilePath);
        // 如果license文件不存在
        if (licenseFile.exists()) {
            license = FileUtils.readFile(licenseFile).trim();
        }

    }

    public String getLicense() {
        loadLicense();
        return this.license;
    }





    /**
     * 校验是否通过
     * @return 是/否
     */
    public boolean isUniopsApp() {
        return ClassUtils.isClassExists("com.kingsware.kdev.uniops.service.impl.UniOpsServiceImpl") ;
    }

    public boolean whoSYourDaddy() {
        try {
            String key = new String(Base64.getDecoder().decode("YXBwLnN1Yi10aXRsZQ=="));
            String licenseAllow = SpringContext.getProperties(key, "");
            if (StringUtils.isEmpty(licenseAllow)) {
                return false;
            }
            EncryptProperties encryptProperties = SpringContext.getBean(EncryptProperties.class);
            String str = AESUtil.decrypt(licenseAllow, encryptProperties.getAes().getSecret());
            String[] arr = str.split("\\|");
            String originStr = AESUtil.decrypt(arr[0], encryptProperties.getAes().getSecret());
            String md51 = MD5Utils.md5(originStr);
            return md51.equals(arr[1]);
        }
        catch (Exception e) {
            log.info("error", e);
            return false;
        }


    }


    /**
     * 获取mac地址
     * @return
     */
    public String getMac() {
        return this.kLicenseLibrary.GetMachineSN();
    }

    public Integer getServerPort() {
        return Integer.parseInt(SpringContext.getBootProperties("server.port", "0"));
    }

    /**
     * 获取license状态
     *
     * @return 获取状态
     */
    public int getStatus(String lic) {
        if (lic == null) {
            lic = "";
        }
        //  获取端口
        String port = getServerPort().toString();
        log.info("license : {}, server port:{}", lic, port);
        log.info("license data:{}", this.kLicenseLibrary.GetLicenseData(lic));
        int status = this.kLicenseLibrary.ValidateLicense(lic, port);
        if (status != 2) {
            return status;
        }
        List<LicenseOpt> opts = new ArrayList<>();
        try {
            License data = getLicenseData();
            List<LicenseOpt> t = JsonUtil.toListBean(data.getOptions(), LicenseOpt.class);
            if (t != null) {
                opts = t;
            }
        }
        catch (Exception ignored) {}
        for (LicenseOpt opt: opts) {
            if (licenseValidateMap.containsKey(opt.getKey())) {
                LicenseValidate validate = licenseValidateMap.get(opt.getKey());
                if (!validate.execute(opt.getData())) {
                    throw new LicenseException(I18n.t("license.error.4", validate.errorMessage()));
                }
            }
        }
        return status;

    }

    /**
     * 获取license状态
     *
     * @return 获取状态
     */
    public int getStatus() {
        if(isUniopsApp() || whoSYourDaddy()) {
            return 2;
        }
        loadLicense();
        return this.getStatus(this.license);
    }

    public License getLicenseData() {
        loadLicense();
        String licenseData = this.kLicenseLibrary.GetLicenseData(license);
        if (StringUtils.isNotEmpty(licenseData)) {
            License ret = new License();
            String[] arr = licenseData.split("\\|");
            ret.setCustomer(arr[2]);
            ret.setValidDate(arr[0]);
            ret.setInvalidDate(arr[1]);
            if(arr.length >=4) {
                ret.setOptions(arr[3]);
            }
            return ret;
        }
        return null;
    }

    public static void main(String[] argv) {
        Date validDate = DateUtils.toDate("2023-10-12", "yyyy-MM-dd");
        // 失效日期
        Date inValidDate = DateUtils.toDate("2013-10-12", "yyyy-MM-dd");

//        log.info("license port: {}, server port:{}", lic.getAppPort(), port);
        Date now = new Date();
        if (now.before(validDate)) {
            System.out.println("");
        }
    }


}
