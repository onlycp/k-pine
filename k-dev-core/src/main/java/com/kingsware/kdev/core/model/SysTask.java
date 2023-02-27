package com.kingsware.kdev.core.model;

import com.kingsware.kdev.core.bean.BaseManageModel;
import com.kingsware.kdev.core.orm.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

/**
 * 任务表
 * @author chenpeng
 * @version 1.0.0
 * @date 2021-12-27 10:20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table
public class SysTask extends BaseManageModel {
    /** 名称 **/
    private String name;
    /** 定时表达式 **/
    private String cron;
    /** 是否分布式 **/
    private Integer distributed;
    /** 应用id **/
    private String applicationId;
    /** 任务类型, 1：Java类 2：流程 3:java脚本  **/
    private Integer taskType;
    /** 任务资源类型 **/
    private String taskResourceId;
    /** 类名 **/
    private String className;
    /** 是否启用 **/
    private Integer enable;
    /** 上次执行状态 1：成功 0：失败 **/
    private Integer lastExecuteStatus;
    /** 上次执行错误信息 **/
    private String lastExecuteMsg;
    /** 上次执行时间 **/
    private Timestamp lastExecuteTime;
    /** 上次执行消耗 **/
    private Long lastExecuteTake;
    /** 最长锁定时间 **/
    private Integer lockForMost;
    /** 最少锁定时间 **/
    private Integer lockForLeast;
    /** 锁定时间 **/
    private Timestamp lockForTime;
    /** 描述 **/
    private String note;
    /** 锁定状态 **/
    private Integer lockStatus;
    /** 任务参数 **/
    private String taskArgv;
    /** 下一个实例 **/
    private String nextInst;

}
