package com.kingsware.kdev.sys.ret;

import lombok.Data;

/**
 * 用户部门名称
 *
 * @author weiyt
 * @date 2022/10/14 0:26
 */
@Data
public class SysUserUnitName {
    /** 用户id **/
    private String sysUserId;
    /** 部门id **/
    private String sysUnitId;
    /** 部门名称 **/
    private String name;
    /** 部门路径 **/
    private String path;
}
