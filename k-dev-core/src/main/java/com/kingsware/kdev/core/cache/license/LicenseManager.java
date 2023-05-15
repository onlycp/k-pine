package com.kingsware.kdev.core.cache.license;

import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.util.*;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
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

    /**
     * mac地址列表
     */
    private final Set<String> macAddresses = new HashSet<>();

    /**
     * license 信息
     **/
    private License license;

    public static LicenseManager getInstance() {
        if (instance == null) {
            synchronized (LicenseManager.class) {
                if (instance == null) {
                    instance = new LicenseManager();
                    instance.initAndGetsMac();
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
    public License parseLicense() {

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
            myLicense.setCustomer(arr[0]);
            myLicense.setMac(arr[1].trim());
            myLicense.setAppCode(arr[2].trim());
            myLicense.setValidDate(arr[3]);
            myLicense.setInvalidDate(arr[4]);
            if (arr.length == 7) {
                myLicense.setAppPort(arr[6]);
            }
            return myLicense;
        } catch (Exception e) {
            log.error("error", e);
            throw BusinessException.serviceThrow("license无效:"  + ExceptionUtils.getStackTrace(e));
        }
    }



    /**
     * 获取所有的mac地址
     *
     * @return
     */
    private boolean initAndGetsMac() {
        try {
            java.util.Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();

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
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < mac.length; i++) {
                        sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                    }
                    // 加入小写
                    macAddresses.add(sb.toString().toLowerCase());
                    // 加入大写
                    macAddresses.add(sb.toString().toUpperCase());
                }
            }

        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * 校验是否通过
     * @return 是/否
     */
    public boolean isUniopsApp() {
        return ClassUtils.isClassExists("com.kingsware.kdev.uniops.service.impl.UniOpsServiceImpl");
    }


    public List<MacAddress> getMacs() {
        List<MacAddress> macAddresses = new ArrayList<>();
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
                    String macName = network.getName();;
                    String displayName = network.getDisplayName();
                    // 忽略的网卡地址
                    Set<String> strings = new HashSet<>();
                    strings.add("awdl");
                    strings.add("lo");
                    strings.add("docker");
                    boolean isIngore = false;
                    for (String s: strings) {
                        if (macName.startsWith(s) || displayName.startsWith(s)) {
                            isIngore = true;
                            break;
                        }
                    }
                    if (isIngore) {
                        continue;
                    }
                    // 去掉虚拟网卡以及不活动网卡
                    if (network.isVirtual() || !network.isUp()) {
                        continue;
                    }
                    String ipAddress = "";
                    if (ip instanceof Inet4Address && !ip.isLoopbackAddress() && !ip.isLoopbackAddress()) {
                        ipAddress = ip.getHostAddress();
                    }
                    if (StringUtils.isEmpty(ipAddress)) {
                        continue;
                    }
                    if (sb.toString().equalsIgnoreCase("00-50-56-C0-00-01")) {
                        continue;
                    }
                    MacAddress macAddress = new MacAddress();
                    macAddress.setName(macName);
                    macAddress.setMac(sb.toString().trim());
                    macAddress.setIp(ipAddress);
                    macAddresses.add(macAddress);

                }
            }

        } catch (Exception ignored) {
        }
        for (MacAddress macAddress: macAddresses) {
            log.info("网卡信息，网卡名称：{}， Mac地址:{}, IP:{}", macAddress.getName(), macAddress.getMac(), macAddress.getIp());
        }
        return macAddresses;
    }


    /**
     * 获取mac地址
     * @return
     */
    public String getMac() {
        List<MacAddress> macAddresses = getMacs();
        // 排序，避免每次查到的不一致
        macAddresses.sort(Comparator.comparing(MacAddress::getMac));
        // 优先取 192开头的
        Optional<MacAddress> optional = macAddresses.stream().filter(it -> it.getIp().startsWith("192.")).findFirst();
        if (optional.isPresent()) {
            return optional.get().getMac();
        }
        // 然后取10开头的
        Optional<MacAddress> optional10 = macAddresses.stream().filter(it -> it.getIp().startsWith("10.")).findFirst();
        if (optional10.isPresent()) {
            return optional10.get().getMac();
        }

        return macAddresses.get(0).getMac();
    }

    public int getStatus(License lic) {
        if (isUniopsApp()) {
            return 2;
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
        //  获取端口
        String port = SpringContext.getBootProperties("server.port", "0");
//        log.info("license port: {}, server port:{}", lic.getAppPort(), port);
        if (!macAddresses.contains(lic.getMac()) ) {
            log.info("mac不一致");
            return -1;
        }
        else if (StringUtils.isNotEmpty(lic.getAppPort()) && !lic.getAppPort().equals(port))  {
            log.info("端口不一致");
            return  -1;
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
//        return 2;
        parseLicense();
        return this.getStatus(this.license);
    }
}
