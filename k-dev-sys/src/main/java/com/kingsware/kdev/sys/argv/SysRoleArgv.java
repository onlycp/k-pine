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
public class SysRoleArgv {
    /** idc**/
    private String id;
    /** 名称 **/
    private String name;
    /** 性别 0：男 1： 女 **/
    private String code;
    /** 生日 **/
    private String note;
    /** 状态 **/
    private Integer status;
    /** 所属应用ID **/
    private String appId;

}
