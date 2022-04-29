package com.kingsware.kdev.sys.initialize;

import com.kingsware.kdev.core.base.SystemInitialize;
import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.kdb.KdbRet;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.sys.bean.FaasUploadRet;
import com.kingsware.kdev.sys.model.SysFile;
import com.kingsware.kdev.sys.service.impl.DevApplicationServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
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
public class KFileMigrateInitialize implements SystemInitialize {

    /** 是否启用文件迁移 **/
    @Value("${app.file-local-to-faas:false}")
    private boolean fileLocalToFaas;
    /** 本地路径 **/
    @Value("${file.base-path:.}")
    private String basePath;


    @Override
    public void execute() {
        if (!fileLocalToFaas) {
            return;
        }
        // 查找所有存储类型为1的文件
        List<SysFile> sysFileList = DB.findList(SysFile.class, "select * from sys_file where save_type=1");
        log.info("开始从本地存储迁移到FAAS，文件数: {}", sysFileList.size());
        int index = 0;
        for (SysFile sysFile: sysFileList) {
            String absFilePath = basePath + sysFile.getFilePath();
            File localFile = new File(absFilePath);
            if (!localFile.exists()) {
                continue;
            }
            try(FileInputStream inputStream = new FileInputStream(absFilePath)) {
                KdbRet<String> kdbRet = DB.kdbApi().uploadFile(inputStream, sysFile.getFileName());
                if ("成功".equals(kdbRet.getMessage())) {
                    sysFile.setSaveType(2);
                    FaasUploadRet faasUploadRet = JsonUtil.toBean(kdbRet.getResponseBody(), FaasUploadRet.class);
                    sysFile.setFilePath(faasUploadRet.getFileName());
                    DB.save(sysFile);
                    log.info("本地存储迁移到FAAS完成，文件序号:{}, 文件名：{}, 文件路径:{}", index++, sysFile.getFileName(), absFilePath);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

//
    }

    @Override
    public int sort() {
        return 5;
    }
}
