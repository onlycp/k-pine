package com.kingsware.kdev.sys.ret;

import com.kingsware.kdev.core.bean.BaseManageRet;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色返回
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:49 上午
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysViewModelFieldRet extends BaseManageRet {
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
