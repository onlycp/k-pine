package com.kingsware.kdev.sys.service;

import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.sys.argv.SysFlowDefineArgv;
import com.kingsware.kdev.sys.argv.SysKdbFlowArgv;
import com.kingsware.kdev.sys.argv.SysKdbFlowQueryArgv;
import com.kingsware.kdev.sys.ret.SysFlowDefineRet;
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
     * 获取流程定义
     * @param id    流程id
     * @return      流程定义
     */
    SysFlowDefineRet getDefine(String id);

    /**
     * 编辑定义文件
     * @param argv 编辑
     */
    void editDefine(SysFlowDefineArgv argv);

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
     * @param name  名称
     * @param content  内容
     */
    void addOrUpdate(String name, String content);
}
