package com.kingsware.kdev.sys.argv;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *  角色参数
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:47 上午
 */
@Data
@EqualsAndHashCode
public class SysTaskArgv {
    /** idc**/
    private String id;
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
    /** 描述 **/
    private String note;
    /** 所属应用ID **/
    private String appId;
    /** 任务参数 **/
    private String taskArgv;

}
