package com.kingsware.kdev.sys.initialize;

import com.kingsware.kdev.core.base.SystemInitialize;
import com.kingsware.kdev.core.kflow.bean.GitCommit;
import com.kingsware.kdev.core.kflow.bean.GitFile;
import com.kingsware.kdev.core.kflow.function.AppGit;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.kdb.Functions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * 公共库初始化
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2024/8/29 14:31
 */
@Component
@Slf4j
public class PublicGitInitialize implements SystemInitialize {

    @Override
    public void execute() throws FileNotFoundException {
        // 判断仓库是否已存在，如果已存在，则跳过初始化
        AppGit appGit = new AppGit("public");
        if (appGit.hasRepo()) {
            log.info("公共库已存在，跳过初始化");
            return;
        }
        // 初始化公共库
        appGit.initRepo();
        // 处理公共函数
        List<Functions> functions = DB.byName("kingDB").findList(Functions.class,"select id, script, updatetime as updateTime from functions where groupname is null or groupname=?", "public");
        List<GitFile> gitFiles = new ArrayList<>();
        for (Functions fun: functions) {
            GitFile gitFile = new GitFile();
            gitFile.setContent(fun.getScript());
            gitFile.setPath("public/" + "functions/" + fun.getId() + ".js");
            gitFiles.add(gitFile);
        }
        appGit.addFiles(gitFiles);
        // 提交
        appGit.commit(appGit.getCommit( "初始化公共库"));


    }

    @Override
    public int sort() {
        return 6;
    }
}
