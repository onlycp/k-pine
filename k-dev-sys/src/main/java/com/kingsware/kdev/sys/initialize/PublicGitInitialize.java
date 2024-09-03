package com.kingsware.kdev.sys.initialize;

import com.kingsware.kdev.core.base.SystemInitialize;
import com.kingsware.kdev.core.kflow.bean.GitFile;
import com.kingsware.kdev.core.kflow.function.AppGit;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.kdb.Functions;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.core.kflow.bean.GitFileHis;
import com.kingsware.kdev.sys.bean.GitHistory;
import com.kingsware.kdev.sys.model.DevApplication;
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

    @Override
    public void execute() throws FileNotFoundException {
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
            // 获取用户映射
            Map<String, String> usernameMap = getUserIdNameMap();
            // 处理页面模板
            List<GitHistory> pageTemplates = DB.findList(GitHistory.class, "select id as rid, page_json as content, when_modified as time, who_modified as uid from dev_page_template");
            for (int i= 0; i < pageTemplates.size(); i++) {
                GitHistory pageTemplate = pageTemplates.get(i);
                // 查找所有历史
                List<GitHistory> historyList = DB.findList(GitHistory.class, "select page_json as content, when_created as time, who_created as uid from dev_page_template_history where tpl_id=? and page_json is not null order by when_created desc limit 10", pageTemplate.getRid());
                Collections.reverse(historyList);
                // 加入当前
                historyList.add(pageTemplate);
                this.commitFile(appGit, "page_templates/"  + pageTemplate.getRid() + ".page", historyList, usernameMap, i, pageTemplates.size());
            }
            // 处理工具库模板
            List<SysLogicTemplate> logicTemplates = DB.findList(SysLogicTemplate.class, "select id, nodes, links, flow_config from sys_logic_template");
            List<GitFile> gitLogicsFiles = new ArrayList<>();
            for (SysLogicTemplate logicTemplate: logicTemplates) {
                GitFile gitFile = new GitFile();
                Map<String, Object> templateData = new HashMap<>();
                templateData.put("nodes", logicTemplate.getNodes());
                templateData.put("links", logicTemplate.getLinks());
                templateData.put("flowConfig", JsonUtil.toJson(logicTemplate.getFlowConfig()));
                gitFile.setContent(JsonUtil.toJson(templateData));
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

    private void commitFile(AppGit appGit, String filePath, List<GitHistory> historyList, Map<String, String> usernameMap, int currentIndex, int totalCount) {
        List<GitFileHis> hisList = new ArrayList<>();
        String lastJson = "";
        // 添加历史记录
        long t2= System.currentTimeMillis();
        for (GitHistory history: historyList) {
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

        }
    }
}
