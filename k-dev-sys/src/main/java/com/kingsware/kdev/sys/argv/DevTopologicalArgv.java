package com.kingsware.kdev.sys.argv;

import com.kingsware.kdev.core.orm.annotation.Table;
import lombok.Data;

/**
 * 页面表
 * @author AndyZheng
 * @version 1.0.0
 * @date 2022-02-13 10:20
 */
@Data
@Table
public class DevTopologicalArgv {
    /** idc**/
    private String id;

    /** 应用ID */
    private String appId;

    /** 应用名 */
    private String name;

    /** 应用介绍 */
    private String description;

    /** 可用状态 */
    private Integer enableStatus;

    /** 页面JSON */
    private String pageJson;

}
