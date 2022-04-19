package com.kingsware.kdev.sys.initialize;

import com.kingsware.kdev.core.base.SystemInitialize;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.kflow.KFlowContext;
import com.kingsware.kdev.core.kflow.KdbFlowExecutor;
import com.kingsware.kdev.core.kflow.bean.KFlowUploadFile;
import com.kingsware.kdev.core.kflow.bean.KdbFlowResult;
import com.kingsware.kdev.core.kflow.define.FlowDefinition;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.kdb.DataSourceInfo;
import com.kingsware.kdev.core.orm.kdb.DataSourceQueryArgv;
import com.kingsware.kdev.core.orm.kdb.EditFlowInfo;
import com.kingsware.kdev.core.orm.kdb.FlowInfo;
import com.kingsware.kdev.core.util.FileUtils;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.core.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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

    @Override
    public void execute() {
        String regex = "\\S+pine";
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
                // 创建流程文件
                KFlowUploadFile uploadFile = new KFlowUploadFile();
                uploadFile.setFileContent(Base64.getEncoder().encodeToString(bytes));
                uploadFile.setFileSize((long)bytes.length);
                // 组装参数
                Map<String, Object> argvMap = new HashMap<>();
                argvMap.put("file", uploadFile);
                // 流程上下文创建
                KFlowContext kFlowContext = new KFlowContext();
                KdbFlowResult result = KdbFlowExecutor.getInstance().execute("a3fb10c16e0942479c42c0f848eab6fc", "", argvMap, kFlowContext);
                // 如果导入成功，
                long t2 = System.currentTimeMillis();
                log.info("应用安装成功，应用包名称:{}, 用时:{} ms", file.getName(), (t2 -t1));
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
