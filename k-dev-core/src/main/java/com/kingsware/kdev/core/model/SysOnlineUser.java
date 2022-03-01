package com.kingsware.kdev.core.model;

import com.kingsware.kdev.core.bean.BaseManageModel;
import com.kingsware.kdev.core.bean.BaseModel;
import com.kingsware.kdev.core.orm.annotation.AutoEnum;
import com.kingsware.kdev.core.orm.annotation.Column;
import com.kingsware.kdev.core.orm.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

/**
 * 在线用户表
 * @author chenpeng
 * @version 1.0.0
 * @date 2021-12-27 10:20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table
public class SysOnlineUser extends BaseModel {
    /** id **/
    @Column(auto = AutoEnum.ID)
    private String id;
    /**  用户id **/
    private String userId;
    /** 登录ip **/
    private String loginIp;
    /** 登录时间 **/
    private Timestamp loginTime;
    /** 登录令牌 **/
    private String loginToken;
    /** 失效时间 **/
    private Timestamp expireTime;
    /** 创建时间 **/
    @Column(auto = AutoEnum.WHEN, updatable = false)
    private Timestamp whenCreated;
}
