package com.kingsware.kdev.biz.kw.service;

import com.kingsware.kdev.biz.kw.argv.KwBankAccountExpandArgv;
import com.kingsware.kdev.biz.kw.argv.KwBankAccountExpandQueryArgv;
import com.kingsware.kdev.biz.kw.ret.KwBankAccountExpandRet;
import com.kingsware.kdev.core.base.BaseService;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;

/**
 * 项目扩展信息管理业务类
 *
 * @author AndyZheng
 * @version 1.0.0
 * @date 2021/1/5 9:35 上午
 */
public interface KwBankAccountExpandService extends BaseService {

    /**
     * 通过id查询
     * @param id    id
     * @return      返回结果
     */
    KwBankAccountExpandRet get(String id);

    /**
     * 新增
     * @param argv 新增
     */
     void add(KwBankAccountExpandArgv argv);

    /**
     * 编辑
     * @param argv 编辑
     */
    void edit(KwBankAccountExpandArgv argv);

    /**
     * 编辑
     * @param argv 编辑
     * @return 查询结果
     */
     PageDataRet<KwBankAccountExpandRet> query(KwBankAccountExpandQueryArgv argv);

    /**
     * 删除
     * @param argv  查询
     */
    void delete(MultiIdArgv argv);
}
