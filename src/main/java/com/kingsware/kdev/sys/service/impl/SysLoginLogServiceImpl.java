package com.kingsware.kdev.sys.service.impl;

import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.SqlWrapper;
import com.kingsware.kdev.core.orm.expression.Op;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.sys.argv.SysLoginLogQueryArgv;
import com.kingsware.kdev.sys.model.SysLoginLog;
import com.kingsware.kdev.sys.ret.SysLoginLogRet;
import com.kingsware.kdev.sys.service.SysLoginLogService;
import org.springframework.stereotype.Service;

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
        wrapper.sortBy("order by operate_time desc");

        return (PageDataRet<SysLoginLogRet>) query(wrapper.getSql(), wrapper.getParams(), argv, SysLoginLogRet.class);
    }

}
