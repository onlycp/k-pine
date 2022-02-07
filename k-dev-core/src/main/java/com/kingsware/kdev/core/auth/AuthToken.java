package com.kingsware.kdev.core.auth;

/**
 * 令牌信息
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/20 2:09 下午
 */
public class AuthToken {

    /** 创建时间 **/
    private long whenCreated;
    /** 发行机构 **/
    private String iss;
    /** 客户端ip, 用来校验用户的id **/
    private String ip;
    /** 用户的基本信息 **/
    private BaseUserInfo userInfo;

    public long getWhenCreated() {
        return whenCreated;
    }

    public void setWhenCreated(long whenCreated) {
        this.whenCreated = whenCreated;
    }

    public String getIss() {
        return iss;
    }

    public void setIss(String iss) {
        this.iss = iss;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public BaseUserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(BaseUserInfo userInfo) {
        this.userInfo = userInfo;
    }

}
