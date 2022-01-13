package com.kingsware.kdev.biz.kw.model;

import com.kingsware.kdev.core.bean.BaseManageModel;
import com.kingsware.kdev.core.orm.annotation.LogicDelete;
import com.kingsware.kdev.core.orm.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description:
 * @author: amzc
 * @date: 2022-01-11 16:32
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@Table
@LogicDelete
public class KwQueueTask extends BaseManageModel {
    /** 任务名称 */
    private String name;
    /** 任务数据 */
    private String data;
    /** 错误信息 **/
    private String error;
    /** 描述信息 **/
    private String description;
    /** 任务类型 **/
    private Integer type;
    /** 任务状态 **/
    private Integer status;
}
