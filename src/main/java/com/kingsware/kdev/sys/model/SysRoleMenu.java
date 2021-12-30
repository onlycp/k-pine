package com.kingsware.kdev.sys.model;

import com.kingsware.kdev.core.bean.BaseManageModel;
import com.kingsware.kdev.sys.enums.MenuType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author andyzheng
 * @version 1.0.0
 * @description: 角色菜单中间关系
 * @date 2021/12/30 10:22
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysRoleMenu extends BaseManageModel {

    /** 菜单ID **/
    private String sysMenuId;
    /** 角色ID **/
    private String sysRoleId;
}
