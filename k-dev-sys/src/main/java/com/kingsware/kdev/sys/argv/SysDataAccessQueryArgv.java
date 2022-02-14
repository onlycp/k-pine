package com.kingsware.kdev.sys.argv;

import com.kingsware.kdev.core.bean.BasePageArgv;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 数据访问组
 * @author AndyZheng
 * @version 1.0.0
 * @date 2021-12-27 10:20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysDataAccessQueryArgv extends BasePageArgv {

    /** 名称 */
    private String name;

    /** 状态 */
    private Integer status;

}
