package com.kingsware.kdev.biz.kw.service.impl;

import com.kingsware.kdev.biz.kw.argv.KwBankAccountArgv;
import com.kingsware.kdev.biz.kw.argv.KwBankAccountQueryArgv;
import com.kingsware.kdev.biz.kw.model.KwBankAccount;
import com.kingsware.kdev.biz.kw.model.KwEdition;
import com.kingsware.kdev.biz.kw.ret.KwBankAccountRet;
import com.kingsware.kdev.biz.kw.service.KwBankAccountService;
import com.kingsware.kdev.biz.kw.service.KwEditionAccountService;
import com.kingsware.kdev.biz.kw.service.KwEditionService;
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

import javax.annotation.Resource;
import java.util.List;

/**
 * 银行版本管理业务实现类
 *
 * @author AndyZheng
 * @version 1.0.0
 * @date 2021/1/5 9:36 上午
 */
@Service
public class KwBankAccountServiceImpl extends BaseServiceImpl implements KwBankAccountService {

    @Resource
    private KwEditionService kwEditionService;

    @Resource
    private KwEditionAccountService kwEditionAccountService;

    @Override
    public KwBankAccountRet get(String id) {
        // 查询model
        KwBankAccount model = DB.findById(KwBankAccount.class, id);
        KwEdition edition = DB.findById(KwEdition.class, model.getEditionId());
        // 转换成ret对象
        KwBankAccountRet kwBankAccountRet = (KwBankAccountRet) model2Ret(model, KwBankAccountRet.class);
        if (edition != null) {
            kwBankAccountRet.setMechanismId(edition.getMechanismId());
        }
        return kwBankAccountRet;
    }

    @Override
    public void add(KwBankAccountArgv argv) {
        if (argv.getRelationType() == null) {
            argv.setRelationType(0);
        }
        if (argv.getBalanceCheck() == null) {
            argv.setBalanceCheck(1);
        }
        KwBankAccount model = BeanUtils.copyObject(argv, KwBankAccount.class);
        // 保存
        DB.save(model);
    }

    @Override
    public void edit(KwBankAccountArgv argv) {
        if (argv.getRelationType() == null) {
            argv.setRelationType(0);
        }
        KwBankAccount model = DB.findById(KwBankAccount.class, argv.getId());
        if (model == null) {
            throw BusinessException.serviceThrow("当前数据不存在，修改失败。");
        }
        BeanUtils.copyProperties(argv, model);
        // 保存
        DB.update(model);

        SqlWrapper selectWrapper = SqlWrapper.selectWrapper(KwBankAccount.class);
        selectWrapper.addCondition("name", Op.LIKE,  "%1%");

    }


    @Override
    public PageDataRet<KwBankAccountRet> query(KwBankAccountQueryArgv argv) {
        // 拼装sql
        StringBuilder sql = new StringBuilder();
        sql.append(" select  ");
        sql.append(" 	kc.name as company_name, ");
        sql.append(" 	km.bank_name as mechanism_name, ");
        sql.append(" 	ke.name as edition_name, ");
        sql.append(" 	kba.* ");
        sql.append(" from kw_bank_account kba  ");
        sql.append(" left join kw_edition ke on ke.id = kba.edition_id  ");
        sql.append(" left join kw_company kc on kba.relation_type = 1 and kba.relation_id = kc.id ");
        sql.append(" left join kw_mechanism km on km.id = ke.mechanism_id  ");
        sql.append(" where kba.deleted=0 ");
//        if (argv.getUpdateDateStartDate() != null && argv.getUpdateDateEndDate() != null) {
//            sql.append(" and kba.balance_update_time between date('" + argv.getUpdateDateStartDate() + "') and date('" + argv.getUpdateDateEndDate() + "') ");
//        }
        SqlWrapper wrapper = new SqlWrapper(sql.toString());
        // 拼装查询sql
        if (StringUtils.isNotEmpty(argv.getMechanismId())) {
            wrapper.addCondition("ke.mechanism_id", Op.EQ, argv.getMechanismId());
        }
        if (StringUtils.isNotEmpty(argv.getAccount())) {
            wrapper.addCondition("kba.account", Op.LIKE, "%" +argv.getAccount() +"%");
        }
        if (StringUtils.isNotEmpty(argv.getCompanyName())) {
            wrapper.addCondition("kc.name", Op.LIKE, "%" +argv.getCompanyName() +"%");
        }
        if (argv.getUpdateDateStartDate() != null && argv.getUpdateDateEndDate() != null) {
            wrapper.between("kba.balance_update_time", argv.getUpdateDateStartDate(), argv.getUpdateDateEndDate());
        }
        wrapper.withAuthority("kw_bank_account", "kba");
//        wrapper.groupBy(" kba.id ");
        wrapper.sortBy("ke.name desc");
        return (PageDataRet<KwBankAccountRet>) query(wrapper.getSql(), wrapper.getParams(), argv, KwBankAccountRet.class);
    }

    @Override
    public PageDataRet<KwBankAccountRet> queryBankAccountWithExpand(KwBankAccountQueryArgv argv) {
        // 拼装sql
        StringBuilder sql = new StringBuilder();
        sql.append(" select  ");
        sql.append(" 	kba.*, kbae.id as pro_id ");
        sql.append(" from kw_bank_account kba  ");
        sql.append(" left join kw_bank_account_expand kbae on kbae.account = kba.account ");
        sql.append(" where 1=1 ");
        sql.append(" where 1=1 ");
        SqlWrapper wrapper = new SqlWrapper(sql.toString());
        // 拼装查询sql
        wrapper.addCondition("kbae.pro_num", Op.EQ, argv.getProNum());
        wrapper.addCondition("kba.account", Op.EQ, argv.getAccount());
        return (PageDataRet<KwBankAccountRet>) query(wrapper.getSql(), wrapper.getParams(), argv, KwBankAccountRet.class);
    }

    @Override
    public void delete(MultiIdArgv argv) {
        for (String id: argv.getIds()) {
            DB.delete(KwBankAccount.class, id);
        }
    }

    /**
     * 同步账户和银行版本的业务方法
     * @param argv  查询
     */
    // TODO: 事务2022/1/17
    //@Transaction
    @Override
    public void addAccountListAndEditions(KwBankAccountArgv argv){
        if(argv == null || argv.getAccountList().size() == 0){
            return;
        }
        String editionId = kwEditionService.findIdByName(argv.getEditionName());
        String editionAccountId = kwEditionAccountService.findIdByBankAccount(argv.getBankAccount());
        List<String> accountList = argv.getAccountList();

        /**
        List<Object> collect = accountList.stream().filter(s -> StringUtils.isNotEmpty(s)).map(s -> {
            DB.findOne()
            return null;
        }).collect(Collectors.toList());
         */
        accountList.forEach(a -> {
            if(StringUtils.isNotEmpty(a)){
                SqlWrapper wrapper = new SqlWrapper("select kba.* from kw_bank_account as kba where 1 = 1");
                wrapper.addCondition("kba.account", Op.EQ, a);
                wrapper.addCondition("kba.deleted", Op.EQ, 0);
                KwBankAccount kbaResult = DB.findOne(KwBankAccount.class, wrapper.getSql(), wrapper.getParams().toArray());
                if(kbaResult == null){
                    //如果不存在，则增加
                    KwBankAccount model = BeanUtils.copyObject(argv, KwBankAccount.class);
                    model.setAccount(a);
                    model.setEditionId(editionId);
                    model.setEditionAccountId(editionAccountId);
                    DB.save(model);
                }else{
                    //如果存在，则更新
                    kbaResult.setEditionId(editionId);
                    kbaResult.setEditionAccountId(editionAccountId);
                    DB.update(kbaResult);
                }
            }
        });
    }
}
