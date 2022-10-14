package com.kingsware.kdev.sys.argv;

import com.kingsware.kdev.core.bean.BasePageArgv;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author AndyZheng
 * @version 1.0.0
 * @description: 字典类型
 * @date 2021-12-27 10:20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserQueryArgv extends BasePageArgv {

    /** 用户名 */
    private String username;
    /** 用户姓名 */
    private String realName;
    /** 手机号码 */
    private String mobile;
    /** 状态 */
    private Integer status;
    /** 所属应用ID **/
    private String appId;
    /** 角色 */
    private String roleId;
    /** 归属部门 */
    private String sysUnitIds;

}
