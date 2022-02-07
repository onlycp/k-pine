package com.kingsware.kdev.core.kflow.define;

/**
 * 节点状态机
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/19 6:31 下午
 */
public enum NodeTypeEnum {
    /** 开始 **/
    START("start"),
    /** 开始 **/
    END("end"),
    /** 分支 **/
    DECISION("decision"),
    /** Task **/
    TASK("task"),
    /** Fork **/
    FORK("fork"),
    /** Join **/
    JOIN("join"),
    ;

    private String value;

    private NodeTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
