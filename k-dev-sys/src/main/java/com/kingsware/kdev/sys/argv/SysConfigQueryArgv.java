package com.kingsware.kdev.sys.argv;

import com.kingsware.kdev.core.bean.BasePageArgv;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统配置类
 *
 * @author crb
 * @version 1.0.0
 * @date 2022/1/13 16:35 上午
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysConfigQueryArgv extends BasePageArgv {
    /** 参数名称 */
    private String name;
    /** 参数键名 */
    private String code;
    /** 是否系统内置 */
    private Integer isSys;
    /** 所属应用ID **/
    private String appId;
}
