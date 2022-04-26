package com.kingsware.kdev.core.auth;

import lombok.Data;

/**
 * 令牌信息
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/20 2:09 下午
 */
@Data
public class AuthToken {

    /** 创建时间 **/
    private long whenCreated;
    /** 发行机构 **/
    private String iss;
    /** 客户端ip, 用来校验用户的id **/
    private String ip;
    /** 会话id **/
    private String kSessionId;
    /** 用户的基本信息 **/
    private BaseUserInfo userInfo;
}
