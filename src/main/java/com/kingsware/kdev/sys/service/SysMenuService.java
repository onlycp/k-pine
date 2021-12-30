package com.kingsware.kdev.sys.service;

import com.kingsware.kdev.core.base.BaseService;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.bean.TreeDataRet;
import com.kingsware.kdev.sys.argv.SysMenuArgv;
import com.kingsware.kdev.sys.argv.SysMenuQueryArgv;
import com.kingsware.kdev.sys.ret.SysMenuRet;

import java.util.List;

/**
 * 部门业务类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:35 上午
 */
public interface SysMenuService extends BaseService {

    /**
     * 通过id查询
     * @param id    id
     * @return      返回结果
     */
    SysMenuRet get(String id);

    /**
     * 新增
     * @param argv 新增
     */
     void add(SysMenuArgv argv);

    /**
     * 编辑
     * @param argv 编辑
     */
    void edit(SysMenuArgv argv);

    /**
     * 编辑
     * @param argv 编辑
     * @return 查询结果
     */
     PageDataRet<SysMenuRet> query(SysMenuQueryArgv argv);


    /**
     * 获取树结构
     * @param excludeId 排除id
     * @return  树型结果∂
     */
     List<TreeDataRet<Object>> treeOptions(String excludeId);

    /**
     * 删除
     * @param argv  查询
     */
    void delete(MultiIdArgv argv);
}
