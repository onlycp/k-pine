package com.kingsware.kdev.sys.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingsware.kdev.core.auth.BaseUserInfo;
import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.LogStack;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.bean.SqlSegment;
import com.kingsware.kdev.core.cache.access.AccessManager;
import com.kingsware.kdev.core.cache.kcache.KCacheManager;
import com.kingsware.kdev.core.cache.license.LicenseManager;
import com.kingsware.kdev.core.cache.open.OpenAccount;
import com.kingsware.kdev.core.cache.page.PageCacheManager;
import com.kingsware.kdev.core.context.KClientContext;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.i18n.I18n;
import com.kingsware.kdev.core.kflow.bean.ErrorResult;
import com.kingsware.kdev.core.kflow.bean.KdbFlowResult;
import com.kingsware.kdev.core.mode.AppModeProperties;

import com.kingsware.kdev.core.model.DevPage;
import com.kingsware.kdev.core.model.SysApiMock;
import com.kingsware.kdev.core.model.SysCache;
import com.kingsware.kdev.core.model.SysFile;
import com.kingsware.kdev.core.model.SysInstance;
import com.kingsware.kdev.core.model.SysI18n;
import com.kingsware.kdev.core.model.SysLogicFlow;
import com.kingsware.kdev.core.model.SysLogicFlowMock;
import com.kingsware.kdev.core.model.SysLoginLog;
import com.kingsware.kdev.core.model.SysNoticeRecord;
import com.kingsware.kdev.core.model.SysOnlineUser;
import com.kingsware.kdev.core.model.SysOperateLog;
import com.kingsware.kdev.core.model.SysTask;
import com.kingsware.kdev.core.model.SysTaskDetail;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.DBChecker;
import com.kingsware.kdev.core.orm.SqlWrapper;
import com.kingsware.kdev.core.orm.expression.Op;
import com.kingsware.kdev.core.orm.kdb.*;
import com.kingsware.kdev.core.util.*;
import com.kingsware.kdev.sys.argv.*;
import com.kingsware.kdev.sys.bean.CopyProcessData;
import com.kingsware.kdev.sys.bean.ResourceFile;
import com.kingsware.kdev.sys.manager.CopyAppManager;
import com.kingsware.kdev.sys.model.*;
import com.kingsware.kdev.sys.ret.DevApplicationRet;
import com.kingsware.kdev.sys.service.DevApplicationService;
import com.kingsware.kdev.sys.service.SysFileService;

import aj.org.objectweb.asm.TypeReference;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 业务实现类
 *
 * @author AndyZheng
 * @version 1.0.0
 * @date 2022-02-13 10:20
 */
@Service
@Slf4j
public class DevApplicationServiceImpl extends BaseServiceImpl implements DevApplicationService {

    @Resource
    private SysFileService sysFileService;
    @Resource
    private AppModeProperties appModeProperties;

    @Value("${app.init-psi-path:.}")
    private String initPsiPath;

    @Override
    public DevApplicationRet get(String id) {
        // 查询model
        DevApplication model = DB.findById(DevApplication.class, id);
        // 转换成ret对象
        return (DevApplicationRet) model2Ret(model, DevApplicationRet.class);
    }

    @Override
    public void add(DevApplicationArgv argv) {
        DevApplication model = BeanUtils.copyObject(argv, DevApplication.class);
        // 唯一校验
        checkUnique(model);
        // 保存
        DB.save(model);
    }

    @Override
    public void edit(DevApplicationArgv argv) {
        DevApplication model = DB.findById(DevApplication.class, argv.getId());
        BeanUtils.copyProperties(argv, model);
        // 唯一校验
        checkUnique(model);
        // 保存
        DB.update(model);
    }

    private void checkUnique(DevApplication model) {
        // 唯一性校验
        DBChecker<DevApplication> checker = DBChecker.build(model, DevApplication.class);
        // 应用短英文名唯一
        checker.uni("shortName", I18n.t("DevApplication.shortName.unique", "应用短英文名必须唯一"));
        // 执行校验
        checker.checkUnique();
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageDataRet<DevApplicationRet> query(DevApplicationQueryArgv argv) {
        // 拼装sql
        SqlWrapper wrapper = new SqlWrapper("select * from dev_application da where deleted=0 ");

        if (StringUtils.isNotEmpty(argv.getKeywords())) {
            wrapper.setSql(wrapper.getSql() + " and (da.name like ? or da.short_name like ? or da.description like ?) ");
            wrapper.getParams().add("%" + argv.getKeywords() + "%");
            wrapper.getParams().add("%" + argv.getKeywords() + "%");
            wrapper.getParams().add("%" + argv.getKeywords() + "%");
        }
        if (argv.getEnableStatus() != null) {
            wrapper.addCondition("da.enable_status", Op.EQ, argv.getEnableStatus());
        }
        if (argv.getDevStatus() != null) {
            wrapper.addCondition("da.dev_status", Op.EQ, argv.getDevStatus());
        }
        if (argv.getVersion() != null) {
            wrapper.addCondition("da.version", Op.EQ, argv.getVersion());
        }
        if (argv.getWhoInCharge() != null) {
            wrapper.addCondition("da.who_in_charge", Op.EQ, argv.getWhoInCharge());
        }
        if (argv.getAppType() != null) {
            wrapper.addCondition("da.app_type", Op.EQ, argv.getAppType());
        }

        // 获取用户信息
        BaseUserInfo userInfo = KClientContext.getContext().getUserInfo();
        // 如果不是web登录或者不登录
        if (userInfo == null) {
            return null;
        }
        // 如果不是超级管理员
        if (!AccessManager.getInstance().isSupperAdmin(userInfo.getRoleIds())) {
            wrapper.appendSql(" and da.id in (select dta.app_id from dev_team_member dtm inner join dev_team_app dta on dta.team_id = dtm.team_id where dtm.user_id = ? and dta.team_id = ?) ", userInfo.getId(), argv.getTeamId());
        }

        wrapper.sortBy("da.when_created desc");

        return (PageDataRet<DevApplicationRet>) query(wrapper.getSql(), wrapper.getParams(), argv, DevApplication.class, DevApplicationRet.class);
    }

    @Override
    public void delete(MultiIdArgv argv) {
        for (String id : argv.getIds()) {
            this.gitRemoveDefine(id);
            DB.delete(DevApplication.class, id);
        }
    }

    private void gitRemoveDefine(String appId) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", appId);
        KdbFlowResult result = FaasInvoke.callFlow("b46a4875a5594c82a87d440ba01416d6", params);
        if (result.getData() instanceof ErrorResult) {
            throw BusinessException.serviceThrow(result.getExceptionStack());
        }
    }

    @Override
    @SuppressWarnings("all")
    public String importApp(String json) {
        return this.importApp(json, null);
    }


    @Override
    @SuppressWarnings("all")
    public String importApp(String json, String teamId) {
        PageCacheManager.getInstance().clear();
        String pineAppId = "064b3b44b85a45fe87fcce88d72b2519";
        String importEnable = SpringContext.getProperties("app.dev.import-enable", "true");
        boolean isDev = SpringContext.getBoolean("app.mode.dev", false);

        if ("false".equalsIgnoreCase(importEnable)) {
            throw BusinessException.serviceThrow(I18n.t("DevApplicationServiceImpl.pineNotAllowInstall", "当前平台禁止导入pine数据！"));
        }
        int subSize = 3500;
        DevPine devPine = appData2Pine(json);
        log.info("开始导入数据");
        // 是否覆盖开发平台系统数据
        boolean forceReplaceDev = devPine.isForceReplaceDev();
        // 处理应用信息
        long appCount = 0;
        Map<String, Long> importMessageMap = new HashMap<>();
        if (devPine.getInfo() != null && StringUtils.isNotEmpty(devPine.getInfo().getId())) {
            appCount = DB.saveOrUpdate(devPine.getInfo(), DevApplication.class);
            // 处理团队关联
            if (StringUtils.isNotEmpty(devPine.getInfo().getId()) && StringUtils.isNotEmpty(teamId)) {
                // 查找当前是否已入库
                long cnt = DB.findCount("select count(1) cnt from dev_team_app where app_id=? and team_id=?", devPine.getInfo().getId(), teamId);
                if (cnt == 0) {
                    DevTeamApp devTeamApp = new DevTeamApp();
                    devTeamApp.setAppId(devPine.getInfo().getId());
                    devTeamApp.setTeamType(0);
                    devTeamApp.setTeamId(teamId);
                    DB.save(devTeamApp);
                }
            }
        }
        log.info("完成导入应用信息：{}", appCount);
        List<DataSourceInfo> dataSourceInfos = DB.kdbApi().queryDataSource(new DataSourceQueryArgv());

        if(devPine.getSources() != null) {
            long sourceCount = 0;
            for (DataSourceInfo fileSource : devPine.getSources()) {
//          // 查看是否已存在
                try {
                    Optional<DataSourceInfo> optional = dataSourceInfos.stream().filter(it -> it.getSourceName().equals(fileSource.getSourceName())).findFirst();
                    log.info("数据源初始化新增: {}", fileSource);
                    if(!optional.isPresent()) {
                        try {
                            DB.kdbApi().addDataSource(fileSource);
                            sourceCount ++;
                        }
                        catch (Exception e) {
                            log.info("数据源接口新增失败，将直接插入数据库：%s", fileSource.getSourceName());
                            DB.byName("kingDB").executeUpdateSql("insert into DATA_SOURCE(SOURCENAME, DRIVERCLASS,JDBCURL,USERNAME,PASSWORD, SOURCEID,JSON) VALUES (?,?,?,?,?,?,?)"
                                    , fileSource.getSourceName(), fileSource.getDriverClass(), fileSource.getJdbcUrl(), fileSource.getUserName(), fileSource.getPassword(), fileSource.getSourceName(), fileSource.getJson());
                        }

                    }

                } catch (Exception e) {
                    log.info("error", e);
                }

            }
            importMessageMap.put(I18n.t("DevApplicationServiceImpl.datasource","数据源"), sourceCount);
        }



        // 处理页面
        List<DevPage> importPages = devPine.getPages();
        if (isDev && !forceReplaceDev && importPages != null && !importPages.isEmpty()){
           importPages = importPages.stream().filter(e -> e.getAppId() != null
                   && !"".equals(e.getAppId()) && !"0".equals(e.getAppId()) && !pineAppId.equals(e.getAppId()) )
                   .collect(Collectors.toList());
        }
        long pageCount = DB.batchSaveOrUpdate(importPages, DevPage.class);
        log.info("完成导入页面信息：{}", pageCount);
        importMessageMap.put(I18n.t("DevApplicationServiceImpl.page", "页面信息") , pageCount);
        // 接口
        List<SysApi> importApis = devPine.getApis();
        if(isDev && !forceReplaceDev && importApis != null && !importApis.isEmpty()){
            importApis = importApis.stream().filter(e -> e.getAppId() != null
                            && !"".equals(e.getAppId()) && !"0".equals(e.getAppId()) && !pineAppId.equals(e.getAppId()) )
                    .collect(Collectors.toList());
        }
        long apiCount = DB.batchSaveOrUpdate(importApis, SysApi.class);
        log.info("完成导入接口信息：{}", apiCount);
        importMessageMap.put(I18n.t("DevApplicationServiceImpl.api", "接口信息"), apiCount);
        // 字典分类
        List<SysDict> importDicts = devPine.getDict();
        if(isDev && !forceReplaceDev && importDicts != null && !importDicts.isEmpty()){
            importDicts = importDicts.stream().filter(e -> e.getAppId() != null
                            && !"".equals(e.getAppId()) && !"0".equals(e.getAppId()) && !pineAppId.equals(e.getAppId()) )
                    .collect(Collectors.toList());
        }
        long dictCount = DB.batchSaveOrUpdate(importDicts, SysDict.class);
        log.info("完成导入字典信息：{}", dictCount);
        importMessageMap.put(I18n.t("DevApplicationServiceImpl.dict",  "字典信息"), dictCount);
        // 字典项
        // 先删除已有字典项
        List<SysDictItem> myItems = DB.findList(SysDictItem.class, "select * from sys_dict_item");
        Map<String, SysDictItem> dictItemMap = new HashMap<>();
        for (SysDictItem item : myItems) {
            String appId = item.getAppId() != null ? item.getAppId() : "";
            dictItemMap.put(String.format("%s-%s-%s", appId, item.getCode(), item.getValue()), item);
        }
        if (devPine.getDictItems() != null && !devPine.getDictItems().isEmpty()) {
            devPine.getDictItems().removeIf(item -> dictItemMap.containsKey(String.format("%s-%s-%s",  item.getAppId() != null ? item.getAppId() : "", item.getCode(), item.getValue())));
            if(isDev && !forceReplaceDev){
                devPine.getDictItems().removeIf(e -> e.getAppId() == null
                                || "".equals(e.getAppId()) || "0".equals(e.getAppId()) || pineAppId.equals(e.getAppId()));
            }
            for (SysDictItem item : devPine.getDictItems()) {
                DB.delete(item);
            }
        }
        long dictItemCount = DB.batchSaveOrUpdate(devPine.getDictItems(), SysDictItem.class);
        log.info("完成导入字典项信息：{}", dictItemCount);
        importMessageMap.put(I18n.t("DevApplicationServiceImpl.dictItem", "字典项信息") , dictItemCount);
        // 任务调度
        // 处理java类任务, 如果已有，即移除他们
        devPine.getTasks().removeIf(task -> task.getTaskType() == 1);
        List<SysTask> importTasks = devPine.getTasks();
        if (isDev && !forceReplaceDev && importTasks != null && !importTasks.isEmpty()){
            importTasks = importTasks.stream().filter(e -> e.getAppId() != null
                            && !"".equals(e.getAppId()) && !"0".equals(e.getAppId()) && !pineAppId.equals(e.getAppId()) )
                    .collect(Collectors.toList());
        }
        long taskCount = DB.batchSaveOrUpdate(importTasks, SysTask.class);
        log.info("完成导入任务调度信息：{}", taskCount);
        importMessageMap.put(I18n.t("DevApplicationServiceImpl.task", "任务调度信息") , taskCount);
        // 系度配置
        List<SysConfig> importConfigs = devPine.getConfigs();
        if (isDev && !forceReplaceDev && importConfigs != null && !importConfigs.isEmpty()){
            importConfigs = importConfigs.stream().filter(e -> e.getAppId() != null
                            && !"".equals(e.getAppId()) && !"0".equals(e.getAppId()) && !pineAppId.equals(e.getAppId()) )
                    .collect(Collectors.toList());
        }
        long configCount = DB.batchSaveOrUpdate(importConfigs, SysConfig.class);
        log.info("完成导入系统配置：{}", configCount);
        importMessageMap.put(I18n.t("DevApplicationServiceImpl.config", "系统配置") , configCount);
        // 国际化
//        long i18nCount = DB.batchSaveOrUpdate(devPine.getI18ns(), SysI18n.class);
//        log.info("完成导入国际化信息：{}", i18nCount);
//        importMessageMap.put(I18n.t("DevApplicationServiceImpl.i18n", "国际化信息") , i18nCount);
        List<SysI18n> importI18ns = devPine.getI18ns();
        if (isDev && !forceReplaceDev && importI18ns != null && !importI18ns.isEmpty()){
            importI18ns = importI18ns.stream().filter(e -> e.getAppId() != null
                            && !"".equals(e.getAppId()) && !"0".equals(e.getAppId()) && !pineAppId.equals(e.getAppId()) )
                    .collect(Collectors.toList());
        }
        long i18nCount = DB.batchSaveOrUpdate(importI18ns, SysI18n.class);
        log.info("完成导入国际化信息：{}", i18nCount);
        importMessageMap.put(I18n.t("DevApplicationServiceImpl.i18n", "国际化信息") , i18nCount);
        // 开放账号
        List<OpenAccount> openAccounts = devPine.getOpenAccounts();
        if (openAccounts != null && !openAccounts.isEmpty()) {
            openAccounts = openAccounts.stream()
                    .collect(Collectors.collectingAndThen(
                            Collectors.groupingBy(OpenAccount::getId),  // 按id分组
                            map -> map.values().stream()
                                    .map(group -> group.get(0))  // 取每组第一个元素
                                    .collect(Collectors.toList())
                    ));
            if (isDev && !forceReplaceDev){
                openAccounts = openAccounts.stream().filter(e -> e.getAppId() != null
                                && !"".equals(e.getAppId()) && !"0".equals(e.getAppId()) && !pineAppId.equals(e.getAppId()) )
                        .collect(Collectors.toList());
            }
            long openAccountCount = DB.batchSaveOrUpdate(openAccounts, OpenAccount.class);
            log.info("完成导入开放账号：{}", openAccountCount);
            importMessageMap.put(I18n.t("DevApplicationServiceImpl.openAccount", "开放账号") , openAccountCount);
        }
        // 开放账号权限
        List<OpenAccountApi> openAccountApis = devPine.getOpenAccountApis();
        // 去除重复数据
        if (openAccountApis != null && !openAccountApis.isEmpty()) {
            openAccountApis = openAccountApis.stream()
                    .collect(Collectors.collectingAndThen(
                            Collectors.groupingBy(OpenAccountApi::getId),  // 按id分组
                            map -> map.values().stream()
                                    .map(group -> group.get(0))  // 取每组第一个元素
                                    .collect(Collectors.toList())
                    ));
            if (isDev && !forceReplaceDev){
                // 因为 OpenAccountApi 不包含 appId，需要查库
                List<String> systemOpenAccountIds = DB.findSingleAttributeList(String.class, "select id from open_account where app_id = ?", pineAppId);
                openAccountApis = openAccountApis.stream().filter(e -> !systemOpenAccountIds.contains(e.getAccountId()))
                        .collect(Collectors.toList());
            }
            long openAccountApiCount = DB.batchSaveOrUpdate(openAccountApis, OpenAccountApi.class);
            log.info("完成导入开放权限：{}", openAccountApiCount);
            importMessageMap.put(I18n.t("DevApplicationServiceImpl.openAccountApi", "开放权限") , openAccountApiCount);
        }
        // 菜单
        long menuCount = 0;
        List<SysMenu> importMenus = devPine.getMenus();
        if (isDev && !forceReplaceDev && importMenus != null && !importMenus.isEmpty()){
            importMenus = importMenus.stream().filter(e -> e.getAppId() != null
                            && !"".equals(e.getAppId()) && !"0".equals(e.getAppId()) && !pineAppId.equals(e.getAppId()) )
                    .collect(Collectors.toList());
        }

        if (LicenseManager.getInstance().isUniopsApp()) {
            List<SysMenu> menus = importMenus.stream().filter(it -> StringUtils.isNotEmpty(it.getAppId()) && !it.getAppId().equals(pineAppId)).collect(Collectors.toList());
            menuCount = DB.batchSaveOrUpdate(menus, SysMenu.class);

        }
        else {
            menuCount = DB.batchSaveOrUpdate(importMenus, SysMenu.class);
        }
        log.info("完成导入菜单：{}", menuCount);
        importMessageMap.put(I18n.t("DevApplicationServiceImpl.menu", "菜单") , menuCount);
        // 开发平台角色
        long devRoleCount = 0;
        if (appModeProperties.getDev() && devPine.getDevRoles() != null && !devPine.getDevRoles().isEmpty()) {
            List<SysRole> devRoles = devPine.getDevRoles();
            if (isDev && !forceReplaceDev) {
                devRoles = devRoles.stream().filter(e -> e.getAppId() != null
                                && !"".equals(e.getAppId()) && !"0".equals(e.getAppId()) && !pineAppId.equals(e.getAppId()) )
                        .collect(Collectors.toList());
            }
            devRoleCount = DB.batchSaveOrUpdate(devRoles, SysRole.class);
            log.info("完成导入开发平台角色：{}", devRoleCount);
            importMessageMap.put(I18n.t("DevApplicationServiceImpl.devRole", "开发平台角色") , devRoleCount);
        }

        // 开发平台角色菜单
        long devRoleMenuCount = 0;

        if (appModeProperties.getDev() && devPine.getDevRoleMenus() != null && !devPine.getDevRoleMenus().isEmpty()) {
            List<SysRoleMenu> devRoleMenus = devPine.getDevRoleMenus();
            if (isDev && !forceReplaceDev) {
                devRoleMenus = devRoleMenus.stream().filter(e -> e.getAppId() != null
                                && !"".equals(e.getAppId()) && !"0".equals(e.getAppId()) && !pineAppId.equals(e.getAppId()) )
                        .collect(Collectors.toList());
            }
            devRoleMenuCount = DB.batchSaveOrUpdate(devRoleMenus, SysRoleMenu.class);
            log.info("完成导入开发平台角色菜单：{}", devRoleMenuCount);
            importMessageMap.put(I18n.t("DevApplicationServiceImpl.roleMenu", "开发平台角色菜单"), devRoleMenuCount);
        }

        // pine逻辑
        if (devPine.getLogicFlows() != null && !devPine.getLogicFlows().isEmpty()){
            List<SysLogicFlow> currentFlows = DB.findList(SysLogicFlow.class, "select id, i18n_keys from sys_logic_flow");
            Map<String, String> currentI18nMap = new HashMap<>();
            for (SysLogicFlow flow: currentFlows) {
                if (StringUtils.isNotEmpty(flow.getI18nKeys())) {
                    currentI18nMap.put(flow.getId(), flow.getI18nKeys());
                }
            }
            for (SysLogicFlow flow : devPine.getLogicFlows()) {
                if (currentI18nMap.containsKey(flow.getId()) && StringUtils.isEmpty(flow.getI18nKeys())) {
                    flow.setI18nKeys(currentI18nMap.get(flow.getId()));
                }
            }
            List<SysLogicFlow> importLogicFlows = devPine.getLogicFlows();
            if (isDev && !forceReplaceDev && importLogicFlows != null && !importLogicFlows.isEmpty()) {
                importLogicFlows = importLogicFlows.stream().filter(e -> e.getApplicationId() != null
                                && !"".equals(e.getApplicationId()) && !"0".equals(e.getApplicationId()) && !pineAppId.equals(e.getApplicationId()) )
                        .collect(Collectors.toList());
            }
            long pineFlowCount = DB.batchSaveOrUpdate(importLogicFlows, SysLogicFlow.class);
            log.info("完成导入pine逻辑：{}", pineFlowCount);
            importMessageMap.put(I18n.t("DevApplicationServiceImpl.logic", "逻辑编排"), pineFlowCount);
        }

        try {
            // faas逻辑
            if (devPine.getKdbFlows() != null && !devPine.getKdbFlows().isEmpty()) {
                // 查询哪些 flowid 是开发平台的
                List<String> systemFlowIds = DB.findSingleAttributeList(String.class, "select flow_id from sys_logic_flow where application_id = ?", pineAppId);

                for (FlowInfo flowInfo : devPine.getKdbFlows()) {
                    if (flowInfo.getFlowId().equalsIgnoreCase("base_flow")) {
                        continue;
                    }
                    KdbFlowQueryArgv kdbFlowQueryArgv = new KdbFlowQueryArgv();
                    kdbFlowQueryArgv.setFlowId(flowInfo.getFlowId());
                    List<FlowInfo> functionInfoList = DB.kdbApi().query(kdbFlowQueryArgv);

                    if (isDev && !forceReplaceDev) {
                        // 查询当前流程是否属于开发平台的
                        if (systemFlowIds.contains(flowInfo.getFlowId())) {
                            continue;
                        }
                    }

                    // 如果没有，则新增
                    if (functionInfoList.isEmpty()) {
                        try {
                            String sql = "insert into flow (flowid,name,content,description) values (?,?,?,?)";
                            DB.byName("kingDB").executeUpdateSql(sql, flowInfo.getFlowId(), flowInfo.getName(), flowInfo.getContent(), flowInfo.getDescription());
                        } catch (Exception e) {
                        }
                    } else {
                        EditFlowInfo editFlowInfo = new EditFlowInfo();
                        editFlowInfo.setFlowId(flowInfo.getFlowId());
                        // 若KDB数据库的flowId没有内容则跳过。解决KDB的FLOWID数据为空的问题
                        if (flowInfo.getContent() == null || flowInfo.getContent().isEmpty()) {
                            continue;
                        }
                        editFlowInfo.setContent(flowInfo.getContent());
                        editFlowInfo.setName(flowInfo.getName());
                        editFlowInfo.setDescription(flowInfo.getDescription());
                        log.info("更新FAAS逻辑编排完整信息:{}", editFlowInfo.toString());
                        DB.kdbApi().editFlow(editFlowInfo);
                        log.info("更新FAAS逻辑编排:{}", editFlowInfo.getName());
                    }

                }
                // 刷新
            }
        } catch (Exception e) {
            log.warn("fass逻辑flow内容更新异常：" + e.getMessage());
        }
        // faas函数
        if (devPine.getFunctions() != null && !devPine.getFunctions().isEmpty()) {
            boolean enableImportFunction = SpringContext.getBoolean("app.import-function", true);
            if (enableImportFunction) {
                List<Functions> importFunctions = devPine.getFunctions();
                if (isDev && !forceReplaceDev) {
                    importFunctions = importFunctions.stream().filter(e -> e.getAppId() != null
                                    && !"".equals(e.getAppId()) && !"0".equals(e.getAppId()) && !pineAppId.equals(e.getAppId()) )
                            .collect(Collectors.toList());
                }

                for (Functions functions : importFunctions) {

                    FunctionQueryArgv functionQueryArgv = new FunctionQueryArgv();
                    functionQueryArgv.setId(functions.getId());
                    List<Functions> functionInfoList = DB.kdbApi().queryFunction(functionQueryArgv);
                    // 如果没有，则新增，后面之所以再次编辑，就是为了实时生效
                    if (functionInfoList.isEmpty()) {
                        try {
                            String sql = "insert into functions (id,name,type,desc,script) values (?,?,?,?,?)";
                            DB.byName("kingDB").executeUpdateSql(sql, functions.getId(), functions.getName(), functions.getType(), functions.getDesc(), functions.getScript());

                        } catch (Exception e) {

                        }
                    }
                    EditFunctionInfo editFunctionInfo = new EditFunctionInfo();
                    editFunctionInfo.setId(functions.getId());
                    editFunctionInfo.setName(functions.getName());
                    editFunctionInfo.setDesc(functions.getDesc());
                    editFunctionInfo.setScript(functions.getScript());
                    editFunctionInfo.setType(functions.getType());
                    DB.kdbApi().editFun(editFunctionInfo);
                }
                importMessageMap.put(I18n.t("DevApplicationServiceImpl.function", "函数库") , (long)devPine.getFunctions().size());
            }

        }
        try {
            if (appModeProperties.getDev() && !LicenseManager.getInstance().isUniopsApp()) {
                // 插入FAAS扩展节点类型
                if (devPine.getDevFaasNodeTypes() != null && !devPine.getDevFaasNodeTypes().isEmpty()) {
                    if (forceReplaceDev){
                        long tCount = DB.batchSaveOrUpdate(devPine.getDevFaasNodeTypes(), DevFaasNodeType.class);
                        log.info("完成导入FAAS扩展节点类型：{}", tCount);
                        importMessageMap.put(I18n.t("DevApplicationServiceImpl.faasExtNodeType", "FAAS扩展节点类型"), tCount);
                    }
                }
                // 插入FAAS扩展节点
                if (devPine.getDevFaasNodes() != null && !devPine.getDevFaasNodes().isEmpty()) {
                    if (forceReplaceDev){
                        long tCount = DB.batchSaveOrUpdate(devPine.getDevFaasNodes(), DevFaasNode.class);
                        log.info("完成导入FAAS扩展节点：{}", tCount);
                        importMessageMap.put(I18n.t("DevApplicationServiceImpl.faasExtNode", "FAAS扩展节点") , tCount);
                    }
                }
                // 能力关联
                if (devPine.getPowerLinks() != null && !devPine.getPowerLinks().isEmpty()) {
                    if (forceReplaceDev){
                        long tCount = DB.batchSaveOrUpdate(devPine.getPowerLinks(), DevPowerLink.class);
                        log.info("完成导入能力关联：{}", tCount);
                        importMessageMap.put(I18n.t("DevApplicationServiceImpl.power", "能力关联"), tCount);
                    }
                }
                // 能力树
                if (devPine.getDevPowerTrees() != null && !devPine.getDevPowerTrees().isEmpty()) {
                    if (forceReplaceDev) {
                        long tCount = DB.batchSaveOrUpdate(devPine.getDevPowerTrees(), DevPowerTree.class);
                        log.info("完成导入能力树：{}", tCount);
                        importMessageMap.put(I18n.t("DevApplicationServiceImpl.powerTree", "能力树"), tCount);
                    }
                }
                // 插件树
                if (devPine.getExtPluginTrees() != null && !devPine.getExtPluginTrees().isEmpty()) {
                    if (forceReplaceDev) {
                        long tCount = DB.batchSaveOrUpdate(devPine.getExtPluginTrees(), ExtPluginTree.class);
                        log.info("完成导入插件树：{}", tCount);
                        importMessageMap.put(I18n.t("DevApplicationServiceImpl.pluginTree", "插件树") , tCount);
                    }
                }
                // 插件接口
                if (devPine.getExtPluginInterfaces() != null && !devPine.getExtPluginInterfaces().isEmpty()) {
                    if (forceReplaceDev) {
                        long tCount = DB.batchSaveOrUpdate(devPine.getExtPluginInterfaces(), ExtPluginInterface.class);
                        log.info("完成导入插件接口：{}", tCount);
                        importMessageMap.put(I18n.t("DevApplicationServiceImpl.pluginApi", "插件接口"), tCount);
                    }
                }
                // 编辑编排模板
                if (devPine.getSysLogicTemplates() != null && !devPine.getSysLogicTemplates().isEmpty()) {
                    List<SysLogicTemplate> importSysLogicTemplates = devPine.getSysLogicTemplates();
                    if (!forceReplaceDev){
                        importSysLogicTemplates = importSysLogicTemplates.stream().filter(e -> e.getAppId() != null
                                        && !"".equals(e.getAppId()) && !"0".equals(e.getAppId()) && !pineAppId.equals(e.getAppId()) )
                                .collect(Collectors.toList());
                    }
                    long tCount = DB.batchSaveOrUpdate(importSysLogicTemplates, SysLogicTemplate.class);
                    log.info("完成导入编辑编排模板：{}", tCount);
                    importMessageMap.put(I18n.t("DevApplicationServiceImpl.logicTemplate", "编辑编排模板"),  tCount);
                }
                // 页面模板
                if (devPine.getDevPageTemplates() != null && !devPine.getDevPageTemplates().isEmpty()) {
                    List<DevPageTemplate> importDevPageTemplates = devPine.getDevPageTemplates();
                    if (!forceReplaceDev){
                        importDevPageTemplates = importDevPageTemplates.stream().filter(e -> e.getAppId() != null
                                        && !"".equals(e.getAppId()) && !"0".equals(e.getAppId()) && !pineAppId.equals(e.getAppId()) )
                                .collect(Collectors.toList());
                    }
                    long tCount = DB.batchSaveOrUpdate(importDevPageTemplates, DevPageTemplate.class);
                    log.info("完成导入页面模板：{}", tCount);
                    importMessageMap.put(I18n.t("DevApplicationServiceImpl.pageTemplate", "页面模板") , tCount);
                }
            }

            List<Method> list = getDevPineTableDataMethods(devPine);
            for (Method method : list) {
                try {
                    // 数据不为空才继续处理
                    if (method.invoke(devPine) == null) {
                        continue;
                    }
                    String typeName = method.getGenericReturnType().toString();
                    String regex = "List<(.+?)>";
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(typeName);
                    if (matcher.find()) {
                        String devModuleType = matcher.group(1);
                        long importCount = DB.batchSaveOrUpdate((List)method.invoke(devPine), Class.forName(devModuleType));
                        String varName = method.getName().substring(3, 4).toLowerCase() + method.getName().substring(4);
                        log.info("完成导入{}：{}", varName, importCount);
                        importMessageMap.put(varName, importCount);
                    }
                } catch (Exception e) {
                    log.error(String.format("导入：{}失败",method.getName()), e);
                } finally {
                    continue;
                }
            }


        } catch (Exception e) {
            log.warn("导入时发生非关键异常(可忽略)，不影响应用使用：" + e.getMessage());
        }
        finally {
            if (devPine.getKdbFlows() != null && !devPine.getKdbFlows().isEmpty()) {
                try {
                    DB.byName("kingDB").executeUpdateSql("CHECKPOINT");
                    log.info("FAAS CHECKPOINT SUCCESS");
                }
                catch (Exception e) {
                    log.warn("CHECKPOINT失败：" + e.getMessage());
                }
            }
        }
        // uniops处理
        try {
            if (LicenseManager.getInstance().isUniopsApp()) {
                Object bean = SpringContext.getBean("uniOpsServiceImpl");
                Method method = ReflectionUtils.findMethod(bean.getClass(), "publishMenu", String.class);
                ReflectionUtils.invokeMethod(method, bean, json);
            }
        } catch (Exception e) {
            log.warn("uniops导入时发生非关键异常(可忽略)，不影响应用使用：" + e.getMessage());
        }

        // 删除原来的导入汇总信息，因为原来的存在NullPointException问题，且不兼容开发平台的数据量显示，改为用map
        String result = String.format(I18n.t("DevApplicationServiceImpl.app", "导入应用数") + ":%d", appCount);
        for(Map.Entry entry : importMessageMap.entrySet()) {
            result += (", " + entry.getKey() + ": " + entry.getValue());
        }
        // 清理缓存
        KCacheManager.getInstance().clear();
        log.info(result);
        return result;

    }

    @Override
    public String importResources(List<ResourceFile> resourceFileList) {
        return "";
    }

//    public static void main(String[] args) {
//        DevPine devPine = new DevPine();
//        List<DevModule> moduleList = new ArrayList();
//        DevModule devModule = new DevModule();
//        devModule.setId("x");
//        devModule.setName("xx");
//        moduleList.add(devModule);
//        devPine.setDevModule(moduleList);
//        List<Method> list = getDevPineTableDataMethods(devPine);
//        for (Method method : list) {
//            try {
////                System.out.println(method.getName());
////                System.out.println(method.invoke(devPine));
//                String typeName = method.getGenericReturnType().toString();
////                System.out.println(typeName);
//                String regex = "List<(.+?)>";
//                Pattern pattern = Pattern.compile(regex);
//                Matcher matcher = pattern.matcher(typeName);
//                if (matcher.find()) {
//                    String devModuleType = matcher.group(1);
////                    System.out.println("DevModule Type: " + devModuleType);
//                    DB.batchSaveOrUpdate((List)method.invoke(devPine), Class.forName(devModuleType).getClass());
//                }
//
//            } catch (IllegalAccessException e) {
//                throw new RuntimeException(e);
//            } catch (InvocationTargetException e) {
//                throw new RuntimeException(e);
//            } catch (ClassNotFoundException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }

    private List<Method> getDevPineTableDataMethods(DevPine devPine) {
        Class<?> devPineClazz = DevPine.class;
        List<Method> resultMethods = new ArrayList();
        Method[] methods = devPineClazz.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().startsWith("get") && method.getParameterCount() == 0
                    && !isSystemImportVars(method.getName())) {
                resultMethods.add(method);
            }
        }
        return resultMethods;
    }

    private boolean isSystemImportVars(String name) {
        for (String sysVarName : DevPine.systemImportVars) {
            String sysMethodName = "get" + sysVarName.substring(0, 1).toUpperCase() + sysVarName.substring(1);
            if (sysMethodName.equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 安装应用
     *
     * @param argv
     */
    @Override
    public Map<String, Object> install(DevAppInstallArgv argv) {
        LogStack logStack = new LogStack();
        try {
            PageCacheManager.getInstance().clear();
            String appid = argv.getAppId();
            if (StringUtils.isEmpty(appid)) {
                appid = "064b3b44b85a45fe87fcce88d72b2519";
                argv.setAppId(appid);
            }
            DevApplication application = DB.findById(DevApplication.class, argv.getAppId());
            logStack.addMessage(I18n.t("DevApplicationServiceImpl.startTip", "启动...") );
            String backupName = String.format("%s_%s", StringUtils.isEmpty(application.getShortName())? "untitle": application.getShortName(), (application.getVersion() == null? "v1": application.getVersion()));
            // 在线升级
            if (argv.getMode() == 1) {
                // 获取通道信息
                DevOtaChannel channel = DB.findById(DevOtaChannel.class, argv.getChannelId());
                // 通道类型
                // 最新代码
                if (argv.getChannel() == 2) {
                    String apiUrl = channel.getChannelUrl() + "/api/v1/pine/app/update-to-date";
                    Map<String, Object> body = new HashMap<>();
                    body.put("accessId", channel.getAuthToken());
                    body.put("timestamp", System.currentTimeMillis());
                    body.put("signNonce", StringUtils.getUUID());
                    body.put("appId", argv.getAppId());
                    body.put("downloadSys", argv.getWithSysData());
                    // 获取签名值
                    String sign = SignUtil.getSign(body, channel.getSignSecret());
                    body.put("sign", sign);
                    logStack.addMessage(I18n.t("DevApplicationServiceImpl.readyReqData", "准备请求远程数据, URL：" )+ apiUrl);
                    String responseBody = HttpUtil.postBody(apiUrl, JsonUtil.toJson(body), new HashMap<>(), true);
                    logStack.addMessage(I18n.t("DevApplicationServiceImpl.completeReqData", "完成请求远程数据，准备安装应用."));
                    String result = importApp(responseBody, argv.getTeamId());
                    this.backupPine(responseBody, backupName);
                    logStack.addMessage(I18n.t("DevApplicationServiceImpl.installComplete", "应用安装完成：") + result);

                }
            }
            // 本地升级,支持多个文件
            else {
                // 先将文件下载下来
                String[] ids = argv.getLocalFileIds().split(",");
                for (String fileId : ids) {
                    SysFile sysFile = DB.findById(SysFile.class, fileId);
                    // 判断 pinezip 压缩包还是普通 pine 文件
                    String fileExt = sysFile.getFileExt();
                    if ("pinezip".equalsIgnoreCase(fileExt)) {
                        // pinezip 安装
                        /**
                         * 1. 解压 pinezip
                         * 2. 依次：安装pine、拷贝fass文件&插件、拷贝api文件、执行DDL、导入表数据
                         */
                        File file = null;
                        if (sysFile.getSaveType() == 2) {
                            file = sysFileService.getFaasFile(sysFile.getFilePath());
                        } else {
                            file = new File(SpringContext.getProperties("file.base-path", "/") + sysFile.getFilePath());
                        }
                        if (file == null || !file.exists()) {
                            throw new RuntimeException(I18n.t("DevApplicationServiceImpl.fileNotFound", "安装文件不存在"));
                        }
                        // 解压文件
                        logStack.addMessage(I18n.t("DevApplicationServiceImpl.startInstall", "开始安装pinezip:") + sysFile.getFileOriginalName());
                        logStack.addMessage(I18n.t("DevApplicationServiceImpl.startUnzip", "开始解压pinezip:") + sysFile.getFileOriginalName());
                        String unzipPath = SpringContext.getProperties("file.base-path", "/") + "temp/" + StringUtils.getUUID();
                        File unzipFile = new File(unzipPath);
                        if (!unzipFile.exists()) {
                            unzipFile.mkdirs();
                        }
                        ZipUtils.unzip(unzipPath, file.getPath(), "UTF8");
                        logStack.addMessage(I18n.t("DevApplicationServiceImpl.finishUnzip", "完成解压pinezip:") + sysFile.getFileOriginalName());
                        // 安装pine
                        logStack.addMessage(I18n.t("DevApplicationServiceImpl.beginPines", "开始导入.pine文件") + sysFile.getFileOriginalName());
                        File pines = new File(unzipPath + "/pines");
                        File[] appFiles = pines.listFiles();
                        if(pines.exists() && appFiles != null && appFiles.length > 0){
                            for (File pine : appFiles) {
                                // 排除 MacOS 文件
                                if (!pine.getName().toLowerCase().endsWith(".pine")){
                                    continue;
                                }
                                String json = FileUtils.readFile(pine);
                                importApp(json, argv.getTeamId());
                            }
                        }
                        logStack.addMessage(I18n.t("DevApplicationServiceImpl.finishPines", "完成导入.pine文件") + sysFile.getFileOriginalName());
                        // 拷贝res文件
                        logStack.addMessage(I18n.t("DevApplicationServiceImpl.beginRes", "开始导入静态资源文件:") + sysFile.getFileOriginalName());
                        File pineFiles = new File(unzipPath + "/pineFiles");
                        copyFileToPine(pineFiles, pineFiles.getPath());
                        logStack.addMessage(I18n.t("DevApplicationServiceImpl.finishRes", "完成导入静态资源文件:") + sysFile.getFileOriginalName());
                        // 拷贝faas文件
                        logStack.addMessage(I18n.t("DevApplicationServiceImpl.beginRes", "开始导入faas服务器文件:") + sysFile.getFileOriginalName());
                        File faasFiles = new File(unzipPath + "/faasFiles");
                        copyFileToFaas(faasFiles, faasFiles.getPath());
                        logStack.addMessage(I18n.t("DevApplicationServiceImpl.finishRes", "完成导入faas服务器文件:") + sysFile.getFileOriginalName());
                        // 导入sysFile表数据
                        File sysFileFiles = new File(unzipPath + "/sysFile");
                        File[] jsonFiles = sysFileFiles.listFiles();
                        if(sysFileFiles.exists() && jsonFiles!= null && jsonFiles.length > 0){
                            for (File jsonFile : jsonFiles) {
                                if (jsonFile.isDirectory() || jsonFile.getName().equalsIgnoreCase(".DS_Store")) {
                                    continue;
                                }
                                String content = FileUtils.readFile(jsonFile);
                                importSysFileData(content);
                            }
                        }
                        // 执行DDL + 导入数据
                        logStack.addMessage(I18n.t("DevApplicationServiceImpl.beginDDL", "开始导入DDL和表数据:") + sysFile.getFileOriginalName());
                        File ddlFiles = new File(unzipPath + "/sqls");
                        File[] ddlFile = ddlFiles.listFiles(e -> e.isDirectory() && !e.getName().equalsIgnoreCase("__MACOSX"));
                        if(ddlFiles.exists() && ddlFile!= null && ddlFile.length > 0){
                            for (File ddl : ddlFile) {
                                if (ddl.isDirectory()) {
                                    importDDL(ddl.getName(), ddl.getPath());
                                }
                            }
                        }
                        logStack.addMessage(I18n.t("DevApplicationServiceImpl.finishDDL", "完成导入DDL和表数据:") + sysFile.getFileOriginalName());
                        logStack.addMessage(I18n.t("DevApplicationServiceImpl.completeInstall", "完成安装pinezip：") + sysFile.getFileOriginalName());
                        // 删除临时文件
                        FileUtils.deleteFileOrDirectory(unzipPath);
                    } else {
                        // pine 安装
                        String json = "";
                        if(sysFile.getSaveType() == 2) {
                            File file = sysFileService.getFaasFile(sysFile.getFilePath());
                            json = FileUtils.readFile(file);
                            file.delete();
                        } else {
                            json = FileUtils.readFile(new File(SpringContext.getProperties("file.base-path", "/") + sysFile.getFilePath()));
                        }
                        logStack.addMessage(I18n.t("DevApplicationServiceImpl.startInstall", "开始安装应用:") + sysFile.getFileOriginalName());
                        String result = importApp(json, argv.getTeamId());
                        this.backupPine(json, backupName);
                        logStack.addMessage(I18n.t("DevApplicationServiceImpl.completeInstall", "应用安装完成：")  + result);
                    }
                }
            }
        } catch (Exception e) {
            logStack.addMessage(ExceptionUtils.getStackTrace(e));
        }
        return logStack.formatMessages();
    }


    /**
     * 更新DDL+数据（pinezip导入）
     */
    public void importDDL(String dsName, String path) throws Exception {
        File[] listFiles = new File(path).listFiles(pathname -> 
            pathname.isFile() && pathname.getName().toLowerCase().endsWith(".sql")
        );
        // 是否允许危险sql执行
        boolean dangerExec = SpringContext.getBoolean("pinezip.danger.sql.import", false);
        // 更新 DDL
        if (listFiles != null && listFiles.length > 0) {
            for (File file : listFiles) {
                List<String> cnt = FileUtils.readAllLine(new FileInputStream(file));
                List<SqlSegment> sqlSegments = SqlUtils.parseSql(cnt);
                for (SqlSegment sql : sqlSegments) {
                    String curSql = sql.getSql();
                    if (DB.byName(dsName).getConfig().getInnerType().equalsIgnoreCase("Oracle") && curSql.endsWith(";")) {
                        curSql = curSql.substring(0, curSql.length() - 1);
                    }
                    // 如果是 delete or drop 语句，执行判断
                    if (curSql.toLowerCase().trim().startsWith("delete") || curSql.toLowerCase().trim().startsWith("drop")) {
                        if(!dangerExec){
                            continue;
                        }
                    }
                    // 这里需要 catch 异常，否则上面的异常了，下面的sql 无法继续执行
                    try {
                        SqlUtils.executeSql(dsName, curSql);
                    }catch (Exception ignored) {
                        ignored.printStackTrace();
                    }
                }
            }
        }
        // 更新数据
        File[] dataFiles = new File(path + "/data").listFiles(pathname -> 
            pathname.isFile() && pathname.getName().toLowerCase().endsWith(".json")
        );
        ObjectMapper objectMapper = new ObjectMapper();
        if (dataFiles!= null && dataFiles.length > 0) {
            for (File file : dataFiles) {
                String shardName = file.getName().split("\\.")[0];
                String tableName = shardName.substring(0, shardName.lastIndexOf("_"));
                String json = FileUtils.readFile(file);
                if (json.isEmpty() || "null".equals(json) || "[]".equals(json)) {
                    continue;
                }
                List<Map<String, Object>> datas = objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, Map.class));
                if (datas!= null &&!datas.isEmpty()) {
                    for (Map<String, Object> data : datas) {
                        String prefix = "insert into " + tableName + " (";
                        String suffix = ") values (";
                        List<Object> params = new ArrayList<>();
                        for (Map.Entry entry : data.entrySet()) {
                            prefix += entry.getKey() + ",";
                            suffix += "?,";
                            params.add(entry.getValue());
                        }
                        prefix = prefix.substring(0, prefix.length() - 1);
                        suffix = suffix.substring(0, suffix.length() - 1);
                        suffix += ")";
                        String sql = prefix + suffix;
                        try {
                            DB.byName(dsName).executeUpdateSql(sql, params.toArray());
                        } catch (Exception e) {
                        }
                    }
                }
            }
        }
    }


    /**
     * 导入 sys_file 表数据（pinezip导入）
     */
    public void importSysFileData(String json) {
        List<SysFile> sysFiles = JsonUtil.toListBeanFixTimestamp(json, SysFile.class);
        if (sysFiles != null &&!sysFiles.isEmpty()) {
            for (SysFile sysFile : sysFiles) {
                SysFile existFile = DB.findById(SysFile.class, sysFile.getId());
                if (existFile == null) {
                    DB.save(sysFile);
                }
            }    
        }
    }

    /**
     * 拷贝pine文件（pinezip导入）
     */
    public void copyFileToPine(File source, String prefix) throws Exception {
        if (!source.exists() || source.getName().equalsIgnoreCase(".DS_Store")){
            return;
        }
        if(source.isDirectory()){
            if (source.getName().equalsIgnoreCase("__MACOSX")){
                return;
            }
            File[] listFiles = source.listFiles();
            for (File file : listFiles) {
                copyFileToPine(file, prefix);
            }
        } else {
            String sourcePath = source.getPath();
            String prefixPath = new File(prefix).getPath();
            String copyPath = sourcePath.substring(prefixPath.length() + 1);
            File copyFile = new File(copyPath);
            if (!copyFile.exists()) {
                copyFile.getParentFile().mkdirs();
                Files.copy(Paths.get(sourcePath), copyFile.toPath());
            }
        }
    }

    /**
     * 拷贝faas文件（pinezip导入）
     */
    public void copyFileToFaas(File source, String prefix) throws Exception {
        if (!source.exists() || source.getName().equalsIgnoreCase(".DS_Store")){
            return;
        }
        if(source.isDirectory()){
            if (source.getName().equalsIgnoreCase("__MACOSX")){
                return;
            }
            File[] listFiles = source.listFiles();
            for (File file : listFiles) {
                copyFileToFaas(file, prefix);
            }
        } else {
            String sourcePath = source.getPath();
            String prefixPath = new File(prefix).getPath();
            String copyPath = sourcePath.substring(prefixPath.length() + 1);
            File copyFile = new File(copyPath);
            String name = copyFile.getName();
            String path = copyFile.getParent();
            path = path.replaceAll("\\\\", "/");
            DB.kdbApi().uploadFile(new FileInputStream(sourcePath), name, path);
        }
    }

    /**
     * json字符串转pine
     *
     * @param appData json
     * @return pine
     */
    @Override
    @SuppressWarnings("all")
    public DevPine appData2Pine(String appData) {

        Map<String, Object> pineMap = JsonUtil.toMap(appData);
        // 处理特殊的字段
        // 应用处理
        if (pineMap.containsKey("info")) {
            Map<String, Object> map = (Map<String, Object>) pineMap.get("info");
            MapUtil.transMapBooleanToInt(map, "devStatus", "enableStatus");
            MapUtil.transMapDateToString(map, "whenCreated", "whenModified");
        }
        if (pineMap.containsKey("pages")) {
            List<Map<String, Object>> list = (List<Map<String, Object>>) pineMap.get("pages");
            for (Map<String, Object> map : list) {
                MapUtil.transMapBooleanToInt(map, "devStatus", "enableStatus", "loginRequired");
                MapUtil.transMapDateToString(map, "whenCreated", "whenModified");
            }

        }
        if (pineMap.containsKey("menus")) {
            List<Map<String, Object>> list = (List<Map<String, Object>>) pineMap.get("menus");
            for (Map<String, Object> map : list) {
                MapUtil.transMapBooleanToInt(map, "isDev", "status");
                MapUtil.transMapIntToBoolean(map, "isHidden");
                map.put("hidden", map.get("isHidden"));
            }
        }
        if ( pineMap.containsKey("logicFlows")) {
            List<Map<String, Object>> list = (List<Map<String, Object>>) pineMap.get("logicFlows");
            for (Map<String, Object> map : list) {
                map.put("tranCtrl", map.get("appId"));
            }
        }
        if (pineMap.containsKey("kdbFlows")) {
            List<Map<String, Object>> list = (List<Map<String, Object>>) pineMap.get("kdbFlows");
            for (Map<String, Object> map : list) {
                map.put("flowId", map.get("flowid"));
                map.put("updateTime", map.get("updatetime"));
                map.put("createTime", map.get("createtime"));
            }
        }
        if (pineMap.containsKey("functions")) {
            List<Map<String, Object>> list = (List<Map<String, Object>>) pineMap.get("functions");
            for (Map<String, Object> map : list) {
                map.put("updateTime", map.get("updatetime"));
                map.put("createTime", map.get("createtime"));
            }
        }
        // 是否覆盖开发平台系统数据
        if(!pineMap.containsKey("isForceReplaceDev")){
            pineMap.put("isForceReplaceDev", false);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        DevPine devPine = null;
        try {
            devPine = objectMapper.readValue(JsonUtil.toJson(pineMap), DevPine.class);
        }
        catch (JsonProcessingException e) {
            log.error("error", e);
            throw BusinessException.serviceThrow(I18n.t("DevApplicationServiceImpl.dataParseFail", "应用包数据解析异常"));
        }
        // 处理menu
        if (devPine.getMenus() != null) {
            devPine.getMenus().stream().forEach(it -> {
                if (it.getHidden() == null) {
                    it.setHidden(false);
                }
            });
        }
        return devPine;
    }

    @Override
    public void copyData(String id, CopyAppArgv context) {
        CopyContextArgv contextArgv = new CopyContextArgv();
        contextArgv.setWithSystemData(0);
        contextArgv.setDeepCopy(1);
        contextArgv.setCodeSuffix(context.getCodeSuffix());
        contextArgv.setNameSuffix(context.getNameSuffix());
        contextArgv.setUrlSuffix(context.getUrlSuffix());
        // 拷贝
        CopyProcessData copyProcessData = new CopyProcessData();
        // 拷贝
        CopyAppManager.getInstance().copyAppData(id, context.getName(), contextArgv, copyProcessData);
        // 开始
        CopyAppManager.getInstance().action(copyProcessData, contextArgv);
        // 创建关联
        DevTeamApp teamApp = new DevTeamApp();
        teamApp.setTeamId(context.getTeamId());
        if (copyProcessData.getAppIds() != null && !copyProcessData.getAppIds().isEmpty()) {
            teamApp.setAppId(copyProcessData.getAppIds().toArray(new String[0])[0]);
            teamApp.setTeamType(0);
            DB.save(teamApp);
        }


    }

    /**
     * 备份
     *
     * @param pineBody
     * @param fileName
     */
    @Override
    public void backupPine(String pineBody, String fileName) {
        // 创建历史目录
        String hisPathString = initPsiPath +"/AppHistory/";
        File hisPath  = new File(hisPathString);
        if (!hisPath.exists()) {
            hisPath.mkdirs();
        }
        String newFileName = fileName;
        if(fileName.endsWith(".pine")) {
            newFileName = fileName.substring(0, fileName.lastIndexOf("."));
        }
        if (newFileName.length()>30) {
            newFileName = newFileName.substring(0, 30);
        }
        newFileName = newFileName + "_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss") + ".pine";
        try {
            Files.write(new File(hisPathString + newFileName).toPath(), pineBody.getBytes(StandardCharsets.UTF_8));
        }
        catch (Exception e) {
            log.info("文件备份失败:{}", e.getMessage());
        }

    }

    @Override
    public void gitCommit(String id, String message) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("message", message);
        KdbFlowResult result = FaasInvoke.callFlow("e506a9def73344a0b468eda8fc6dc3a9", params);
        if (result.getData() instanceof ErrorResult) {
            throw BusinessException.serviceThrow(result.getExceptionStack());
        }
    }

    @Override
    public void gitRemove(String id, String message) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("message", message);
        KdbFlowResult result = FaasInvoke.callFlow("b46a4875a5594c82a87d440ba01416d6", params);
        if (result.getData() instanceof ErrorResult) {
            throw BusinessException.serviceThrow(result.getExceptionStack());
        }
    }
}
