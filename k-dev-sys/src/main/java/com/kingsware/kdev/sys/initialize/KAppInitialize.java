package com.kingsware.kdev.sys.initialize;

import com.kingsware.kdev.core.base.SystemInitialize;
import com.kingsware.kdev.core.kflow.KFlowContext;
import com.kingsware.kdev.core.kflow.KdbFlowExecutor;
import com.kingsware.kdev.core.kflow.bean.KFlowUploadFile;
import com.kingsware.kdev.core.kflow.bean.KdbFlowResult;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.sys.service.impl.DevApplicationServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 青松安装包初始化
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/3/28 4:37 下午
 */
@Component
@Slf4j
public class KAppInitialize {

    @Value("${app.init-psi-path:.}")
    private String initPsiPath;

    @Resource
    private DevApplicationServiceImpl devApplicationService;


    public void execute() {
        String regex = ".*\\.pine";
//        executeExportFlowSql();
        // 扫描指定目录的psi文件
        File[] files = new File(initPsiPath).listFiles((dir, name) -> Pattern.compile(regex).matcher(name).matches());
        if (files == null) {
            return;
        }
        Arrays.sort(files, Comparator.comparing(File::getName));

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
                devApplicationService.importApp(stringBuffer.toString());
                // 如果导入成功，备份文件
                devApplicationService.backupPine(stringBuffer.toString(), file.getName());
                // 移除当前文件
                Files.delete(file.toPath());
                long t2 = System.currentTimeMillis();
                log.info("应用安装成功，应用包名称:{}, 用时:{} ms", file.getName(),  (t2 -t1));


            }
            catch (Exception e) {
                log.error("文件读取失败: ", e);
            }

        }
    }

}
