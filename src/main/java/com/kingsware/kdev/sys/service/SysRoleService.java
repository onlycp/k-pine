package com.kingsware.kdev.sys.service;

import com.kingsware.kdev.core.base.BaseService;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.sys.argv.SysRoleArgv;
import com.kingsware.kdev.sys.argv.SysRoleQueryArgv;
import com.kingsware.kdev.sys.ret.SysRoleRet;

/**
 * 角色业务类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:35 上午
 */
public interface SysRoleService extends BaseService {

    /**
     * 通过id查询
     * @param id    id
     * @return      返回结果
     */
    SysRoleRet get(String id);

    /**
     * 新增
     * @param argv 新增
     */
     void add(SysRoleArgv argv);

    /**
     * 编辑
     * @param argv 编辑
     */
    void edit(SysRoleArgv argv);

    /**
     * 编辑
     * @param argv 编辑
     * @return 查询结果
     */
     PageDataRet<SysRoleRet> query(SysRoleQueryArgv argv);

    /**
     * 删除
     * @param argv  查询
     */
    void delete(MultiIdArgv argv);
}
