package com.kingsware.kdev.biz.kw.service;

import com.kingsware.kdev.core.base.BaseService;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.biz.kw.argv.KwMechanismArgv;
import com.kingsware.kdev.biz.kw.argv.KwMechanismQueryArgv;
import com.kingsware.kdev.biz.kw.ret.KwMechanismRet;

/**
 * 行别管理业务类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:35 上午
 */
public interface KwMechanismService extends BaseService {

    /**
     * 通过id查询
     * @param id    id
     * @return      返回结果
     */
    KwMechanismRet get(String id);

    /**
     * 新增
     * @param argv 新增
     */
     void add(KwMechanismArgv argv);

    /**
     * 编辑
     * @param argv 编辑
     */
    void edit(KwMechanismArgv argv);

    /**
     * 编辑
     * @param argv 编辑
     * @return 查询结果
     */
     PageDataRet<KwMechanismRet> query(KwMechanismQueryArgv argv);

    /**
     * 删除
     * @param argv  查询
     */
    void delete(MultiIdArgv argv);
}
