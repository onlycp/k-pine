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
public class DevFaasNode extends BaseManageModel {

    /** 名称 */
    private String name ;
    /** 编码 */
    private String code ;
    /** 类型id */
    private String typeId ;
    /** 配置文件 */
    private String config ;
    /** 脚本模板 */
    private String template ;
    /** 图标 */
    private String icon ;
    /** 发布状态 0：未发布 1：已发布 */
    private Integer pubStatus ;
    /** 排序 */
    private String orderNum ;
}
