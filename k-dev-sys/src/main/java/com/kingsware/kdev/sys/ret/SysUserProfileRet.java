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
public class SysUserProfileRet extends BaseManageRet {
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
    /** 部门名称 **/
    private String sysUnitName;
    /** 岗位 **/
    private String post;
    /** 备注 */
    private String note;
    /** 角色id,多个用逗号分隔 **/
    private String roleIds;
    /** 角色名,多个用逗号分隔 **/
    private String roleNames;

}
