package com.kingsware.kdev.sys.argv;

import com.kingsware.kdev.core.cache.open.OpenAccount;
import com.kingsware.kdev.core.orm.kdb.DataSourceInfo;
import com.kingsware.kdev.core.orm.kdb.FlowInfo;
import com.kingsware.kdev.core.orm.kdb.Functions;
import com.kingsware.kdev.core.model.DevPage;
import com.kingsware.kdev.core.model.SysApiMock;
import com.kingsware.kdev.core.model.SysCache;
import com.kingsware.kdev.core.model.SysFile;
import com.kingsware.kdev.core.model.SysInstance;
import com.kingsware.kdev.core.model.SysI18n;
import com.kingsware.kdev.core.model.SysLogicFlow;
import com.kingsware.kdev.core.model.SysLogicFlowMock;
import com.kingsware.kdev.core.model.SysLoginLog;
import com.kingsware.kdev.core.model.SysNoticeRecord;
import com.kingsware.kdev.core.model.SysOnlineUser;
import com.kingsware.kdev.core.model.SysOperateLog;
import com.kingsware.kdev.core.model.SysTask;
import com.kingsware.kdev.core.model.SysTaskDetail;
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

    public static final String[] systemImportVars = new String[]
            {"configs", "pages", "sources", "apis", "functions",
            "dictItems", "dict", "kdbFlows", "logicFlows", "menus",
            "tasks", "info", "powerLinks", "devPowerTrees", "extPluginInterfaces",
            "extPluginTrees", "sysLogicTemplates", "devFaasNodeTypes", "devFaasNodes", "extPluginInterfaces",
            "devRoles", "devRoleMenus", "devPageTemplates"};
    /** [x]系统配置 **/
    private List<SysConfig> configs;
    /** [x]页面信息 **/
    private List<DevPage> pages;
    /** [x]数据源 **/
    private List<DataSourceInfo> sources;
    /** [x]接口 **/
    private List<SysApi> apis;
    /** [x]函数 **/
    private List<Functions> functions;
    /** [x]字典项 **/
    private List<SysDictItem> dictItems;
    /** [x]字典 **/
    private List<SysDict> dict;
    /** [x]kdb流程 **/
    private List<FlowInfo> kdbFlows;
    /** [x]青松流程信息 **/
    private List<SysLogicFlow> logicFlows;
    /** [x]菜单 **/
    private List<SysMenu> menus;
    /** [x]定时任务 **/
    private List<SysTask> tasks;
    /** [x]应用信息 **/
    private DevApplication info;
    /** 能力中间表 **/
    private List<DevPowerLink> powerLinks;
    /** 能力树 **/
    private List<DevPowerTree> devPowerTrees;
    /** 插件接口 **/
    private List<ExtPluginInterface> extPluginInterfaces;
    /** 插件树 **/
    private List<ExtPluginTree> extPluginTrees;
    /** 逻辑编排模板 **/
    private List<SysLogicTemplate> sysLogicTemplates;
    /** faas扩展节点类型 **/
    private List<DevFaasNodeType> devFaasNodeTypes;
    /** faas节点 **/
    private List<DevFaasNode> devFaasNodes;
    /** 开发平台角色 **/
    private List<SysRole> devRoles;
    /** 开发平台角色菜单 **/
    private List<SysRoleMenu> devRoleMenus;
    /** 页面模板 **/
    private List<DevPageTemplate> devPageTemplates;
    /** [x]国际化 **/
    private List<SysI18n> i18ns;
    /** 开放账号 **/
    private List<OpenAccount> openAccounts;
    /** 开放账号权限 **/
    private List<OpenAccountApi> openAccountApis;
    /**
     * 如果是应用导出的，变量名要带s，如果是对应表的一般没有。
     */
    private List<DevModule> devModule;

    private List<DevPinePlugin> devPinePlugin;

    private List<DevPluginApi> devPluginApi;

    private List<DevPluginGroup> devPluginGroup;

    private List<DevPluginOperation> devPluginOperation;

    private List<DevTeam> devTeam;

    private List<DevTeamApp> devTeamApp;

    private List<DevTeamMember> devTeamMember;

    private List<SysAuthSource> sysAuthSource;

    private List<SysAutoSerial> sysAutoSerial;

    private List<SysFile> sysFile;

    private List<SysI18n> sysI18n;
}
