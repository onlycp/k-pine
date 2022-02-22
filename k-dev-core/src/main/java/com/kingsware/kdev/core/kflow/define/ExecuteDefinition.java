package com.kingsware.kdev.core.kflow.define;

import lombok.Data;

/**
 * 流程定义
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/19 6:13 下午
 */
@Data
public class ExecuteDefinition {

    /** 脚本定义 **/
    private Script script;

    /**
     * 创建sql变量
     * @param sourceName
     * @param content
     * @return
     */
    public static ExecuteDefinition createSqlScript(String sourceName, String content) {
        Script s = new Script();
        s.setSourceName(sourceName);
        s.setContent(content);
        s.setType("sql");
        ExecuteDefinition executeDefinition = new ExecuteDefinition();
        executeDefinition.script = s;
        return executeDefinition;
    }

    /**
     * 创建Js变量
     * @param js
     * @return
     */
    public static ExecuteDefinition createJsScript(String js) {
        Script s = new Script();
        s.setContent(js);
        s.setType("js");
        ExecuteDefinition executeDefinition = new ExecuteDefinition();
        executeDefinition.script = s;
        return executeDefinition;
    }

}
