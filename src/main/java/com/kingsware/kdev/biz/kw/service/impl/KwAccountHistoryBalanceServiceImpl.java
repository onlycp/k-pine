package com.kingsware.kdev.biz.kw.service.impl;

import com.kingsware.kdev.biz.kw.argv.KwWaterQueryArgv;
import com.kingsware.kdev.biz.kw.ret.KwBankAccountRet;
import com.kingsware.kdev.biz.kw.ret.KwWaterRet;
import com.kingsware.kdev.biz.kw.service.KwAccountHistoryBalanceService;
import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.BaseSimpleRet;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.excel.ExcelWorker;
import com.kingsware.kdev.core.excel.KExcel;
import com.kingsware.kdev.core.excel.RegionDefine;
import com.kingsware.kdev.core.orm.SqlWrapper;
import com.kingsware.kdev.core.orm.expression.Op;
import com.kingsware.kdev.core.util.BeanUtils;
import com.kingsware.kdev.core.util.DateUtils;
import com.kingsware.kdev.core.util.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *
 */
@Service
public class KwAccountHistoryBalanceServiceImpl extends BaseServiceImpl implements KwAccountHistoryBalanceService {
    @Override
    public KwWaterRet get(String id) {
        return null;
    }

    @Override
    public void add(KwWaterQueryArgv argv) {

    }

    /**
     * 分页查询历史余额（从流水中）
     *
     * @param argv 查询
     * @return
     */
    @Override
    public PageDataRet<KwBankAccountRet> query(KwWaterQueryArgv argv) {
        String sql = "select kea.bank_account as edition_account, ke.name as edition_name, km.bank_name as mechanism_name, kba.*, SUBSTRING_INDEX(t2.all_balance_info, \'|\', -1) as water_balance " +
                "from kw_bank_account kba " +
                "left JOIN " +
                "( " +
                " select t1.account, max(t1.balance_info) as all_balance_info " +
                " from ( " +
                "  SELECT " +
                "  w.account, concat(w.transaction_date,\'|\',from_unixtime(w.date_index),\'|\',w.account_balance) as  balance_info " +
                "  from kw_water w " +
                "  WHERE w.deleted = 0 ";

        SqlWrapper wrapper = new SqlWrapper(sql);
        if (StringUtils.isNotEmpty(argv.getEndDate())) {
            wrapper.addCondition("w.transaction_date", Op.LT_EQ, argv.getEndDate());
        }

        wrapper.setSql(
                wrapper.getSql() +
                        " ) t1 " +
                        " GROUP by t1.account " +
                        ") t2 " +
                        "on t2.account = kba.account " +
                        "LEFT JOIN kw_edition ke on ke.id=kba.edition_id " +
                        "LEFT JOIN kw_edition_account kea on kea.id=kba.edition_account_id " +
                        "LEFT JOIN kw_mechanism km on km.id = ke.mechanism_id " +
                        "WHERE kba.deleted = 0 " +
                        "and kea.deleted = 0 " +
                        "and km.deleted = 0 ");

        if (StringUtils.isNotEmpty(argv.getAccount())) {
            wrapper.addCondition("kba.account", Op.LIKE, "%" + argv.getAccount() + "%");
        }
        //
        if (argv.getIds() != null) {
            wrapper.in("kba.id", Arrays.asList(argv.getIds().split(",")));
        }

        wrapper.withAuthority("kw_bank_account", "kba");

        PageDataRet<? extends BaseSimpleRet> query = query(wrapper.getSql(), wrapper.getParams(), argv, KwBankAccountRet.class);

        return (PageDataRet<KwBankAccountRet>) query;
    }

    @Override
    public void export(KwWaterQueryArgv argv) {

        // 直接调用查询方法
        argv.setPageQuery(false);
        if(StringUtils.isEmpty(argv.getEndDate())){
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY,-24);
            argv.setEndDate(DateUtils.formatDate(calendar.getTime(),DateUtils.DATE));
        }

        PageDataRet<KwBankAccountRet> pageDataRet = this.query(argv);
        
        // 定义标题
        List<RegionDefine> defineList = new ArrayList<>();
        defineList.add(
                RegionDefine.builder().propName("account").labelName("日期").format((value, model) -> {
                    return argv.getEndDate();
                }).build());
        defineList.add(RegionDefine.textDefine("mechanismName", "机构名称"));
        defineList.add(RegionDefine.textDefine("editionName", "版本名称"));
        defineList.add(RegionDefine.textDefine("editionAccount", "版本账号"));
        defineList.add(RegionDefine.textDefine("account", "账户"));
        defineList.add(RegionDefine.textDefine("waterBalance", "账户余额"));
        defineList.add(RegionDefine.dateDefine("accountType", "账户性质","kw_bank_account_account_type"));

        // 导出
        KExcel kExcel = KExcel.fromDataList("账户历史余额查询.xls", "Sheet1", defineList, pageDataRet.getList());
        ExcelWorker.getInstance().writeToWeb(kExcel);

    }
}
