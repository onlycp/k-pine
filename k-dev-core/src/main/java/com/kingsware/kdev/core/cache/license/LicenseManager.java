package com.kingsware.kdev.core.cache.license;

import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.mode.AppModeProperties;
import com.kingsware.kdev.core.util.*;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.*;
import java.util.stream.Collectors;

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
    /** 万能mac **/
    private final String allPurposeMac = "kingsware@2022";

    /**
     * license 信息
     **/
    private License license;

    public static LicenseManager getInstance() {
        if (instance == null) {
            synchronized (LicenseManager.class) {
                if (instance == null) {
                    instance = new LicenseManager();
                }
            }
        }
        return instance;
    }

    private LicenseManager() {
    }

    /**
     * 获取license
     *
     * @return 返回license
     */
    public License getLicense() {

        try {
            this.license = this.loadLicense();
        }
        catch (Exception e) {
            this.license = null;
        }
        return this.license;

    }

    public License loadLicense() {
        // license目录
        String licenseDir = SpringContext.getProperties("license.dir", ".");
        // 读取文件
        String licenseFilePath = licenseDir + "/pine.license";
        File licenseFile = new File(licenseFilePath);
        // 如果license文件不存在
        if (!licenseFile.exists()) {
            return null;
        }
        String text = FileUtils.readFile(licenseFile).trim();
        // license无效
        if (StringUtils.isEmpty(text)) {
            return null;
        }
        try {
            return parseLicense(text);
        }
        catch (Exception e) {
            log.warn("license无法解析:{}", text);
        }
        return null;


    }

    /**
     * 解析license
     * @param text  文本
     * @return
     */
    public License parseLicense(String text) {
        try {
            // license公钥
            String licensePubKey = SpringContext.getProperties("license.pub-key", "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDNMCUzO6yJeyOdXoknkheWRtzXxiICR1o1fNm67a09m1nhhTkFc3Hj6rVrDkkKISXCJlm3N2wa1dJTg3WBNx/+IZx/VXDFYPMLXdrW65ZLJfUj+tE+SVMS9HbSkOu2teDQnStyqcWo5+GSx0ctkH56k8oWhZNlt/XasvCdyK05IQIDAQAB");
            String originText = RSAUtils.publicKeyDecrypt(licensePubKey, text);
//            log.info("license:" + originText);
            String[] arr = originText.split("\\|");
            License myLicense = new License();
            myLicense = new License();
            myLicense.setCustomer(arr[0]);
            myLicense.setMac(arr[1].trim());
            myLicense.setAppCode(arr[2].trim());
            myLicense.setValidDate(arr[3]);
            myLicense.setInvalidDate(arr[4]);
            return myLicense;
        } catch (Exception e) {
            throw BusinessException.serviceThrow("license无效");
        }
    }



    /**
     * 获取所有的mac地址
     *
     * @return
     */
    private boolean validateMac(String macAddress) {
        // 如果是万能mac。直接通过
        if (allPurposeMac.equalsIgnoreCase(macAddress)) {
            return true;
        }
        try {
            java.util.Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
            StringBuilder sb = new StringBuilder();
            while (en.hasMoreElements()) {
                NetworkInterface networkInterface = en.nextElement();
                List<InterfaceAddress> addresses = networkInterface.getInterfaceAddresses();
                for (InterfaceAddress address : addresses) {
                    InetAddress ip = address.getAddress();
                    NetworkInterface network = NetworkInterface.getByInetAddress(ip);
                    if (network == null) {
                        continue;
                    }
                    byte[] mac = network.getHardwareAddress();
                    String name = network.getName();

                    if (mac == null) {
                        continue;
                    }
                    sb.delete(0, sb.length());
                    for (int i = 0; i < mac.length; i++) {
                        sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                    }
                    if (macAddress.equalsIgnoreCase(sb.toString().trim())) {
                        return true;
                    }
                }
            }

        } catch (Exception ignored) {
        }
        return false;
    }


    /**
     * 获取mac地址
     * @return
     */
    public String getMac() {
        try {
            java.util.Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
            StringBuilder sb = new StringBuilder();
            while (en.hasMoreElements()) {
                NetworkInterface networkInterface = en.nextElement();
                List<InterfaceAddress> addresses = networkInterface.getInterfaceAddresses();
                for (InterfaceAddress address : addresses) {
                    InetAddress ip = address.getAddress();
                    NetworkInterface network = NetworkInterface.getByInetAddress(ip);
                    if (network == null) {
                        continue;
                    }
                    byte[] mac = network.getHardwareAddress();

                    if (mac == null) {
                        continue;
                    }
                    sb.delete(0, sb.length());
                    for (int i = 0; i < mac.length; i++) {
                        sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                    }
                    return sb.toString().trim();

                }
            }

        } catch (Exception ignored) {
        }
        return null;
    }

    public int getStatus(License lic) {

        AppModeProperties appModeProperties = SpringContext.getBean(AppModeProperties.class);
        // 如果是开发模式
        if (appModeProperties.getDev().equals(Boolean.TRUE)) {
            return -2;
        }
        // license不存在或无效
        if (lic == null) {
            return 0;
        }
        // 获取日期
        Date now = new Date();
        // 生效日期
        Date validDate = DateUtils.toDate(lic.getValidDate(), "yyyy-MM-dd");
        // 失效日期
        Date inValidDate = DateUtils.toDate(lic.getInvalidDate(), "yyyy-MM-dd");
//        log.info("license: {}", lic);
        if (!validateMac(lic.getMac())) {
            return -1;
        }
        else if (now.before(validDate)) {
            return 1;
        }

        else if (now.after(inValidDate)) {
            return 3;
        }
        else {
            return 2;
        }
    }

    /**
     * 获取license状态
     *
     * @return 获取状态
     */
    public int getStatus() {
        getLicense();
        return this.getStatus(this.license);
    }
}
