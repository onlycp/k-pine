package com.kingsware.kdev.sys.model;

import com.kingsware.kdev.core.bean.BaseManageModel;
import com.kingsware.kdev.core.orm.annotation.LogicDelete;
import com.kingsware.kdev.core.orm.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 认证源
 * @author AndyZheng
 * @version 1.0.0
 * @date 2022-02-13 10:20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table
public class SysAuthSource extends BaseManageModel {

    /** 应用名 */
    private String name;

    /** 编码 */
    private String code;

    /** 状态 */
    private Integer status;

    /** 序号 */
    private Integer orderNum;

    /** 配置 */
    private String config;

    /** 备注 */
    private String note;

    /** 逻辑编排id **/
    private String logicFlowId;


}
