package com.kingsware.kdev.sys.model;

import com.kingsware.kdev.core.bean.BaseManageModel;
import com.kingsware.kdev.core.orm.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * FAAS节点模板
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2023/1/31 10:58 AM
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table
public class DevFaasNodeType extends BaseManageModel {

    /** 名称 */
    private String name ;
    /** 发布状态 */
    private Integer pubStatus ;
    /** 图标 */
    private String icon ;
}
