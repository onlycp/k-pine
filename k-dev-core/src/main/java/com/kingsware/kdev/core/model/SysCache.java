package com.kingsware.kdev.core.model;

import com.kingsware.kdev.core.bean.BaseModel;
import com.kingsware.kdev.core.orm.annotation.AutoEnum;
import com.kingsware.kdev.core.orm.annotation.Column;
import com.kingsware.kdev.core.orm.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 缓存
 * @author AndyZheng
 * @version 1.0.0
 * @date 2022-12-5
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table
public class SysCache extends BaseModel {
    /** id **/
    @Column(auto = AutoEnum.ID)
    private String id;
    /** 缓存KEY **/
    private String code;
    /** 缓存值 **/
    private String value;
    /** 过期时间 **/
    private String whenExpired;
    /** 关联应用ID **/
    private String appId;
    /** 创建时间 **/
    @Column(auto = AutoEnum.WHEN, updatable = false)
    private String whenCreated;

}
