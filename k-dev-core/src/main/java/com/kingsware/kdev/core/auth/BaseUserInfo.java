package com.kingsware.kdev.core.auth;

import com.kingsware.kdev.core.enums.ApiSystemEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

/**
 * 登录用户的基本信息
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/20 2:02 下午
 */
@Data
@EqualsAndHashCode
public class BaseUserInfo {
    /** 用户id **/
    private String id;
    /** 用户名 **/
    private String username;
    /** 姓名 **/
    private String realName;
    /** 头像 **/
    private String avatar;
    /** 手机号码 **/
    private String mobile;
    /** 电子邮箱 **/
    private String email;
    /** 角色id,多个用逗号分隔 **/
    private String roleIds;
    /** 角色code,多个用逗号分隔 **/
    private String roleCodes;
    /** 数据权限id **/
    private String accessIds;
    /** 角色名,多个用逗号分隔 **/
    private String roleNames;
    /** 权限体系 **/
    private ApiSystemEnum apiSystem;
    /** 归属单位id **/
    private String sysUnitId;
    /** 角色拥有的菜单权限 **/
    private Set<String> permissions;
}
