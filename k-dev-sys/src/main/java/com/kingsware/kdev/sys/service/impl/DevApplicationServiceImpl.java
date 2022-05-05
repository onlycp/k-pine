package com.kingsware.kdev.sys.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingsware.kdev.core.auth.BaseUserInfo;
import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.cache.access.AccessManager;
import com.kingsware.kdev.core.context.KClientContext;
import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.i18n.I18n;
import com.kingsware.kdev.core.model.SysTask;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.DBChecker;
import com.kingsware.kdev.core.orm.SqlWrapper;
import com.kingsware.kdev.core.orm.expression.Op;
import com.kingsware.kdev.core.orm.kdb.*;
import com.kingsware.kdev.core.util.BeanUtils;
import com.kingsware.kdev.core.util.DateUtils;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.sys.argv.DevApplicationArgv;
import com.kingsware.kdev.sys.argv.DevApplicationQueryArgv;
import com.kingsware.kdev.sys.argv.DevPine;
import com.kingsware.kdev.sys.model.*;
import com.kingsware.kdev.sys.ret.DevApplicationRet;
import com.kingsware.kdev.sys.service.DevApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
            wrapper.addCondition("da.devStatus", Op.EQ, argv.getDevStatus());
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
    public void importApp(String json) {

        Map<String, Object> pineMap = JsonUtil.toMap(json);
        // 处理特殊的字段
        // 应用处理
        if (pineMap.containsKey("info")) {
            Map<String, Object> map = (Map<String, Object>) pineMap.get("info");
            transMapBooleanToInt( map,"devStatus", "enableStatus");
            transMapDateToString(map, "whenCreated", "whenModified");
        }
        if (pineMap.containsKey("pages")) {
            List<Map<String, Object>> list = (List<Map<String, Object>>) pineMap.get("pages");
            for (Map<String, Object> map: list) {
                transMapBooleanToInt( map,"devStatus", "enableStatus");
                transMapDateToString(map, "whenCreated", "whenModified");
            }

        }
        if (pineMap.containsKey("menus")) {
            List<Map<String, Object>> list = (List<Map<String, Object>>) pineMap.get("menus");
            for (Map<String, Object> map: list) {
                transMapBooleanToInt( map,"isDev", "status");
                transMapIntToBoolean(map, "isHidden");
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
        // 处理应用信息
        long appCount = DB.saveOrUpdate(devPine.getInfo(), DevApplication.class);
        // 处理页面
        long pageCount = DB.batchSaveOrUpdate(devPine.getPages(), DevPage.class);
        // 接口
        long apiCount = DB.batchSaveOrUpdate(devPine.getApis(), SysApi.class);
        // 字典分类
        long dictCount = DB.batchSaveOrUpdate(devPine.getDict(), SysDict.class);
        // 字典项
        long dictItemCount = DB.batchSaveOrUpdate(devPine.getDictItems(), SysDictItem.class);
        // 任务调度
        long taskCount = DB.batchSaveOrUpdate(devPine.getTasks(), SysTask.class);
        // 系度配置
        long configCount = DB.batchSaveOrUpdate(devPine.getConfigs(), SysConfig.class);
        // 菜单
        long menuCount = DB.batchSaveOrUpdate(devPine.getMenus(), SysMenu.class);
        // pine逻辑
        long pineFlowCount = DB.batchSaveOrUpdate(devPine.getLogicFlows(), SysLogicFlow.class);
        // faas逻辑
        for (FlowInfo flowInfo: devPine.getKdbFlows()) {
            if (flowInfo.getFlowId().equalsIgnoreCase("base_flow")) {
                continue;
            }
            KdbFlowQueryArgv kdbFlowQueryArgv = new KdbFlowQueryArgv();
            kdbFlowQueryArgv.setFlowId(flowInfo.getFlowId());
            List<FlowInfo> functionInfoList = DB.kdbApi().query(kdbFlowQueryArgv);
            // 如果没有，则新增
            if (functionInfoList.isEmpty() ) {
                String sql = "insert into flow (flowid,name,content,description) values (?,?,?,?)";
                DB.byName("kingDB").executeUpdateSql(sql, flowInfo.getFlowId(), flowInfo.getName(), flowInfo.getContent(), flowInfo.getDescription());
            }
            else {
                String sql = "update flow set name=?, content=?,  description=? where flowid=?";
                DB.byName("kingDB").executeUpdateSql(sql, flowInfo.getName(), flowInfo.getContent(), flowInfo.getDescription(), flowInfo.getFlowId());
            }
        }
        // faas逻辑
        for (Functions functions: devPine.getFunctions()) {

            FunctionQueryArgv functionQueryArgv = new FunctionQueryArgv();
            functionQueryArgv.setId(functions.getId());
            List<Functions> functionInfoList = DB.kdbApi().queryFunction(functionQueryArgv);
            // 如果没有，则新增
            if (functionInfoList.isEmpty() ) {
                String sql = "insert into functions (id,name,type,desc,script) values (?,?,?,?,?)";
                DB.byName("kingDB").executeUpdateSql(sql, functions.getId(), functions.getName(), functions.getType(), functions.getDesc(), functions.getScript());
            }
            else {
                String sql = "update functions set name=?, type=?, desc=?, script=? where id=?";
                DB.byName("kingDB").executeUpdateSql(sql, functions.getName(), functions.getType(), functions.getDesc(), functions.getScript(), functions.getId());
            }
        }
        log.info("导入应用数:{}, 页面数:{}, 接口数:{}, 字典分类数:{}, 字典项数:{}, 任务调度数:{}, 系统配置数:{}， 菜单数:{}, pine逻辑:{}, faas逻辑:{}, 函数数:{}"
                , appCount, pageCount, apiCount, dictCount, dictItemCount, taskCount, configCount, menuCount, pineFlowCount, devPine.getKdbFlows().size(), devPine.getFunctions().size());

    }

    private void transMapBooleanToInt(Map<String, Object> map, String...keys) {
        for (String key: keys) {
            if (map.containsKey(key)) {
                if (map.get(key) instanceof Boolean) {
                    map.put(key, map.get(key).equals(true) ? 1: 0);
                }
                else {
                    map.put(key, 0);
                }
            }
        }

    }


    private void transMapIntToBoolean(Map<String, Object> map, String...keys) {
        for (String key: keys) {
            if (map.containsKey(key)) {
                Object value = map.get(key);
                if (value == null) {
                    map.put(key, null);
                }
                else {
                    map.put(key, "1".equals(map.get(key).toString().trim()));
                }
            }
            else {
                map.put(key, false);
            }
        }

    }

    private void transMapDateToString(Map<String, Object> map, String...keys) {
        for (String key: keys) {
            if (map.get(key) != null) {
                if (map.get(key) instanceof Long) {
                    map.put(key, DateUtils.formatDate(new Date((Long)map.get(key)), "yyyy-MM-dd HH:mm:ss"));
                }

            }
        }

    }
}
