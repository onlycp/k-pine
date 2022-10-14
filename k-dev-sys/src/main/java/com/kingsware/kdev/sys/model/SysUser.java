package com.kingsware.kdev.sys.model;

import com.kingsware.kdev.core.bean.BaseManageModel;
import com.kingsware.kdev.core.orm.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户表
 * @author chenpeng
 * @version 1.0.0
 * @date 2021-12-27 10:20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table
public class SysUser extends BaseManageModel {

    /** 用户名 */
    private String username;
    /** 密码 */
    private String password;
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
    /** 岗位 **/
    private String post;
    /** 状态 **/
    private Integer status;
    /** 备注 */
    private String note;
    /** 所属应用ID **/
    private String appId;

}
