package com.kingsware.kdev.sys.model;

import com.kingsware.kdev.core.bean.BaseManageModel;
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
@LogicDelete
public class DevPageTemplate extends BaseManageModel {

    /** 应用ID */
    private String appId;

    /** 应用名 */
    private String name;

    /** 应用介绍 */
    private String description;

    /** 应用类型 */
    private String appType;

    /** 页面JSON */
    private String pageJson;

    /** 应用标签 */
    private String tags;

    /** 模块ID */
    private String moduleId;

    /** 模板快照图片id，模板快照图片在保存模板时前端保存 */
    private String snapshotImgId;

    /** 排序 */
    private Integer orderNum;

    /** 模板展示的背景色 */
    private String bgColors;

    /** 模板页面类型：page;form;flow;report; */
    private String pageType;

//    /** 模板使用人数 */
//    private Integer useNum;

    /** 用于配置页面参数示例数据JSON */
    private String extra;

}
