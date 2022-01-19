package com.kingsware.kdev.biz.kw.service.impl;

import com.kingsware.kdev.biz.kw.argv.KwBankAccountExpandArgv;
import com.kingsware.kdev.biz.kw.argv.KwBankAccountExpandQueryArgv;
import com.kingsware.kdev.biz.kw.model.KwBankAccountExpand;
import com.kingsware.kdev.biz.kw.ret.KwBankAccountExpandRet;
import com.kingsware.kdev.biz.kw.service.KwBankAccountExpandService;
import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.SqlWrapper;
import com.kingsware.kdev.core.orm.expression.Op;
import com.kingsware.kdev.core.util.BeanUtils;
import com.kingsware.kdev.core.util.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 银行版本管理业务实现类
 *
 * @author AndyZheng
 * @version 1.0.0
 * @date 2021/1/5 9:36 上午
 */
@Service
public class KwBankAccountExpandServiceImpl extends BaseServiceImpl implements KwBankAccountExpandService {

    @Override
    public KwBankAccountExpandRet get(String id) {
        // 查询model
        KwBankAccountExpand model = DB.findById(KwBankAccountExpand.class, id);
        // 转换成ret对象
        return (KwBankAccountExpandRet) model2Ret(model, KwBankAccountExpandRet.class);
    }

    @Override
    public void add(KwBankAccountExpandArgv argv) {
        KwBankAccountExpand model = BeanUtils.copyObject(argv, KwBankAccountExpand.class);
        // 保存
        DB.save(model);
    }

    @Override
    public void edit(KwBankAccountExpandArgv argv) {
        KwBankAccountExpand model = DB.findById(KwBankAccountExpand.class, argv.getId());
        if (model == null) {
            throw BusinessException.serviceThrow("当前数据不存在，修改失败。");
        }
        BeanUtils.copyProperties(argv, model);
        // 保存
        DB.update(model);
    }


    @Override
    public PageDataRet<KwBankAccountExpandRet> query(KwBankAccountExpandQueryArgv argv) {
        // 拼装sql
        SqlWrapper wrapper = new SqlWrapper(" select * from kw_bank_account_expand where 1=1 ");
        // 拼装查询sql
        if (StringUtils.isNotEmpty(argv.getAccount())) {
            wrapper.addCondition("account", Op.EQ, argv.getAccount());
        }
        if (StringUtils.isNotEmpty(argv.getProNum())) {
            wrapper.addCondition("pro_num", Op.EQ, argv.getProNum());
        }
        return (PageDataRet<KwBankAccountExpandRet>) query(wrapper.getSql(), wrapper.getParams(), argv, KwBankAccountExpandRet.class);
    }

    @Override
    public void delete(MultiIdArgv argv) {
        for (String id: argv.getIds()) {
            DB.delete(KwBankAccountExpand.class, id);
        }
    }
}
