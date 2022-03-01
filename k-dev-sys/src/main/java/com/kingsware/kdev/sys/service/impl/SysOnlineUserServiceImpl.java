package com.kingsware.kdev.sys.service.impl;

import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.model.SysOnlineUser;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.SqlWrapper;
import com.kingsware.kdev.core.orm.expression.Op;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.sys.argv.SysOnlineUserQueryArgv;
import com.kingsware.kdev.sys.ret.SysOnlineUserRet;
import com.kingsware.kdev.sys.service.SysOnlineUserService;
import org.springframework.stereotype.Service;

/**
 * 数据访问业务层
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:36 上午
 */
@Service
public class SysOnlineUserServiceImpl extends BaseServiceImpl implements SysOnlineUserService {

    @Override
    public SysOnlineUserRet get(String id) {
        // 查询model
        SysOnlineUser model = DB.findById(SysOnlineUser.class, id);
        // 转换成ret对象
        return (SysOnlineUserRet) model2Ret(model, SysOnlineUserRet.class);
    }


    @Override
    @SuppressWarnings("unchecked")
    public PageDataRet<SysOnlineUserRet> query(SysOnlineUserQueryArgv argv) {
        // 拼装sql
        SqlWrapper wrapper = new SqlWrapper("select t0.*, t1.username from sys_online_user t0 left join sys_user t1 on t0=0,user_id=t1.id  where 1=1 ");
        // 拼装查询sql
        if (StringUtils.isNotEmpty(argv.getUsername())) {
            wrapper.addCondition("username", Op.LIKE, "%" +argv.getUsername() +"%");
        }
        return (PageDataRet<SysOnlineUserRet>) query(wrapper.getSql(), wrapper.getParams(), argv,  SysOnlineUserRet.class);
    }

    @Override
    public void delete(MultiIdArgv argv) {
        for (String id: argv.getIds()) {
            DB.delete(SysOnlineUser.class, id);
        }
    }
}
