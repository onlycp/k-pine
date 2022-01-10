package com.kingsware.kdev.sys.service.impl;

import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.excel.ExcelWorker;
import com.kingsware.kdev.core.excel.KExcel;
import com.kingsware.kdev.core.excel.RegionDefine;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.SqlWrapper;
import com.kingsware.kdev.core.orm.expression.Op;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.sys.argv.SysLoginLogQueryArgv;
import com.kingsware.kdev.sys.model.SysLoginLog;
import com.kingsware.kdev.sys.ret.SysLoginLogRet;
import com.kingsware.kdev.sys.service.SysLoginLogService;
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
public class SysLoginLogServiceImpl extends BaseServiceImpl implements SysLoginLogService {

    @Override
    public SysLoginLogRet get(String id) {
        // 查询model
        SysLoginLog model = DB.findById(SysLoginLog.class, id);
        // 转换成ret对象
        return (SysLoginLogRet) model2Ret(model, SysLoginLogRet.class);
    }


    @Override
    @SuppressWarnings("unchecked")
    public PageDataRet<SysLoginLogRet> query(SysLoginLogQueryArgv argv) {
        // 拼装sql
        SqlWrapper wrapper = new SqlWrapper("select * from sys_login_log where 1=1 ");
        // 拼装查询sql
        if (StringUtils.isNotEmpty(argv.getOperator())) {
            wrapper.addCondition("operator", Op.LIKE, "%" +argv.getOperator() +"%");
        }
        if (StringUtils.isNotEmpty(argv.getOperateTimes())) {
            wrapper.between("operate_time", argv.getOperateTimes().split(",")[0], argv.getOperateTimes().split(",")[1]);
        }
        if (argv.getIds() != null) {
            String[] splits = argv.getIds().split(",");
            Set<Object> ids = Arrays.asList(splits).stream().collect(Collectors.toSet());
            wrapper.in("id", ids);
        }
        // 加入权限sql
        wrapper.withAuthority("sys_login_log", "");
        // 排序
        wrapper.sortBy("order by operate_time desc");

        return (PageDataRet<SysLoginLogRet>) query(wrapper.getSql(), wrapper.getParams(), argv, SysLoginLogRet.class);
    }

    @Override
    public void export(SysLoginLogQueryArgv argv) {
        // 直接调用查询方法
        argv.setPageQuery(false);
        PageDataRet<SysLoginLogRet> pageDataRet = query(argv);
        // 定义标题
        List<RegionDefine> defineList = new ArrayList<>();
        defineList.add(RegionDefine.builder().propName("id").labelName("ID").build());
        defineList.add(RegionDefine.builder().propName("ip").labelName("IP地址").build());
        defineList.add(RegionDefine.builder().propName("times").labelName("耗时").build());
        defineList.add(RegionDefine.builder().propName("requestBody").labelName("请求内容体").build());
        defineList.add(RegionDefine.builder().propName("responseCode").labelName("响应码").build());
        defineList.add(RegionDefine.builder().propName("responseMessage").labelName("响应消息").build());
        defineList.add(RegionDefine.builder().propName("operator").labelName("用户名").build());
        defineList.add(RegionDefine.builder().propName("operateTime").labelName("操作时间").build());
        // 导出
        KExcel kExcel = KExcel.fromDataList("登录日志.xls", "登录日志", defineList, pageDataRet.getList());
        ExcelWorker.getInstance().getHandler().writeToWeb(kExcel);
    }

}
