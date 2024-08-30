package com.kingsware.kdev.core.orm.kdb;

import com.kingsware.kdev.core.kflow.bean.DebugNode;
import com.kingsware.kdev.core.util.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    /** 事务id **/
    private String transactionUuid;
    /** 实例id **/
    private String instID;
    /** 会话id **/
    private String sessionID;
    /** 节点可变参数 **/
    private Map<String, StepArgv> script = new LinkedHashMap<>();
    /** 上下文变量 **/
    private Map<String, Object> variables = new LinkedHashMap<>();
    /** 调试节点列表 **/
    private List<DebugNode> debugger;

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

    /**
     * 获取与指定键对应的字符串值
     * 如果键不存在，则返回默认值；如果值为null，则返回null
     *
     * @param key        要获取的变量的键
     * @param defaultValue  如果键不存在时返回的默认值
     * @return 对应键的字符串值，如果键不存在或值为null，则返回null
     */
    public String getString(String key, String defaultValue) {
        if (!variables.containsKey(key)) {
            return defaultValue;
        }
        Object value = variables.get(key);
        if (value == null) {
            return null;
        }
        return value.toString();
    }

    /**
     * 获取与指定键对应的整数值
     * 如果键不存在，则返回默认值；如果值为null，则返回null
     *
     * @param key        要获取的变量的键
     * @param defaultValue  如果键不存在时返回的默认整数值
     * @return 对应键的整数值，如果键不存在或值为null，则返回null
     */
    public Integer getInteger(String key, Integer defaultValue) {
        if (!variables.containsKey(key)) {
            return defaultValue;
        }
        Object value = variables.get(key);
        if (value == null) {
            return null;
        }
        return Integer.parseInt(value.toString());
    }

    /**
     * 获取与指定键对应的布尔值
     * 如果键不存在，则返回默认值
     *
     * @param key        要获取的变量的键
     * @param defaultValue  如果键不存在时返回的默认布尔值
     * @return 对应键的布尔值，如果键不存在则返回默认值
     */
    public Boolean getBoolean(String key, Boolean defaultValue) {
        if (!variables.containsKey(key)) {
            return defaultValue;
        }
        return Boolean.parseBoolean(variables.get(key).toString());
    }

}
