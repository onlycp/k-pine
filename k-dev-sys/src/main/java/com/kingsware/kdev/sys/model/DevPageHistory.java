package com.kingsware.kdev.sys.model;

import com.kingsware.kdev.core.bean.BaseModel;
import com.kingsware.kdev.core.orm.annotation.AutoEnum;
import com.kingsware.kdev.core.orm.annotation.Column;
import com.kingsware.kdev.core.orm.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

/**
 * 页面修改记录表
 * @author AndyZheng
 * @version 1.0.0
 * @date 2022-02-13 10:20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table
public class DevPageHistory extends BaseModel {

    /** id **/
    @Column(auto = AutoEnum.ID)
    private String id;

    /** 页面ID */
    private String pageId;

    /** 页面JSON */
    private String pageJson;

    /** 创建人员 **/
    @Column(auto = AutoEnum.WHO, updatable = false)
    private String whoCreated;

    /** 创建时间 **/
    @Column(auto = AutoEnum.WHEN, updatable = false)
    private Timestamp whenCreated;

}
