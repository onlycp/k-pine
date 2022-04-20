package com.kingsware.kdev.sys.service;

import com.kingsware.kdev.core.base.BaseService;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.sys.argv.SysConfigArgv;
import com.kingsware.kdev.sys.argv.SysConfigQueryArgv;
import com.kingsware.kdev.sys.ret.SysConfigRet;

import java.util.Map;


/**
 * 系统配置接口
 *
 * @author crb
 * @version 1.0.0
 * @date 2022/1/13 16:35 上午
 */
public interface SysConfigService extends BaseService {

    /**
     * 通过id查询
     * @param id    id
     * @return      返回结果
     */
    SysConfigRet get(String id);

    /**
     * 新增
     * @param argv 新增
     */
    void add(SysConfigArgv argv);

    /**
     * 编辑
     * @param argv 编辑
     */
    void edit(SysConfigArgv argv);

    /**
     * 编辑
     * @param argv 编辑
     * @return 查询结果
     */
    PageDataRet<SysConfigRet> query(SysConfigQueryArgv argv);

    /**
     * 删除
     * @param argv  查询
     */
    void delete(MultiIdArgv argv);

    Map<String, Object> getSysConfig();
}
