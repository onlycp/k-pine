package com.kingsware.kdev.biz.kw.service.impl;

import com.kingsware.kdev.biz.kw.argv.KwRPAUserViewQueryArgv;
import com.kingsware.kdev.biz.kw.argv.KwUserArgv;
import com.kingsware.kdev.biz.kw.argv.KwUserQueryArgv;
import com.kingsware.kdev.biz.kw.ret.KwRPAUserViewRet;
import com.kingsware.kdev.biz.kw.ret.KwUserRet;
import com.kingsware.kdev.biz.kw.service.KwRPAUserViewService;
import com.kingsware.kdev.biz.kw.service.KwUserService;
import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.SqlWrapper;
import com.kingsware.kdev.core.util.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 银行账户视图业务实现类
 *
 * @author AndyZheng
 * @version 1.0.0
 * @date 2021/1/18 17:36
 */
@Service
public class KwRPAUserViewServiceImpl extends BaseServiceImpl implements KwRPAUserViewService {

    @Resource
    private KwUserService kwUserService;

    @Override
    public PageDataRet<KwRPAUserViewRet> query(KwRPAUserViewQueryArgv argv) {
        PageDataRet pageDataRet = new PageDataRet();
        pageDataRet.setList(getUserViewList(argv));
        pageDataRet.setPage(1);
        return pageDataRet;
    }

    public List<KwRPAUserViewRet> getUserViewList(KwRPAUserViewQueryArgv argv) {
        // 拼装sql
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT  ");
        sql.append(" 用户ID AS username, ");
        sql.append(" 用户姓名 AS real_name, ");
        sql.append(" 用户状态 AS status, ");
        sql.append(" 创建日期 AS when_created, ");
        sql.append(" 组织架构ID AS unit_id ");
//        sql.append(" FROM MONITOR.\"RPAuser_view\" ");
        sql.append(" FROM RPAuser_view ");
        sql.append(" where 1=1 ");
        SqlWrapper wrapper = new SqlWrapper(sql.toString());
        return DB.byName("oracle").findList(KwRPAUserViewRet.class, wrapper.getSql(), wrapper.getParams().toArray());
    }

    @Override
    public void updateUsers() {
        updateUsers(getUserViewList(new KwRPAUserViewQueryArgv()));
    }

    public void updateUsers(List<KwRPAUserViewRet> list) {
        for (KwRPAUserViewRet user : list) {
            PageDataRet<KwUserRet> userList = kwUserService.query(initSysUserQueryArgv(user.getUsername()));
            if (userList != null && userList.getList() != null && userList.getList().size() > 0) {
                KwUserArgv argv = converSysUserArgv(user);
                argv.setId(userList.getList().get(0).getId());
                kwUserService.edit(argv);
            } else {
                kwUserService.add(converSysUserArgv(user));
            }
        }
    }

    private KwUserArgv converSysUserArgv(KwRPAUserViewRet view) {
        return BeanUtils.copyObject(view, KwUserArgv.class);
    }

    private KwUserQueryArgv initSysUserQueryArgv(String username) {
        KwUserQueryArgv argv = new KwUserQueryArgv();
        argv.setPageQuery(false);
        argv.setUsername(username);
        return argv;
    }
}
