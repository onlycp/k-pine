package com.kingsware.kdev.biz.kw.service.impl;

import com.kingsware.kdev.biz.kw.argv.*;
import com.kingsware.kdev.biz.kw.ret.*;
import com.kingsware.kdev.biz.kw.service.*;
import com.kingsware.kdev.biz.kw.task.UpdateBankByViewTask;
import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.SqlWrapper;
import com.kingsware.kdev.core.orm.expression.Op;
import com.kingsware.kdev.core.util.BeanUtils;
import com.kingsware.kdev.core.util.DateUtils;
import com.kingsware.kdev.sys.argv.SysUserArgv;
import com.kingsware.kdev.sys.argv.SysUserQueryArgv;
import com.kingsware.kdev.sys.ret.SysUserRet;
import com.kingsware.kdev.sys.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * 银行账户视图业务实现类
 *
 * @author AndyZheng
 * @version 1.0.0
 * @date 2021/1/18 17:36
 */
@Service
public class KwRPABankViewServiceImpl extends BaseServiceImpl implements KwRPABankViewService {
    /** 日志打印 **/
    private static final Logger logger  = LoggerFactory.getLogger(KwRPABankViewServiceImpl.class);

    @Resource
    private KwBankAccountExpandService kwBankAccountExpandService;

    @Resource
    private KwBankAccountService kwBankAccountService;

    @Resource
    private KwRPAAcrmViewService kwRPAAcrmViewService;

    @Resource
    private KwRPAUserViewService kwRPAUserViewService;

    @Resource
    private SysUserService sysUserService;

    @Override
    public PageDataRet<KwRPABankViewRet> query(KwRPABankViewQueryArgv argv) {
        PageDataRet pageDataRet = new PageDataRet();
        pageDataRet.setList(getBankViewList(argv));
        pageDataRet.setPage(1);
        return pageDataRet;
    }

    public List<KwRPABankViewRet> getBankViewList(KwRPABankViewQueryArgv argv) {
        // 拼装sql
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT  ");
        sql.append(" 银行账号 AS account, ");
        sql.append(" 总行银行名称 AS parent_bank_name, ");
        sql.append(" 支行银行名称 AS bank_name, ");
        sql.append(" 账户类型 AS account_type, ");
        sql.append(" 账户状态 AS account_status, ");
        sql.append(" 开户日期 AS create_account_time, ");
        sql.append(" 销户日期 AS cancel_account_time, ");
        sql.append(" 项目编号 AS pro_num, ");
        sql.append(" 项目名称 AS pro_name, ");
        sql.append(" 上架日期 AS up_date, ");
        sql.append(" 下架日期 AS down_date, ");
        sql.append(" 项目阶段 AS pro_phase, ");
        sql.append(" 项目经理编号 AS pro_pm_account, ");
        sql.append(" 项目经理名称 AS pro_pm, ");
        sql.append(" 信托会计编号 AS trust_accounting_account, ");
        sql.append(" 信托会计名称 AS trust_accounting ");
//        sql.append(" FROM MONITOR.\"RPAbank_view\" ");
        sql.append(" FROM RPAbank_view ");
        sql.append(" where 1=1 ");
        SqlWrapper wrapper = new SqlWrapper(sql.toString());
        return DB.byName("oracle").findList(KwRPABankViewRet.class, wrapper.getSql(), wrapper.getParams().toArray());
    }

    @Override
    public void updateBankAccountExpand() {
        updateBankAccountExpand(getBankViewList(new KwRPABankViewQueryArgv()));
    }

    public void updateBankAccountExpand(List<KwRPABankViewRet> list) {
        for (KwRPABankViewRet ret : list) {
            PageDataRet<KwBankAccountExpandRet> retList = kwBankAccountExpandService.query(initBankAccountExpandQueryArgv(ret.getAccount(), ret.getProNum()));
            if (retList != null && retList.getList() != null && retList.getList().size() > 0) {
                KwBankAccountExpandArgv argv = converBankAccountExpandArgv(ret);
                argv.setId(retList.getList().get(0).getId());
                kwBankAccountExpandService.edit(argv);
            } else {
                KwBankAccountExpandArgv argv = converBankAccountExpandArgv(ret);
                kwBankAccountExpandService.add(argv);
            }
        }
    }

    private KwBankAccountExpandArgv converBankAccountExpandArgv(KwRPABankViewRet view) {
        KwBankAccountExpandArgv argv = BeanUtils.copyObject(view, KwBankAccountExpandArgv.class);
        return argv;
    }

    private KwBankAccountExpandQueryArgv initBankAccountExpandQueryArgv(String account, String proNum) {
        KwBankAccountExpandQueryArgv argv = new KwBankAccountExpandQueryArgv();
        argv.setPageQuery(false);
        argv.setAccount(account);
        argv.setProNum(proNum);
        return argv;
    }

    @Override
    public void updateBankAccount() {
        updateBankAccount(getBankViewList(new KwRPABankViewQueryArgv()));
    }

    @Override
    public void updateBankAccountAllByView() {
        logger.info("开始更新 kw_user...");
        kwRPAUserViewService.updateUsers();
        logger.info("结束更新 kw_user...");

        logger.info("开始更新 kw_bank_account_expand...");
        updateBankAccountExpand();
        logger.info("结束更新 kw_bank_account_expand...");

        logger.info("开始更新 kw_bank_account_manager...");
        kwRPAAcrmViewService.updateBankAccountManager();
        logger.info("结束更新 kw_bank_account_manager...");

        logger.info("开始更新 kw_bank_account...");
        updateBankAccount();
        logger.info("开始更新 kw_bank_account...");
    }

    public void updateBankAccount(List<KwRPABankViewRet> list) {
        for (KwRPABankViewRet ret : list) {
            PageDataRet<KwBankAccountRet> retList = kwBankAccountService.queryBankAccountWithExpand(initBankAccountQueryArgv(ret.getAccount(), ret.getProNum()));
            if (retList != null && retList.getList() != null && retList.getList().size() > 0) {
                KwBankAccountArgv argv = converBankAccountArgv(ret);
                argv.setId(retList.getList().get(0).getId());
                kwBankAccountService.edit(argv);
            } else {
                PageDataRet<KwBankAccountExpandRet> proRetList = kwBankAccountExpandService.query(initBankAccountExpandQueryArgv(ret.getAccount(), ret.getProNum()));
                if (proRetList != null && proRetList.getList() != null && proRetList.getList().size() > 0) {
                    KwBankAccountArgv argv = converBankAccountArgv(ret);
                    argv.setRelationId(proRetList.getList().get(0).getId());
                    kwBankAccountService.add(argv);
                }
            }
        }
        // 添加用户by bank_account中的项目经理&项目经理编号
        insertSysUser(querySysUserByManager(true), true);
        insertSysUser(querySysUserByManager(false), false);
    }
    private List<KwBankAccountExpandRet> querySysUserByManager(boolean isPm) {
        // 拼装sql
        StringBuilder sql = new StringBuilder();

        if (isPm) {
            sql.append(" select DISTINCT (kbae.pro_pm_account) as pro_pm_account, kbae.pro_pm ");
            sql.append(" 		from kw_bank_account_expand kbae  ");
            sql.append(" 		left join sys_user su on su.username = kbae.pro_pm_account  ");
        } else {
            sql.append(" select DISTINCT (kbae.trust_accounting_account) as trust_accounting_account, kbae.trust_accounting ");
            sql.append(" 		from kw_bank_account_expand kbae  ");
            sql.append(" 		left join sys_user su on su.username = kbae.trust_accounting_account  ");
        }
        sql.append(" 		where su.id is null ");
        SqlWrapper wrapper = new SqlWrapper(sql.toString());
        // 拼装查询sql
        return DB.findList(KwBankAccountExpandRet.class, wrapper.getSql(), wrapper.getParams().toArray());
    }

    private void insertSysUser(List<KwBankAccountExpandRet> list, boolean isPm) {
        for (KwBankAccountExpandRet ret : list) {
            SysUserArgv user = new SysUserArgv();

            user.setUsername(isPm ? ret.getProPmAccount() : ret.getTrustAccountingAccount());
            user.setRealName(isPm ? ret.getProPm() : ret.getTrustAccounting());
            user.setPassword("S0BBZm0yMDIy"); // K@Afm2022
            user.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
            user.setSex(0);
            user.setStatus(1);
            sysUserService.add(user);

            insertSysUserRole(isPm ? ret.getProPmAccount() : ret.getTrustAccountingAccount(), isPm);
        }
    }

    private void insertSysUserRole(String username, boolean isPm) {
        // 拼装sql
        StringBuilder sql = new StringBuilder();
        sql.append(" INSERT INTO sys_user_role (id, sys_user_id, sys_role_id, who_created, when_created) ");
        sql.append(" 			select MD5(uuid()), su.id, sr.id, '056fb0eeb9a44cb0953534b4c0ca01fa', CURRENT_TIMESTAMP() ");
        sql.append(" 			from sys_user su  ");
        if (isPm) {
            sql.append(" left join sys_role sr on sr.code = 'customer_manager' ");
        } else {
            sql.append(" left join sys_role sr on sr.code = 'trust_accounting' ");
        }
        sql.append(" 			where 1=1 ");
        SqlWrapper wrapper = new SqlWrapper(sql.toString());
        wrapper.addCondition("su.username", Op.EQ, username);
        DB.executeUpdateSql(wrapper.getSql(), wrapper.getParams().toArray());
    }


    private KwBankAccountArgv converBankAccountArgv(KwRPABankViewRet view) {
        KwBankAccountArgv argv = BeanUtils.copyObject(view, KwBankAccountArgv.class);
        int accountType = 0;
        switch (view.getAccountType()) {
            case "信托专户":
                accountType = 0;
                break;
            case "托管户":
                accountType = 1;
                break;
            case "辅助账户":
                accountType = 2;
                break;
            default:
                accountType = 0;
        }
        argv.setAccountType(accountType);
        argv.setRelationType(2);
        argv.setBankDeposit(view.getBankName());
        argv.setAccountStatus(view.getAccountStatus());
        Date createTime = DateUtils.toDate(view.getCreateAccountTime(), "yyyy/M/d");
        Timestamp createAccountTime = createTime != null ? new Timestamp(createTime.getTime()) : null;
        Date cancelTime = DateUtils.toDate(view.getCreateAccountTime(), "yyyy/M/d");
        Timestamp cancelAccountTime = createTime != null ? new Timestamp(cancelTime.getTime()) : null;
        argv.setCreateAccountTime(createAccountTime);
        argv.setCancelAccountTime(cancelAccountTime);
        return argv;
    }



    private KwBankAccountQueryArgv initBankAccountQueryArgv(String account, String proNum) {
        KwBankAccountQueryArgv argv = new KwBankAccountQueryArgv();
        argv.setPageQuery(false);
        argv.setAccount(account);
        argv.setProNum(proNum);
        return argv;
    }

}
