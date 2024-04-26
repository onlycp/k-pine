package com.kingsware.kdev.sys.model;

import com.kingsware.kdev.core.bean.BaseManageModel;
import com.kingsware.kdev.core.orm.annotation.LogicDelete;
import com.kingsware.kdev.core.orm.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Table
public class DevPluginGroup extends BaseManageModel {
    /** 名称 */
    private String name ;
    /** 编码 */
    private String code ;
    /** 描述 */
    private String notes ;
    /** 排序 */
    private Integer orderNum ;

}