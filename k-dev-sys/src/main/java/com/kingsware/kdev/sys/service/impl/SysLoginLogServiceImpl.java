package com.kingsware.kdev.sys.service.impl;

import com.kingsware.kdev.core.auth.BaseUserInfo;
import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.context.KClientContext;
import com.kingsware.kdev.core.excel.ExcelWorker;
import com.kingsware.kdev.core.excel.KExcel;
import com.kingsware.kdev.core.excel.RegionDefine;
import com.kingsware.kdev.core.i18n.I18n;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.SqlWrapper;
import com.kingsware.kdev.core.orm.annotation.Transactional;
import com.kingsware.kdev.core.orm.expression.Op;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.sys.argv.SysLoginLogQueryArgv;
import com.kingsware.kdev.core.model.SysLoginLog;
import com.kingsware.kdev.sys.ret.SysLoginLogRet;
import com.kingsware.kdev.sys.service.SysLoginLogService;
import com.kingsware.kdev.sys.util.ClientUtil;
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
        // 修复漏洞：日志管理登陆日志垂直越权
        // 修复措施：普通用户只允许查询自己的登录日志
        boolean isAdmin = ClientUtil.isAdmin();
        BaseUserInfo userInfo = KClientContext.getContext().getUserInfo();

        // 拼装sql
        // 配置列表不显示ip，以免泄漏
        SqlWrapper wrapper = new SqlWrapper("select id, operate_time, operator, times, response_code, response_message, when_created, ip, address from sys_login_log where 1=1 ");
        // 拼装查询sql
        if (StringUtils.isNotEmpty(argv.getOperator())) {
            wrapper.addCondition("operator", Op.LIKE, "%" +argv.getOperator() +"%");
        }

        if (!isAdmin){
            wrapper.addCondition("operator", Op.EQ, userInfo.getUsername());
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
        defineList.add(RegionDefine.builder().propName("operator").labelName(I18n.t("LoginLog.username", "用户名")).build());
        defineList.add(RegionDefine.dateTimeDefine("operateTime", I18n.t("LoginLog.loginTime", "登录时间")));
        defineList.add(RegionDefine.builder().propName("ip").labelName("IP").build());
        defineList.add(RegionDefine.builder().propName("address").labelName(I18n.t("LoginLog.address", "地址")).build());
        defineList.add(RegionDefine.builder().propName("times").labelName(I18n.t("LoginLog.takeTime", "耗时")).build());
        defineList.add(RegionDefine.builder().propName("responseCode").labelName(I18n.t("LoginLog.responseCode", "响应码")).build());
        defineList.add(RegionDefine.builder().propName("responseMessage").labelName(I18n.t("LoginLog.responseMessage", "响应消息")).build());


        // 导出
        KExcel kExcel = KExcel.fromDataList(I18n.t("LoginLog.title", "登录日志") + ".xls", I18n.t("LoginLog.title", "登录日志") , defineList, pageDataRet.getList());
        ExcelWorker.getInstance().writeToWeb(kExcel);
    }

//    @Override
//    @Transactional
//    public void testTran() {
//        DB.executeUpdateSql("delete from t_t where 1=1");
//        DB.executeUpdateSql("delete from t_t2222 where 1=1");
//    }
//

}
