package com.kingsware.kdev.sys.argv;

import com.kingsware.kdev.core.model.SysTask;
import com.kingsware.kdev.core.orm.kdb.DataSourceInfo;
import com.kingsware.kdev.core.orm.kdb.FlowInfo;
import com.kingsware.kdev.core.orm.kdb.Functions;
import com.kingsware.kdev.sys.model.*;
import lombok.Data;

import java.util.List;

/**
 * 应用包数据
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/4/25 10:10 AM
 */
@Data
public class DevPine {
    /** 系统配置 **/
    private List<SysConfig> configs;
    /** 页面信息 **/
    private List<DevPage> pages;
    /** 数据源 **/
    private List<DataSourceInfo> datasources;
    /** 接口 **/
    private List<SysApi> apis;
    /** 函数 **/
    private List<Functions> functions;
    /** 字典项 **/
    private List<SysDictItem> dictItems;
    /** 字典 **/
    private List<SysDict> dict;
    /** kdb流程 **/
    private List<FlowInfo> kdbFlows;
    /** 青松流程信息 **/
    private List<SysLogicFlow> logicFlows;
    /** 菜单 **/
    private List<SysMenu> menus;
    /** 定时任务 **/
    private List<SysTask> tasks;
    /** 应用信息 **/
    private DevApplication info;
}
