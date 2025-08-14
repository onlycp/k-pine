package com.kingsware.kdev.sys.service.impl;

import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.context.KClientContext;
import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.i18n.I18n;
import com.kingsware.kdev.core.kflow.bean.GitCommit;
import com.kingsware.kdev.core.kflow.bean.GitFile;
import com.kingsware.kdev.core.kflow.function.AppGit;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.kdb.DataSourceInfo;
import com.kingsware.kdev.core.orm.kdb.DataSourceQueryArgv;
import com.kingsware.kdev.core.orm.kdb.KdbApi;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.core.util.PageUtil;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.core.util.ServletUtil;
import com.kingsware.kdev.core.util.*;
import com.kingsware.kdev.sys.argv.DataBaseInstanceArgv;
import com.kingsware.kdev.sys.argv.DataSourceTakeArgv;
import com.kingsware.kdev.sys.argv.SysKdbDataSourceArgv;
import com.kingsware.kdev.sys.argv.SysKdbDataSourceQueryArgv;
import com.kingsware.kdev.sys.model.DevApplication;
import com.kingsware.kdev.sys.ret.SysKdbDataSourceRet;
import com.kingsware.kdev.sys.service.SysKdbDataSourceService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * kdb数据源业务实现类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:36 上午
 */
@Service
public class SysKdbDataSourceServiceImpl extends BaseServiceImpl implements SysKdbDataSourceService {

    private static final Logger log = LoggerFactory.getLogger(SysKdbDataSourceServiceImpl.class);
    /** 分隔符 **/
    private final String SPLIT_TAG = "\\\0a01";

    @Override
    public SysKdbDataSourceRet get(String id) {
        // 参数
        DataSourceQueryArgv argv = new DataSourceQueryArgv();
        argv.setSourceName(id);
        // 查询model
        KdbApi api = (KdbApi)(DB.getDefault());
        List<DataSourceInfo> list = api.queryDataSource(argv);
        // 转换成ret对象
        if(list != null && !list.isEmpty()){
            for(DataSourceInfo dataSourceInfo : list){
                if (dataSourceInfo.getSourceName().equals(id)) {
                    return list.isEmpty() ? null : toRet(dataSourceInfo);
                }
            }

        }
        return null;
    }
    private SysKdbDataSourceRet toRet(DataSourceInfo info) {
        return toRet(info, true);
    }
    private SysKdbDataSourceRet toRet(DataSourceInfo info, boolean isCrud) {
        SysKdbDataSourceRet ret = new SysKdbDataSourceRet();
        ret.setId(info.getSourceName());
        ret.setDriverClass(info.getDriverClass());
        ret.setJdbcUrl(info.getJdbcUrl());
        ret.setUsername(info.getUserName());
        if(isCrud) {
            ret.setPassword(info.getPassword());
        }
        ret.setPassword(info.getPassword());
        ret.setAppId(info.getAppId());
        if (info.getJson() == null) {
            ret.setJson("{}");
        }
        else {
            ret.setJson(info.getJson());
            Map<String, Object> json2Map = JsonUtil.toBean(ret.getJson(), Map.class);
            if(json2Map.containsKey("appId")){
                ret.setAppId((String)json2Map.get("appId"));
            }
        }

        if(isCrud) {
            // 处理instance
            List<DataBaseInstanceArgv> instances = new ArrayList<>();
            String[] urls = info.getJdbcUrl().split(SPLIT_TAG);
            String[] usernames = info.getUserName().split(SPLIT_TAG);
            String[] passwords = info.getPassword().split(SPLIT_TAG);
            for (int i=0; i<urls.length; i++) {
                DataBaseInstanceArgv instance = new DataBaseInstanceArgv();
                instance.setJdbcUrl(urls[i]);
                instance.setUserName((usernames.length-1 < i)? "":  usernames[i]);
                if(isCrud) {
                    instance.setPassword((passwords.length-1 < i)? "":  passwords[i]);
                }
                instances.add(instance);
            }
            ret.setInstances(instances);
        }
        return ret;
    }

    @Override
    public void add(SysKdbDataSourceArgv argv) {
        // 首先查询数据源名称是否已经存在，存在则抛出【数据源名称已经存在】异常
        // 否则，经常会错误地认为是数据库连接参数有误，导致用户难以区分
        SysKdbDataSourceRet isExist = get(argv.getId());
        if (isExist != null){
            throw BusinessException.serviceThrow(I18n.t("SysKdbDataSource.tip.alreadyExist", "数据源名称已存在，请更换其他名称！"));
        }

        try {
            DataSourceInfo info = new DataSourceInfo();
            info.setSourceName(argv.getId());
            info.setDriverClass(argv.getDriverClass());
            info.setAppId(argv.getAppId());
//            info.setAppId(argv.getAppId());
            instanceToField(info, argv);
            if (StringUtils.isNotEmpty(argv.getJson())) {
                // 添加appId到json字段里
                String argvJson = argv.getJson();
                if (StringUtils.isNotEmpty(argv.getAppId())){
                    Map<String, Object> json2Map = JsonUtil.toBean(argvJson, Map.class);
                    json2Map.put("appId", argv.getAppId());
                    argvJson = JsonUtil.toJson(json2Map);
                }
                info.setJson(argvJson);
            }
            else {
                String defaultJson = "{}";
                if (StringUtils.isNotEmpty(argv.getAppId())){
                    Map<String, Object> map = new HashMap<>();
                    map.put("appId", argv.getAppId());
                    defaultJson = JsonUtil.toJson(map);
                }
                info.setJson(defaultJson);
            }
            KdbApi api = (KdbApi)(DB.getDefault());
            api.addDataSource(info);

            // 查询数据源详情
            SysKdbDataSourceRet sysKdbDataSourceRet = get(info.getSourceName());

            // 提交git
            String repoId = info.getAppId();
            if (StringUtils.isEmpty(repoId)) repoId = "public";
            String resourceId = info.getSourceName();

            GitFile gitFile = new GitFile();
            gitFile.setPath("data_sources/" + resourceId + ".json");
            gitFile.setContent(JsonUtil.toJson(sysKdbDataSourceRet));

            AppGit appGit = new AppGit(repoId);
            GitCommit gitCommit = appGit.getCommit("添加数据源: " + resourceId);
            gitCommit.setAuthor(KClientContext.getContext().getUserInfo().getUsername());
            appGit.addCommitFile(gitFile, gitCommit);
        }
        catch (Exception e) {
            log.warn("新增数据源失败", e );
            throw BusinessException.serviceThrow(I18n.t("SysKdbDataSource.tip.addFail", "数据源新增失败，请检查连接信息！"));
        }

    }

    /**
     * 将实例的json展开为属性列表
     * @param info
     * @param argv
     */
    private void instanceToField(DataSourceInfo info, SysKdbDataSourceArgv argv) {
        if (argv.getInstances() != null && !argv.getInstances().isEmpty()) {
            List<DataBaseInstanceArgv> instances = argv.getInstances();
            info.setJdbcUrl(StringUtils.joinFieldToString(instances, DataSourceInfo.Fields.jdbcUrl, SPLIT_TAG));
            info.setUserName(StringUtils.joinFieldToString(instances, DataSourceInfo.Fields.userName, SPLIT_TAG));
            info.setPassword(StringUtils.joinFieldToString(instances, DataSourceInfo.Fields.password, SPLIT_TAG));
        }
        else {
            info.setJdbcUrl(argv.getJdbcUrl());
            info.setUserName(argv.getUsername());
            info.setPassword(argv.getPassword());
        }
    }

    private String updateDataSourceAppId(String json, String appId) {
        if (StringUtils.isNotEmpty(json)) {
            // 添加appId到json字段里
            String argvJson = json;
            if (StringUtils.isNotEmpty(appId)) {
                Map<String, Object> json2Map = JsonUtil.toBean(argvJson, Map.class);
                json2Map.put("appId", appId);
                argvJson = JsonUtil.toJson(json2Map);
            }
            else {
                Map<String, Object> json2Map = JsonUtil.toBean(argvJson, Map.class);
                json2Map.remove("appId");
                argvJson = JsonUtil.toJson(json2Map);
            }
            return argvJson;
        } else {
            String defaultJson = "{}";
            if (StringUtils.isNotEmpty(appId)) {
                Map<String, Object> map = new HashMap<>();
                map.put("appId", appId);
                defaultJson = JsonUtil.toJson(map);
            }
            return defaultJson;
        }
    }

    @Override
    public void edit(SysKdbDataSourceArgv argv) {
        try {
            DataSourceInfo info = new DataSourceInfo();
            info.setSourceName(argv.getId());
            info.setDriverClass(argv.getDriverClass());
            // 截止2024/09/09，还不能设置此appId，否则faas会报错：
            // Caused by: java.sql.SQLSyntaxErrorException: user lacks privilege or object not found:
            // APPID in statement [update data_source set password=?,driverClass=?,appId=?,jdbcUrl=?,json=?,sourceName=?,userName=? where sourceName = ?]
            // 但是 faas 表是已经有appId字段的了，推测是faas代码问题
//            info.setAppId(argv.getAppId());

            instanceToField(info, argv);
            KdbApi api = (KdbApi)(DB.getDefault());

            String newJson = updateDataSourceAppId(argv.getJson(), argv.getAppId());
            info.setJson(newJson);
            api.editDataSource(info);

            // 查询数据源详情
            SysKdbDataSourceRet sysKdbDataSourceRet = get(info.getSourceName());

            // 提交git
            String repoId = argv.getAppId();
            if (StringUtils.isEmpty(repoId)) repoId = "public";
            String resourceId = info.getSourceName();

            GitFile gitFile = new GitFile();
            gitFile.setPath("data_sources/" + resourceId + ".json");
            gitFile.setContent(JsonUtil.toJson(sysKdbDataSourceRet));

            AppGit appGit = new AppGit(repoId);
            GitCommit gitCommit = appGit.getCommit("编辑数据源: " + resourceId);
            gitCommit.setAuthor(KClientContext.getContext().getUserInfo().getUsername());
            appGit.addCommitFile(gitFile, gitCommit);
        }
        catch (Exception e) {
            log.warn("编辑数据源失败", e );
            throw BusinessException.serviceThrow(I18n.t("SysKdbDataSource.tip.addFail", "数据源编辑失败，请检查连接信息！") + e.getMessage());
        }

    }

    @Override
    @SuppressWarnings("unchecked")
    public PageDataRet<SysKdbDataSourceRet> query(SysKdbDataSourceQueryArgv argv) {
        DataSourceQueryArgv info = new DataSourceQueryArgv();
        info.setSourceName(argv.getName());
        // 查询所有数据
        KdbApi api = (KdbApi)(DB.getDefault());
        List<DataSourceInfo> list = api.queryDataSource(info);
        // 转为ret类
        List<SysKdbDataSourceRet> retList = new ArrayList<>();
        for (DataSourceInfo infoL: list) {
            retList.add(toRet(infoL, argv.isCrud()));
        }
        // 按应用id过滤
        String appId = KClientContext.getContext().getRequest().getHeader("_request_app");
        // nginx 默认不转发带下划线的header，所以获取_request_app之后再获取一次 appId 防止为空。
        // 之所以优先使用 _request_app 是因为切换应用时 _request_app 会实时变化，但是 appId 不会实时更新。
        if(StringUtils.isEmpty(appId)) {
            appId = KClientContext.getContext().getRequest().getHeader("appId");
        }
        if (StringUtils.isNotEmpty(appId)) {
            String finalAppId = appId;
            retList.removeIf(ret -> !finalAppId.equals(ret.getAppId()) && StringUtils.isNotEmpty(ret.getAppId()));
        } else {
            retList.removeIf(ret -> StringUtils.isNotEmpty(ret.getAppId()));
        }
        // 按driverClass过滤
        if (StringUtils.isNotEmpty(argv.getDriverClass())) {
            retList.removeIf(ret -> !ret.getDriverClass().contains(argv.getDriverClass()));
        }
        // 按jdbc url过滤
        if (StringUtils.isNotEmpty(argv.getJdbcUrl())) {
            retList.removeIf(ret -> !ret.getJdbcUrl().contains(argv.getJdbcUrl()));
        }
        // 排序
        retList.sort(Comparator.comparing(SysKdbDataSourceRet::getId));
        return PageUtil.memoryPage(argv, retList, SysKdbDataSourceRet.class);
    }

    @Override
    public PageDataRet<SysKdbDataSourceRet> queryByAppId(SysKdbDataSourceQueryArgv argv) {
        DataSourceQueryArgv info = new DataSourceQueryArgv();
        info.setSourceName(argv.getName());
        // 查询所有数据
        KdbApi api = (KdbApi)(DB.getDefault());
        List<DataSourceInfo> list = api.queryDataSource(info);
        // 转为ret类
        List<SysKdbDataSourceRet> retList = new ArrayList<>();
        for (DataSourceInfo infoL: list) {
            retList.add(toRet(infoL));
        }
        // 处理appId
        for(SysKdbDataSourceRet ret: retList){
            String appId = ret.getAppId();
            String json = ret.getJson();
            if (StringUtils.isEmpty(appId) && StringUtils.isNotEmpty(json)){
                Map<String, Object> json2Map = JsonUtil.toBean(json, Map.class);
                String anotherAppId = json2Map.get("appId") != null ? json2Map.get("appId").toString():null;
                if (StringUtils.isNotEmpty(anotherAppId)){
                    ret.setAppId(anotherAppId);
                    json2Map.remove("appId");
                    ret.setJson(JsonUtil.toJson(json2Map));
                }
            }
        }
        // 按driverClass过滤
        if (StringUtils.isNotEmpty(argv.getDriverClass())) {
            retList.removeIf(ret -> !ret.getDriverClass().contains(argv.getDriverClass()));
        }
        // 按jdbc url过滤
        if (StringUtils.isNotEmpty(argv.getJdbcUrl())) {
            retList.removeIf(ret -> !ret.getJdbcUrl().contains(argv.getJdbcUrl()));
        }
        // 按应用id过滤
        if (StringUtils.isNotEmpty(argv.getAppId()) && argv.getType() == 1) {
            // 应用数据源 appId = #{appId}
            retList.removeIf(ret -> !argv.getAppId().equals(ret.getAppId()));
        } else {
            DevApplication devApplication =  DB.findById(DevApplication.class, argv.getAppId());
            String depends = devApplication.getDependDatasources() == null ? "" : devApplication.getDependDatasources();
            String[] arr = depends.split(",");
            Set<String> set = new HashSet<>(Arrays.asList(arr));
            // 公共数据源 appId is null or appId = ''
            retList.removeIf(ret -> !set.contains(ret.getId()));
            // [KPINE-1075] 依赖数据源 kingDB
            boolean hasKingDB = retList.stream().anyMatch(ret -> ret.getId().equalsIgnoreCase("kingDB"));
            if(!hasKingDB && set.contains("kingDB")){
                SysKdbDataSourceRet sourceRet = new SysKdbDataSourceRet();
                sourceRet.setId("kingDB");
                retList.add(sourceRet);
            }
        }
        // 排序
        retList.sort(Comparator.comparing(SysKdbDataSourceRet::getId));
        return PageUtil.memoryPage(argv, retList, SysKdbDataSourceRet.class);
    }

    @Override
    public void delete(MultiIdArgv argv) {
        KdbApi api = (KdbApi)(DB.getDefault());
        for (String id: argv.getIds()) {
           api.deleteDataSource(id);
        }
        // 提交git
        String repoId = argv.getAppId();
        if (StringUtils.isEmpty(repoId)) repoId = "public";
        String resourceId = StringUtils.joinToString(new ArrayList<>(argv.getIds()), ",");

        // ids转文件路径
        List<String> filePathList = new ArrayList<>();
        for (String id : argv.getIds()) {
            filePathList.add("data_sources/" + id + ".json");
        }

        AppGit appGit = new AppGit(repoId);
        GitCommit gitCommit = appGit.getCommit("删除数据源: " + resourceId);
        appGit.deleteCommitMultiFile(filePathList, gitCommit);

    }

    /**
     * 刷新基础数据源
     *
     * @return
     */
    @Override
    public void refreshBaseFlow() {
        DB.kdbApi().refreshBaseFlow();
    }

    @Override
    public void take(DataSourceTakeArgv argv) {
        SysKdbDataSourceRet data = get(argv.getSourceName());
        String sql = "update data_source set app_id = ?, json = ? where sourcename = ?";
        String appId = argv.getAppId();
        String json = updateDataSourceAppId(data.getJson(), argv.getAppId());
        DB.byName("kingDB").executeUpdateSql(sql, appId, json, argv.getSourceName());
    }

    @Override
    public void unTake(DataSourceTakeArgv argv) {
        SysKdbDataSourceRet data = get(argv.getSourceName());
        String sql = "update data_source set app_id = ?, json = ? where sourcename = ?";
        String json = updateDataSourceAppId(data.getJson(), null);
        DB.byName("kingDB").executeUpdateSql(sql, null, json, argv.getSourceName());
    }

}
