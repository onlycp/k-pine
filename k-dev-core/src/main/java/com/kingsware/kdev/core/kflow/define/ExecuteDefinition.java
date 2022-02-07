package com.kingsware.kdev.core.kflow.define;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

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
        s.sourceName = sourceName;
        s.content = content;
        s.type = "sql";
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
        s.content = js;
        s.type = "js";
        ExecuteDefinition executeDefinition = new ExecuteDefinition();
        executeDefinition.script = s;
        return executeDefinition;
    }



    /**
     * 脚本定义
     */
    @Data
    static class Script {
        /** 数据源名称 **/
        private String sourceName;
        /** 脚本内容 **/
        private String content;
        /** 类型 **/
        private String type;
        /** 参数 **/
        private List<Object> params = new ArrayList<>();
    }
}
