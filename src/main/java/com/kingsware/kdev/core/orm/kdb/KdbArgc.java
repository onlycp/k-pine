package com.kingsware.kdev.core.orm.kdb;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * kdb查询传参
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/24 2:41 下午
 */
@Data
@AllArgsConstructor
public class KdbArgc<T> {
    /** 数据源 **/
    private String dataSourceName;
    /** 流程id **/
    private String flowID;
    /** 脚本 **/
    private String script;
    /** 参数 **/
    private T params;


}
