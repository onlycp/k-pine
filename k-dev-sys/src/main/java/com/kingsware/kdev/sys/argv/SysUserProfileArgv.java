package com.kingsware.kdev.sys.argv;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *  角色参数
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:47 上午
 */
@Data
@EqualsAndHashCode
public class SysUserProfileArgv {
    /** id */
    private String id;
    /** 用户名 */
    private String username;
    /** 姓名 **/
    private String realName;
    /** 头像 **/
    private String avatar;
    /** 手机号码 **/
    private String mobile;
    /** 电子邮箱 **/
    private String email;
    /** 性别 **/
    private Integer sex;
    /** 部门id **/
    private String sysUnitIds;
    /** 岗位 **/
    private String post;
    /** 备注 */
    private String note;
    /** 角色id **/
    private String sysRoleIds;
}
