package com.kingsware.kdev.sys.ret;

import com.kingsware.kdev.core.bean.BaseManageRet;
import com.kingsware.kdev.core.orm.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 拓扑图
 * @author AndyZheng
 * @version 1.0.0
 * @date 2022-02-13 10:20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table
public class DevTopologicalRet extends BaseManageRet {

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
