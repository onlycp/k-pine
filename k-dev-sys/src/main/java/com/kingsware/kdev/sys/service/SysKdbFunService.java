package com.kingsware.kdev.sys.service;

import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.sys.argv.SysKdbFunArgv;
import com.kingsware.kdev.sys.argv.SysKdbFunQueryArgv;
import com.kingsware.kdev.sys.ret.SysKdbFunRet;

/**
 * 流程业务接口
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/5 4:06 下午
 */
public interface SysKdbFunService {

    /**
     * 通过id查询
     * @param id    id
     * @return      返回结果
     */
    SysKdbFunRet get(String id);

    /**
     * 新增
     * @param argv 新增
     */
    void add(SysKdbFunArgv argv);

    /**
     * 编辑
     * @param argv 编辑
     */
    void edit(SysKdbFunArgv argv);

    /**
     * 编辑
     * @param argv 编辑
     * @return 查询结果
     */
    PageDataRet<SysKdbFunRet> query(SysKdbFunQueryArgv argv);

    /**
     * 删除
     * @param argv  查询
     */
    void delete(MultiIdArgv argv);

}
