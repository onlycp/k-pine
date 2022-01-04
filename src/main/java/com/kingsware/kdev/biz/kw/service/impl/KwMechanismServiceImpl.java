package com.kingsware.kdev.biz.kw.service.impl;

import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.i18n.I18n;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.DBChecker;
import com.kingsware.kdev.core.orm.SqlWrapper;
import com.kingsware.kdev.core.orm.expression.Op;
import com.kingsware.kdev.core.util.BeanUtils;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.biz.kw.argv.KwMechanismArgv;
import com.kingsware.kdev.biz.kw.argv.KwMechanismQueryArgv;
import com.kingsware.kdev.biz.kw.model.KwMechanism;
import com.kingsware.kdev.biz.kw.ret.KwMechanismRet;
import com.kingsware.kdev.biz.kw.service.KwMechanismService;
import org.springframework.stereotype.Service;

/**
 * 角色业务实现类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:36 上午
 */
@Service
public class KwMechanismServiceImpl extends BaseServiceImpl implements KwMechanismService {

    @Override
    public KwMechanismRet get(String id) {
        // 查询model
        KwMechanism model = DB.findById(KwMechanism.class, id);
        // 转换成ret对象
        return (KwMechanismRet) model2Ret(model, KwMechanismRet.class);
    }

    @Override
    public void add(KwMechanismArgv argv) {
        KwMechanism model = BeanUtils.copyObject(argv, KwMechanism.class);
        // 校验
        checkUnique(model);
        // 保存
        DB.save(model);
    }

    @Override
    public void edit(KwMechanismArgv argv) {
        KwMechanism model = DB.findById(KwMechanism.class, argv.getId());
        model.setBankNumber(argv.getBankNumber());
        model.setBankName(argv.getBankName());
        model.setBankType(argv.getBankType());
        model.setBankShort(argv.getBankShort());
        // 校验
        checkUnique(model);
        // 保存
        DB.update(model);
    }

    /**
     * 校验唯一性
     * @param model 模型
     */
    private void checkUnique(KwMechanism model) {
        // 唯一性校验
        DBChecker<KwMechanism> checker =DBChecker.build(model, KwMechanism.class);
        // 银行名称
        checker.uni("bankName", I18n.t("KwMechanism.bankName.unique", "银行名称必须唯一"));
        // 银行代码
        checker.uni("bankNumber", I18n.t("KwMechanism.bankNumber.unique", "银行代码必须唯一"));
        // 银行简称
        checker.uni("bankShort", I18n.t("KwMechanism.bankShort.unique", "银行简称必须唯一"));
        // 执行校验
        checker.checkUnique();
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageDataRet<KwMechanismRet> query(KwMechanismQueryArgv argv) {
        // 拼装sql
        SqlWrapper wrapper = new SqlWrapper("select * from kw_mechanism where 1=1 ");
        // 拼装查询sql
        if (StringUtils.isNotEmpty(argv.getBankName())) {
            wrapper.addCondition("bank_name", Op.LIKE, "%" +argv.getBankName() +"%");
        }
        if (argv.getBankType() != null) {
            wrapper.addCondition("bank_type", Op.EQ, argv.getBankType());
        }
        return (PageDataRet<KwMechanismRet>) query(wrapper.getSql(), wrapper.getParams(), argv, KwMechanism.class, KwMechanismRet.class);
    }

    @Override
    public void delete(MultiIdArgv argv) {
        for (String id: argv.getIds()) {
            DB.delete(KwMechanism.class, id);
        }
    }
}
