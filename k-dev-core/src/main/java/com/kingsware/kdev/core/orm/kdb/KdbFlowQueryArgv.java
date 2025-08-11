package com.kingsware.kdev.core.orm.kdb;

import lombok.Data;

import java.util.List;

/**
 * 流程信息
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/5 4:20 下午
 */
@Data

public class KdbFlowQueryArgv {

    /** 流程id **/
    private String flowId = "";
    /** 流程内容 **/
    private String content = "";
    /** 名称 **/
    private String name = "";
    /** 父流程id **/
    private String parentId = "";
    /** 当前页 **/
    private Integer page;
    /** 每页数量 **/
    private Integer offset;
    /** 流程id集合 **/
    private List<String> flowIds;
    /** 是否分页 **/
    private boolean pageQuery;
}
