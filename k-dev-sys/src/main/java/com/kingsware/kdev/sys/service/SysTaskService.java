package com.kingsware.kdev.sys.service;

import com.kingsware.kdev.core.base.BaseService;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.sys.argv.SysTaskArgv;
import com.kingsware.kdev.sys.argv.SysTaskQueryArgv;
import com.kingsware.kdev.sys.ret.SysTaskRet;

/**
 * 数据访问配置
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:35 上午
 */
public interface SysTaskService extends BaseService {

    /**
     * 通过id查询
     * @param id    id
     * @return      返回结果
     */
    SysTaskRet get(String id);

    /**
     * 新增
     * @param argv 新增
     */
     void add(SysTaskArgv argv);

    /**
     * 编辑
     * @param argv 编辑
     */
    void edit(SysTaskArgv argv);

    /**
     * 编辑
     * @param argv 编辑
     * @return 查询结果
     */
     PageDataRet<SysTaskRet> query(SysTaskQueryArgv argv);

    /**
     * 删除
     * @param argv  查询
     */
    void delete(MultiIdArgv argv);

    /**
     * 任务执行
     * @param task
     */
    void executeTask(String task);
}
