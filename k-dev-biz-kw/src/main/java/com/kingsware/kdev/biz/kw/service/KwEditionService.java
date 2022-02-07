package com.kingsware.kdev.biz.kw.service;

import com.kingsware.kdev.biz.kw.argv.KwEditionArgv;
import com.kingsware.kdev.biz.kw.argv.KwEditionQueryArgv;
import com.kingsware.kdev.biz.kw.ret.KwEditionRet;
import com.kingsware.kdev.core.base.BaseService;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;

/**
 * 银行版本管理业务类
 *
 * @author AndyZheng
 * @version 1.0.0
 * @date 2021/12/23 9:35 上午
 */
public interface KwEditionService extends BaseService {

    /**
     * 通过id查询
     * @param id    id
     * @return      返回结果
     */
    KwEditionRet get(String id);

    /**
     * 新增
     * @param argv 新增
     */
     void add(KwEditionArgv argv);

    /**
     * 编辑
     * @param argv 编辑
     */
    void edit(KwEditionArgv argv);

    /**
     * 编辑
     * @param argv 编辑
     * @return 查询结果
     */
     PageDataRet<KwEditionRet> query(KwEditionQueryArgv argv);

    /**
     * 删除
     * @param argv  查询
     */
    void delete(MultiIdArgv argv);

    /**
     * 根据银行版本名称查找对应的id
     * @param name  银行版本名称
     */
    String findIdByName(String name);
}
