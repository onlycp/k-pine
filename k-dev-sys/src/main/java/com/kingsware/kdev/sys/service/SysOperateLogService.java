package com.kingsware.kdev.sys.service;

import com.kingsware.kdev.core.base.BaseService;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.sys.argv.SysOperateLogQueryArgv;
import com.kingsware.kdev.sys.ret.SysOperateLogRet;

/**
 * 角色业务类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:35 上午
 */
public interface SysOperateLogService extends BaseService {

    /**
     * 通过id查询
     * @param id    id
     * @return      返回结果
     */
    SysOperateLogRet get(String id);

    /**
     * 编辑
     * @param argv 编辑
     * @return 查询结果
     */
     PageDataRet<SysOperateLogRet> query(SysOperateLogQueryArgv argv);

    void export(SysOperateLogQueryArgv argv);

    PageDataRet<SysOperateLogRet> moduleList(SysOperateLogQueryArgv argv);

    PageDataRet<SysOperateLogRet> actionList(SysOperateLogQueryArgv argv);
}
