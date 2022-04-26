package com.kingsware.kdev.core.orm.kdb;

import com.kingsware.kdev.core.orm.annotation.Table;
import lombok.Data;

/**
 * 流程信息
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/5 4:20 下午
 */
@Data
@Table("flow")
public class FlowInfo {

    /** 流程id **/
    private String flowId;
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
