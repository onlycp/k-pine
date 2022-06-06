package com.kingsware.kdev.sys.service;

import com.kingsware.kdev.core.base.BaseService;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.sys.argv.DevPageHistoryArgv;
import com.kingsware.kdev.sys.argv.DevPageHistoryQueryArgv;
import com.kingsware.kdev.sys.ret.DevPageHistoryRet;

/**
 * 接口业务类
 * @author AndyZheng
 * @version 1.0.0
 * @date 2022-02-13 10:20
 */
public interface DevPageHistoryService extends BaseService {

    /**
     * 通过id查询
     * @param id    id
     * @return      返回结果
     */
    DevPageHistoryRet get(String id);

    /**
     * 新增
     * @param argv 新增
     */
     void add(DevPageHistoryArgv argv);

    /**
     * 编辑
     * @param argv 编辑
     */
    void edit(DevPageHistoryArgv argv);

    void rollback(DevPageHistoryArgv argv);

    /**
     * 编辑
     * @param argv 编辑
     * @return 查询结果
     */
     PageDataRet<DevPageHistoryRet> query(DevPageHistoryQueryArgv argv);

    /**
     * 删除
     * @param argv  查询
     */
    void delete(MultiIdArgv argv);

}
