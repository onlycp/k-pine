package com.kingsware.kdev.core.model;

import com.kingsware.kdev.core.bean.BaseManageModel;
import com.kingsware.kdev.core.orm.annotation.Column;
import com.kingsware.kdev.core.orm.annotation.Table;
import com.kingsware.kdev.core.orm.kdb.FlowInfo;
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
public class SysLogicFlow extends BaseManageModel {

    /** 流程id **/
    private String flowId;
    /** 名称 **/
    private String name;
    /** 应用id **/
    private String applicationId;
    /** 标签 **/
    private String tags;
    /** 备注 **/
    private String note;
    /** 输入参数定义 **/
    private String inArgv;
    /** 输出参数定义 **/
    private String outArgv;
    /** 子流程ids **/
    private String subFlowIds;
    /** 默认数据源 **/
    private String defaultSourceName;
    /** 是否开始事务控制 **/
    @Column(name = "app_id")
    private String tranCtrl;

}
