package com.kingsware.kdev.biz.kw.service.impl;

import com.kingsware.kdev.biz.kw.argv.*;
import com.kingsware.kdev.biz.kw.ret.*;
import com.kingsware.kdev.biz.kw.service.KwBankAccountExpandService;
import com.kingsware.kdev.biz.kw.service.KwBankAccountManagerService;
import com.kingsware.kdev.biz.kw.service.KwRPAAcrmViewService;
import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.SqlWrapper;
import com.kingsware.kdev.core.orm.expression.Op;
import com.kingsware.kdev.core.util.BeanUtils;
import com.kingsware.kdev.sys.argv.SysUserArgv;
import com.kingsware.kdev.sys.argv.SysUserQueryArgv;
import com.kingsware.kdev.sys.model.SysUser;
import com.kingsware.kdev.sys.ret.SysUserRet;
import com.kingsware.kdev.sys.service.SysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 银行账户视图业务实现类
 *
 * @author AndyZheng
 * @version 1.0.0
 * @date 2021/1/18 17:36
 */
@Service
public class KwRPAAcrmViewServiceImpl extends BaseServiceImpl implements KwRPAAcrmViewService {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private KwBankAccountManagerService kwBankAccountManagerService;

    @Resource
    private KwBankAccountExpandService kwBankAccountExpandService;

    @Override
    public PageDataRet<KwRPAAcrmViewRet> query(KwRPAAcrmViewQueryArgv argv) {
        PageDataRet pageDataRet = new PageDataRet();
        pageDataRet.setList(getCrmViewList(argv));
        pageDataRet.setPage(1);
        return pageDataRet;
    }

    public List<KwRPAAcrmViewRet> getCrmViewList(KwRPAAcrmViewQueryArgv argv) {
        // 拼装sql
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT  ");
        sql.append(" 	预约编号 AS book_number, ");
        sql.append(" 	客户编号 AS customer_number, ");
        sql.append(" 	客户姓名 AS customer_name, ");
        sql.append(" 	理财经理编号 AS customer_manager_number, ");
        sql.append(" 	客户经理 AS customer_manager_name, ");
        sql.append(" 	预约金额 AS amount, ");
        sql.append(" 	预约时间 AS book_time, ");
        sql.append(" 	产品编号 AS pro_pm_account, ");
        sql.append(" 	产品名称 AS pro_pm, ");
        sql.append(" 	项目编号 AS pro_num, ");
        sql.append(" 	项目名称 AS pro_name ");
//        sql.append(" FROM MONITOR.\"RPAcrm_view\" ");
        sql.append(" FROM RPAcrm_view ");
        sql.append(" where 1=1 ");
        SqlWrapper wrapper = new SqlWrapper(sql.toString());
        return DB.byName("oracle").findList(KwRPAAcrmViewRet.class, wrapper.getSql(), wrapper.getParams().toArray());
    }

    @Override
    public void updateBankAccountManager() {
        updateBankAccountManager(getCrmViewList(new KwRPAAcrmViewQueryArgv()));
    }

    public void updateBankAccountManager(List<KwRPAAcrmViewRet> list) {
        for (KwRPAAcrmViewRet ret : list) {
            PageDataRet<KwBankAccountManagerRet> retList = kwBankAccountManagerService.queryBankAccountManagerWithExpand(initBankAccountManagerQueryArgv(ret.getBookNumber(), ret.getProNum()));
            if (retList != null && retList.getList() != null && retList.getList().size() > 0) {
                KwBankAccountManagerArgv argv = converBankAccountManagerArgv(ret);
                argv.setId(retList.getList().get(0).getId());
                argv.setProId(retList.getList().get(0).getProId());
                kwBankAccountManagerService.edit(argv);
            } else {
                KwBankAccountExpandQueryArgv expandArgv = new KwBankAccountExpandQueryArgv();
                expandArgv.setPageQuery(false);
                expandArgv.setProNum(ret.getProNum());
                PageDataRet<KwBankAccountExpandRet> proRetList = kwBankAccountExpandService.query(expandArgv);
                if (proRetList != null && proRetList.getList() != null && proRetList.getList().size() > 0) {
                    KwBankAccountManagerArgv argv = converBankAccountManagerArgv(ret);
                    argv.setProId(proRetList.getList().get(0).getId());
                    kwBankAccountManagerService.add(argv);
                }

            }
        }
        // insert or update user
        insertSysUser(querySysUserByManager());
    }

    private List<KwBankAccountManagerRet> querySysUserByManager() {
        // 拼装sql
        StringBuilder sql = new StringBuilder();
        sql.append(" select kbam.customer_manager_number, kbam.customer_manager_name ");
        sql.append(" 		from kw_bank_account_manager kbam  ");
        sql.append(" 		left join sys_user su on su.username = kbam.customer_manager_number  ");
        sql.append(" 		where su.id is null ");
        SqlWrapper wrapper = new SqlWrapper(sql.toString());
        // 拼装查询sql
        return DB.findList(KwBankAccountManagerRet.class, wrapper.getSql(), wrapper.getParams().toArray());
    }

    private void insertSysUser(List<KwBankAccountManagerRet> list) {
        for (KwBankAccountManagerRet ret : list) {
            SysUserArgv user = new SysUserArgv();
            user.setUsername(ret.getCustomerManagerNumber());
            user.setRealName(ret.getCustomerManagerName());
            user.setPassword("S0BBZm0yMDIy"); // K@Afm2022
            user.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
            user.setSex(0);
            user.setStatus(1);
            sysUserService.add(user);

            insertSysUserRole(ret.getCustomerManagerNumber());
        }
    }

    private void insertSysUserRole(String customerManagerNumber) {
        // 拼装sql
        StringBuilder sql = new StringBuilder();
        sql.append(" INSERT INTO sys_user_role (id, sys_user_id, sys_role_id, who_created, when_created) ");
        sql.append(" 			select MD5(uuid()), su.id, '8c513fd287874efbbcec09ad503de7e3', '056fb0eeb9a44cb0953534b4c0ca01fa', CURRENT_TIMESTAMP() ");
        sql.append(" 			from sys_user su ");
        sql.append(" 			where 1=1 ");
        SqlWrapper wrapper = new SqlWrapper(sql.toString());
        wrapper.addCondition("su.username", Op.EQ, customerManagerNumber);
        DB.executeUpdateSql(wrapper.getSql(), wrapper.getParams().toArray());
    }

    private KwBankAccountManagerArgv converBankAccountManagerArgv(KwRPAAcrmViewRet view) {
        KwBankAccountManagerArgv argv = BeanUtils.copyObject(view, KwBankAccountManagerArgv.class);
        return argv;
    }

    private KwBankAccountManagerQueryArgv initBankAccountManagerQueryArgv(String bookNumber, String proNum) {
        KwBankAccountManagerQueryArgv argv = new KwBankAccountManagerQueryArgv();
        argv.setPageQuery(false);
        argv.setBookNumber(bookNumber);
        argv.setProNum(proNum);
        return argv;
    }
}
