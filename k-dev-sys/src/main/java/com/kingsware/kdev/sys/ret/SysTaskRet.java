package com.kingsware.kdev.sys.ret;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kingsware.kdev.core.bean.BaseManageRet;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

/**
 * 角色返回
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:49 上午
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysTaskRet extends BaseManageRet {
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
    /** 最后执行状态 1：成功 0：失败 **/
    private Integer lastExecuteStatus;
    /** 最后执行错误信息 **/
    private String lastExecuteMsg;
    /** 最后执行错误信息 **/
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
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
}
