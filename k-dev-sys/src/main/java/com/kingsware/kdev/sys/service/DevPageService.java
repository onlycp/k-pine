package com.kingsware.kdev.sys.service;

import com.kingsware.kdev.core.base.BaseService;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.model.DevPage;
import com.kingsware.kdev.sys.argv.CopyContextArgv;
import com.kingsware.kdev.sys.argv.DevPageArgv;
import com.kingsware.kdev.sys.argv.DevPageQueryArgv;
import com.kingsware.kdev.sys.ret.DevPageRet;

/**
 * 接口业务类
 * @author AndyZheng
 * @version 1.0.0
 * @date 2022-02-13 10:20
 */
public interface DevPageService extends BaseService {

    /**
     * 通过id查询
     * @param id    id
     * @return      返回结果
     */
    DevPageRet get(String id);

    /**
     * 通过路径查询
     * @param path
     * @return
     */
    DevPageRet getByPath(String path);

    /**
     * 新增
     * @param argv 新增
     */
     void add(DevPageArgv argv);

    /**
     * 编辑
     * @param argv 编辑
     */
    void edit(DevPageArgv argv);

    /**
     * 校验唯一
     * @param model
     */
    void checkUnique(DevPage model);

    /**
     * 编辑
     * @param argv 编辑
     * @return 查询结果
     */
     PageDataRet<DevPageRet> query(DevPageQueryArgv argv);

    /**
     * 删除
     * @param argv  查询
     */
    void delete(MultiIdArgv argv);

    /**
     * 渲染页面
     * @param id
     */
    void render(String id);


    /**
     * 拷贝
     * @param id        id
     * @param copyData  拷贝参数
     */
    void copyData(String id, CopyContextArgv copyData);

    /**
     * 导出pine包
     * @param argv
     * @return
     */
    void exportPine(MultiIdArgv argv);

}
