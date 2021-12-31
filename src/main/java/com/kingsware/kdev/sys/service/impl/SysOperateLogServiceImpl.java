package com.kingsware.kdev.sys.service.impl;

import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.SqlWrapper;
import com.kingsware.kdev.core.orm.expression.Op;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.sys.argv.SysOperateLogQueryArgv;
import com.kingsware.kdev.sys.model.SysOperateLog;
import com.kingsware.kdev.sys.ret.SysOperateLogRet;
import com.kingsware.kdev.sys.service.SysOperateLogService;
import org.springframework.stereotype.Service;

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
            wrapper.between("operate_time", argv.getOperateTimes().split(",")[0], argv.getOperateTimes().split(",")[1]);
        }
        wrapper.sortBy("order by operate_time desc");

        return (PageDataRet<SysOperateLogRet>) query(wrapper.getSql(), wrapper.getParams(), argv, SysOperateLogRet.class);
    }

}
