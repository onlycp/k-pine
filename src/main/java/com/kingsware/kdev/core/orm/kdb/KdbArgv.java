package com.kingsware.kdev.core.orm.kdb;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingsware.kdev.core.util.BeanUtils;
import com.kingsware.kdev.core.util.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * kdb查询传参
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/24 2:41 下午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KdbArgv {
    /** 流程id **/
    private String flowID;
    /** 节点可变参数 **/
    private Map<String, StepArgv> script = new LinkedHashMap<>();
    /** 上下文变量 **/
    private Map<String, Object> variables = new LinkedHashMap<>();

    /**
     * 增加节点参数
     * @param name      名称
     * @param content   内容
     * @param params    参数
     */
    public KdbArgv addStep(String name, String content, String sourceName, List<Object> params) {
        StepArgv argv = new StepArgv();
        argv.setContent(content);
        argv.setParams(params);
        argv.setSourceName(sourceName);
        script.put(name, argv);
        return this;
    }

    /**
     * 增加变量
     * @param key       键名
     * @param value     键值
     * @return      当前对象
     */
    public KdbArgv addVariable(String key, Object value) {
        this.variables.put(key, value);
        return this;
    }

    /**
     * 增加变量
     * @param variables   变量集合
     * @return      当前对象
     */
    public KdbArgv addVariable(Map<String, Object> variables) {
        this.variables.putAll(variables);
        return this;
    }

    /**
     * 增加变量
     * @param object    对象
     * @return          当前变量
     */
    public KdbArgv addVariable(Object object) {
        this.variables.putAll(JsonUtil.beanToMap(object));
        return this;
    }

}
