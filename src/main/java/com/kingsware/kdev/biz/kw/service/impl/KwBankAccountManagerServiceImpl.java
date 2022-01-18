package com.kingsware.kdev.biz.kw.service.impl;

import com.kingsware.kdev.biz.kw.argv.KwBankAccountManagerArgv;
import com.kingsware.kdev.biz.kw.argv.KwBankAccountManagerQueryArgv;
import com.kingsware.kdev.biz.kw.argv.KwBankAccountQueryArgv;
import com.kingsware.kdev.biz.kw.model.KwBankAccountManager;
import com.kingsware.kdev.biz.kw.ret.KwBankAccountManagerRet;
import com.kingsware.kdev.biz.kw.ret.KwBankAccountRet;
import com.kingsware.kdev.biz.kw.service.KwBankAccountManagerService;
import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.SqlWrapper;
import com.kingsware.kdev.core.orm.expression.Op;
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
public class KwBankAccountManagerServiceImpl extends BaseServiceImpl implements KwBankAccountManagerService {

    @Override
    public KwBankAccountManagerRet get(String id) {
        // 查询model
        KwBankAccountManager model = DB.findById(KwBankAccountManager.class, id);
        // 转换成ret对象
        return (KwBankAccountManagerRet) model2Ret(model, KwBankAccountManagerRet.class);
    }

    @Override
    public void add(KwBankAccountManagerArgv argv) {
        KwBankAccountManager model = BeanUtils.copyObject(argv, KwBankAccountManager.class);
        // 保存
        DB.save(model);
    }

    @Override
    public void edit(KwBankAccountManagerArgv argv) {
        KwBankAccountManager model = DB.findById(KwBankAccountManager.class, argv.getId());
        if (model == null) {
            throw BusinessException.serviceThrow("当前数据不存在，修改失败。");
        }
        BeanUtils.copyProperties(argv, model);
        // 保存
        DB.update(model);
    }


    @Override
    public PageDataRet<KwBankAccountManagerRet> query(KwBankAccountManagerQueryArgv argv) {
        // 拼装sql
        SqlWrapper wrapper = new SqlWrapper(" select * from kw_bank_account_manager where 1=1 ");
        // 拼装查询sql
//        if (StringUtils.isNotEmpty(argv.getName())) {
//            wrapper.addCondition("ke.name", Op.LIKE, "%" +argv.getName() +"%");
//        }
        return (PageDataRet<KwBankAccountManagerRet>) query(wrapper.getSql(), wrapper.getParams(), argv, KwBankAccountManagerRet.class);
    }

    @Override
    public PageDataRet<KwBankAccountManagerRet> queryBankAccountManagerWithExpand(KwBankAccountManagerQueryArgv argv) {
        // 拼装sql
        StringBuilder sql = new StringBuilder();
        sql.append(" select  ");
        sql.append(" 	kbam.* ");
        sql.append(" from kw_bank_account_manager kbam  ");
        sql.append(" where 1=1 ");
        SqlWrapper wrapper = new SqlWrapper(sql.toString());
        // 拼装查询sql
        wrapper.addCondition("kbam.book_number", Op.EQ, argv.getBookNumber());
        return (PageDataRet<KwBankAccountManagerRet>) query(wrapper.getSql(), wrapper.getParams(), argv, KwBankAccountManagerRet.class);
    }

    @Override
    public void delete(MultiIdArgv argv) {
        for (String id: argv.getIds()) {
            DB.delete(KwBankAccountManager.class, id);
        }
    }
}
