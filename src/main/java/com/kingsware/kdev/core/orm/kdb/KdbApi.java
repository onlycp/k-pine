package com.kingsware.kdev.core.orm.kdb;

import com.kingsware.kdev.core.orm.kdb.FlowInfo;
import com.kingsware.kdev.core.orm.kdb.KdbRet;

import java.util.List;

/**
 * kdb操作接口
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/13 5:39 下午
 */
public interface KdbApi {

    /**
     * 新增流程
     * @param flowInfo  流程信息
     * @return
     */
    void addFlow(FlowInfo flowInfo);

    /**
     * 编辑流程
     * @param flowInfo 流程信息
     * @return
     */
    void editFlow(FlowInfo flowInfo);

    /**
     * 删除流程
     * @param flowId 流程id
     * @return
     */
    void deleteFlow(String flowId);

    /**
     * 流程查询
     * @param flowInfo
     * @return
     */
    List<FlowInfo> query(KdbFlowQueryArgv flowInfo);


    /**
     * 新增数据源
     * @param dataSourceInfo  数据源信息
     */
    void addDataSource(DataSourceInfo dataSourceInfo);

    /**
     * 编辑流程
     * @param dataSourceInfo 数据源信息
     * @return
     */
    void editDataSource(DataSourceInfo dataSourceInfo);

    /**
     * 删除流程
     * @param dataSource 数据源名称
     * @return
     */
    void deleteDataSource(String dataSource);

    /**
     * 流程查询
     * @param dataSourceInfo   查询参数
     * @return
     */
    List<DataSourceInfo> queryDataSource(DataSourceQueryArgv dataSourceInfo);




}
