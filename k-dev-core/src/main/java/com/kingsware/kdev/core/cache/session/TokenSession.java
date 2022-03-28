package com.kingsware.kdev.core.cache.session;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 登录会话
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/3/7 11:19 上午
 */
@Data
public class TokenSession {
    /** id **/
    private String id;
    /** 登录时间 **/
    private Timestamp loginTime;
    /** 登录令牌 **/
    private String loginToken;
    /** 失效时间 **/
    private Timestamp expireTime;
    /** 最新活动时间 **/
    private Timestamp activeTime;
    /** 是否有变化 **/
    private boolean hasChanged = false;
    /** ping时间 **/
    private Timestamp pingTime;
}
