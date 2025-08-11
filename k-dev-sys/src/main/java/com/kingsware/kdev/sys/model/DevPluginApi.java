package com.kingsware.kdev.sys.model;

import com.kingsware.kdev.core.bean.BaseManageModel;
import com.kingsware.kdev.core.orm.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Table
public class DevPluginApi extends BaseManageModel {
    /** 标题 */
    private String title ;
    /** 组编码 */
    private String groupCode ;
    /** 编码 */
    private String code ;
    /** 标签 */
    private String tags ;
    /** 描述 */
    private String notes ;
    /** 位置 */
    private String orderNum ;
}
