package com.kingsware.kdev.sys.model;

import com.kingsware.kdev.core.bean.BaseModel;
import com.kingsware.kdev.core.orm.annotation.AutoEnum;
import com.kingsware.kdev.core.orm.annotation.Column;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

/**
 * @author andyzheng
 * @version 1.0.0
 * @description: 角色菜单中间关系
 * @date 2021/12/30 10:22
 */
@Data
@EqualsAndHashCode
public class SysRoleMenu extends BaseModel {
    /** id **/
    @Column(auto = AutoEnum.ID)
    private String id;
    /** 菜单ID **/
    private String sysMenuId;
    /** 角色ID **/
    private String sysRoleId;
    /** 创建人员 **/
    @Column(auto = AutoEnum.WHO, updatable = false)
    private String whoCreated;
    /** 创建时间 **/
    @Column(auto = AutoEnum.WHEN, updatable = false)
    private Timestamp whenCreated;
    /** 所属应用ID **/
    private String appId;
}
