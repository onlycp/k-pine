package com.kingsware.kdev.sys.service;

import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.sys.argv.SysKdbFlowArgv;
import com.kingsware.kdev.sys.argv.SysKdbFlowQueryArgv;
import com.kingsware.kdev.sys.ret.SysKdbFlowRet;

/**
 * 流程业务接口
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/5 4:06 下午
 */
public interface SysKdbFlowService {

    /**
     * 通过id查询
     * @param id    id
     * @return      返回结果
     */
    SysKdbFlowRet get(String id);

    /**
     * 新增
     * @param argv 新增
     */
    void add(SysKdbFlowArgv argv);

    /**
     * 编辑
     * @param argv 编辑
     */
    void edit(SysKdbFlowArgv argv);

    /**
     * 编辑
     * @param argv 编辑
     * @return 查询结果
     */
    PageDataRet<SysKdbFlowRet> query(SysKdbFlowQueryArgv argv);

    /**
     * 删除
     * @param argv  查询
     */
    void delete(MultiIdArgv argv);

    /**
     * 新增或编辑
     * @param argv  参数
     */
    void addOrUpdate(String name, String content);
}
