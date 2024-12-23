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
public class SysKdbDataSourceQueryArgv extends BasePageArgv {
    /** 流程名称 **/
    private String name = "";
    /** 所属应用ID **/
    private String appId;
    /** 驱动类 **/
    private String driverClass;
    /** jdbc url **/
    private String jdbcUrl;
    /** 是否是crud管理列表 **/
    private boolean crud;
}
