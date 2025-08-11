package com.kingsware.kdev.sys.ret;

import com.kingsware.kdev.core.bean.BaseSimpleRet;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

/**
 * 在线用户返回
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/6 11:52 上午
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysOnlineUserRet extends BaseSimpleRet {

    /** id **/
    private String id;
    /**  用户id **/
    private String userId;
    /** 用户名 **/
    private String username;
    /** 登录ip **/
    private String loginIp;
    /** 登录时间 **/
    private Timestamp loginTime;
    /** 失效时间 **/
    private Timestamp expireTime;
    /** 创建时间 **/
    private Timestamp whenCreated;
    /** 所属应用ID **/
    private String appId;
}
