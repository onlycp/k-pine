package com.kingsware.kdev.sys.model;

import com.kingsware.kdev.core.bean.BaseManageModel;
import com.kingsware.kdev.core.orm.annotation.LogicDelete;
import com.kingsware.kdev.core.orm.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 视图模型
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/29 3:44 下午
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table
public class SysViewModelField extends BaseManageModel {
    /** 模型id **/
    private String viewModelId;
    /** 属性名 **/
    private String field;
    /** 标签 **/
    private String label;
    /** 数据类型 **/
    private String type;
    /** 格式类型 **/
    private String formatType;
    /** 格式规则 **/
    private String formatPattern;
    /** 默认显示 **/
    private String defaultText;
    /** 是否隐藏 **/
    private Integer hidden;
    /** 排序 **/
    private Integer orderNum;
}
