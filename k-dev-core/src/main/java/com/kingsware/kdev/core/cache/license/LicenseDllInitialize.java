package com.kingsware.kdev.core.cache.license;

import com.kingsware.kdev.core.base.SystemInitialize;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.util.FileUtils;
import com.kingsware.kdev.core.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.FileInputStream;
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
        String libFileName = "";
        String targetFileName = "";
        String libName = "k-license";
        log.info("osName:{}, osArch:{}", osName, osArch);
        if (osName.toLowerCase().contains("mac")) {
            targetFileName = "libk-license.dylib";
            if (osArch.contains("86") || osArch.contains("amd")) {
                libFileName = "libk-license.dylib";
            } else {

                libFileName = "libk-license-arm64.dylib";
            }
        } else if (osName.toLowerCase().contains("linux")) {
            targetFileName = "libk-license.so";
            if (osArch.contains("86") || osArch.contains("amd")) {
                libFileName = "libk-license.so";
            } else {
                libFileName = "libk-license-arm64.so";
            }
        } else if (osName.toLowerCase().contains("window")) {
            targetFileName = "libk-license.dll";
            if (osArch.contains("86") || osArch.contains("amd")) {
                libFileName = "libk-license.dll";
            } else {
                libFileName = "libk-license-arm64.dll";
            }
        }
        String path = ResourceUtils.CLASSPATH_URL_PREFIX + "lib/" + libFileName;
        try {
            Resource res = SpringContext.getResource(path);
            Resource[] resources = SpringContext.getResources(ResourceUtils.CLASSPATH_URL_PREFIX + "lib/*");
            if (osName.contains("linux")) {
                try {
                    // 判断文件是否存在以及md5是否一致
                    if (new File("./" + targetFileName).exists()) {
                        String md5 = FileUtils.getMD5(Files.newInputStream(new File("./" + targetFileName).toPath()));
                        String md51 = FileUtils.getMD5(res.getInputStream());
                        if (StringUtils.isNotEmpty(md5) && md5.equals(md51)) {
                            log.info("动态库已存在，无需拷贝");
                        }
                        else {
                            Files.write(new File("./" + targetFileName).toPath(), StreamUtils.copyToByteArray(res.getInputStream()));
                        }
                    }
                    System.load(new File("./" + targetFileName).getAbsolutePath());

                } catch (Exception e) {
                    log.warn("动态库拷贝失败，请手动将动态库拷贝到/usr/lib64/目录");
                }

            } else if (osName.contains("mac")) {
                new File("dll").mkdirs();
                Files.write(new File("dll/" + targetFileName).toPath(), StreamUtils.copyToByteArray(res.getInputStream()));
                System.load(new File("dll/" + targetFileName).getAbsolutePath());

            } else if (osName.contains("window")) {
                new File("dll").mkdirs();
                Files.write(new File("dll/" + targetFileName).toPath(), StreamUtils.copyToByteArray(res.getInputStream()));
                System.load(new File("dll/" + targetFileName).getAbsolutePath());
            }

            // 加载动态库
            KLicenseLibrary kLicenseLibrary = com.sun.jna.Native.load(libName, KLicenseLibrary.class);
            LicenseManager.getInstance().setkLicenseLibrary(kLicenseLibrary);
        } catch (Exception e) {
            log.error("当前找不到动态库", e);
        }


    }

    @Override
    public int sort() {
        return 0;
    }


}
