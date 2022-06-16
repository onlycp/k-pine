package com.kingsware.kdev.sys.model;

import com.kingsware.kdev.core.bean.BaseManageModel;
import com.kingsware.kdev.core.orm.annotation.LogicDelete;
import com.kingsware.kdev.core.orm.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 应用表
 * @author AndyZheng
 * @version 1.0.0
 * @date 2022-02-13 10:20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table
@LogicDelete
public class DevApplication extends BaseManageModel {

    /** 应用名 */
    private String name;

    /** 应用短英文名 */
    private String shortName;

    /** 应用介绍 */
    private String description;

    /** 可用状态 */
    private Integer enableStatus;

    /** 开发状态 */
    private Integer devStatus;

    /** 当前发布版本 */
    private String version;

    /** 负责人 */
    private String whoInCharge;

    /** 应用图标 */
    private String systemLogo;

    /** 应用类型 */
    private String appType;

    /** 默认路径 */
    private String defaultPath;

    /** 应用发布类型 */
    private Integer appPublicType;

}
