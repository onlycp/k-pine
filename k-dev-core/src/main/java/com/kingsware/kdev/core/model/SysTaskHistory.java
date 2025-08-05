package com.kingsware.kdev.core.model;

import com.kingsware.kdev.core.bean.BaseManageModel;
import com.kingsware.kdev.core.orm.annotation.Table;
import lombok.Data;

/**
 * 任务执行历史
 */
@Data
@Table
public class SysTaskHistory extends BaseManageModel {
    /** 任务 id **/
    private String taskId;

    /** 任务名称 **/
    private String taskName;

    /** 执行状态 1：成功 0：失败 **/
    private Integer executeStatus;

    /** 执行消耗 **/
    private Long executeTake;

    /** 执行开始时间 **/
    private String executeBeginTime;

    /** 执行结束时间 **/
    private String executeEndTime;

    /** 上次执行错误信息 **/
    private String executeMsg;
}
