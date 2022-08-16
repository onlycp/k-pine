package com.kingsware.kdev.core.kflow.define;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 节点连线
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/19 6:07 下午
 */
@Data
public class NodeLink {
    /** id **/
    private String id;
    /** 名称 **/
    private String name;
    /** from **/
    private String from;
    /** to **/
    private String to;
    /** 异常处理标识 **/
    @JsonProperty("catch_exception")
    private String catchException;
    /** 变量 **/
    private ExecuteDefinition variables;
    /** 条件 **/
    private ConditionDefinition conditions;
}
