package com.kingsware.kdev.sys.initialize;

import com.kingsware.kdev.core.base.SystemInitialize;
import com.kingsware.kdev.core.kflow.bean.GitFile;
import com.kingsware.kdev.core.kflow.function.AppGit;
import com.kingsware.kdev.core.model.DevPage;
import com.kingsware.kdev.core.model.SysLogicFlow;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.kdb.*;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.sys.model.DevApplication;
import com.kingsware.kdev.sys.model.SysApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 应用库初始化
 */
@Component
@Slf4j
public class AppGitRepoInitialize implements SystemInitialize {

    private static final int pageSize = 50;

    @Override
    public void execute() throws FileNotFoundException {
        // 开辟一个新线程，防止卡住主线程
        new Thread(this::doHandler).start();
    }

    private void doHandler(){
        // 等待所有 pine 包安装完成后，启动 Git仓库 的初始化
        while (hasPineInstalled()){
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        // 查询所有应用
        List<DevApplication> apps = DB.findList(DevApplication.class, "select * from dev_application where deleted = 0");
        if(apps.isEmpty()){
            return;
        }

        /*
         * 应用库的版本管理说明
         *
         * 1、全新部署时，需要初始化 Git 仓库
         * 2、页面、逻辑编排、接口等数据，至少要有 1 次 commit（整体），从而相关历史页面有数据
         */

        for (DevApplication app : apps) {
            // 初始化仓库
            AppGit appGit = new AppGit(app.getId());
            if (appGit.hasRepo()) {
                log.info("应用库[{}]已存在，跳过初始化", app.getName());
                continue;
            }
            appGit.initRepo();
            appGit.commit(appGit.getCommit("初始化应用库"));

            // 应用信息
            appInfoInit(app, appGit);
            // 数据源
            dataSourceInit(app, appGit);
            // 逻辑编排
            logicInit(app, appGit);
            // 页面
            pageInit(app, appGit);
            // 接口
            apiInit(app, appGit);
            // 函数库
            functionInit(app, appGit);
        }

    }

    // 数据源初始化
    private void dataSourceInit(DevApplication app, AppGit appGit) {
        // 25/03/18：因为 faas 数据源暂未支持 appId原因，需要单独兼容
        // 先查出所有数据源，因为数据源不会有太多数据，直接查询所有即可
        DataSourceQueryArgv dataSourceQueryArgv = new DataSourceQueryArgv();
        List<DataSourceInfo> allDataSources = DB.kdbApi().queryDataSource(dataSourceQueryArgv);
        if (allDataSources == null || allDataSources.isEmpty()) {
            return;
        }
        // 过滤应用id的数据源
        List<DataSourceInfo> appDataSources = allDataSources.stream().filter(it -> app.getId().equals(it.getAppId())
                || (it.getJson() != null && !it.getJson().isEmpty() && it.getJson().contains(app.getId()))).collect(Collectors.toList());
        // 提交到 Git 仓库
        if (appDataSources.isEmpty()) {
            return;
        }
        List<GitFile> gitFiles = new ArrayList<>();
        for (DataSourceInfo ds : appDataSources) {
            GitFile gitFile = new GitFile();
            gitFile.setPath("data_sources/" + ds.getSourceName() + ".json");
            gitFile.setContent(JsonUtil.toJson(ds));
            gitFiles.add(gitFile);
        }
        appGit.addFiles(gitFiles);
        appGit.commit(appGit.getCommit("初始化数据源"));
    }

    // 函数库初始化
    private void functionInit(DevApplication app, AppGit appGit) {
        int total = (int) DB.byName("kingDB").findCount("select count(1) total from functions where app_id = ?", app.getId());
        if (total == 0) {
            return;
        }
        int totalPage = (int) Math.ceil(total / (double) pageSize);
        for (int i = 1; i <= totalPage ; i++) {
            int start = (i - 1) * pageSize;
            List<Functions> functions = DB.byName("kingDB").findList(Functions.class, "select * from functions where app_id = ? " +
                    "order by createtime limit ? offset ?", app.getId(), pageSize, start);
            // 提交到 Git 仓库
            List<GitFile> gitFiles = new ArrayList<>();
            for (Functions fun : functions) {
                GitFile gitFile = new GitFile();
                gitFile.setPath("functions/" + fun.getId() + ".js");
                gitFile.setContent(fun.getScript());
                gitFiles.add(gitFile);
            }
            appGit.addFiles(gitFiles);
        }
        appGit.commit(appGit.getCommit("初始化函数库"));
    }

    // 接口初始化
    private void apiInit(DevApplication app, AppGit appGit) {
        int total = (int) DB.findCount("select count(1) total from sys_api where app_id = ?", app.getId());
        if (total == 0) {
            return;
        }
        int totalPage = (int) Math.ceil(total / (double) pageSize);
        for (int i = 1; i <= totalPage ; i++) {
            int start = (i - 1) * pageSize;
            List<SysApi> apis = DB.findList(SysApi.class, "select * from sys_api where app_id = ? " +
                    "order by when_created limit ? offset ?", app.getId(), pageSize, start);
            // 提交到 Git 仓库
            List<GitFile> gitFiles = new ArrayList<>();
            for (SysApi api : apis) {
                GitFile gitFile = new GitFile();
                gitFile.setPath("apis/" + api.getId() + ".json");
                gitFile.setContent(JsonUtil.toJson(api));
                gitFiles.add(gitFile);
            }
            appGit.addFiles(gitFiles);
        }
        appGit.commit(appGit.getCommit("初始化接口"));
    }

    // 流程初始化
    private void logicInit(DevApplication app, AppGit appGit) {
        int total = (int) DB.findCount("select count(1) total from sys_logic_flow where application_id = ?", app.getId());
        if (total == 0) {
            return;
        }
        int totalPage = (int) Math.ceil(total / (double) pageSize);
        for (int i = 1; i <= totalPage ; i++) {
            int start = (i - 1) * pageSize;
            // logicFlow内容
            List<SysLogicFlow> logicFlows = DB.findList(SysLogicFlow.class, "select * from sys_logic_flow where application_id = ? " +
                    "order by when_created limit ? offset ?", app.getId(), pageSize, start);
            // 提交到 Git 仓库
            List<GitFile> gitFiles = new ArrayList<>();
            for (SysLogicFlow logicFlow : logicFlows) {
                // 合并logicFlow和kdbFlow
                Map<String, Object> mergeContent = new HashMap<>();
                mergeContent.put("logicFlow", logicFlow);

                // 查询 kdbFlow 内容
                // 25/03/18 实测，kdbFlowQueryArgv.setFlowIds 未实现，返回结果是空，暂以单条查询替代
                KdbFlowQueryArgv kdbFlowQueryArgv = new KdbFlowQueryArgv();
                kdbFlowQueryArgv.setFlowId(logicFlow.getFlowId());
                List<FlowInfo> kdbFlows = DB.kdbApi().queryFlow(kdbFlowQueryArgv).getList();
                if(!kdbFlows.isEmpty()) {
                    Map<String, Object> objectToMap = JsonUtil.beanToMap(kdbFlows.get(0));
                    if(objectToMap.get("content") != null && !objectToMap.get("content").toString().isEmpty()) {
                        objectToMap.put("content", JsonUtil.toMap(objectToMap.get("content").toString()));
                    }
                    // 保持和已有数据格式一致，kdbFlow 列名小写
                    Map<String, Object> lowerCaseMap = new HashMap<>();
                    for (String key : objectToMap.keySet()) {
                        lowerCaseMap.put(key.toLowerCase(), objectToMap.get(key));
                    }
                    mergeContent.put("kdbFlow", lowerCaseMap);
                }

                GitFile gitFile = new GitFile();
                gitFile.setPath("logic/" + logicFlow.getFlowId() + ".json");
                gitFile.setContent(JsonUtil.toJson(mergeContent));
                gitFiles.add(gitFile);
            }
            appGit.addFiles(gitFiles);
        }
        appGit.commit(appGit.getCommit("初始化逻辑编排"));
    }

    // 页面初始化
    private void pageInit(DevApplication app, AppGit appGit) {
        int total = (int) DB.findCount("select count(1) total from dev_page where deleted = 0 and app_id = ?", app.getId());
        if (total == 0) {
            return;
        }
        int totalPage = (int) Math.ceil(total / (double) pageSize);
        for (int i = 1; i <= totalPage ; i++) {
            int start = (i - 1) * pageSize;
            List<DevPage> pages = DB.findList(DevPage.class, "select * from dev_page where deleted = 0 and app_id = ? " +
                    "order by when_created limit ? offset ?", app.getId(), pageSize, start);
            // 提交到 Git 仓库
            List<GitFile> gitFiles = new ArrayList<>();
            for (DevPage page : pages) {
                // 转换 pageJson 类型
                Map<String, Object> objectToMap = JsonUtil.beanToMap(page);
                if(objectToMap.get("pageJson") != null && !objectToMap.get("pageJson").toString().isEmpty()) {
                    objectToMap.put("pageJson", JsonUtil.toMap(objectToMap.get("pageJson").toString()));
                }
                GitFile gitFile = new GitFile();
                gitFile.setPath("pages/" + page.getId() + ".json");
                gitFile.setContent(JsonUtil.toJson(objectToMap));
                gitFiles.add(gitFile);
            }
            appGit.addFiles(gitFiles);
        }
        appGit.commit(appGit.getCommit("初始化页面"));
    }

    // 应用信息初始化
    private void appInfoInit(DevApplication app, AppGit appGit) {
        GitFile gitFile = new GitFile();
        gitFile.setPath("app.json");
        gitFile.setContent(JsonUtil.toJson(app));
        appGit.addFiles(Collections.singletonList(gitFile));
        appGit.commit(appGit.getCommit("初始化应用信息"));
    }

    // 判断是否有 pine 包正在安装
    private boolean hasPineInstalled(){
        String currentDirectory = System.getProperty("user.dir");
        File[] files = new File(currentDirectory).listFiles((dir, name) -> name.toLowerCase().endsWith(".pine") || name.toLowerCase().endsWith(".pinezip"));
        return files != null && files.length > 0;
    }

    @Override
    public int sort() {
        return 8;
    }

}





























