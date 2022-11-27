package com.kingsware.kdev.sys.model;

import com.kingsware.kdev.core.bean.BaseManageModel;
import com.kingsware.kdev.core.orm.annotation.Column;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author andyzheng
 * @version 1.0.0
 * @description: 菜单信息
 * @date 2021/12/30 10:22
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysMenu extends BaseManageModel {

    /** 名称 **/
    private String name;
    /** 上级菜单ID **/
    private String parentId;
    /** 图标 **/
    private String icon;
    /** 编码 **/
    private String code;
    /** 路由路径 **/
    private String routerPath;
    /** 组件路径 **/
    private String componentPath;
    /** 是否隐藏 **/
    @Column(name = "is_hidden")
    private Boolean hidden;
    /** 菜单类型（M目录 C菜单 F按钮） **/
    private String menuType;
    /** 接口编码，多个用逗号分隔 **/
    private String apiCodes;
    /** 打开方式 **/
    private Integer openMode;
    /** 是否刷新 **/
    private Integer keepAlive;
    /** 菜单层级关系，自动生成 **/
    private String path;
    /** 排序 **/
    private Integer orderNum;
    /** 可用状态 **/
    private Boolean status;
    /** 所属应用ID **/
    private String appId;
    /** 数据类型：0系统，1业务应用, 2开发平台, 3移动端 **/
    private Integer dataType;
    /** layout主题 **/
    private String theme;
    /** 页面渲染方式：0 Vue， 1 JSON **/
    private Integer pageType;
    /** 侧边菜单显示模式：0 不显示，1 从一级菜单开始显示，2 从二级菜单开始显示 **/
    private Integer sidebarNavMode;
    /** 顶部菜单显示模式：0 完全不显示, 1 不显示nav，2 从一级菜单开始显示，3 从二级菜单开始显示 **/
    private Integer topNavMode;
    /** 内容区显示样式：0 自动撑开，1 居中 **/
    private Integer mainMode;
    /** 是否开发者模式 **/
    @Column(name = "is_dev")
    private Boolean dev;
    /** 所属应用页面ID **/
    private String pageId;
    /** 完整路径 **/
    private String fullPath;

}
