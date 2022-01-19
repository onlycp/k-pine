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
import com.kingsware.kdev.core.excel.ExcelWorker;
import com.kingsware.kdev.core.excel.KExcel;
import com.kingsware.kdev.core.excel.RegionDefine;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.SqlWrapper;
import com.kingsware.kdev.core.orm.expression.Op;
import com.kingsware.kdev.core.util.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;


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
                if ((argv.getEditionId() != null || StringUtils.isNotEmpty(argv.getEditionId())) && !editionId.equals(argv.getEditionId()))// （ 有传editionId 或 传的不是空串 ） 且 与本循环不一致
                    continue;

                // 3 账号数量
                accountNum = this.countAccountByEditionId(editionId);
                if (accountNum <= 0) // 没有账号，跳过
                    continue;

                // 4 余额异常数量
                balanceException = this.countBalanceException(editionId, argv);

                // 5 没有回单的流水
                noReceipt = this.countNoReceipt(editionId, argv);

                // 6 没有流水的回单
                noWater = this.countNoWater(editionId, argv);

                if ((noWater + noReceipt + balanceException) <= 0) // 该版本下没有异常
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
     * 查询行别
     *
     * @return
     */
    private List<KwMechanism> findMechaism() {
        String sql = "select * from kw_mechanism where deleted = 0 and id is not null ";
        List<KwMechanism> mechanisms = DB.findList(KwMechanism.class, sql);
        return mechanisms;
    }

    /**
     * 查询版本,通过行别id
     *
     * @param mechaismId
     * @return
     */
    private List<KwEdition> findEditionByMechaisId(String mechaismId) {
        String sql = "select * from kw_edition where deleted = 0 and mechanism_id = ? ";
        List<KwEdition> editions = DB.findList(KwEdition.class, sql, mechaismId);
        return editions;
    }

    /**
     * 版本下, 账号数量
     *
     * @param editionId
     * @return
     */
    private Integer countAccountByEditionId(String editionId) {
        String sql = "select COUNT(id) as num1 FROM kw_bank_account kba where deleted = 0 and edition_id = ?";

        SqlWrapper wrapper = new SqlWrapper(sql);
        wrapper.getParams().add(editionId);
        wrapper.withAuthority("kw_bank_account", "kba");

        KwNumRet one = DB.findOne(KwNumRet.class, wrapper.getSql(), wrapper.getParams().toArray());
        return one.getNum1();
    }

    /**
     * 版本下， 余额异常账号数量
     *
     * @param editionId
     * @param argv
     * @return
     */
    private Integer countBalanceException(String editionId, KwAbnormalQueryArgv argv) {
        String sql = "SELECT count(kw.id) as num1 from kw_water kw " +
                "LEFT JOIN kw_bank_account kba on kba.account = kw.account and kba.deleted =0  " +
                "LEFT JOIN kw_edition ke on ke.id =  kba.edition_id and ke.deleted = 0 " +
                "where abnormal=1 " +
                "and ke.id = ? ";

        SqlWrapper wrapper = new SqlWrapper(sql);
        wrapper.getParams().add(editionId);


        if (argv != null && argv.getStartDate() != null && argv.getEndDate() != null && StringUtils.isNotEmpty(argv.getStartDate())) {
            wrapper.addCondition("kw.transaction_date", Op.BETWEEN, argv.getStartDate(), argv.getEndDate());
        }
        if (argv != null && argv.getAccount() != null) {
            wrapper.addCondition("kw.account", Op.LIKE, "%" + argv.getAccount() + "%");
        }

        wrapper.withAuthority("kw_bank_account", "kba");

        KwNumRet one = DB.findOne(KwNumRet.class, wrapper.getSql(), wrapper.getParams().toArray());
//        System.out.println(editionId+" -- 余额异常 -- "+one.getNum1());

        return one.getNum1();
    }

    /**
     * 版本下， 没有回单的流水
     *
     * @param editionId
     * @param argv
     * @return
     */
    private Integer countNoReceipt(String editionId, KwAbnormalQueryArgv argv) {
        String sql = "SELECT count(kw.id) as num1 from kw_water kw " +
                "LEFT JOIN kw_bank_account kba on kba.account=kw.account and kba.deleted =0  " +
                "LEFT JOIN kw_edition ke on ke.id =  kba.edition_id and ke.deleted = 0 " +
                "where kw.has_receipt=0 " +
                "and ke.id = ? ";
        SqlWrapper wrapper = new SqlWrapper(sql);
        wrapper.getParams().add(editionId);


        if (argv != null && argv.getStartDate() != null && argv.getEndDate() != null && StringUtils.isNotEmpty(argv.getStartDate())) {
            wrapper.addCondition("kw.transaction_date", Op.BETWEEN, argv.getStartDate(), argv.getEndDate());
        }
        if (argv != null && argv.getAccount() != null) {
            wrapper.addCondition("kw.account", Op.LIKE, "%" + argv.getAccount() + "%");
        }
        wrapper.withAuthority("kw_bank_account", "kba");
        KwNumRet one = DB.findOne(KwNumRet.class, wrapper.getSql(), wrapper.getParams().toArray());
        return one.getNum1();
    }

    /**
     * 版本下， 没有流水的回单
     *
     * @param editionId
     * @param argv
     * @return
     */
    private Integer countNoWater(String editionId, KwAbnormalQueryArgv argv) {
        String sql = "SELECT count(kr.id) as num1 from kw_receipt kr " +
                " LEFT JOIN kw_bank_account kba on kba.account=kr.self_account and kba.deleted =0 " +
                " LEFT JOIN kw_edition ke on ke.id =  kba.edition_id and ke.deleted = 0  " +
                " where kr.has_water=0 " +
                " and ke.id = ? ";
        SqlWrapper wrapper = new SqlWrapper(sql);
        wrapper.getParams().add(editionId);

        if (argv != null && argv.getStartDate() != null && argv.getEndDate() != null && StringUtils.isNotEmpty(argv.getStartDate())) {
            wrapper.addCondition("kr.book_date", Op.BETWEEN, argv.getStartDate(), argv.getEndDate());
        }
        if (argv != null && argv.getAccount() != null) {
            wrapper.addCondition("kr.self_account", Op.LIKE, "%" + argv.getAccount() + "%");
        }

        wrapper.withAuthority("kw_bank_account", "kba");

        KwNumRet one = DB.findOne(KwNumRet.class, wrapper.getSql(), wrapper.getParams().toArray());
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

        // 2 查找所有账号
        List<String> accounts = this.findAllAccountId();
//        System.out.println(accountIds);
        for (String account : accounts) {
            // 3 查找账号下的所有流水
            List<KwWater> waters = this.findWaterByAccountId(account);
            // 4 检查异常流水
            ids = this.checkBalance(ids, waters);
        }
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
        String sql = " select account from kw_bank_account where 1=1 and id is not null ";
        SqlWrapper wrapper = new SqlWrapper(sql);
        List<String> list = DB.findSingleAttributeList(String.class, wrapper.getSql(), wrapper.getParams().toArray());
        return list;
    }

    /**
     * 查找账号下的流水，日期升序、数据次序升序
     *
     * @param account
     * @return
     */
    private List<KwWater> findWaterByAccountId(String account) {
        String sql = " select * from kw_water kw where 1=1 and kw.account = ? order by kw.transaction_date asc,kw.date_index asc ";
        List<KwWater> list = DB.findList(KwWater.class, sql, account);
        return list;
    }

    /**
     * 检查余额
     *
     * @param ids    异常id列表
     * @param waters 检查的流水列表  （* 同一账号，有序）
     * @return
     */
    private List<String> checkBalance(List<String> ids, List<KwWater> waters) {
        if (waters.size() < 1) {
            return ids;
        }
        String sBalance = waters.get(0).getAccountBalance();
        if (sBalance.isEmpty()) {
            return ids;
        }
        //获取前一条流水的交易日期
        Date preDate = waters.get(0).getTransactionDate();
        BigDecimal bg = this.changeBigDecimal(sBalance);
        int revenue = 0;//0是收入,1是支出
        for (int i = 1; i < waters.size(); i++) {
            KwWater we = waters.get(i);
            revenue = we.getRevenue();
//            System.out.println(we.getAccountBalance() +"   " +(we.getRevenue()==1?"-":"+") +"  "+we.getTransactionAmount());
            BigDecimal bgresultBalance = this.changeBigDecimal(we.getAccountBalance());
            BigDecimal bgamount = this.changeBigDecimal(we.getTransactionAmount());
            if (revenue == 0) {
                if (bgamount.add(bg).compareTo(bgresultBalance) == 0) {
                    bg = bgresultBalance;
                    continue;
                }
                bg = bgresultBalance;
                System.out.println("-- 异常流水 -- " + we.getId() + " -- " + we.getAccount() + " -- " + we.getTransactionAmount() + " -- " + we.getAccountBalance());
                ids.add(we.getId());

            } else if (revenue == 1) {
                if (bg.subtract(bgamount).compareTo(bgresultBalance) == 0) {
                    bg = bgresultBalance;
                    continue;
                }
                System.out.println("-- 异常流水 -- " + we.getId() + " -- " + we.getAccount() + " -- " + we.getTransactionAmount() + " -- " + we.getAccountBalance());
                ids.add(we.getId());
                bg = bgresultBalance;
            }
        }
        return ids;
    }

    private BigDecimal changeBigDecimal(String sBalance) {
        double dBalance = Double.valueOf(sBalance);//数值过大时会自动转科学计数法
        return new BigDecimal(dBalance).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    private void flagAbnormalWater(String id) {
        String sql = "update kw_water set abnormal = 1 where id=?;";
        DB.executeUpdateSql(sql, id);
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
        SqlWrapper wrapper = new SqlWrapper(" SELECT  kba.bank_deposit ,km.bank_name as mechanism_name,ke.name as edition_name,ke.path, " +
                " kw.*, " +
                " kea.bank_account as edition_account, kea.bank_account, kea.cert_number, kea.bank_password, kea.ukey_password ,kea.usb_port, kea.usb_ip, kea.usb_group, " +
                " kea.is_ok_key , kea.usb_ip_ok ,kea.usb_port_ok, kea.usb_group_ok, kea.reserve1  " +
                " FROM kw_water kw " +
                " LEFT JOIN kw_receipt kr on kw.receipt_id = kr.id and kr.deleted = 0 " +
                " LEFT JOIN kw_bank_account kba on kw.account = kba.account and kba.deleted = 0  " +
                " LEFT JOIN kw_edition ke on kba.edition_id = ke.id  and ke.deleted = 0 " +
                " LEFT JOIN kw_edition_account kea on kea.id = kba.edition_account_id and kea.deleted = 0  " +
                " LEFT JOIN kw_mechanism km on ke.mechanism_id = km.id and km.deleted = 0  " +
                " where kw.abnormal = 1");

        // 拼装查询sql,并注入参数
        if (argv.getEditionId() != null && StringUtils.isNotEmpty(argv.getEditionId())) {
            wrapper.addCondition("kba.edition_id", Op.EQ, argv.getEditionId());
        }
        if (argv.getEditionName() != null && StringUtils.isNotEmpty(argv.getEditionName())) {
            wrapper.addCondition("ke.name", Op.LIKE, "%" + argv.getEditionName() + "%");
        }
        if (argv.getAccount() != null && StringUtils.isNotEmpty(argv.getAccount())) {
            wrapper.addCondition("kw.account", Op.LIKE, "%" + argv.getAccount() + "%");
        }
        if (argv.getStartDate() != null && StringUtils.isNotEmpty(argv.getStartDate())) {
            wrapper.addCondition("kw.transaction_date", Op.BETWEEN, argv.getStartDate(), argv.getEndDate());
        }
        if (argv.getIds() != null) {
            wrapper.in("kw.id", Arrays.asList(argv.getIds().split(",")));
        }

        // 访问权限
        wrapper.withAuthority("kw_bank_account", "kba");

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
//        final String accountId = curWaterRet.getAccountId();
        final String account = curWaterRet.getAccount();
        final Date transactionDate = curWaterRet.getTransactionDate();
        final Integer dateIndex = curWaterRet.getDateIndex();

        // 2、查找前n条流水
        // 同一天 2
        List<KwWaterRet> nearlyWater1 = null;
        List<KwWaterRet> nearlyWater2;
        List<KwWaterRet> nearlyWater3;
        List<KwWaterRet> nearlyWater4 = null;
        nearlyWater2 = this.findNearlyWater(2,false, account, transactionDate, dateIndex);
        // 非同一天 1
        if (nearlyWater2.size() < 5) {
            nearlyWater1 = this.findNearlyWater(1,false, account, transactionDate, dateIndex);
        }
        // 3、查找后n条流水
        // 同一天  3
        nearlyWater3 = this.findNearlyWater(3, false,account, transactionDate, dateIndex);
        // 非同一天 4
        if (nearlyWater3.size() < 5) {
            nearlyWater4 = this.findNearlyWater(4, false,account, transactionDate, dateIndex);
        }

        // 拼接返回列表
        // 早于本日
        if (nearlyWater1 != null && nearlyWater1.size() > 0) {
            Collections.reverse(nearlyWater1);
            for (KwWaterRet kwWaterRet : nearlyWater1) {
                retList.add(kwWaterRet);
            }
        }
        // 同日，早于本条
        if (nearlyWater2 != null && nearlyWater2.size() > 0) {
            Collections.reverse(nearlyWater2);
            for (KwWaterRet kwWaterRet : nearlyWater2) {
                retList.add(kwWaterRet);
            }
        }
        // 本条
        retList.add(curWaterRet);
        // 同日，晚于本条
        if (nearlyWater3 != null && nearlyWater3.size() > 0) {
            for (KwWaterRet kwWaterRet : nearlyWater3) {
                retList.add(kwWaterRet);
            }
        }
        // 晚于本日
        if (nearlyWater4 != null && nearlyWater4.size() > 0) {
            for (KwWaterRet kwWaterRet : nearlyWater4) {
                retList.add(kwWaterRet);
            }
        }
        return retList;
    }


    /**
     * 查找相邻的流水
     * @param type            1： 早于今天 2：同一天 ，本条之前 3：同一天，本条之后 4：本日之后
     * @param flag            是否正常  false 全部  true 正常
     * @param account
     * @param transactionDate
     * @param dateIndex
     * @return
     */
    private List<KwWaterRet> findNearlyWater(int type, boolean flag, String account, Date transactionDate, Integer dateIndex) {
        String sql;
        List<KwWaterRet> list;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = df.format(transactionDate);

        switch (type) {
            case 1:
                // 早于本日
                sql = !flag ? "select * from kw_water where deleted=0 and account = ? and transaction_date < ?  order by transaction_date desc,date_index desc limit 0,5 " :
                        " select * from kw_water where deleted=0 and account = ? and transaction_date < ? and abnormal=0  order by transaction_date desc,date_index desc limit 0,5 ";
                dateStr += " 00:00:00";
                list = DB.findList(KwWaterRet.class, sql, account, dateStr);
                break;
            case 2:
                // 同一天，次序在本条之前
                sql = !flag ? "select * from kw_water where deleted=0 and account = ? and transaction_date like ? and date_index<? order by transaction_date desc,date_index desc limit 0,5" :
                        "select * from kw_water where deleted=0 and account = ? and transaction_date like ? and date_index<? and abnormal=0 order by transaction_date desc,date_index desc limit 0,5";
                dateStr += "%";
                list = DB.findList(KwWaterRet.class, sql, account, dateStr, dateIndex);
                break;
            case 3:
                // 同一天，次序在本条之后
                sql = "select * from kw_water where deleted=0 and account = ? and transaction_date like ? and date_index>?  order by transaction_date asc,date_index asc limit 0,5 ";
                dateStr += "%";
                list = DB.findList(KwWaterRet.class, sql, account, dateStr, dateIndex);
                break;
            case 4:
                // 晚于本日
                sql = "select * from kw_water where deleted=0 and account = ? and transaction_date > ?   order by transaction_date asc,date_index asc limit 0,5 ";
                dateStr += " 23:59:59";
                list = DB.findList(KwWaterRet.class, sql, account, dateStr);
                break;
            default:
                return null;
        }
        return list;
    }

    @Override
    public void exportBalanceAbnormal(KwWaterQueryArgv argv) {
        // 直接调用查询方法
        argv.setPageQuery(false);
        List<KwWaterRet> list = this.queryBalanceAbnormal(argv);
        // 定义标题
        List<RegionDefine> defineList = new ArrayList<>();
        // 银行名称 mechanismName  银行版本editionName 网银地址 path
        // 登录账号 bankAccount  客户号 reserve1 登录证书号 certNumber  网银密码 bankPassword  Ukey密码 ukeyPassword  云柜ip usbIp  云柜端口 usbPort  ukey插口 usbGroup
        // 是否需要按ok键  isOkKey ok_云柜ip usbIpOk ok_云柜端口 usbPortOk ok_云柜插口 usbGroupOk
        // 账户 account  上条流水日期 lastDate  当前流水日期 curDate
        defineList.add(RegionDefine.textDefine("mechanismName", "机构名称"));
        defineList.add(RegionDefine.textDefine("editionName", "版本名称"));
        defineList.add(RegionDefine.textDefine("reserve1", "客户号"));
        defineList.add(RegionDefine.textDefine("path", "网银地址"));
        defineList.add(RegionDefine.textDefine("bankAccount", "登录账号"));
        defineList.add(RegionDefine.textDefine("bankPassword", "网银密码"));
        defineList.add(RegionDefine.textDefine("certNumber", "登录证书号"));
        defineList.add(RegionDefine.textDefine("ukeyPassword", "Ukey密码"));
        defineList.add(RegionDefine.textDefine("usbIp", "云柜ip"));
        defineList.add(RegionDefine.textDefine("usbPort", "云柜端口"));
        defineList.add(RegionDefine.textDefine("usbGroup", "ukey插口"));

//        defineList.add(RegionDefine.textDefine("isOkKey", "是否需要按ok键"));
        defineList.add(
                RegionDefine.builder().propName("isOkKey").labelName("是否需要按ok键").format((value, model) -> {
//                    Integer isOkKey = (Integer) BeanUtils.getFieldValue("isOkKey", model);
                    Integer isOkKey = (Integer)value;
                    if (value != null) {
                        if (isOkKey == 0) {
                            return "否";
                        } else if (isOkKey == 1) {
                            return "是";
                        }
                    }
                    return "异常";
                }).build());
        defineList.add(RegionDefine.textDefine("usbIpOk", "ok_云柜ip"));
        defineList.add(RegionDefine.textDefine("usbPortOk", "ok_云柜端口"));
        defineList.add(RegionDefine.textDefine("usbGroupOk", "ok_云柜插口"));

        defineList.add(RegionDefine.textDefine("account", "银行账户"));
        defineList.add(RegionDefine.dateDefine("lastDate", "上条流水日期"));
        defineList.add(RegionDefine.dateDefine("transactionDate", "当前流水日期"));
        // 导出
        KExcel kExcel = KExcel.fromDataList("异常流水.xls", "Sheet1", defineList, list);
        ExcelWorker.getInstance().writeToWeb(kExcel);
    }

    /**
     * 找出问题流水的前一天流水信息
     * @param argv
     * @return
     */
    public List<KwWaterRet> queryBalanceAbnormal(KwWaterQueryArgv argv) {
        // 1、找到问题流水
        PageDataRet<KwWaterRet> pageDataRet = this.queryAbnormalWater(argv);
        List<KwWaterRet> curWaters = pageDataRet.getList();
        for (KwWaterRet curWater : curWaters) {
            // 2、找出问题流水的前一条
            List<KwWaterRet> nearlyWater = this.findNearlyWater(2, true,curWater.getAccount(), curWater.getTransactionDate(), curWater.getDateIndex());
            if (nearlyWater.size() < 1) {
                nearlyWater = this.findNearlyWater(1, true, curWater.getAccount(), curWater.getTransactionDate(), curWater.getDateIndex());
            }
            if (nearlyWater.size() < 1) { // 没有前一条流水
                throw new RuntimeException("该异常流水没有前一条数据");
            }else {
                curWater.setLastDate(nearlyWater.get(0).getTransactionDate());
            }
        }
        return curWaters;
    }

}
