package com.kingsware.kdev.core.cache.license;

import com.kingsware.kdev.core.base.SystemInitialize;
import com.kingsware.kdev.core.bean.ShellResult;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.util.FileUtils;
import com.kingsware.kdev.core.util.ShellUtils;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.core.util.ThreadUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
                            log.info("开始拷贝动态库1");
                            Files.write(new File("./" + targetFileName).toPath(), StreamUtils.copyToByteArray(res.getInputStream()));
                        }
                    }
                    else {
                        log.info("开始拷贝动态库2");
                        Files.write(new File("./" + targetFileName).toPath(), StreamUtils.copyToByteArray(res.getInputStream()));
                    }
                    Files.copy(Paths.get(targetFileName), Paths.get("/usr/lib64/" + new File(targetFileName).getName()));
                    System.load(new File(targetFileName).getAbsolutePath());


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
                // 扫描dll目录，加载所有的dll
                for (File file : Objects.requireNonNull(new File("dll").listFiles())) {
                    if (file.getName().endsWith(".dll")) {
                        System.load(file.getAbsolutePath());
                    }
                }
//                System.load(new File("dll/" + targetFileName).getAbsolutePath());
            }

            // 加载动态库
            KLicenseLibrary kLicenseLibrary = com.sun.jna.Native.load(libName, KLicenseLibrary.class);
            LicenseManager.getInstance().setkLicenseLibrary(kLicenseLibrary);

            if (osName.contains("linux")) {
                // 如果当前系统没有被授权，并且机器码在黑名单中，那么就调用shell命令重新生成mache-id
                List<String> blackMacheList = getBlackMacheList();
                if (LicenseManager.getInstance().getStatus() != 2 && blackMacheList.contains(kLicenseLibrary.GetMachineSN())) {
                    log.info("开始重新生成机器id");
                    String oldMacheId = FileUtils.readFileText(new File("/etc/machine-id"));
                    Files.deleteIfExists(new File("/etc/machine-id").toPath());
                    Files.deleteIfExists(new File("/var/lib/dbus/machine-id").toPath());
                    ShellUtils.execute(new File("/usr/bin").getAbsolutePath(), "./systemd-machine-id-setup", true);
                    ThreadUtils.sleep(1000);
                    String newMacheId = FileUtils.readFileText(new File("/etc/machine-id"));
                    log.info("结束重新生成机器id，新id:{}，旧id:{}", newMacheId, oldMacheId);
                }

            }
        } catch (Exception e) {
            log.error("当前找不到动态库", e);
        }


    }

    /**
     * 获取黑名单列表
     * @return 黑名单列表
     */
    private List<String> getBlackMacheList() {
        List<String> innerBlackList = new ArrayList<>();
        innerBlackList.add("a99b9078b984187b69096ba1159f141c6b28aee9b364b3c86ede15ba0f8d8adb");
        // 如果文件不存在，则创建一个black-machine.txt文件，并且将innerBlackList写入到文件中，然后返回innerBlackList（按行写入），如果已存在，则读随之后就返回列表
        if (!new File("black-machine.txt").exists()) {
            try {
                Files.write(new File("./black-machine.txt").toPath(), innerBlackList);
            } catch (Exception e) {
                log.error("创建黑名单文件失败", e);
            }
        } else {
            try {
                innerBlackList = Files.readAllLines(new File("./black-machine.txt").toPath());
            } catch (Exception e) {
            }
        }
        return innerBlackList;
    }


    @Override
    public int sort() {
        return 0;
    }


}
