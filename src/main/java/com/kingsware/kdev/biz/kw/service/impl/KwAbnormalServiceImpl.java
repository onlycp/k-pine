package com.kingsware.kdev.biz.kw.service.impl;

import com.kingsware.kdev.biz.kw.argv.KwAbnormalQueryArgv;
import com.kingsware.kdev.biz.kw.argv.KwBankAccountArgv;
import com.kingsware.kdev.biz.kw.argv.KwWaterQueryArgv;
import com.kingsware.kdev.biz.kw.model.KwBankAccount;
import com.kingsware.kdev.biz.kw.model.KwWater;
import com.kingsware.kdev.biz.kw.ret.KwAbnormalRet;
import com.kingsware.kdev.biz.kw.ret.KwBankAccountRet;
import com.kingsware.kdev.biz.kw.ret.KwWaterRet;
import com.kingsware.kdev.biz.kw.service.KwAbnormalService;
import com.kingsware.kdev.biz.kw.service.KwWaterService;
import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.BasePageArgv;
import com.kingsware.kdev.core.bean.BaseSimpleRet;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.SqlWrapper;
import com.kingsware.kdev.core.orm.expression.Op;
import com.kingsware.kdev.core.util.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class KwAbnormalServiceImpl extends BaseServiceImpl implements KwAbnormalService {


    /**
     * 异常总汇页面
     * @param argv
     * @return
     */
    @Override
    public PageDataRet<KwAbnormalRet> queryAbnormal(KwAbnormalQueryArgv argv) {
        return null;
    }

    /**
     * 检查异常余额
     */
    @Override
    public void checkbalance() {
        this.resetAbnormal();
        Set<String> accountIds = this.findAllAccountId();
        System.out.println(accountIds);
        for (String accountId : accountIds) {




        }

    }
    /**
     * 检查余额异常
     * 0、将流水的异常字段标志为 0
     * 1、查找账号ID列表
     * 2、账号下，交易日期、次序降序查询
     * 3、查找余额，交易金额 出现问题的流水
     * 4、将问题流水的异常字段标志为 1
     */

    /**
     * 将流水的异常字段标志为 0
     */
    private void resetAbnormal(){
        String sql = "update kw_water set abnormal = 0 where abnormal=1;";
//        SqlWrapper wrapper = new SqlWrapper(sql);
//        query(sql,new ArrayList<>(),new BasePageArgv(),BaseSimpleRet.class);
        DB.executeUpdateSql(sql);
    }

    /**
     * 查找账号ID列表
     * @return
     */
    private Set<String> findAllAccountId(){
        Set<String> set = new HashSet();
        String sql = " select * from kw_bank_account where 1=1 ";
        SqlWrapper wrapper = new SqlWrapper(sql);
//        List<KwBankAccount> list = DB.findList(KwBankAccount.class, sql);
        List<String> list = DB.findSingleAttributeList(String.class, wrapper.getSql(), wrapper.getParams());
        for (String id : list) {
            set.add(id);
        }

        return set;
    }

    private List<KwWater> findWaterByAccount(String accountId){
        List<KwWater> waters = new ArrayList<>();
        String sql = " select * from kw_bank_account where 1=1 ";



        return waters;
    }



    /**
     * 异常余额页面
     * @param argv
     * @return
     */
    @Override
    public PageDataRet<KwWaterRet> queryAbnormalWater(KwWaterQueryArgv argv) {
        return null;
    }

    /**
     * 查询异常流水上下文
     * @param Id
     * @return
     */
    @Override
    public List<KwWaterRet> getAbnormalWater(String Id) {
        return null;
    }
}
