package com.kingsware.kdev.sys.ret;

import com.kingsware.kdev.core.bean.BaseManageModel;
import com.kingsware.kdev.core.bean.BaseManageRet;
import com.kingsware.kdev.core.orm.annotation.LogicDelete;
import com.kingsware.kdev.core.orm.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 页面表
 * @author AndyZheng
 * @version 1.0.0
 * @date 2022-02-13 10:20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table
public class DevPageRet extends BaseManageRet {

    /** 应用ID */
    private String appId;

    /** 应用名 */
    private String name;

    /** 应用介绍 */
    private String description;

    /** 路径 */
    private String path;

    /** 应用类型 */
    private String appType;

    /** 是否需要登录 */
    private Integer loginRequired;

    /** 可用状态 */
    private Integer enableStatus;

    /** 开发状态 */
    private Integer devStatus;

    /** 页面JSON */
    private String pageJson;

}
