package com.kingsware.kdev.sys.argv;

import com.kingsware.kdev.core.bean.BasePageArgv;
import com.kingsware.kdev.core.orm.annotation.Column;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 单位查询
 * @author chen peng
 * @version 1.0.0
 * @date 2021-12-27 10:20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysMenuQueryArgv extends BasePageArgv {

    /** 名称 */
    private String name;
    /** 状态 */
    private Integer status;
    /** 所属应用ID **/
    private String appId;
    /** 数据类型：0系统，1业务应用, 2开发平台 **/
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
    private Boolean dev;

}
