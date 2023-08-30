package com.kingsware.kdev.core.cache.license;

import com.kingsware.kdev.core.base.SystemInitialize;
import com.kingsware.kdev.core.context.SpringContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;

/**
 * License加载
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2023/8/29 09:53
 */
@Component
@Slf4j
public class LicenseDllInitialize implements SystemInitialize {

    @Override
    public void execute() throws FileNotFoundException {

        String osName = System.getProperty("os.name").toLowerCase();
        String osArch = System.getProperty("os.arch").toLowerCase();
        String libName = "";
        String libFileName = "";
        log.info("osName:{}, osArch:{}", osName, osArch);
        if (osName.toLowerCase().contains("mac")) {
            if (osArch.contains("86") || osArch.contains("amd")) {
                libName = "k-license";
                libFileName = "libk-license.dylib";
            }
            else {
                libName =  "k-license_arm";
                libFileName = "libk-license_arm.dylib";
            }
        }
        else if (osName.toLowerCase().contains("linux")) {
            if (osArch.contains("86") || osArch.contains("amd") ) {
                libName = "k-license";
                libFileName = "libk-license.so";
            }
            else {
                libName =  "k-license_arm";
                libFileName = "libk-license_arm.so";
            }
        }
        else if (osName.toLowerCase().contains("window")) {
            if (osArch.contains("86") || osArch.contains("amd")) {
                libName = "k-license_x86";
                libFileName = "libk-license_x86.dll";
            }
            else {
                libName =  "k-license_arm";
                libFileName = "libk-license_arm.dll";
            }
        }
        String path = ResourceUtils.CLASSPATH_URL_PREFIX + "lib/" + libFileName;
        try {
            Resource res = SpringContext.getResource(path);
            new File("dll").mkdirs();
            Files.write(new File("dll/" + libFileName).toPath(), StreamUtils.copyToByteArray(res.getInputStream()));

            // 加载动态库
            System.load(new File("dll/" + libFileName).getAbsolutePath());
            KLicenseLibrary kLicenseLibrary = com.sun.jna.Native.load(libName, KLicenseLibrary.class);
            LicenseManager.getInstance().setkLicenseLibrary(kLicenseLibrary);
        }
        catch (Exception e) {
            log.error("当前找不到动态库", e);
        }


    }

    @Override
    public int sort() {
        return 0;
    }


}
