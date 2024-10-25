package com.kingsware.kdev.sys.initialize;

import com.kingsware.kdev.core.base.SystemInitialize;
import com.kingsware.kdev.core.kflow.bean.GitFile;
import com.kingsware.kdev.core.kflow.function.AppGit;
import com.kingsware.kdev.core.model.DevPage;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.SqlWrapper;
import com.kingsware.kdev.core.orm.kdb.Functions;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.core.kflow.bean.GitFileHis;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.sys.bean.GitHistory;
import com.kingsware.kdev.sys.model.DevApplication;
import com.kingsware.kdev.sys.model.SysApi;
import com.kingsware.kdev.sys.model.SysLogicTemplate;
import com.kingsware.kdev.sys.model.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.util.*;

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

    private Map<String, String> usernameMap;

    @Override
    public void execute() throws FileNotFoundException {
        if (1 == 1) {
            return ;
        }
        usernameMap = this.getUserIdNameMap();
        // 应用库初始化
        this.initApps();
        // 判断仓库是否已存在，如果已存在，则跳过初始化
        AppGit appGit = new AppGit("public");
        if (appGit.hasRepo()) {
            log.info("公共库已存在，跳过初始化");
            return;
        }
        try {

            // 初始化公共库
            appGit.initRepo();
            // 提交
            appGit.commit(appGit.getCommit( "初始化公共库"));
            // 处理公共函数
            List<Functions> functions = DB.byName("kingDB").findList(Functions.class,"select id, script, updatetime as updateTime from functions where app_id is null or app_id=?", "public");
            List<GitFile> gitFiles = new ArrayList<>();
            for (Functions fun: functions) {
                GitFile gitFile = new GitFile();
                gitFile.setContent(fun.getScript());
                gitFile.setPath("functions/" + fun.getId() + ".js");
                gitFiles.add(gitFile);
            }
            appGit.addFiles(gitFiles);
            appGit.commit(appGit.getCommit( "初始化函数库"));
            // 处理页面模板
            List<GitHistory> pageTemplates = DB.findList(GitHistory.class, "select id as rid, page_json as content, when_modified as time, who_modified as uid from dev_page_template");
            for (int i= 0; i < pageTemplates.size(); i++) {
                GitHistory pageTemplate = pageTemplates.get(i);
                // 查找所有历史
                List<GitHistory> historyList = DB.findList(GitHistory.class, "select page_json as content, when_created as time, who_created as uid from dev_page_template_history where tpl_id=? and page_json is not null order by when_created desc limit 10", pageTemplate.getRid());
                Collections.reverse(historyList);
                // 加入当前
                historyList.add(pageTemplate);
                this.commitFile(appGit, "page_templates/"  + pageTemplate.getRid() + ".page", historyList, i, pageTemplates.size());
            }
            // 处理工具库模板
            List<SysLogicTemplate> logicTemplates = DB.findList(SysLogicTemplate.class, "select * from sys_logic_template order by when_created asc");
            List<GitFile> gitLogicsFiles = new ArrayList<>();
            for (SysLogicTemplate logicTemplate: logicTemplates) {
                GitFile gitFile = new GitFile();
                gitFile.setContent(JsonUtil.toJson(logicTemplate));
                gitFile.setPath("logic_templates/" + logicTemplate.getId() + ".json");
                gitLogicsFiles.add(gitFile);
            }
            if (!gitLogicsFiles.isEmpty()) {
                appGit.addFiles(gitLogicsFiles);
            }
            appGit.commit(appGit.getCommit( "初始化逻辑编排模板"));



        }
        catch (Exception e) {
            log.error("公共库初始化失败", e);
        }
    }

    /**
     * 提交文件
     * @param appGit appGit
     * @param filePath
     * @param historyList
     * @param currentIndex
     * @param totalCount
     */
    private void commitFile(AppGit appGit, String filePath, List<GitHistory> historyList, int currentIndex, int totalCount) {
        List<GitFileHis> hisList = new ArrayList<>();
        String lastJson = "";
        // 添加历史记录
        long t2= System.currentTimeMillis();
        for (GitHistory history: historyList) {
            if (StringUtils.isEmpty(history.getContent())) {
                continue;
            }
            if (!lastJson.equals(history.getContent())) {
                String prettyJson = JsonUtil.prettyJson(history.getContent());
//                        log.info("格式化用时：{}", t2 - t1);
                String commit = appGit.getCommit(usernameMap.getOrDefault(history.getUid(), "admin"), history.getTime(), "").toString();
                hisList.add(new GitFileHis(prettyJson, commit));
                lastJson = history.getContent();
            }
        };
        appGit.batchAddCommit(filePath, hisList);
        long t3 = System.currentTimeMillis();
        log.info("资源初始化：{}, 记录数: {}, 当前进度：{}/{},  提交用时:{}", filePath, hisList.size(), currentIndex+1, totalCount,  t3-t2);
    }

    /**
     * 获取用户id和用户名
     * @return
     */
    private Map<String, String> getUserIdNameMap() {
        List<SysUser> users = DB.findList(SysUser.class, "select id, username from sys_user");
        Map<String, String> map = new HashMap<>();
        for (SysUser user: users) {
            map.put(user.getId(), user.getUsername());
        }
        return map;
    }

    @Override
    public int sort() {
        return 6;
    }

    /**
     * 提交函数库
     * @param appId
     */
    private void gitCommitFunctions(String appId) {
        List<Functions> functions = DB.byName("kingDB").findList(Functions.class,"select id, script, updatetime as updateTime from functions where app_id=?", appId);
        List<GitFile> gitFiles = new ArrayList<>();
        for (Functions fun: functions) {
            GitFile gitFile = new GitFile();
            gitFile.setContent(fun.getScript());
            gitFile.setPath("functions/" + fun.getId() + ".js");
            gitFiles.add(gitFile);
        }
        AppGit appGit = new AppGit(appId);
        if (!gitFiles.isEmpty()) {
            appGit.addFiles(gitFiles);
            appGit.commit(appGit.getCommit( "初始化函数库"));
        }


    }

    /**
     * 接口信息
     * @param appId
     */
    private void gitCommitApis(String appId) {
        List<SysApi> apis = DB.findList(SysApi.class, "select * from sys_api where app_id=?", appId);
        List<GitFile> gitFiles = new ArrayList<>();
        for (SysApi api: apis) {
            GitFile gitFile = new GitFile();
            gitFile.setContent(JsonUtil.prettyJson(JsonUtil.toJson(api)));
            gitFile.setPath("apis/" + api.getId() + ".json");
            gitFiles.add(gitFile);
        }
        if (!gitFiles.isEmpty()) {
            AppGit appGit = new AppGit(appId);
            appGit.addFiles(gitFiles);
            appGit.commit(appGit.getCommit( "初始化接口库"));
        }

    }

    private void gitCommitPages(String appId) {
        AppGit appGit = new AppGit(appId);
        List<GitHistory> pages = DB.findList(GitHistory.class, "select id as rid, page_json as content, when_modified as time, who_modified as uid from dev_page where app_id=? and page_json is not null", appId);
        for (int i= 0; i < pages.size(); i++) {
            GitHistory pageTemplate = pages.get(i);
            // 查找所有历史
            List<GitHistory> historyList = DB.findList(GitHistory.class, "select page_json as content, when_created as time, who_created as uid from dev_page where app_id=? and page_json is not null order by when_created desc limit 10", pageTemplate.getRid());
            Collections.reverse(historyList);
            // 加入当前
            historyList.add(pageTemplate);
            this.commitFile(appGit, "pages/"  + pageTemplate.getRid() + ".json", historyList, i, pages.size());
        }
    }

    private void gitCommitLogics(String appId) {
        AppGit appGit = new AppGit(appId);
        // 获取当前的逻辑编排id
        List<GitHistory>  gitHistories = DB.findList(GitHistory.class, "select flow_id as rid, when_modified as time, who_modified as uid from sys_logic_flow where application_id=?", appId);
        List<Object> flowIds = new ArrayList<>();
        for (GitHistory gitHistory: gitHistories) {
            flowIds.add(gitHistory.getRid());
        }
        if (flowIds.isEmpty()) {
            return;
        }
        // 从faas中查询流程定义
        SqlWrapper wrapper = new SqlWrapper("select flowid as rid, content as content from PUBLIC.FLOW where 1=1 ");
        wrapper.in("flowid", flowIds);
        List<GitHistory> flowJsonList = DB.byName("kingDB").findList(GitHistory.class, wrapper.getSql(), wrapper.getParams().toArray());
        Map<String, String> flowContentMap = new HashMap<>();
        for (GitHistory his: flowJsonList) {
            flowContentMap.put(his.getRid(), his.getContent());
        }
        // 填充content
        for (GitHistory his: gitHistories) {
           his.setContent(flowContentMap.get(his.getRid()));
        }

        for (int i= 0; i < gitHistories.size(); i++) {
            GitHistory pageTemplate = gitHistories.get(i);
            // 查找所有历史
            List<GitHistory> historyList = DB.findList(GitHistory.class, "select flow_json as content, when_created as time, who_created as uid from sys_logic_history where flow_id=?  order by when_created desc limit 10", pageTemplate.getRid());
            Collections.reverse(historyList);
            // 加入当前
            historyList.add(pageTemplate);
            this.commitFile(appGit, "logic/"  + pageTemplate.getRid() + ".json", historyList, i, gitHistories.size());
        }
    }

    private void initApps() {
        List<DevApplication> applications = DB.findList(DevApplication.class, "select * from dev_application");
        for (DevApplication application: applications) {
            AppGit appGit = new AppGit(application.getId());
            if (appGit.hasRepo()) {
                log.info("应用库已存在，跳过初始化");
                return;
            }
            // 初始化应用创建
            appGit.initRepo();
            appGit.commit(appGit.getCommit("应用库初始化"));
            // 应用元数据
            GitFile gitFile = new GitFile();
            gitFile.setContent(JsonUtil.toJson(application));
            gitFile.setPath("app.json");
            appGit.addCommitFile(gitFile, appGit.getCommit("应用元数据"));
//            // 页面
//            this.gitCommitPages(application.getId());
//            // 函数列表
//            this.gitCommitFunctions(application.getId());
//            // 接口列表
//            this.gitCommitApis(application.getId());
//            // 编排
//            this.gitCommitLogics(application.getId());

        }
    }
}
