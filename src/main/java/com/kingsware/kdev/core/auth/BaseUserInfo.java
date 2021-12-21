package com.kingsware.kdev.core.auth;

import com.kingsware.kdev.core.enums.ApiSystemEnum;

/**
 * 登录用户的基本信息
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/20 2:02 下午
 */
public class BaseUserInfo {
    /** 用户id **/
    private String id;
    /** 用户名 **/
    private String username;
    /** 姓名 **/
    private String realName;
    /** 手机号码 **/
    private String mobile;
    /** 角色id,多个用逗号分隔 **/
    private String roleIds;
    /** 权限体系 **/
    private ApiSystemEnum apiSystem;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public ApiSystemEnum getApiSystem() {
        return apiSystem;
    }

    public void setApiSystem(ApiSystemEnum apiSystem) {
        this.apiSystem = apiSystem;
    }
}
