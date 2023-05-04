package com.kingsware.kdev.uniops.init;

import com.kingsware.kdev.core.base.SystemInitialize;
import com.kingsware.kdev.uniops.service.UniOpsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.regex.Pattern;


/**
 * @author chenp
 * @date 2023/1/12
 */
@Component
@Slf4j
public class UniopsInstallInitialize implements SystemInitialize {

    @Resource
    private UniOpsService uniOpsService;

    @Value("${app.init-psi-path:.}")
    private String initPsiPath;

    @Override
    public void execute()  {
        String regex = ".*\\.pine";
        // 扫描指定目录的psi文件
        File[] files = new File(initPsiPath).listFiles((dir, name) -> Pattern.compile(regex).matcher(name).matches());
        if (files == null) {
            return;
        }
        // 遍历安装
        for (File file: files) {
            try {
                log.info("开始安装应用: {}", file.getName());
                long t1 = System.currentTimeMillis();
                List<String> lines  = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
                StringBuilder stringBuffer = new StringBuilder();
                for (String line: lines) {
                    stringBuffer.append(line);
                }
                uniOpsService.publishMenu(stringBuffer.toString());
            }
            catch (Exception e) {
                log.error("uniops菜单安装失败: ", e);
            }

        }
    }

    @Override
    public int sort() {
        return 3;
    }
}
