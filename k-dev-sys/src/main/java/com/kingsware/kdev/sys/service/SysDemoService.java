package com.kingsware.kdev.sys.service;

import com.kingsware.kdev.core.base.BaseService;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.sys.argv.SysDemoArgv;
import com.kingsware.kdev.sys.argv.SysDemoQueryArgv;
import com.kingsware.kdev.sys.ret.SysDemoRet;

/**
 * 演示业务基类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:35 上午
 */
public interface SysDemoService extends BaseService {

    /**
     * 通过id查询
     * @param id    id
     * @return      返回结果
     */
    SysDemoRet get(String id);

    /**
     * 新增
     * @param argv 新增
     */
     void add(SysDemoArgv argv);

    /**
     * 编辑
     * @param argv 编辑
     */
    void edit(SysDemoArgv argv);

    /**
     * 编辑
     * @param argv 编辑
     * @return 查询结果
     */
     PageDataRet<SysDemoRet> query(SysDemoQueryArgv argv);

    /**
     * 删除
     * @param argv  查询
     */
    void delete(MultiIdArgv argv);
}
