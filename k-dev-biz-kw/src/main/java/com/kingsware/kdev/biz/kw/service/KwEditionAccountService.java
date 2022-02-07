package com.kingsware.kdev.biz.kw.service;

import com.kingsware.kdev.biz.kw.argv.KwEditionAccountArgv;
import com.kingsware.kdev.biz.kw.argv.KwEditionAccountQueryArgv;
import com.kingsware.kdev.biz.kw.ret.KwEditionAccountRet;
import com.kingsware.kdev.core.base.BaseService;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;

/**
 * 银行账号业务类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:35 上午
 */
public interface KwEditionAccountService extends BaseService {

    /**
     * 通过id查询
     * @param id    id
     * @return      返回结果
     */
    KwEditionAccountRet get(String id);

    /**
     * 新增
     * @param argv 新增
     */
     void add(KwEditionAccountArgv argv);

    /**
     * 编辑
     * @param argv 编辑
     */
    void edit(KwEditionAccountArgv argv);

    /**
     * 编辑
     * @param argv 编辑
     * @return 查询结果
     */
     PageDataRet<KwEditionAccountRet> query(KwEditionAccountQueryArgv argv);

    /**
     * 删除
     * @param argv  查询
     */
    void delete(MultiIdArgv argv);

    /**
     * 根据银行账号查找id
     * @param bankAccount 银行账号
     */
    String findIdByBankAccount(String bankAccount);
}
