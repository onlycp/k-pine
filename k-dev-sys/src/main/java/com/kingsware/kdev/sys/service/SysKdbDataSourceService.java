package com.kingsware.kdev.sys.service;

import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.sys.argv.SysKdbDataSourceArgv;
import com.kingsware.kdev.sys.argv.SysKdbDataSourceQueryArgv;
import com.kingsware.kdev.sys.ret.SysKdbDataSourceRet;

/**
 * 流程业务接口
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/5 4:06 下午
 */
public interface SysKdbDataSourceService {

    /**
     * 通过id查询
     * @param id    id
     * @return      返回结果
     */
    SysKdbDataSourceRet get(String id);

    /**
     * 新增
     * @param argv 新增
     */
    void add(SysKdbDataSourceArgv argv);

    /**
     * 编辑
     * @param argv 编辑
     */
    void edit(SysKdbDataSourceArgv argv);

    /**
     * 编辑
     * @param argv 编辑
     * @return 查询结果
     */
    PageDataRet<SysKdbDataSourceRet> query(SysKdbDataSourceQueryArgv argv);

    /**
     * APPID分页查询
     * @param argv 编辑
     * @return 查询结果
     */
    PageDataRet<SysKdbDataSourceRet> queryByAppId(SysKdbDataSourceQueryArgv argv);

    /**
     * 删除
     * @param argv  查询
     */
    void delete(MultiIdArgv argv);

    /**
     * 刷新基础数据源
     * @return
     */
    void refreshBaseFlow();
}
