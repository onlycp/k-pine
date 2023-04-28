package com.kingsware.kdev.sys.model;

import com.kingsware.kdev.core.bean.BaseModel;
import com.kingsware.kdev.core.orm.annotation.AutoEnum;
import com.kingsware.kdev.core.orm.annotation.Column;
import com.kingsware.kdev.core.orm.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

/**
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2023/4/27 17:00
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table
public class DevPowerLink extends BaseModel {

    /** id **/
    @Column(auto = AutoEnum.ID)
    private String id;
    /** 能力树id **/
    private String treeId;
    /** 能力id **/
    private String powerId;
    /** 能力类型 **/
    private Integer powerType;
    /** 创建人员 **/
    @Column(auto = AutoEnum.WHO, updatable = false)
    private String whoCreated;
    /** 创建时间 **/
    @Column(auto = AutoEnum.WHEN, updatable = false)
    private Timestamp whenCreated;
}
