package com.kingsware.kdev.core.auth;

import com.kingsware.kdev.core.enums.ApiSystemEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
    /** 角色id,多个用逗号分隔 **/
    private String roleIds;
    /** 权限体系 **/
    private ApiSystemEnum apiSystem;

}
