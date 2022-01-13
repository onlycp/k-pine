package com.kingsware.kdev.biz.kw.service.impl;

import com.kingsware.kdev.biz.kw.argv.KwWaterQueryArgv;
import com.kingsware.kdev.biz.kw.model.KwBankAccount;
import com.kingsware.kdev.biz.kw.ret.KwBankAccountRet;
import com.kingsware.kdev.biz.kw.ret.KwCompanyRet;
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

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
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
     * @param argv 查询
     * @return
     */
    @Override
    public PageDataRet<KwBankAccountRet> query(KwWaterQueryArgv argv) {
        String sql = "SELECT * from" +
                "(SELECT b.*,w.account_balance as reserve1 " +
                "FROM kw_water w " +
                "LEFT JOIN kw_bank_account b ON w.account=b.account " +
                "WHERE b.account IS NOT NULL " ;

        SqlWrapper wrapper = new SqlWrapper(sql);
        if (argv.getAccount()!=null){
            wrapper.addCondition("w.account", Op.LIKE,""+argv.getAccount()+"");
        }
        if (argv.getEndDate()!=null){
            wrapper.addCondition("w.transaction_date", Op.LT_EQ,argv.getEndDate());
        }

        wrapper.withAuthority("kw_bank_account","b");

        wrapper.sortBy("ORDER BY w.transaction_date desc,w.date_index desc " +
                "limit 2100000000)a " +
                "group by account");

        PageDataRet<? extends BaseSimpleRet> query = query(wrapper.getSql(), wrapper.getParams(), argv, KwBankAccountRet.class);

        return (PageDataRet<KwBankAccountRet>)query;
    }

    @Override
    public void export(KwWaterQueryArgv argv) {

        // 直接调用查询方法
        argv.setPageQuery(false);
        PageDataRet<KwBankAccountRet> pageDataRet = this.query(argv);
        // 定义标题
        List<RegionDefine> defineList = new ArrayList<>();
        defineList.add(RegionDefine.dateDefine("account", "银行账户"));
        defineList.add(RegionDefine.dateDefine("reserve1", "账户余额"));

        // 导出
        KExcel kExcel = KExcel.fromDataList("流水查询.xls", "Sheet1", defineList, pageDataRet.getList());
        ExcelWorker.getInstance().writeToWeb(kExcel);

    }
}
