package com.kingsware.kdev.sys.argv;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 *  数据访问配置参数
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:47 上午
 */
@Data
@EqualsAndHashCode
@Accessors(chain = true)
public class SysViewModelFieldArgv {
    private String id;
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
    /** 所属应用ID **/
    private String appId;

}
