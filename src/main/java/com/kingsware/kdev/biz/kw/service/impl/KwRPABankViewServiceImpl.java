package com.kingsware.kdev.biz.kw.service.impl;

import com.kingsware.kdev.biz.kw.argv.*;
import com.kingsware.kdev.biz.kw.ret.*;
import com.kingsware.kdev.biz.kw.service.*;
import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.SqlWrapper;
import com.kingsware.kdev.core.util.BeanUtils;
import com.kingsware.kdev.core.util.DateUtils;
import com.kingsware.kdev.sys.argv.SysUserArgv;
import com.kingsware.kdev.sys.argv.SysUserQueryArgv;
import com.kingsware.kdev.sys.ret.SysUserRet;
import com.kingsware.kdev.sys.service.SysUserService;
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

    @Resource
    private KwBankAccountExpandService kwBankAccountExpandService;

    @Resource
    private KwBankAccountService kwBankAccountService;

    @Resource
    private KwRPAAcrmViewService kwRPAAcrmViewService;

    @Resource
    private KwRPAUserViewService kwRPAUserViewService;

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
        kwRPAUserViewService.updateUsers();
        updateBankAccountExpand();
        kwRPAAcrmViewService.updateBankAccountManager();
        updateBankAccount();
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
