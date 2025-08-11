package com.kingsware.kdev.sys.service.impl;

import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.enums.RetEnum;
import com.kingsware.kdev.core.excel.ExcelWorker;
import com.kingsware.kdev.core.excel.KExcel;
import com.kingsware.kdev.core.excel.RegionDefine;
import com.kingsware.kdev.core.i18n.I18n;
import com.kingsware.kdev.core.model.SysOperateLog;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.SqlWrapper;
import com.kingsware.kdev.core.orm.expression.Op;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.sys.argv.SysOperateLogQueryArgv;
import com.kingsware.kdev.sys.ret.SysOperateLogRet;
import com.kingsware.kdev.sys.service.SysOperateLogService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 角色业务实现类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:36 上午
 */
@Service
public class SysOperateLogServiceImpl extends BaseServiceImpl implements SysOperateLogService {

    @Override
    public SysOperateLogRet get(String id) {
        // 查询model
        SysOperateLog model = DB.findById(SysOperateLog.class, id);
        // 转换成ret对象
        return (SysOperateLogRet) model2Ret(model, SysOperateLogRet.class);
    }


    @Override
    @SuppressWarnings("unchecked")
    public PageDataRet<SysOperateLogRet> query(SysOperateLogQueryArgv argv) {
        // 拼装sql
        SqlWrapper wrapper = new SqlWrapper("select * from sys_operate_log where 1=1 ");
        // 拼装查询sql
        if (StringUtils.isNotEmpty(argv.getModule())) {
            wrapper.addCondition("module", Op.LIKE, "%" +argv.getModule() +"%");
        }
        if (StringUtils.isNotEmpty(argv.getAction())) {
            wrapper.addCondition("action", Op.LIKE, "%" +argv.getAction() +"%");
        }
        if (StringUtils.isNotEmpty(argv.getOperator())) {
            wrapper.addCondition("operator", Op.LIKE, "%" +argv.getOperator() +"%");
        }
        if (StringUtils.isNotEmpty(argv.getOperateTimes())) {
            wrapper.between("operate_time", argv.getOperateTimes().replace("+", " ").split(",")[0], argv.getOperateTimes().replace("+", " ").split(",")[1]);
        }
        if (argv.getResponseCode() != null) {
            //筛选成功或者失败的日志(200和非200)
            Op op = argv.getResponseCode() == RetEnum.OK.getCode() ? Op.EQ : Op.NOT_EQ;
            wrapper.addCondition("response_code", op, RetEnum.OK.getCode());
        }
        if (argv.getIds() != null) {
            String[] splits = argv.getIds().split(",");
            Set<Object> ids = Arrays.asList(splits).stream().collect(Collectors.toSet());
            wrapper.in("id", ids);
        }
        wrapper.sortBy("order by operate_time desc");

        return (PageDataRet<SysOperateLogRet>) query(wrapper.getSql(), wrapper.getParams(), argv, SysOperateLogRet.class);
    }

    @Override
    public void export(SysOperateLogQueryArgv argv) {
        // 直接调用查询方法
        argv.setPageQuery(false);
        PageDataRet<SysOperateLogRet> pageDataRet = query(argv);
        // 定义标题
        List<RegionDefine> defineList = new ArrayList<>();
        defineList.add(RegionDefine.builder().propName("id").labelName("ID").build());
        defineList.add(RegionDefine.builder().propName("module").labelName(I18n.t("SysOperate.field.module", "模块")).build());
        defineList.add(RegionDefine.builder().propName("action").labelName(I18n.t("SysOperate.field.action", "动作")).build());
        defineList.add(RegionDefine.builder().propName("url").labelName(I18n.t("SysOperate.field.url", "路径")).build());
        defineList.add(RegionDefine.builder().propName("ip").labelName(I18n.t("SysOperate.field.ip", "IP地址")).build());
        defineList.add(RegionDefine.builder().propName("times").labelName(I18n.t("SysOperate.field.times", "耗时")).build());
        defineList.add(RegionDefine.builder().propName("requestBody").labelName(I18n.t("SysOperate.field.requestBody", "请求内容体")).build());
        defineList.add(RegionDefine.builder().propName("responseCode").labelName(I18n.t("SysOperate.field.responseCode", "响应码")).build());
        defineList.add(RegionDefine.builder().propName("responseMessage").labelName(I18n.t("SysOperate.field.responseMessage", "响应消息")).build());
        defineList.add(RegionDefine.builder().propName("method").labelName(I18n.t("SysOperate.field.method", "方法名称")).build());
        defineList.add(RegionDefine.builder().propName("requestMethod").labelName(I18n.t("SysOperate.field.requestMethod", "请求方式")).build());
        defineList.add(RegionDefine.builder().propName("responseBody").labelName(I18n.t("SysOperate.field.responseBody", "响应内容体")).build());
        defineList.add(RegionDefine.builder().propName("operator").labelName(I18n.t("SysOperate.field.operator", "用户名")).build());
        defineList.add(RegionDefine.builder().propName("operateTime").labelName(I18n.t("SysOperate.field.operateTime", "操作时间")).build());
        // 导出
        KExcel kExcel = KExcel.fromDataList(I18n.t("SysOperate.title", "服务日志" ) +".xls", I18n.t("SysOperate.title", "服务日志" ), defineList, pageDataRet.getList());
        ExcelWorker.getInstance().writeToWeb(kExcel);
    }

    @Override
    public PageDataRet<SysOperateLogRet> moduleList(SysOperateLogQueryArgv argv) {
        // 拼装sql
        SqlWrapper wrapper = new SqlWrapper("select DISTINCT module from sys_operate_log where 1=1 ");
        // 拼装查询sql
        if (StringUtils.isNotEmpty(argv.getModule())) {
            wrapper.addCondition("module", Op.LIKE, "%" +argv.getModule() +"%");
        }

        return (PageDataRet<SysOperateLogRet>) query(wrapper.getSql(), wrapper.getParams(), argv, SysOperateLogRet.class);
    }

    @Override
    public PageDataRet<SysOperateLogRet> actionList(SysOperateLogQueryArgv argv) {
        // 拼装sql
        SqlWrapper wrapper = new SqlWrapper("select DISTINCT action from sys_operate_log where 1=1 ");
        // 拼装查询sql
        if (StringUtils.isNotEmpty(argv.getAction())) {
            wrapper.addCondition("action", Op.LIKE, "%" + argv.getAction() + "%");
        }
        return (PageDataRet<SysOperateLogRet>) query(wrapper.getSql(), wrapper.getParams(), argv, SysOperateLogRet.class);
    }
}
