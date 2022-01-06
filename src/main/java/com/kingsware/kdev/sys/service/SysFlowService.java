package com.kingsware.kdev.sys.service;

import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.sys.argv.SysFlowArgv;
import com.kingsware.kdev.sys.argv.SysFlowQueryArgv;
import com.kingsware.kdev.sys.ret.SysFlowRet;

/**
 * 流程业务接口
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/5 4:06 下午
 */
public interface SysFlowService {

    /**
     * 通过id查询
     * @param id    id
     * @return      返回结果
     */
    SysFlowRet get(String id);

    /**
     * 新增
     * @param argv 新增
     */
    void add(SysFlowArgv argv);

    /**
     * 编辑
     * @param argv 编辑
     */
    void edit(SysFlowArgv argv);

    /**
     * 编辑
     * @param argv 编辑
     * @return 查询结果
     */
    PageDataRet<SysFlowRet> query(SysFlowQueryArgv argv);

    /**
     * 删除
     * @param argv  查询
     */
    void delete(MultiIdArgv argv);
}
