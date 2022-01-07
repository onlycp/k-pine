package com.kingsware.kdev.biz.kw.service.impl;

import com.kingsware.kdev.biz.kw.argv.KwMechanismArgv;
import com.kingsware.kdev.biz.kw.argv.KwMechanismQueryArgv;
import com.kingsware.kdev.biz.kw.argv.KwWaterQueryArgv;
import com.kingsware.kdev.biz.kw.model.KwMechanism;
import com.kingsware.kdev.biz.kw.model.KwWater;
import com.kingsware.kdev.biz.kw.ret.KwMechanismRet;
import com.kingsware.kdev.biz.kw.ret.KwWaterRet;
import com.kingsware.kdev.biz.kw.service.KwMechanismService;
import com.kingsware.kdev.biz.kw.service.KwWaterService;
import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.BaseSimpleRet;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.i18n.I18n;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.DBChecker;
import com.kingsware.kdev.core.orm.SqlWrapper;
import com.kingsware.kdev.core.orm.expression.Op;
import com.kingsware.kdev.core.util.BeanUtils;
import com.kingsware.kdev.core.util.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class KwWaterServiceImpl extends BaseServiceImpl implements KwWaterService {

    @Override
    public KwWaterRet get(String id) {
        // 查询model
        KwWater model = DB.findById(KwWater.class, id);
        // 转换成ret对象
        return (KwWaterRet) model2Ret(model, KwWaterRet.class);
    }

    @Override
    public void add(KwWaterQueryArgv argv) {
//        KwMechanism model = BeanUtils.copyObject(argv, KwWater.class);
//        // 校验
//        checkUnique(model);
//        // 保存
//        DB.save(model);
    }

    @Override
    public void edit(KwWaterQueryArgv argv) {
//        KwMechanism model = DB.findById(KwWater.class, argv.getId());
//        model.setBankNumber(argv.getBankNumber());
//        model.setBankName(argv.getBankName());
//        model.setBankType(argv.getBankType());
//        model.setBankShort(argv.getBankShort());
//        // 校验
//        checkUnique(model);
//        // 保存
//        DB.update(model);
    }

    /**
     * 校验唯一性
     * @param model 模型
     */
    private void checkUnique(KwWater model) {
//        // 唯一性校验
//        DBChecker<KwWater> checker =DBChecker.build(model, KwWater.class);
//        // 银行名称
//        checker.uni("bankName", I18n.t("KwMechanism.bankName.unique", "银行名称必须唯一"));
//        // 银行代码
//        checker.uni("bankNumber", I18n.t("KwMechanism.bankNumber.unique", "银行代码必须唯一"));
//        // 银行简称
//        checker.uni("bankShort", I18n.t("KwMechanism.bankShort.unique", "银行简称必须唯一"));
        // 执行校验
//        checker.checkUnique();
    }

    /**
     *
             SELECT km.bank_name as mechanism_name,ke.name as edtion_name,kw.* FROM kw_water kw
             LEFT JOIN kw_receipt kr on kw.receipt_id = kr.id
             LEFT JOIN kw_bank_account kba on kw.account_id = kba.id
             LEFT JOIN kw_edition ke on kba.edition_id = ke.id
             LEFT JOIN kw_mechanism km on ke.mechanism_id = km.id
             where 1=1
             -- and kba.edition_id = ?1
             -- and kw.account like ?2
             -- and kw.transaction_date >= ?3
             -- and kw.transaction_date <= ?4
             ORDER BY kw.transaction_date desc,date_index desc
             -- LIMIT ?5,?6;
     * @param argv 编辑
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public PageDataRet<KwWaterRet> query(KwWaterQueryArgv argv) {
        // 基础sql
        SqlWrapper wrapper = new SqlWrapper(" SELECT km.bank_name as mechanism_name,ke.name as edition_name,kw.* FROM kw_water kw " +
                " LEFT JOIN kw_receipt kr on kw.receipt_id = kr.id" +
                " LEFT JOIN kw_bank_account kba on kw.account_id = kba.id " +
                " LEFT JOIN kw_edition ke on kba.edition_id = ke.id " +
                " LEFT JOIN kw_mechanism km on ke.mechanism_id = km.id " +
                " where 1=1 " );

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

    @Override
    public void delete(MultiIdArgv argv) {
        for (String id: argv.getIds()) {
            DB.delete(KwWater.class, id);
        }
    }
}
