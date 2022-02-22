package com.kingsware.kdev.core.kflow.define;

/**
 * 节点状态机
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/19 6:31 下午
 */
public enum ScriptTypeEnum {
    /** 开始 **/
    JS("js"),
    /** 开始 **/
    SQL("sql"),
    ;

    private String value;

    private ScriptTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
