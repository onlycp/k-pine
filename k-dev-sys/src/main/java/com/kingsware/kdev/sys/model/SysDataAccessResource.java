package com.kingsware.kdev.sys.model;

import com.kingsware.kdev.core.bean.BaseModel;
import com.kingsware.kdev.core.orm.annotation.AutoEnum;
import com.kingsware.kdev.core.orm.annotation.Column;
import com.kingsware.kdev.core.orm.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 数据权限和资源
 * @author chenpeng
 * @version 1.0.0
 * @date 2021-12-27 10:20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table
public class SysDataAccessResource extends BaseModel {
    /** id **/
    @Column(auto = AutoEnum.ID)
    private String id;
    /** 数据id **/
    private String dataId;
    /** 表名 **/
    private String tableName;
    /** 数据权限id **/
    private String accessId;
    /** 创建人员 **/
    @Column(auto = AutoEnum.WHO, updatable = false)
    private String whoCreated;
    /** 创建时间 **/
    @Column(auto = AutoEnum.WHEN, updatable = false)
    private String whenCreated;
    /** 所属应用ID **/
    private String appId;

}
