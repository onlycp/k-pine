package com.kingsware.kdev.core.kflow.define;

import lombok.Data;

/**
 * 节点定义
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/19 6:05 下午
 */
@Data
public class NodeDefinition {
    /** 节点id **/
    private String id;
    /** 名称 **/
    private String name;
    /** 是否自动完成 **/
    private boolean auto = true;
    /** 状态 **/
    private String state = "completed";
    /** 是否调试 **/
    private boolean debug;
    /** 节点类型 **/
    private String type;
    /** 节点变量 **/
    private VariableDefinition variables;
}
