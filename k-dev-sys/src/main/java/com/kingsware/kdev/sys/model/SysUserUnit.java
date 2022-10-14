package com.kingsware.kdev.sys.model;

import com.kingsware.kdev.core.bean.BaseModel;
import com.kingsware.kdev.core.orm.annotation.AutoEnum;
import com.kingsware.kdev.core.orm.annotation.Column;
import com.kingsware.kdev.core.orm.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户部门中间表
 *
 * @author weiyt
 * @date 2022/10/14 11:03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table
public class SysUserUnit extends BaseModel {
    /** id **/
    @Column(auto = AutoEnum.ID)
    private String id;
    /** 用户id **/
    private String sysUserId;
    /** 部门id **/
    private String sysUnitId;
    /** 创建人员 **/
    @Column(auto = AutoEnum.WHO, updatable = false)
    private String whoCreated;
    /** 创建时间 **/
    @Column(auto = AutoEnum.WHEN, updatable = false)
    private String whenCreated;
    /** 所属应用ID **/
    private String appId;
}
