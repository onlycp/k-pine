package com.kingsware.kdev.biz.kw.service;

import com.kingsware.kdev.biz.kw.argv.KwBankAccountArgv;
import com.kingsware.kdev.biz.kw.argv.KwBankAccountQueryArgv;
import com.kingsware.kdev.biz.kw.ret.KwBankAccountRet;
import com.kingsware.kdev.core.base.BaseService;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;

/**
 * 银行账户信息管理业务类
 *
 * @author AndyZheng
 * @version 1.0.0
 * @date 2021/1/5 9:35 上午
 */
public interface KwBankAccountService extends BaseService {

    /**
     * 通过id查询
     * @param id    id
     * @return      返回结果
     */
    KwBankAccountRet get(String id);

    /**
     * 新增
     * @param argv 新增
     */
     void add(KwBankAccountArgv argv);

    /**
     * 编辑
     * @param argv 编辑
     */
    void edit(KwBankAccountArgv argv);

    /**
     * 编辑
     * @param argv 编辑
     * @return 查询结果
     */
     PageDataRet<KwBankAccountRet> query(KwBankAccountQueryArgv argv);

    /**
     * 删除
     * @param argv  查询
     */
    void delete(MultiIdArgv argv);

    /**
     * 批量更新银行版本、账户、账号的关系
     * @param argv  查询
     */
    void addAccountListAndEditions(KwBankAccountArgv argv);
}
