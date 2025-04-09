package com.kingsware.kdev.sys.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingsware.kdev.core.auth.BaseUserInfo;
import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.LogStack;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
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
import com.kingsware.kdev.core.model.*;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import javax.annotation.Resource;
import java.io.File;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
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
        if ("false".equalsIgnoreCase(importEnable)) {
            throw BusinessException.serviceThrow(I18n.t("DevApplicationServiceImpl.pineNotAllowInstall", "当前平台禁止导入pine数据！"));
        }
        int subSize = 3500;
        DevPine devPine = appData2Pine(json);
        log.info("开始导入数据");
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
        long pageCount = DB.batchSaveOrUpdate(devPine.getPages(), DevPage.class);
        log.info("完成导入页面信息：{}", pageCount);
        importMessageMap.put(I18n.t("DevApplicationServiceImpl.page", "页面信息") , pageCount);
        // 接口
        long apiCount = DB.batchSaveOrUpdate(devPine.getApis(), SysApi.class);
        log.info("完成导入接口信息：{}", apiCount);
        importMessageMap.put(I18n.t("DevApplicationServiceImpl.api", "接口信息"), apiCount);
        // 字典分类
        long dictCount = DB.batchSaveOrUpdate(devPine.getDict(), SysDict.class);
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
        long taskCount = DB.batchSaveOrUpdate(devPine.getTasks(), SysTask.class);
        log.info("完成导入任务调度信息：{}", taskCount);
        importMessageMap.put(I18n.t("DevApplicationServiceImpl.task", "任务调度信息") , taskCount);
        // 系度配置
        long configCount = DB.batchSaveOrUpdate(devPine.getConfigs(), SysConfig.class);
        log.info("完成导入系统配置：{}", configCount);
        importMessageMap.put(I18n.t("DevApplicationServiceImpl.config", "系统配置") , configCount);
        // 国际化
        long i18nCount = DB.batchSaveOrUpdate(devPine.getI18ns(), SysI18n.class);
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
            long openAccountCount = DB.batchSaveOrUpdate(openAccounts, OpenAccount.class);
            log.info("完成导入开放账号：{}", openAccountCount);
            importMessageMap.put(I18n.t("DevApplicationServiceImpl.openAccount", "开放账号") , i18nCount);
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
            long openAccountApiCount = DB.batchSaveOrUpdate(openAccountApis, OpenAccountApi.class);
            log.info("完成导入开放权限：{}", openAccountApiCount);
            importMessageMap.put(I18n.t("DevApplicationServiceImpl.openAccountApi", "开放权限") , i18nCount);
        }
        // 菜单
        long menuCount = 0;
        if (LicenseManager.getInstance().isUniopsApp()) {
            List<SysMenu> menus = devPine.getMenus().stream().filter(it -> StringUtils.isNotEmpty(it.getAppId()) && !it.getAppId().equals(pineAppId)).collect(Collectors.toList());
            menuCount = DB.batchSaveOrUpdate(menus, SysMenu.class);

        }
        else {
            menuCount = DB.batchSaveOrUpdate(devPine.getMenus(), SysMenu.class);
        }
        log.info("完成导入菜单：{}", menuCount);
        importMessageMap.put(I18n.t("DevApplicationServiceImpl.menu", "菜单") , menuCount);
        // 开发平台角色
        long devRoleCount = 0;
        if (appModeProperties.getDev() && devPine.getDevRoles() != null && !devPine.getDevRoles().isEmpty()) {
            devRoleCount = DB.batchSaveOrUpdate(devPine.getDevRoles(), SysRole.class);
            log.info("完成导入开发平台角色：{}", devRoleCount);
            importMessageMap.put(I18n.t("DevApplicationServiceImpl.devRole", "开发平台角色") , devRoleCount);
        }

        // 开发平台角色菜单
        long devRoleMenuCount = 0;

        if (appModeProperties.getDev() && devPine.getDevRoleMenus() != null && !devPine.getDevRoleMenus().isEmpty()) {
            devRoleMenuCount = DB.batchSaveOrUpdate(devPine.getDevRoleMenus(), SysRoleMenu.class);
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
            long pineFlowCount = DB.batchSaveOrUpdate(devPine.getLogicFlows(), SysLogicFlow.class);
            log.info("完成导入pine逻辑：{}", pineFlowCount);
            importMessageMap.put(I18n.t("DevApplicationServiceImpl.logic", "逻辑编排"), pineFlowCount);
        }

        // faas逻辑
        if (devPine.getKdbFlows() != null) {
            for (FlowInfo flowInfo : devPine.getKdbFlows()) {
                if (flowInfo.getFlowId().equalsIgnoreCase("base_flow")) {
                    continue;
                }
                KdbFlowQueryArgv kdbFlowQueryArgv = new KdbFlowQueryArgv();
                kdbFlowQueryArgv.setFlowId(flowInfo.getFlowId());
                List<FlowInfo> functionInfoList = DB.kdbApi().query(kdbFlowQueryArgv);

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
        // faas函数
        if (devPine.getFunctions() != null) {
            boolean enableImportFunction = SpringContext.getBoolean("app.import-function", true);
            if (enableImportFunction) {
                for (Functions functions : devPine.getFunctions()) {

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
                    long tCount = DB.batchSaveOrUpdate(devPine.getDevFaasNodeTypes(), DevFaasNodeType.class);
                    log.info("完成导入FAAS扩展节点类型：{}", tCount);
                    importMessageMap.put(I18n.t("DevApplicationServiceImpl.faasExtNodeType", "FAAS扩展节点类型"), tCount);
                }
                // 插入FAAS扩展节点
                if (devPine.getDevFaasNodes() != null && !devPine.getDevFaasNodes().isEmpty()) {
                    long tCount = DB.batchSaveOrUpdate(devPine.getDevFaasNodes(), DevFaasNode.class);
                    log.info("完成导入FAAS扩展节点：{}", tCount);
                    importMessageMap.put(I18n.t("DevApplicationServiceImpl.faasExtNode", "FAAS扩展节点") , tCount);
                }
                // 能力关联
                if (devPine.getPowerLinks() != null && !devPine.getPowerLinks().isEmpty()) {
                    long tCount = DB.batchSaveOrUpdate(devPine.getPowerLinks(), DevPowerLink.class);
                    log.info("完成导入能力关联：{}", tCount);
                    importMessageMap.put(I18n.t("DevApplicationServiceImpl.power", "能力关联"), tCount);
                }
                // 能力树
                if (devPine.getDevPowerTrees() != null && !devPine.getDevPowerTrees().isEmpty()) {
                    long tCount = DB.batchSaveOrUpdate(devPine.getDevPowerTrees(), DevPowerTree.class);
                    log.info("完成导入能力树：{}", tCount);
                    importMessageMap.put(I18n.t("DevApplicationServiceImpl.powerTree", "能力树"), tCount);
                }
                // 插件树
                if (devPine.getExtPluginTrees() != null && !devPine.getExtPluginTrees().isEmpty()) {
                    long tCount = DB.batchSaveOrUpdate(devPine.getExtPluginTrees(), ExtPluginTree.class);
                    log.info("完成导入插件树：{}", tCount);
                    importMessageMap.put(I18n.t("DevApplicationServiceImpl.pluginTree", "插件树") , tCount);
                }
                // 插件接口
                if (devPine.getExtPluginInterfaces() != null && !devPine.getExtPluginInterfaces().isEmpty()) {
                    long tCount = DB.batchSaveOrUpdate(devPine.getExtPluginInterfaces(), ExtPluginInterface.class);
                    log.info("完成导入插件接口：{}", tCount);
                    importMessageMap.put(I18n.t("DevApplicationServiceImpl.pluginApi", "插件接口"), tCount);
                }
                // 编辑编排模板
                if (devPine.getSysLogicTemplates() != null && !devPine.getSysLogicTemplates().isEmpty()) {
                    long tCount = DB.batchSaveOrUpdate(devPine.getSysLogicTemplates(), SysLogicTemplate.class);
                    log.info("完成导入编辑编排模板：{}", tCount);
                    importMessageMap.put(I18n.t("DevApplicationServiceImpl.logicTemplate", "编辑编排模板"),  tCount);
                }
                // 页面模板
                if (devPine.getDevPageTemplates() != null && !devPine.getDevPageTemplates().isEmpty()) {
                    long tCount = DB.batchSaveOrUpdate(devPine.getDevPageTemplates(), DevPageTemplate.class);
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
                    String json = "";
                    if(sysFile.getSaveType() == 2) {
                        File file = sysFileService.getFaasFile(sysFile.getFilePath());
                        json = FileUtils.readFile(file);
                        file.delete();
                    }
                    else {
                        json = FileUtils.readFile(new File(SpringContext.getProperties("file.base-path", "/") + sysFile.getFilePath()));
                    }
                    logStack.addMessage(I18n.t("DevApplicationServiceImpl.startInstall", "开始安装应用:") + sysFile.getFileOriginalName());
                    String result = importApp(json, argv.getTeamId());
                    this.backupPine(json, backupName);
                    logStack.addMessage(I18n.t("DevApplicationServiceImpl.completeInstall", "应用安装完成：")  + result);
                }
            }


        } catch (Exception e) {
            logStack.addMessage(ExceptionUtils.getStackTrace(e));
        }

        return logStack.formatMessages();
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
