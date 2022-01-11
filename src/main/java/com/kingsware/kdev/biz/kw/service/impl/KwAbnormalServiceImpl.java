package com.kingsware.kdev.biz.kw.service.impl;

import com.kingsware.kdev.biz.kw.argv.KwAbnormalQueryArgv;
import com.kingsware.kdev.biz.kw.argv.KwWaterQueryArgv;
import com.kingsware.kdev.biz.kw.model.KwEdition;
import com.kingsware.kdev.biz.kw.model.KwMechanism;
import com.kingsware.kdev.biz.kw.model.KwWater;
import com.kingsware.kdev.biz.kw.ret.KwAbnormalRet;
import com.kingsware.kdev.biz.kw.ret.KwNumRet;
import com.kingsware.kdev.biz.kw.ret.KwWaterRet;
import com.kingsware.kdev.biz.kw.service.KwAbnormalService;
import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.BaseSimpleRet;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.cache.dict.DictTask;
import com.kingsware.kdev.core.cron.KTask;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.SqlWrapper;
import com.kingsware.kdev.core.orm.expression.Op;
import com.kingsware.kdev.core.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class KwAbnormalServiceImpl extends BaseServiceImpl implements KwAbnormalService {


    /**
     * 异常总汇页面
     * @param argv
     * @return
     */
    @Override
    public PageDataRet<KwAbnormalRet> queryAbnormal(KwAbnormalQueryArgv argv) {
        String mechanismName;
        String mechanismId;
        String editionName;
        String editionId;
        Integer accountNum;
        Integer balanceException;
        Integer noReceipt;
        Integer noWater;
        PageDataRet<KwAbnormalRet> pageDataRet = new PageDataRet<>();
        List<KwAbnormalRet> list = new ArrayList<>();

        // 1 查询所有行别
        List<KwMechanism> mechanisms = this.findMechaism();
        for (KwMechanism mechanism : mechanisms) {
            mechanismName = mechanism.getBankName();
            mechanismId = mechanism.getId();
            // 2 查询所有版本
            List<KwEdition> editions = this.findEditionByMechaisId(mechanismId);
            for (KwEdition edition : editions) {
                editionName = edition.getName();
                editionId = edition.getId();

                // 查询条件 版本
                if ((argv.getEditionId()!=null || StringUtils.isNotEmpty(argv.getEditionId()) )&& !editionId.equals(argv.getEditionId()))// （ 有传editionId 或 传的不是空串 ） 且 与本循环不一致
                    continue;

                // 3 账号数量
                accountNum = this.countAccountByEditionId(editionId);
                if (accountNum<=0) // 没有账号，跳过
                    continue;

                // 4 余额异常数量
                balanceException = this.countBalanceException(editionId,argv);

                // 5 没有回单的流水
                noReceipt = this.countNoReceipt(editionId,argv);

                // 6 没有流水的回单
                noWater = this.countNoWater(editionId,argv);

                if((noWater + noReceipt + balanceException)<=0) // 该版本下没有异常
                    continue;

                // 7 封装返回对象
                KwAbnormalRet kwAbnormalRet = new KwAbnormalRet();
                kwAbnormalRet.setMechanismId(mechanismId);
                kwAbnormalRet.setMechanismName(mechanismName);
                kwAbnormalRet.setEditionId(editionId);
                kwAbnormalRet.setEditionName(editionName);
                kwAbnormalRet.setAccountNum(accountNum);
                kwAbnormalRet.setBalanceException(balanceException);
                kwAbnormalRet.setNoReceipt(noReceipt);
                kwAbnormalRet.setNoWater(noWater);

                list.add(kwAbnormalRet);
            }
        }
        pageDataRet.setList(list);

        return pageDataRet;
    }

    /**
     * 查询行别，通过名称
     * @return
     */
    private List<KwMechanism> findMechaism(){
        String sql = "select * from kw_mechanism where 1 = 1 and id is not null ";
        List<KwMechanism> mechanisms = DB.findList(KwMechanism.class, sql);
        return mechanisms;
    }

    /**
     * 查询版本,通过行别id
     * @param mechaismId
     * @return
     */
    private List<KwEdition> findEditionByMechaisId(String mechaismId){
        String sql = "select * from kw_edition where 1=1 and mechanism_id = ? ";
        List<KwEdition> editions = DB.findList(KwEdition.class, sql, mechaismId);
        return editions;
    }

    /**
     * 版本下, 账号数量
     * @param editionId
     * @return
     */
    private Integer countAccountByEditionId(String editionId){
        String sql = "select COUNT(id) as num1 FROM kw_bank_account where edition_id = ?";
        KwNumRet one =DB.findOne(KwNumRet.class, sql,editionId);
        return one.getNum1();
    }

    /**
     * 版本下， 余额异常账号数量
     * @param editionId
     * @param argv
     * @return
     */
    private Integer countBalanceException(String editionId, KwAbnormalQueryArgv argv){
        String sql = "SELECT count(kw.id) as num1 from kw_water kw " +
                "LEFT JOIN kw_bank_account kba on kba.id=kw.account_id " +
                "LEFT JOIN kw_edition ke on ke.id = kba.edition_id " +
                "where abnormal=1 " +
                "and ke.id = ? ";
        List<Object> params = new ArrayList<>();
        params.add(editionId);


        if (argv!=null && argv.getStartDate()!=null && argv.getEndDate() !=null && StringUtils.isNotEmpty(argv.getStartDate())){
            sql += " and kw.transaction_date BETWEEN ? and ? ";
            params.add(argv.getStartDate());
            params.add(argv.getEndDate());
        }
        if (argv!=null && argv.getAccount()!=null ){
            sql += " and kw.account like ? ";
            params.add("%"+argv.getAccount()+"%");

        }

        KwNumRet one = DB.findOne(KwNumRet.class, sql, params.toArray());
//        System.out.println(editionId+" -- 余额异常 -- "+one.getNum1());

        return one.getNum1();
    }

    /**
     * 版本下， 没有回单的流水
     * @param editionId
     * @param argv
     * @return
     */
    private Integer countNoReceipt(String editionId, KwAbnormalQueryArgv argv){
        String sql = "SELECT count(kw.id) as num1 from kw_water kw " +
                "LEFT JOIN kw_bank_account kba on kba.id=kw.account_id " +
                "LEFT JOIN kw_edition ke on ke.id =  kba.edition_id " +
                "where kw.has_receipt=0 " +
                "and ke.id = ? ";
        SqlWrapper wrapper = new SqlWrapper(sql);
        List<Object> params = wrapper.getParams();
        params.add(editionId);

        if (argv!=null && argv.getStartDate()!=null && argv.getEndDate() !=null && StringUtils.isNotEmpty(argv.getStartDate())){
            wrapper.addCondition("kw.transaction_date",Op.BETWEEN,argv.getStartDate(),argv.getEndDate());
        }
        if (argv!=null && argv.getAccount()!=null ){
            wrapper.addCondition("kw.account",Op.LIKE,"%"+argv.getAccount()+"%");
        }

        KwNumRet one = DB.findOne(KwNumRet.class, wrapper.getSql(), params.toArray());
        return one.getNum1();
    }

    /**
     * 版本下， 没有流水的回单
     * @param editionId
     * @param argv
     * @return
     */
    private Integer countNoWater(String editionId, KwAbnormalQueryArgv argv){
        String sql = "SELECT count(kr.id) as num1 from kw_receipt kr " +
                "LEFT JOIN kw_bank_account kba on kba.id=kr.account_id " +
                "LEFT JOIN kw_edition ke on ke.id =  kba.edition_id " +
                "where kr.has_water=0 " +
                "and ke.id = ? ";
        SqlWrapper wrapper = new SqlWrapper(sql);
        List<Object> params = wrapper.getParams();

        params.add(editionId);
//        System.out.println(" -- "+wrapper.getParams());

        if (argv!=null && argv.getStartDate()!=null && argv.getEndDate() !=null && StringUtils.isNotEmpty(argv.getStartDate())){
            wrapper.addCondition("kr.book_date",Op.BETWEEN,argv.getStartDate(),argv.getEndDate());
        }
        if (argv!=null && argv.getAccount()!=null ){
            wrapper.addCondition("kr.self_account",Op.LIKE,"%"+argv.getAccount()+"%");
        }

        KwNumRet one = DB.findOne(KwNumRet.class, wrapper.getSql(), params.toArray());
        return one.getNum1();
    }




    /**
     * 检查异常余额,并标记
     */
    @Override
    public void checkBalance() {
        // 1 清除异常流水标记
        this.resetAbnormal();
        List<String> ids = new ArrayList<>(); // 问题流水列表

        // 2 查找所有账号ID
        List<String> accountIds = this.findAllAccountId();
//        System.out.println(accountIds);
        for (String accountId : accountIds) {
            // 3 查找账号下的所有流水
            List<KwWater> waters = this.findWaterByAccountId(accountId);
            // 4 检查异常流水
            ids = this.checkBalance(ids,waters);
        }
//        System.out.println(ids);
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
                " and kw.abnormal = 1 " );

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
    public List<KwWaterRet> getWaterContext(String waterId) {
        List<KwWaterRet> retList = new ArrayList<>();
        // 1、查找本条流水
        KwWater model = DB.findById(KwWater.class, waterId);
        KwWaterRet curWaterRet = (KwWaterRet) model2Ret(model, KwWaterRet.class);
        final String accountId = curWaterRet.getAccountId();
        final Date transactionDate = curWaterRet.getTransactionDate();
        final Integer dateIndex = curWaterRet.getDateIndex();

        System.out.println(accountId+" -- "+transactionDate+" -- "+dateIndex);
        // 2、查找前n条流水
        // 同一天 2
        List<KwWaterRet> nearlyWater1 = null;
        List<KwWaterRet> nearlyWater2;
        List<KwWaterRet> nearlyWater3;
        List<KwWaterRet> nearlyWater4 = null;
        nearlyWater2 = this.findNearlyWater(2, accountId, transactionDate, dateIndex);
//        System.out.println(nearlyWater2);

        // 非同一天 1
        if(nearlyWater2.size()<5){
            nearlyWater1 = this.findNearlyWater(1, accountId, transactionDate, dateIndex);
        }


        // 3、查找后n条流水
        // 同一天  3
        nearlyWater3 = this.findNearlyWater(3, accountId, transactionDate, dateIndex);
//        System.out.println(nearlyWater3);
        // 非同一天 4
        if (nearlyWater3.size() < 5){
            nearlyWater4 = this.findNearlyWater(4, accountId, transactionDate, dateIndex);
        }


        // 拼接返回列表
        // 早于本日
        if(nearlyWater1 !=null && nearlyWater1.size() >0 ){
            Collections.reverse(nearlyWater1);
            for (KwWaterRet kwWaterRet : nearlyWater1) {
//                System.out.println("type 1 == "+kwWaterRet.getTransactionDate()+" -- "+kwWaterRet.getDateIndex());
                retList.add(kwWaterRet);
            }
        }
        // 同日，早于本条
        if (nearlyWater2 !=null && nearlyWater2.size() >0 ){
            Collections.reverse(nearlyWater2);
            for (KwWaterRet kwWaterRet : nearlyWater2) {
//                System.out.println("type 2 == "+kwWaterRet.getTransactionDate()+" -- "+kwWaterRet.getDateIndex());
                retList.add(kwWaterRet);
            }
        }
        // 本条
//        System.out.println("本条 == "+curWaterRet.getTransactionDate()+" -- "+curWaterRet.getDateIndex());
        retList.add(curWaterRet);
        // 同日，晚于本条
        if (nearlyWater3 !=null && nearlyWater3.size() >0 ){
            for (KwWaterRet kwWaterRet : nearlyWater3) {
//                System.out.println("type 3 == "+kwWaterRet.getTransactionDate()+" -- "+kwWaterRet.getDateIndex());
                retList.add(kwWaterRet);
            }
        }
        // 晚于本日
        if (nearlyWater4 !=null && nearlyWater4.size() >0 ){
            for (KwWaterRet kwWaterRet : nearlyWater4) {
//                System.out.println("type 4 == "+kwWaterRet.getTransactionDate()+" -- "+kwWaterRet.getDateIndex());
                retList.add(kwWaterRet);
            }
        }

        return retList;
    }


    private List<KwWaterRet> findNearlyWater(int type,String accountId,Date transactionDate,Integer dateIndex){
        String sql;
        List<KwWaterRet> list;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = df.format(transactionDate);

        switch (type){
            case 1:
                // 早于本日
                sql="select * from kw_water where account_id = ? and transaction_date < ?  order by transaction_date desc,date_index desc limit 0,5 ";
                dateStr+=" 00:00:00";
                list = DB.findList(KwWaterRet.class,sql,accountId,dateStr);
                break;
            case 2:
                // 同一天，次序在本条之前
                sql="select * from kw_water where account_id = ? and transaction_date like ? and date_index<? order by transaction_date desc,date_index desc limit 0,5";
                dateStr+="%";
                list = DB.findList(KwWaterRet.class,sql,accountId,dateStr,dateIndex);
                break;
            case 3:
                // 同一天，次序在本条之后
                sql="select * from kw_water where account_id = ? and transaction_date like ? and date_index>?  order by transaction_date asc,date_index asc limit 0,5 ";
                dateStr+="%";
                list = DB.findList(KwWaterRet.class,sql,accountId,dateStr,dateIndex);
                break;
            case 4:
                // 晚于本日
                sql="select * from kw_water where account_id = ? and transaction_date > ?   order by transaction_date asc,date_index asc limit 0,5 ";
                dateStr+=" 23:59:59";
                list = DB.findList(KwWaterRet.class,sql,accountId,dateStr);
                break;
            default:
                return null;
        }
        return list;
    }





}
