package com.kingsware.kdev.biz.kw.service.impl;

import com.kingsware.kdev.biz.kw.argv.KwReceiptArgv;
import com.kingsware.kdev.biz.kw.argv.KwReceiptQueryArgv;
import com.kingsware.kdev.biz.kw.argv.KwWaterArgv;
import com.kingsware.kdev.biz.kw.argv.KwWaterQueryArgv;
import com.kingsware.kdev.biz.kw.model.KwWater;
import com.kingsware.kdev.biz.kw.ret.KwReceiptRet;
import com.kingsware.kdev.biz.kw.ret.KwWaterRet;
import com.kingsware.kdev.biz.kw.service.KwReceiptService;
import com.kingsware.kdev.biz.kw.service.KwWaterService;
import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.BaseSimpleRet;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.excel.ExcelWorker;
import com.kingsware.kdev.core.excel.KExcel;
import com.kingsware.kdev.core.excel.RegionDefine;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.SqlWrapper;
import com.kingsware.kdev.core.orm.expression.Op;
import com.kingsware.kdev.core.util.BeanUtils;
import com.kingsware.kdev.core.util.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class KwReceiptServiceImpl extends BaseServiceImpl implements KwReceiptService {


    @Override
    public KwWaterRet get(String id) {
        return null;
    }

    @Override
    public void add(KwReceiptArgv argv) {

    }

    /**
     * 回单分页查询
     * @param argv 查询
     * @return
     */
    @Override
    public PageDataRet<KwReceiptRet> query(KwReceiptQueryArgv argv) {
        String sql = "SELECT kba.bank_deposit, kea.bank_account as edition_account, kbae.pro_name," +
                " km.bank_name as mechanism_name, km.id as mechanism_id, ke.name as edition_name,ke.id as edition_id, " +
                " kba.id as bank_account_id, kw.id as water_id, " +
                " kr.* " +
                " FROM kw_receipt kr " +
                " LEFT JOIN kw_file kf on kf.id = kr.file_id and kf.deleted=0 " +
                " LEFT JOIN kw_water kw on kw.receipt_id = kr.id and kw.deleted = 0 " +
                " LEFT JOIN kw_bank_account kba on kba.account = kr.self_account and kba.deleted = 0 " +
                " LEFT JOIN kw_bank_account_expand kbae on kba.account = kbae.account " +
                " LEFT JOIN kw_edition ke on kba.edition_id = ke.id and ke.deleted = 0 " +
                " LEFT JOIN kw_edition_account kea on kea.id = kba.edition_account_id and kea.deleted = 0 " +
                " LEFT JOIN kw_mechanism km on ke.mechanism_id = km.id and km.deleted = 0 " +
                " where kr.deleted = 0 ";
        SqlWrapper wrapper = new SqlWrapper(sql);
        if (argv.getEditionId() !=null && StringUtils.isNotEmpty(argv.getEditionId())){
            wrapper.addCondition("ke.id", Op.EQ,argv.getEditionId());
        }
        if (argv.getEditionName() != null && StringUtils.isNotEmpty(argv.getEditionName())) {
            wrapper.addCondition("ke.name", Op.LIKE, "%" + argv.getEditionName() + "%");
        }
        if (argv.getAccount() != null && StringUtils.isNotEmpty(argv.getAccount())) {
            wrapper.addCondition("kr.self_account", Op.LIKE, "%" + argv.getAccount() + "%");
        }
        if (argv.getStartDate() != null && StringUtils.isNotEmpty(argv.getStartDate())) {
            wrapper.addCondition("kw.transaction_date", Op.BETWEEN, argv.getStartDate(), argv.getEndDate());
        }
        if (argv.getIds() != null) {
            wrapper.in("kr.id", Arrays.asList(argv.getIds().split(",")));
        }

        // 数据权限
        wrapper.withAuthority("kw_bank_account", "kba");
        // 排序
        wrapper.sortBy("ORDER BY kr.book_date desc");
        // 执行查询
        PageDataRet<? extends BaseSimpleRet> query = query(wrapper.getSql(), wrapper.getParams(), argv, KwReceiptRet.class);

        return (PageDataRet<KwReceiptRet>) query;
    }

    @Override
    public void exportImportTemplate() {

    }

    @Override
    public void export(KwReceiptQueryArgv argv) {

    }
}
