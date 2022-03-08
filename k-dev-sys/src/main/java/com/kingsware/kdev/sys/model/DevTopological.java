package com.kingsware.kdev.sys.model;

import com.kingsware.kdev.core.bean.BaseManageModel;
import com.kingsware.kdev.core.orm.annotation.LogicDelete;
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
@LogicDelete
public class DevTopological extends BaseManageModel {

    /** 应用ID */
    private String appId;

    /** 拓扑图名称 */
    private String name;

    /** 拓扑图介绍 */
    private String description;

    /** 可用状态 */
    private Integer enableStatus;

    /** 页面JSON */
    private String pageJson;

}
