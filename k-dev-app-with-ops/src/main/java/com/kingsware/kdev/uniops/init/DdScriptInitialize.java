package com.kingsware.kdev.uniops.init;

import com.kingsware.kdev.core.base.SystemInitialize;
import com.kingsware.kdev.core.util.FileUtils;
import com.kingsware.kdev.core.util.PathSecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author chenp
 * @date 2023/3/23
 */
@Slf4j
@Component
public class DdScriptInitialize implements SystemInitialize {

    @Value("${app.faas.db-root:..}")
    private String dbRootDir;

    @Value("${app.faas.db-source-dir:../db}")
    private String dbSourceDir;

    @Value("${app.faas.db-target-dir:../db}")
    private String dbTargetDir;
    /**
     * 系统初始化处理
     */
    @Override
    public void execute() throws FileNotFoundException {
        try {
            File rootDir = PathSecurityUtils.canonicalFile(dbRootDir, "app.faas.db-root");
            File sourceDir = PathSecurityUtils.canonicalFile(dbSourceDir, "app.faas.db-source-dir");
            File targetDir = PathSecurityUtils.canonicalFile(dbTargetDir, "app.faas.db-target-dir");
            // 仅允许在受控目录内拷贝，防止路径穿越到任意目录
            if (!PathSecurityUtils.isInsideRoot(sourceDir, rootDir) || !PathSecurityUtils.isInsideRoot(targetDir, rootDir)) {
                log.warn("faas数据库目录越界，跳过初始化拷贝。root={}, source={}, target={}",
                        rootDir.getPath(), sourceDir.getPath(), targetDir.getPath());
                return;
            }
            // 判断faas的db目录是否存在
            if (!sourceDir.exists()) {
                return;
            }
            // 如果已存在，就不替换
            if (targetDir.exists()) {
                return;
            }
            FileUtils.copyDir(sourceDir.getPath(), targetDir.getPath());
        }
        catch (IOException e) {
            log.error("faas的数据库拷贝失败");
            throw new RuntimeException(e);
        }


    }

    @Override
    public int sort() {
        return -1;
    }
}
