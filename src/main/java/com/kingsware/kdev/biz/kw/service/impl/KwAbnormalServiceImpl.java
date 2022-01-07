package com.kingsware.kdev.biz.kw.service.impl;

import com.kingsware.kdev.biz.kw.argv.KwAbnormalQueryArgv;
import com.kingsware.kdev.biz.kw.argv.KwBankAccountArgv;
import com.kingsware.kdev.biz.kw.argv.KwWaterQueryArgv;
import com.kingsware.kdev.biz.kw.model.KwBankAccount;
import com.kingsware.kdev.biz.kw.model.KwCompany;
import com.kingsware.kdev.biz.kw.model.KwWater;
import com.kingsware.kdev.biz.kw.ret.KwAbnormalRet;
import com.kingsware.kdev.biz.kw.ret.KwBankAccountRet;
import com.kingsware.kdev.biz.kw.ret.KwCompanyRet;
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

import java.math.BigDecimal;
import java.util.*;
import java.util.zip.DeflaterOutputStream;


@Service
public class KwAbnormalServiceImpl extends BaseServiceImpl implements KwAbnormalService {


    /**
     * 异常总汇页面
     *
     * @param argv
     * @return
     */
    @Override
    public PageDataRet<KwAbnormalRet> queryAbnormal(KwAbnormalQueryArgv argv) {
        return null;
    }

    /**
     * 检查异常余额,并标记
     */
    @Override
    public void checkbalance() {
        // 1 清除异常流水标记
        this.resetAbnormal();
        List<String> ids = new ArrayList<>(); // 问题流水列表

        // 2 查找所有账号ID
        List<String> accountIds = this.findAllAccountId();
        System.out.println(accountIds);
        for (String accountId : accountIds) {
            // 3 查找账号下的所有流水
            List<KwWater> waters = this.findWaterByAccountId(accountId);
            // 4 检查异常流水
            ids = this.checkBalance(ids,waters);
        }
        System.out.println(ids);

        // 5 标记异常流水
        for (String id : ids) {
            this.flagAbnormalWater(id);
        }

    }

    /**
     * 将流水的异常字段标志为 0
     */
    private void resetAbnormal() {
        String sql = "update kw_water set abnormal = 0 where abnormal=1;";
        DB.executeUpdateSql(sql);
    }

    /**
     * 查找账号ID列表
     *
     * @return
     */
    private List<String> findAllAccountId() {
        String sql = " select id from kw_bank_account where 1=1 and id is not null ";
        SqlWrapper wrapper = new SqlWrapper(sql);
        List<String> list = DB.findSingleAttributeList(String.class, wrapper.getSql(), wrapper.getParams().toArray());
        return list;
    }

    /**
     * 查找账号下的流水，日期升序、数据次序升序
     *
     * @param accountId
     * @return
     */
    private List<KwWater> findWaterByAccountId(String accountId) {
        String sql = " select * from kw_water kw where 1=1 and kw.account_id = ? order by kw.transaction_date asc,kw.date_index asc ";
        List<KwWater> list = DB.findList(KwWater.class, sql, accountId);
        return list;
    }

    /**
     * 检查余额
     * @param ids     异常id列表
     * @param waters  检查的流水列表  （* 同一账号，有序）
     * @return
     */
    private List<String> checkBalance(List<String> ids, List<KwWater> waters){
        if(waters.size() < 1){
            return ids;
        }
        String sBalance = waters.get(0).getAccountBalance();
        if(sBalance.isEmpty()){
            return ids;
        }
        //获取前一条流水的交易日期
        Date preDate = waters.get(0).getTransactionDate();
        BigDecimal bg = this.changeBigDecimal(sBalance);
        int revenue = 0;//0是收入,1是支出
        for(int i = 1; i<waters.size(); i++){
            KwWater we = waters.get(i);
            revenue = we.getRevenue();
//            System.out.println(we.getAccountBalance() +"   " +(we.getRevenue()==1?"-":"+") +"  "+we.getTransactionAmount());
            BigDecimal bgresultBalance = this.changeBigDecimal(we.getAccountBalance());
            BigDecimal bgamount = this.changeBigDecimal(we.getTransactionAmount());
            if(revenue == 0){
                if(bgamount.add(bg).compareTo(bgresultBalance) == 0){
                    bg = bgresultBalance;
                    continue;
                }
                bg = bgresultBalance;
                System.out.println("-- 异常流水 -- " + we.getId() + " -- " + we.getAccount()+ " -- " + we.getTransactionAmount()+ " -- " + we.getAccountBalance());
                ids.add(we.getId());

            } else if(revenue == 1)
            {
                if(bg.subtract(bgamount).compareTo(bgresultBalance)==0){
                    bg = bgresultBalance;
                    continue;
                }
                System.out.println("-- 异常流水 -- " + we.getId() + " -- " + we.getAccount()+ " -- " + we.getTransactionAmount()+ " -- " + we.getAccountBalance());
                ids.add(we.getId());
                bg = bgresultBalance;
            }
        }
        return ids;
    }

    private BigDecimal changeBigDecimal(String sBalance){
        double dBalance = Double.valueOf(sBalance);//数值过大时会自动转科学计数法
        return new BigDecimal(dBalance).setScale(2,BigDecimal.ROUND_HALF_UP);
    }

    private void flagAbnormalWater(String id) {
        String sql = "update kw_water set abnormal = 1 where id=?;";
        DB.executeUpdateSql(sql,id);
    }


    /**
     * 异常余额页面
     *
     * @param argv
     * @return
     */
    @Override
    public PageDataRet<KwWaterRet> queryAbnormalWater(KwWaterQueryArgv argv) {
        // 基础sql
        SqlWrapper wrapper = new SqlWrapper(" SELECT km.bank_name as mechanism_name,ke.name as edition_name,kw.* FROM kw_water kw " +
                " LEFT JOIN kw_receipt kr on kw.receipt_id = kr.id" +
                " LEFT JOIN kw_bank_account kba on kw.account_id = kba.id " +
                " LEFT JOIN kw_edition ke on kba.edition_id = ke.id " +
                " LEFT JOIN kw_mechanism km on ke.mechanism_id = km.id " +
                " where 1=1 " +
                " and kw.abnormal=1 " );

        // 拼装查询sql,并注入参数
        if (argv.getEditionId() != null) {
            wrapper.addCondition("kba.edition_id", Op.EQ, argv.getEditionId());
        }
        if (argv.getEditionName()!=null&&StringUtils.isNotEmpty(argv.getEditionName())) {
            wrapper.addCondition("ke.name", Op.LIKE, "%"+argv.getEditionName() +"%");
        }
        if (argv.getAccount()!=null&&StringUtils.isNotEmpty(argv.getAccount())) {
            wrapper.addCondition("kw.account", Op.LIKE, "%"+argv.getAccount() +"%");
        }
        if (argv.getStartDate()!=null&&StringUtils.isNotEmpty(argv.getStartDate())) {
            wrapper.addCondition("kw.transaction_date", Op.BETWEEN, argv.getStartDate(),argv.getEndDate());
        }
        // 排序
        wrapper.sortBy("ORDER BY kw.transaction_date desc,date_index desc");
        // 执行查询
        PageDataRet<? extends BaseSimpleRet> query = query(wrapper.getSql(), wrapper.getParams(), argv, KwWaterRet.class);

        return (PageDataRet<KwWaterRet>) query;
    }

    /**
     * 查询异常流水上下文
     *
     * @param waterId
     * @return
     */
    @Override
    public List<KwWaterRet> getAbnormalWater(String waterId) {
        List<KwWaterRet> retList = new ArrayList<>();
        // 1、查找本条流水
        KwWater model = DB.findById(KwWater.class, waterId);
        KwWaterRet curWaterRet = (KwWaterRet) model2Ret(model, KwWaterRet.class);
        final Date transactionDate = curWaterRet.getTransactionDate();
        final Integer dateIndex = curWaterRet.getDateIndex();


        // 2、查找前n条流水
        // 同一天

        // 非同一天


        // 3、查找后n条流水
        // 同一天

        // 非同一天

        return retList;
    }


    private List<KwWaterRet> findNearlyWater(int type,Date transactionDate,Integer dateIndex){
        String sql;
        switch (type){
            case 1:
                // 同一天，次序在本条之前
                sql="select * from kw_water where transactionDate=? and dateIndex<? order by transactionDate desc,dateIndex desc limit 0,5";
                break;

            case 2:
                // 早于本日
                sql="select * from kw_water where transactionDate < ?  order by transactionDate desc,dateIndex desc limit 0,5 ";
                break;
            case 3:
                // 同一天，次序在本条之后
                sql="select * from kw_water where transactionDate = ? and dateIndex>?  order by transactionDate asc,dateIndex asc limit 0,5 ";
                break;
            case 4:
                // 晚于本日
                sql="select * from kw_water where transactionDate > ?   order by transactionDate asc,dateIndex asc limit 0,5 ";
                break;
        }

        return null;
    }


}
