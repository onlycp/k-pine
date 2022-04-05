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
public class SysRoleQueryArgv extends BasePageArgv {

    /** 字典名 */
    private String name;
    /** 字典代码 */
    private String code;
    /** 状态 */
    private Integer status;
    /** 所属应用ID **/
    private String appId;

}
