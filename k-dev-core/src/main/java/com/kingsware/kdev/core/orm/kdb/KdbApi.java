package com.kingsware.kdev.core.orm.kdb;

import lombok.SneakyThrows;

import java.io.File;
import java.io.InputStream;
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
     * @return 流程id
     */
    String addFlow(AddFlowInfo flowInfo);

    /**
     * 编辑流程
     * @param flowInfo 流程信息
     * @return
     */
    void editFlow(EditFlowInfo flowInfo);

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
     * 获取流程
     * @param flowId    流程id
     * @return  返回流程
     */
    FlowInfo get(String flowId);


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

    /**
     * 执行流程
     *
     * @param argv  参数
     * @param debug
     * @return 流程执行结果
     */
    KdbRet<String>  executeFlow(KdbArgv argv, boolean debug);

    /**
     * 新增函数
     * @param argv  函数
     */
    void addFun(AddFunctionInfo argv);

    /**
     * 编辑函数
     * @param argv 参数
     */
    void editFun(EditFunctionInfo argv);

    /**
     * 删除函数
     * @param funId 函数id
     */
    void deleteFun(String funId);

    /**
     * 函数查询
     * @param argv  参数
     * @return 查询结果
     */
    List<Functions> queryFunction(FunctionQueryArgv argv);

    /**
     * http上传文件
     * @param inputStream   流
     * @return          返回上传信息
     */
    KdbRet<String> uploadFile(InputStream inputStream, String fileName, String path);


    /**
     * 下载文件，直接流操作
     * @param path  文件路径
     */
    File downloadFile(String path, String fileName);


    @SneakyThrows
    File downloadFile(String path, String fileName, String prefix, String subfix);
}
