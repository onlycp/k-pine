package com.kingsware.kdev.sys.service;

import com.kingsware.kdev.core.base.BaseService;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.sys.argv.SysOnlineUserQueryArgv;
import com.kingsware.kdev.sys.ret.SysOnlineUserRet;

/**
 * 数据访问配置
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:35 上午
 */
public interface SysOnlineUserService extends BaseService {

    /**
     * 通过id查询
     * @param id    id
     * @return      返回结果
     */
    SysOnlineUserRet get(String id);

    /**
     * 编辑
     * @param argv 编辑
     * @return 查询结果
     */
     PageDataRet<SysOnlineUserRet> query(SysOnlineUserQueryArgv argv);

    /**
     * 删除
     * @param argv  查询
     */
    void delete(MultiIdArgv argv);
}
