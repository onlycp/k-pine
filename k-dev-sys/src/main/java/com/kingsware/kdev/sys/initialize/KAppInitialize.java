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

import javax.annotation.Resource;
import java.io.File;
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
public class KAppInitialize implements SystemInitialize {

    @Value("${app.init-psi-path:.}")
    private String initPsiPath;

    @Resource
    private DevApplicationServiceImpl devApplicationService;

    @Override
    public void execute() {
        String regex = "\\S+pine";
//        executeExportFlowSql();
        // 扫描指定目录的psi文件
        File[] files = new File(initPsiPath).listFiles((dir, name) -> Pattern.compile(regex).matcher(name).matches());
        if (files == null) {
            return;
        }
        // 创建历史目录
        String hisPathString = initPsiPath +"/AppHistory/";
        File hisPath  = new File(hisPathString);
        if (!hisPath.exists()) {
            hisPath.mkdirs();
        }
        // 遍历安装
        for (File file: files) {
            try {
                log.info("开始安装应用: {}", file.getName());
                long t1 = System.currentTimeMillis();
                byte[] bytes = Files.readAllBytes(file.toPath());
                devApplicationService.importApp(new String(bytes));
                // 如果导入成功，
                long t2 = System.currentTimeMillis();
                log.info("应用安装成功，应用包名称:{}, 用时:{} ms", file.getName(),  (t2 -t1));
                Files.move(file.toPath(), new File(hisPathString+ file.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);

            }
            catch (Exception e) {
                log.error("文件读取失败: ", e);
            }

        }
    }

    @Override
    public int sort() {
        return 4;
    }
}
