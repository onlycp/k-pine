package com.kingsware.kdev.sys.ret;

import com.kingsware.kdev.core.bean.BaseManageRet;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统配置返回
 *
 * @author crb
 * @version 1.0.0
 * @date 2022/1/14 11:35 上午
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysConfigRet extends BaseManageRet {
    /** 参数名称 */
    private String name;
    /** 参数键名 */
    private String code;
    /** 参数类型 */
    private Integer valueType;
    /** 参数键值 */
    private String value;
    /** 是否系统内置 */
    private Integer isSys;
    /** 是否开放配置 */
    private Integer isPublic;
    /** 备注 */
    private String note;
    /** 所属应用ID **/
    private String appId;
}
