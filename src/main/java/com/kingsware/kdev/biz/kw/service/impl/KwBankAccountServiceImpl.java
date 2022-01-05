package com.kingsware.kdev.biz.kw.service.impl;

import com.kingsware.kdev.biz.kw.argv.KwBankAccountArgv;
import com.kingsware.kdev.biz.kw.argv.KwBankAccountQueryArgv;
import com.kingsware.kdev.biz.kw.model.KwBankAccount;
import com.kingsware.kdev.biz.kw.ret.KwBankAccountRet;
import com.kingsware.kdev.biz.kw.service.KwBankAccountService;
import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.SqlWrapper;
import com.kingsware.kdev.core.util.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * 银行版本管理业务实现类
 *
 * @author AndyZheng
 * @version 1.0.0
 * @date 2021/1/5 9:36 上午
 */
@Service
public class KwBankAccountServiceImpl extends BaseServiceImpl implements KwBankAccountService {

    @Override
    public KwBankAccountRet get(String id) {
        // 查询model
        KwBankAccount model = DB.findById(KwBankAccount.class, id);
        // 转换成ret对象
        return (KwBankAccountRet) model2Ret(model, KwBankAccountRet.class);
    }

    @Override
    public void add(KwBankAccountArgv argv) {
        KwBankAccount model = BeanUtils.copyObject(argv, KwBankAccount.class);
        // 保存
        DB.save(model);
    }

    @Override
    public void edit(KwBankAccountArgv argv) {
        KwBankAccount model = DB.findById(KwBankAccount.class, argv.getId());
        if (model == null) {
            throw BusinessException.serviceThrow("当前数据不存在，修改失败。");
        }
        BeanUtils.copyProperties(argv, model);
        // 保存
        DB.update(model);
    }


    @Override
    public PageDataRet<KwBankAccountRet> query(KwBankAccountQueryArgv argv) {
        // 拼装sql
        SqlWrapper wrapper = new SqlWrapper(" select * from kw_bank_account where 1=1 ");
        // 拼装查询sql
//        if (StringUtils.isNotEmpty(argv.getName())) {
//            wrapper.addCondition("ke.name", Op.LIKE, "%" +argv.getName() +"%");
//        }
        return (PageDataRet<KwBankAccountRet>) query(wrapper.getSql(), wrapper.getParams(), argv, KwBankAccountRet.class);
    }

    @Override
    public void delete(MultiIdArgv argv) {
        for (String id: argv.getIds()) {
            DB.delete(KwBankAccount.class, id);
        }
    }
}
