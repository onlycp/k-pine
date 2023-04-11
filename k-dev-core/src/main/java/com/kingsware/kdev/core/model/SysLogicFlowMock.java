package com.kingsware.kdev.core.model;

import com.kingsware.kdev.core.bean.BaseManageModel;
import com.kingsware.kdev.core.orm.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 接口表
 * @author chenpeng
 * @version 1.0.0
 * @date 2021-12-27 10:20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table
public class SysLogicFlowMock extends BaseManageModel {

    /** 名称 */
    private String name ;
    /** 逻辑编排id */
    private String flowId ;
    /** 依赖mockId */
    private String dependId ;
    /** 请求参数 */
    private String requestArgv ;
    /** 表达式 */
    private String assertExpr ;
    /** 是否启用 */
    private Integer enableMock ;

}
