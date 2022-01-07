package com.kingsware.kdev.sys.service;

import com.kingsware.kdev.core.base.BaseService;
import com.kingsware.kdev.core.bean.BaseRelationArgv;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.sys.argv.SysDataAccessArgv;
import com.kingsware.kdev.sys.argv.SysDataAccessQueryArgv;
import com.kingsware.kdev.sys.ret.SysDataAccessRet;

import java.util.List;

/**
 * 数据访问配置
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:35 上午
 */
public interface SysDataAccessService extends BaseService {

    /**
     * 通过id查询
     * @param id    id
     * @return      返回结果
     */
    SysDataAccessRet get(String id);

    /**
     * 新增
     * @param argv 新增
     */
     void add(SysDataAccessArgv argv);

    /**
     * 编辑
     * @param argv 编辑
     */
    void edit(SysDataAccessArgv argv);

    /**
     * 编辑
     * @param argv 编辑
     * @return 查询结果
     */
     PageDataRet<SysDataAccessRet> query(SysDataAccessQueryArgv argv);

    /**
     * 删除
     * @param argv  查询
     */
    void delete(MultiIdArgv argv);

    /**
     * 获取已选用户id
     * @param id    用户id
     * @return      用户id列表
     */
    List<String> querySelectedUserIds(String id);

    /**
     * 数据权限和用户的关联关系存储
     * @param baseRelationArgv  关联关系
     */
    void saveDataAccessUser(BaseRelationArgv baseRelationArgv);


    /**
     * 获取已选的数据id
     * @param id    用户id
     * @return      用户id列表
     */
    List<String> querySelectedDataIds(String resourceId, String id);

    /**
     * 数据权限和数据的关联关系存储
     * @param baseRelationArgv  关联关系
     */
    void saveDataAccessResource(String resourceId, BaseRelationArgv baseRelationArgv);
}
