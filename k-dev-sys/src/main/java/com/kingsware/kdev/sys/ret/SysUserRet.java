package com.kingsware.kdev.sys.ret;

import com.kingsware.kdev.core.bean.BaseManageRet;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户返回
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:49 上午
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserRet extends BaseManageRet {
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
    private String sysUnitId;
    /** 部门名称 **/
    private String sysUnitName;
    /** 部门路径 **/
    private String sysUnitPath;
    /** 角色ids **/
    private String sysRoleIds;
    /** 角色名 **/
    private String sysRoleNames;
    /** 岗位 **/
    private String post;
    /** 状态 **/
    private Integer status;
    /** 备注 */
    private String note;
}
