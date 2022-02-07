package com.kingsware.kdev.sys.service;

import com.kingsware.kdev.core.base.BaseService;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.sys.argv.SysDictItemArgv;
import com.kingsware.kdev.sys.argv.SysDictItemQueryArgv;
import com.kingsware.kdev.sys.ret.SysDictItemRet;

import java.util.Map;

/**
 * 字典数据接口
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:35 上午
 */
public interface SysDictItemService extends BaseService {

    /**
     * 通过id查询
     * @param id    id
     * @return      返回结果
     */
    SysDictItemRet get(String id);

    /**
     * 新增
     * @param argv 新增
     */
     void add(SysDictItemArgv argv);

    /**
     * 编辑
     * @param argv 编辑
     */
    void edit(SysDictItemArgv argv);

    /**
     * 编辑
     * @param argv 编辑
     * @return 查询结果
     */
     PageDataRet<SysDictItemRet> query(SysDictItemQueryArgv argv);

    /**
     * 删除
     * @param argv  查询
     */
    void delete(MultiIdArgv argv);

    Map<String, Object> getAllDict();
}
