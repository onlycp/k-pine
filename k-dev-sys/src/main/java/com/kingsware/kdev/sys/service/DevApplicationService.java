package com.kingsware.kdev.sys.service;

import com.kingsware.kdev.core.base.BaseService;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.sys.argv.*;
import com.kingsware.kdev.sys.ret.DevApplicationRet;

import java.util.Map;

/**
 * 接口业务类
 * @author AndyZheng
 * @version 1.0.0
 * @date 2022-02-13 10:20
 */
public interface DevApplicationService extends BaseService {

    /**
     * 通过id查询
     * @param id    id
     * @return      返回结果
     */
    DevApplicationRet get(String id);

    /**
     * 新增
     * @param argv 新增
     */
     void add(DevApplicationArgv argv);

    /**
     * 编辑
     * @param argv 编辑
     */
    void edit(DevApplicationArgv argv);

    /**
     * 编辑
     * @param argv 编辑
     * @return 查询结果
     */
     PageDataRet<DevApplicationRet> query(DevApplicationQueryArgv argv);

    /**
     * 删除
     * @param argv  查询
     */
    void delete(MultiIdArgv argv);

    /**
     * 导入应用
     * @param json json
     */
    String importApp(String json);

    /**
     * 安装应用
     * @param argv
     */
    Map<String, Object> install(DevAppInstallArgv argv);

    /**
     * json字符串转pine
     * @param appData json
     * @return  pine
     */
    DevPine appData2Pine(String appData);

    /**
     * 拷贝
     * @param id        id
     * @param copyData  拷贝参数
     */
    void copyData(String id, CopyAppArgv copyData);
}
