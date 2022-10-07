package com.kingsware.kdev.sys.argv;

import com.kingsware.kdev.core.bean.BasePageArgv;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author AndyZheng
 * @version 1.0.0
 * @description: 搜索配置表查询
 * @date 2021-12-27 10:20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysSearchConfigQueryArgv extends BasePageArgv {

    /** 数据源名 */
    private String sourceName;
    /** 表名 */
    private String tableName;

}
