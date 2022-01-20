package com.kingsware.kdev.biz.kw.service.impl;

import com.kingsware.kdev.biz.kw.argv.KwEditionAccountArgv;
import com.kingsware.kdev.biz.kw.argv.KwEditionAccountQueryArgv;
import com.kingsware.kdev.biz.kw.model.KwEditionAccount;
import com.kingsware.kdev.biz.kw.ret.KwEditionAccountRet;
import com.kingsware.kdev.biz.kw.service.KwEditionAccountService;
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
import org.springframework.stereotype.Service;

/**
 * 银行账号业务实现
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:36 上午
 */
@Service
public class KwEditionAccountServiceImpl extends BaseServiceImpl implements KwEditionAccountService {

    @Override
    public KwEditionAccountRet get(String id) {
        // 查询model
        KwEditionAccount model = DB.findById(KwEditionAccount.class, id);
        // 转换成ret对象
        return (KwEditionAccountRet) model2Ret(model, KwEditionAccountRet.class);
    }

    @Override
    public void add(KwEditionAccountArgv argv) {
        KwEditionAccount model = BeanUtils.copyObject(argv, KwEditionAccount.class);
        model.setPasswordRetried(0);
        // 校验
        checkUnique(model);
        // 保存
        DB.save(model);
    }

    @Override
    public void edit(KwEditionAccountArgv argv) {
        KwEditionAccount model = DB.findById(KwEditionAccount.class, argv.getId());
        model.setEditionId(argv.getEditionId());
        model.setLoginType(argv.getLoginType());
        model.setBankAccount(argv.getBankAccount());
        model.setBankPassword(argv.getBankPassword());
        model.setCertNumber(argv.getCertNumber());
        model.setUsbIp(argv.getUsbIp());
        model.setUsbPort(argv.getUsbPort());
        model.setUsbGroup(argv.getUsbGroup());
        model.setIsOkKey(argv.getIsOkKey());
        model.setUsbIpOk(argv.getUsbIpOk());
        model.setUsbPortOk(argv.getUsbPortOk());
        model.setUsbGroupOk(argv.getUsbGroupOk());
        model.setStatus(argv.getStatus());
        model.setReserve1(argv.getReserve1());// 备用1,客户号
        // 校验
        checkUnique(model);
        // 保存
        DB.update(model);
    }

    /**
     * 校验唯一性
     * @param model 模型
     */
    private void checkUnique(KwEditionAccount model) {
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageDataRet<KwEditionAccountRet> query(KwEditionAccountQueryArgv argv) {
        // 拼装sql
        SqlWrapper wrapper = new SqlWrapper("select ea.*, ed.name as edition_name, me.bank_name from kw_edition_account ea left join kw_edition ed on ed.id=ea.edition_id left join kw_mechanism me on me.id=ed.mechanism_id where ea.deleted=0  ");
        // 拼装查询sql
        if (StringUtils.isNotEmpty(argv.getBankAccount())) {
            wrapper.addCondition("ea.bank_account", Op.LIKE, "%" +argv.getBankAccount() +"%");
        }
        if (StringUtils.isNotEmpty(argv.getUsbIp())) {
            wrapper.addCondition("ea.usb_ip", Op.LIKE, "%" +argv.getUsbIp() +"%");
        }
        if (StringUtils.isNotEmpty(argv.getEditionId())) {
            wrapper.addCondition("ea.edition_id", Op.EQ, argv.getEditionId());
        }
        wrapper.sortBy("order by ea.when_created desc");

        return (PageDataRet<KwEditionAccountRet>) query(wrapper.getSql(), wrapper.getParams(), argv, KwEditionAccountRet.class);
    }

    @Override
    public void delete(MultiIdArgv argv) {
        for (String id: argv.getIds()) {
            DB.delete(KwEditionAccount.class, id);
        }
    }

    /**
     * 根据银行账号查找id
     * @param bankAccount 银行账号
     */
    @Override
    public String findIdByBankAccount(String bankAccount){
        if(bankAccount == null || !StringUtils.isNotEmpty(bankAccount)) {
            return null;
        }else{
            //SqlWrapper wrapper = new SqlWrapper("select kea.id from kw_edition_account as kea where 1 = 1");
            SqlWrapper wrapper = new SqlWrapper("select kea.* from kw_edition_account as kea where 1 = 1");
            wrapper.addCondition("kea.bank_account", Op.EQ, bankAccount);
            KwEditionAccount kwEditionAccount = DB.findOne(KwEditionAccount.class, wrapper.getSql(), wrapper.getParams().toArray());
            if(kwEditionAccount == null){
                return null;
            }
            return kwEditionAccount.getId();
        }
    }
}
