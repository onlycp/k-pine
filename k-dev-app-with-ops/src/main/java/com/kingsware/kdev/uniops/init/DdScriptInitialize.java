package com.kingsware.kdev.uniops.init;

import com.kingsware.kdev.core.base.SystemInitialize;
import com.kingsware.kdev.core.util.FileUtils;
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

    @Value("${app.faas.db-source-dir}")
    private String dbSourceDir;

    @Value("${app.faas.db-target-dir}")
    private String dbTargetDir;
    /**
     * 系统初始化处理
     */
    @Override
    public void execute() throws FileNotFoundException {
        // 判断faas的db目录是否存在
        if (!new File(dbSourceDir).exists()) {
            return;
        }
        // 如果已存在，就不替换
        if (new File(dbTargetDir).exists()) {
            return;
        }
        try {
            FileUtils.copyDir(dbSourceDir, dbTargetDir);
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
