package com.kingsware.kdev.sys.argv;

import com.kingsware.kdev.core.bean.BasePageArgv;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 单位查询
 * @author chen peng
 * @version 1.0.0
 * @date 2021-12-27 10:20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUnitQueryArgv extends BasePageArgv {

    /** 名称 */
    private String name;
    /** 状态 */
    private Integer status;
    /** 所属应用ID **/
    private String appId;

}
