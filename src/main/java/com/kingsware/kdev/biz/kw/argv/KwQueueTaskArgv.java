package com.kingsware.kdev.biz.kw.argv;

import lombok.Data;
import lombok.ToString;

/**
 * @description: 任务队列表
 * @version 1.0.0
 * @author: amzc
 * @date: 2022-01-11 15:49
 **/
@Data
public class KwQueueTaskArgv {
    /** id **/
    private String id;
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
