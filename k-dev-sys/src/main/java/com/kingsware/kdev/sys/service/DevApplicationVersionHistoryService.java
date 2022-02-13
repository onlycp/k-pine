package com.kingsware.kdev.sys.service;

import com.kingsware.kdev.core.base.BaseService;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.sys.argv.DevApplicationVersionHistoryArgv;
import com.kingsware.kdev.sys.argv.DevApplicationVersionHistoryQueryArgv;
import com.kingsware.kdev.sys.ret.DevApplicationVersionHistoryRet;

/**
 * 接口业务类
 * @author AndyZheng
 * @version 1.0.0
 * @date 2022-02-13 10:20
 */
public interface DevApplicationVersionHistoryService extends BaseService {

    /**
     * 通过id查询
     * @param id    id
     * @return      返回结果
     */
    DevApplicationVersionHistoryRet get(String id);

    /**
     * 新增
     * @param argv 新增
     */
     void add(DevApplicationVersionHistoryArgv argv);

    /**
     * 编辑
     * @param argv 编辑
     */
    void edit(DevApplicationVersionHistoryArgv argv);

    /**
     * 编辑
     * @param argv 编辑
     * @return 查询结果
     */
     PageDataRet<DevApplicationVersionHistoryRet> query(DevApplicationVersionHistoryQueryArgv argv);

    /**
     * 删除
     * @param argv  查询
     */
    void delete(MultiIdArgv argv);

}
