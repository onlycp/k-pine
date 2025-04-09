package com.kingsware.kdev.sys.devops.task;

import lombok.Data;

@Data
public class FlowInfoCopy {
    /** 流程id **/
    private String flowid;
    /** 流程内容 **/
    private String content;
    /** 名称 **/
    private String name;
    /** 描述 **/
    private String description = "";

    /** 创建时间 **/
    private Long createTime;
    /** 更新时间 **/
    private Long updateTime;
}
