package com.kingsware.kdev.core.orm.kdb;

/**
 * kdb查询传参
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/24 2:41 下午
 */
public class KdbArgc<T> {
    /** 流程id **/
    private String flowID;
    /** 脚本 **/
    private String script;
    /** 参数 **/
    private T params;

    public KdbArgc(String flowID, String script, T params) {
        this.flowID = flowID;
        this.script = script;
        this.params = params;
    }

    public String getFlowID() {
        return flowID;
    }

    public void setFlowID(String flowID) {
        this.flowID = flowID;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public T getParams() {
        return params;
    }

    public void setParams(T params) {
        this.params = params;
    }
}
