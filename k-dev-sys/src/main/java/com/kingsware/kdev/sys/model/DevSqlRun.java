package com.kingsware.kdev.sys.model;

import com.kingsware.kdev.core.bean.BaseManageModel;
import com.kingsware.kdev.core.bean.BaseModel;
import com.kingsware.kdev.core.bean.BaseSimpleRet;
import com.kingsware.kdev.core.orm.annotation.AutoEnum;
import com.kingsware.kdev.core.orm.annotation.Column;
import com.kingsware.kdev.core.orm.annotation.LogicDelete;
import com.kingsware.kdev.core.orm.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

/**
 * 页面表
 * @author AndyZheng
 * @version 1.0.0
 * @date 2022-02-13 10:20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table
public class DevSqlRun extends BaseModel {

    /** id **/
    @Column(auto = AutoEnum.ID)
    private String id;
    /** 创建时间 **/
    @Column(auto = AutoEnum.WHEN, updatable = false)
    private Timestamp whenCreated;
    /** version **/
    private Integer version;
    /** 版本MD5 **/
    private String md5;
    /** 执行时长 **/
    private Long executionTime;
    /** 是否执行成功 **/
    private Integer success;
}
