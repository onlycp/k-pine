package com.kingsware.kdev.sys.model;

import com.kingsware.kdev.core.bean.BaseModel;
import com.kingsware.kdev.core.orm.annotation.AutoEnum;
import com.kingsware.kdev.core.orm.annotation.Column;
import com.kingsware.kdev.core.orm.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

/**
 * 应用表
 * @author AndyZheng
 * @version 1.0.0
 * @date 2022-02-13 10:20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table
public class DevTeamApp extends BaseModel {

    /** id **/
    @Column(auto = AutoEnum.ID)
    private String id;

    /** 团队id */
    private String teamId;

    /** 应用id */
    private String appId;

    /** 团队类型 **/
    private Integer teamType;

    /** 创建人员 **/
    @Column(auto = AutoEnum.WHO, updatable = false)
    private String whoCreated;
    /** 创建时间 **/
    @Column(auto = AutoEnum.WHEN, updatable = false)
    private Timestamp whenCreated;


}
