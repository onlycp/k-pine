package com.kingsware.kdev.sys.initialize;

import com.kingsware.kdev.core.base.SystemInitialize;
import com.kingsware.kdev.core.kflow.bean.GitFile;
import com.kingsware.kdev.core.kflow.function.AppGit;
import com.kingsware.kdev.core.model.DevPage;
import com.kingsware.kdev.core.model.SysLogicFlow;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.kdb.*;
import com.kingsware.kdev.core.util.DateUtils;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.sys.model.DevApplication;
import com.kingsware.kdev.sys.model.DevPageTemplate;
import com.kingsware.kdev.sys.model.SysApi;
import com.kingsware.kdev.sys.model.SysLogicTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 公共库初始化
 */
@Component
@Slf4j
public class PublicGitRepoInitialize implements SystemInitialize {

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

        // 初始化仓库
        AppGit appGit = new AppGit("public");
        if (appGit.hasRepo()) {
            log.info("公共库[{}]已存在，跳过初始化", "public");
            return;
        }
        appGit.initRepo();
        appGit.commit(appGit.getCommit("初始化公共库"));

        // 数据源
        dataSourceInit(appGit);
        // 逻辑编排
        logicInit(appGit);
        // 页面
        pageInit(appGit);
        // 接口
        apiInit(appGit);
        // 函数库
        functionInit(appGit);
        // 页面模板
        pageTemplateInit(appGit);
        // 逻辑编排工具库
        logicTemplateInit(appGit);

    }

    // 逻辑编排工具库
    private void logicTemplateInit(AppGit appGit){
        int total = (int) DB.findCount("select count(1) total from sys_logic_template");
        if (total == 0) {
            return;
        }
        int totalPage = (int) Math.ceil(total / (double) pageSize);
        for (int i = 1; i <= totalPage ; i++) {
            int start = (i - 1) * pageSize;
            List<SysLogicTemplate> templates = DB.findList(SysLogicTemplate.class, "select * from sys_logic_template " +
                    "order by when_created limit ? offset ?", pageSize, start);
            // 提交到 Git 仓库
            List<GitFile> gitFiles = new ArrayList<>();
            for (SysLogicTemplate template : templates) {
                // 时间戳需要转换格式，保持格式一致
                Map<String, Object> object2Map = JsonUtil.beanToMap(template);
                Timestamp whenCreated = template.getWhenCreated();
                Timestamp whenModified = template.getWhenModified();
                if (whenCreated != null) {
                    object2Map.put("whenCreated",DateUtils.formatDate(whenCreated, "yyyy-MM-dd HH:mm:ss"));
                }
                if (whenModified != null) {
                    object2Map.put("whenModified",DateUtils.formatDate(whenModified, "yyyy-MM-dd HH:mm:ss"));
                }

                GitFile gitFile = new GitFile();
                gitFile.setPath("logic_templates/" + template.getId() + ".json");
                gitFile.setContent(JsonUtil.toJson(object2Map));
                gitFiles.add(gitFile);
            }
            appGit.addFiles(gitFiles);
        }
        appGit.commit(appGit.getCommit("初始化逻辑编排工具库"));
    }

    // 页面模板初始化
    private void pageTemplateInit(AppGit appGit){
        int total = (int) DB.findCount("select count(1) total from dev_page_template");
        if (total == 0) {
            return;
        }
        int totalPage = (int) Math.ceil(total / (double) pageSize);
        for (int i = 1; i <= totalPage ; i++) {
            int start = (i - 1) * pageSize;
            List<DevPageTemplate> templates = DB.findList(DevPageTemplate.class, "select * from dev_page_template " +
                    "order by when_created limit ? offset ?", pageSize, start);
            // 提交到 Git 仓库
            List<GitFile> gitFiles = new ArrayList<>();
            for (DevPageTemplate template : templates) {
                GitFile gitFile = new GitFile();
                gitFile.setPath("page_templates/" + template.getId() + ".page");
                gitFile.setContent(template.getPageJson());
                gitFiles.add(gitFile);
            }
            appGit.addFiles(gitFiles);
        }
        appGit.commit(appGit.getCommit("初始化页面模板"));
    }

    // 数据源初始化
    private void dataSourceInit(AppGit appGit) {
        // 25/03/18：因为 faas 数据源暂未支持 appId原因，需要单独兼容
        // 先查出所有数据源，因为数据源不会有太多数据，直接查询所有即可
        DataSourceQueryArgv dataSourceQueryArgv = new DataSourceQueryArgv();
        List<DataSourceInfo> allDataSources = DB.kdbApi().queryDataSource(dataSourceQueryArgv);
        if (allDataSources == null || allDataSources.isEmpty()) {
            return;
        }
        List<DataSourceInfo> publicDataSources = allDataSources.stream()
                .filter(it -> {
                    String json = it.getJson();
                    boolean existJsonAppId = false;
                    if (json != null && !json.isEmpty()) {
                        Map<String, Object> json2Map = JsonUtil.toMap(json);
                        if (json2Map != null) {
                            existJsonAppId = json2Map.containsKey("appId");
                        }
                    }
                    return !existJsonAppId && (it.getAppId() == null || it.getAppId().isEmpty());
                })
                .collect(Collectors.toList());
        // 提交到 Git 仓库
        if (publicDataSources.isEmpty()) {
            return;
        }
        List<GitFile> gitFiles = new ArrayList<>();
        for (DataSourceInfo ds : publicDataSources) {
            GitFile gitFile = new GitFile();
            gitFile.setPath("data_sources/" + ds.getSourceName() + ".json");
            gitFile.setContent(JsonUtil.toJson(ds));
            gitFiles.add(gitFile);
        }
        appGit.addFiles(gitFiles);
        appGit.commit(appGit.getCommit("初始化数据源"));
    }

    // 函数库初始化
    private void functionInit(AppGit appGit) {
        int total = (int) DB.byName("kingDB").findCount("select count(1) total from functions where app_id is null or app_id = '' ");
        if (total == 0) {
            return;
        }
        int totalPage = (int) Math.ceil(total / (double) pageSize);
        for (int i = 1; i <= totalPage ; i++) {
            int start = (i - 1) * pageSize;
            List<Functions> functions = DB.byName("kingDB").findList(Functions.class, "select * from functions where app_id is null or app_id = '' " +
                    "order by createtime limit ? offset ?", pageSize, start);
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
    private void apiInit(AppGit appGit) {
        int total = (int) DB.findCount("select count(1) total from sys_api where app_id is null  or app_id = '' ");
        if (total == 0) {
            return;
        }
        int totalPage = (int) Math.ceil(total / (double) pageSize);
        for (int i = 1; i <= totalPage ; i++) {
            int start = (i - 1) * pageSize;
            List<SysApi> apis = DB.findList(SysApi.class, "select * from sys_api where app_id is null  or app_id = '' " +
                    "order by when_created limit ? offset ?", pageSize, start);
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
    private void logicInit(AppGit appGit) {
        int total = (int) DB.findCount("select count(1) total from sys_logic_flow where application_id is null  or application_id = '' ");
        if (total == 0) {
            return;
        }
        int totalPage = (int) Math.ceil(total / (double) pageSize);
        for (int i = 1; i <= totalPage ; i++) {
            int start = (i - 1) * pageSize;
            // logicFlow内容
            List<SysLogicFlow> logicFlows = DB.findList(SysLogicFlow.class, "select * from sys_logic_flow where application_id is null  or application_id = '' " +
                    "order by when_created limit ? offset ?",  pageSize, start);
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
    private void pageInit(AppGit appGit) {
        int total = (int) DB.findCount("select count(1) total from dev_page where deleted = 0 and (app_id is null or app_id = '') ");
        if (total == 0) {
            return;
        }
        int totalPage = (int) Math.ceil(total / (double) pageSize);
        for (int i = 1; i <= totalPage ; i++) {
            int start = (i - 1) * pageSize;
            List<DevPage> pages = DB.findList(DevPage.class, "select * from dev_page where deleted = 0 and (app_id is null or app_id = '') " +
                    "order by when_created limit ? offset ?", pageSize, start);
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

    // 判断是否有 pine 包正在安装
    private boolean hasPineInstalled(){
        String currentDirectory = System.getProperty("user.dir");
        File[] files = new File(currentDirectory).listFiles((dir, name) -> name.toLowerCase().endsWith(".pine"));
        return files != null && files.length > 0;
    }

    @Override
    public int sort() {
        return 7;
    }

}





























