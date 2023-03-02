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
import com.kingsware.kdev.core.context.KClientContext;
import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.i18n.I18n;
import com.kingsware.kdev.core.model.SysFile;
import com.kingsware.kdev.core.model.SysLogicFlow;
import com.kingsware.kdev.core.model.SysTask;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.DBChecker;
import com.kingsware.kdev.core.orm.SqlWrapper;
import com.kingsware.kdev.core.orm.expression.Op;
import com.kingsware.kdev.core.orm.kdb.*;
import com.kingsware.kdev.core.util.*;
import com.kingsware.kdev.sys.argv.DevAppInstallArgv;
import com.kingsware.kdev.sys.argv.DevApplicationArgv;
import com.kingsware.kdev.sys.argv.DevApplicationQueryArgv;
import com.kingsware.kdev.sys.argv.DevPine;
import com.kingsware.kdev.sys.model.*;
import com.kingsware.kdev.sys.ret.DevApplicationRet;
import com.kingsware.kdev.sys.service.DevApplicationService;
import com.kingsware.kdev.sys.service.SysFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 业务实现类
 * @author AndyZheng
 * @version 1.0.0
 * @date 2022-02-13 10:20
 */
@Service
@Slf4j
public class DevApplicationServiceImpl extends BaseServiceImpl implements DevApplicationService {

    @Resource
    private SysFileService sysFileService;

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
        DBChecker<DevApplication> checker =DBChecker.build(model, DevApplication.class);
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
        for (String id: argv.getIds()) {
            DB.delete(DevApplication.class, id);
        }
    }



    @Override
    @SuppressWarnings("all")
    public String importApp(String json) {

        int subSize = 3500;
        DevPine devPine = appData2Pine(json);
        log.info("开始导入数据");
        // 处理应用信息
        long appCount = DB.saveOrUpdate(devPine.getInfo(), DevApplication.class);
        log.info("完成导入应用信息：{}", appCount);
        // 处理页面
        long pageCount = DB.batchSaveOrUpdate(devPine.getPages(), DevPage.class);
        log.info("完成导入页面信息：{}", pageCount);
        // 接口
        long apiCount = DB.batchSaveOrUpdate(devPine.getApis(), SysApi.class);
        log.info("完成导入接口信息：{}", apiCount);
        // 字典分类
        long dictCount = DB.batchSaveOrUpdate(devPine.getDict(), SysDict.class);
        log.info("完成导入字典信息：{}", dictCount);
        // 字典项
        // 先删除已有字典项
        if (devPine.getDictItems() != null &&  !devPine.getDictItems().isEmpty()) {
            for (SysDictItem item: devPine.getDictItems()) {
                DB.delete(item);
            }
        }
        long dictItemCount = DB.batchSaveOrUpdate(devPine.getDictItems(), SysDictItem.class);
        log.info("完成导入字典项信息：{}", dictItemCount);
        // 任务调度
        long taskCount = DB.batchSaveOrUpdate(devPine.getTasks(), SysTask.class);
        log.info("完成导入任务调度信息：{}", taskCount);
        // 系度配置
        long configCount = DB.batchSaveOrUpdate(devPine.getConfigs(), SysConfig.class);
        log.info("完成导入系统配置：{}", configCount);
        // 菜单
        long menuCount = DB.batchSaveOrUpdate(devPine.getMenus(), SysMenu.class);
        log.info("完成导入菜单：{}", menuCount);
        // pine逻辑
        long pineFlowCount = DB.batchSaveOrUpdate(devPine.getLogicFlows(), SysLogicFlow.class);
        log.info("完成导入pine逻辑：{}", pineFlowCount);
        // faas逻辑
        for (FlowInfo flowInfo: devPine.getKdbFlows()) {
            if (flowInfo.getFlowId().equalsIgnoreCase("base_flow")) {
                continue;
            }
//            DB.byName("kingDB").executeUpdateSql("delete from flow where flowid=?", flowInfo.getFlowId());
            KdbFlowQueryArgv kdbFlowQueryArgv = new KdbFlowQueryArgv();
            kdbFlowQueryArgv.setFlowId(flowInfo.getFlowId());
            List<FlowInfo> functionInfoList = DB.kdbApi().query(kdbFlowQueryArgv);

            // 如果没有，则新增
//            if (functionInfoList.isEmpty() ) {
//                AddFlowInfo addFlowInfo = new AddFlowInfo();
//                addFlowInfo.setFlowId(flowInfo.getFlowId());
//                addFlowInfo.setContent(flowInfo.getContent());
//                addFlowInfo.setName(flowInfo.getName());
//                addFlowInfo.setDescription(flowInfo.getDescription());
//                DB.kdbApi().addFlow(addFlowInfo);
//            }
            if (functionInfoList.isEmpty() ) {
                String sql = "insert into flow (flowid,name,content,description) values (?,?,?,?)";
                DB.byName("kingDB").executeUpdateSql(sql, flowInfo.getFlowId(), flowInfo.getName(), flowInfo.getContent(), flowInfo.getDescription());
            }
            else {
                EditFlowInfo editFlowInfo = new EditFlowInfo();
                editFlowInfo.setFlowId(flowInfo.getFlowId());
                editFlowInfo.setContent(flowInfo.getContent());
                editFlowInfo.setName(flowInfo.getName());
                editFlowInfo.setDescription(flowInfo.getDescription());
                DB.kdbApi().editFlow(editFlowInfo);
                log.info("更新FAAS逻辑编排:{}", editFlowInfo.getName() );
            }
        }
        // faas逻辑
        for (Functions functions: devPine.getFunctions()) {

            FunctionQueryArgv functionQueryArgv = new FunctionQueryArgv();
            functionQueryArgv.setId(functions.getId());
            List<Functions> functionInfoList = DB.kdbApi().queryFunction(functionQueryArgv);
//            // 如果没有，则新增
//            if (functionInfoList.isEmpty() ) {
//                AddFunctionInfo addFunctionInfo = new AddFunctionInfo();
//                addFunctionInfo.setId(functions.getId());
//                addFunctionInfo.setName(functions.getName());
//                addFunctionInfo.setDesc(functions.getDesc());
//                addFunctionInfo.setScript(functions.getScript());
//                addFunctionInfo.setType(functions.getType());
//                DB.kdbApi().addFun(addFunctionInfo);
//            }
            if (functionInfoList.isEmpty() ) {
                String sql = "insert into functions (id,name,type,desc,script) values (?,?,?,?,?)";
                DB.byName("kingDB").executeUpdateSql(sql, functions.getId(), functions.getName(), functions.getType(), functions.getDesc(), functions.getScript());
            }
            else {
                EditFunctionInfo editFunctionInfo = new EditFunctionInfo();
                editFunctionInfo.setId(functions.getId());
                editFunctionInfo.setName(functions.getName());
                editFunctionInfo.setDesc(functions.getDesc());
                editFunctionInfo.setScript(functions.getScript());
                editFunctionInfo.setType(functions.getType());
                DB.kdbApi().editFun(editFunctionInfo);
            }
        }
        String result = String.format("导入应用数:%d, 页面数:%d, 接口数:%d, 字典分类数:%d, 字典项数:%d, 任务调度数:%d, 系统配置数:%d， 菜单数:%d, pine逻辑:%d, faas逻辑:%d, 函数数:%d"
                , appCount, pageCount, apiCount, dictCount, dictItemCount, taskCount
                , configCount, menuCount, pineFlowCount, devPine.getKdbFlows().size()
                , devPine.getFunctions().size());
        // 清理缓存
        KCacheManager.getInstance().clear();
        log.info(result);
        return result;

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
            DevApplication application = DB.findById(DevApplication.class, argv.getAppId());
            logStack.addMessage("启动...");
            // 在线升级
            if (argv.getMode() == 1 ) {
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
                    logStack.addMessage("准备请求远程数据, URL：" + apiUrl);
                    String responseBody = HttpUtil.postBody(apiUrl, JsonUtil.toJson(body), new HashMap<>(), true);
                    logStack.addMessage("完成请求远程数据，准备安装应用." );
                    String result = importApp(responseBody);
                    logStack.addMessage("应用安装完成：" + result);

                }
            }
            // 本地升级,支持多个文件
            else {
                // 先将文件下载下来
                String[] ids = argv.getLocalFileIds().split(",");
                for (String fileId: ids) {
                    SysFile sysFile = DB.findById(SysFile.class, fileId);
                    File file = sysFileService.getFaasFile(sysFile.getFilePath());
                    String json = FileUtils.readFile(file);
                    logStack.addMessage("开始安装应用:" + sysFile.getFileOriginalName());
                    String result = importApp(json);
                    logStack.addMessage("应用安装完成：" + result);
                }
            }
        }
        catch (Exception e) {
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
            MapUtil.transMapBooleanToInt( map,"devStatus", "enableStatus");
            MapUtil.transMapDateToString(map, "whenCreated", "whenModified");
        }
        if (pineMap.containsKey("pages")) {
            List<Map<String, Object>> list = (List<Map<String, Object>>) pineMap.get("pages");
            for (Map<String, Object> map: list) {
                MapUtil.transMapBooleanToInt( map,"devStatus", "enableStatus");
                MapUtil.transMapDateToString(map, "whenCreated", "whenModified");
            }

        }
        if (pineMap.containsKey("menus")) {
            List<Map<String, Object>> list = (List<Map<String, Object>>) pineMap.get("menus");
            for (Map<String, Object> map: list) {
                MapUtil.transMapBooleanToInt( map,"isDev", "status");
                MapUtil.transMapIntToBoolean(map, "isHidden");
                map.put("hidden", map.get("isHidden"));
            }
        }
        if (pineMap.containsKey("kdbFlows")) {
            List<Map<String, Object>> list = (List<Map<String, Object>>) pineMap.get("kdbFlows");
            for (Map<String, Object> map: list) {
                map.put("flowId", map.get("flowid"));
                map.put("updateTime", map.get("updatetime"));
                map.put("createTime", map.get("createtime"));
            }
        }
        if (pineMap.containsKey("functions")) {
            List<Map<String, Object>> list = (List<Map<String, Object>>) pineMap.get("functions");
            for (Map<String, Object> map: list) {
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
        } catch (JsonProcessingException e) {
            throw BusinessException.serviceThrow("应用包数据解析异常");
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

}
