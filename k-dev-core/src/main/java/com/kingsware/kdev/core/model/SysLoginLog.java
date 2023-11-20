package com.kingsware.kdev.core.model;

import com.kingsware.kdev.core.bean.BaseModel;
import com.kingsware.kdev.core.orm.annotation.AutoEnum;
import com.kingsware.kdev.core.orm.annotation.Column;
import com.kingsware.kdev.core.orm.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

/**
 * 操作日志
 * @author chenpeng
 * @version 1.0.0
 * @date 2021-12-27 10:20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table
public class SysLoginLog extends BaseModel {
    /** id **/
    @Column(auto = AutoEnum.ID)
    private String id;
    /** 操作时间 **/
    private Timestamp operateTime;
    /** 操作人员 **/
    private String operator;
    /** ip **/
    private String ip;
    /**  耗时 **/
    private int times;
    /** 响应码 **/
    private int responseCode;

    private String address;
    /** 响应消息 **/
    private String responseMessage;
    /** 创建时间 **/
    @Column(auto = AutoEnum.WHEN, updatable = false)
    private String whenCreated;

}
