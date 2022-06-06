package com.kingsware.kdev.sys.service;

import com.kingsware.kdev.core.base.BaseService;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.sys.argv.SysLogicHistoryArgv;
import com.kingsware.kdev.sys.argv.SysLogicHistoryQueryArgv;
import com.kingsware.kdev.sys.ret.SysLogicHistoryRet;

/**
 * 接口业务类
 * @author AndyZheng
 * @version 1.0.0
 * @date 2022-02-13 10:20
 */
public interface SysLogicHistoryService extends BaseService {

    /**
     * 通过id查询
     * @param id    id
     * @return      返回结果
     */
    SysLogicHistoryRet get(String id);

    /**
     * 新增
     * @param argv 新增
     */
     void add(SysLogicHistoryArgv argv);

    /**
     * 编辑
     * @param argv 编辑
     */
    void edit(SysLogicHistoryArgv argv);

    /**
     * 编辑
     * @param argv 编辑
     * @return 查询结果
     */
     PageDataRet<SysLogicHistoryRet> query(SysLogicHistoryQueryArgv argv);

    /**
     * 删除
     * @param argv  查询
     */
    void delete(MultiIdArgv argv);

}
