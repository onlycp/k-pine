package com.kingsware.kdev.sys.initialize;

import com.kingsware.kdev.core.base.SystemInitialize;
import com.kingsware.kdev.core.kflow.KFlowContext;
import com.kingsware.kdev.core.kflow.KdbFlowExecutor;
import com.kingsware.kdev.core.kflow.bean.KFlowUploadFile;
import com.kingsware.kdev.core.kflow.bean.KdbFlowResult;
import com.kingsware.kdev.core.model.SysFile;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.util.FileUtils;
import com.kingsware.kdev.core.util.MD5Utils;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.sys.manager.FileManager;
import com.kingsware.kdev.sys.service.impl.DevApplicationServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
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
            String fileId = "";
            try {
                // 通过文件名和md5去查询
                String md5 = FileUtils.getMD5(new FileInputStream(file));
                String id = DB.findSingleAttribute(String.class, "select id from sys_file where file_md5 = ? and file_name = ?", md5, file.getName());
                String fileContent = readFile(file);
                if(StringUtils.isNotEmpty(id)) {
                    log.info("文件已存在，跳过: {}", file.getName());
                }
                else {
                    log.info("准备上传pine数据包，文件名:{}", file.getName());
                    SysFile sysFile = FileManager.getInstance().register(file, "install", 2);
                    fileId = sysFile.getId();
                    log.info("完成上传pine数据包，文件ID:{}", sysFile.getId());

                    log.info("开始安装应用: {}", file.getName());
                    long t1 = System.currentTimeMillis();

                    devApplicationService.importApp(fileContent);
                    long t2 = System.currentTimeMillis();
                    log.info("应用安装成功，应用包名称:{}, 用时:{} ms", file.getName(),  (t2 -t1));
                }
                // 将文件移动到备份目录
                devApplicationService.backupPine(fileContent, file.getName());
                // 移除当前文件
                Files.delete(file.toPath());
            }
            catch (Exception e) {
                if (StringUtils.isNotEmpty(fileId)) {
                    DB.delete(SysFile.class, fileId);
                }

                log.error("文件读取失败: ", e);

            }

        }
    }


    private String readFile(File file) throws Exception {
        List<String> lines  = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
        StringBuilder stringBuffer = new StringBuilder();
        for (String line: lines) {
            stringBuffer.append(line);
        }
        return stringBuffer.toString();
    }
}
