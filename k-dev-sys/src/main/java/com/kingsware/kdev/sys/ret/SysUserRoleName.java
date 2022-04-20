package com.kingsware.kdev.sys.ret;

import lombok.Data;

/**
 * 用户角色名称
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/4/19 6:08 PM
 */
@Data
public class SysUserRoleName {
    /** 用户id **/
    private String sysUserId;
    /** 角色名称 **/
    private String name;
}
