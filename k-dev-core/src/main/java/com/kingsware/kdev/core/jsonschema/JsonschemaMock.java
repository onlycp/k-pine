package com.kingsware.kdev.core.jsonschema;

import com.fasterxml.jackson.databind.JsonNode;
import com.kingsware.kdev.core.kflow.FlowUtils;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.core.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Json schema mock
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/3/1 4:42 下午
 */
public class JsonschemaMock {
    private static JsonschemaMock instance;

    private JsonschemaMock() {
    }

    public static JsonschemaMock getInstance() {
        if (instance == null) {
            instance = new JsonschemaMock();
        }
        return instance;
    }


    /**
     * 生成mock数据
     * @param jsonSchema    json
     * @return  返回json字符串
     */
    public String mock(String jsonSchema) {
        // 返回
        return JsonUtil.toJson(mockMap(jsonSchema));
    }

    /**
     * 生成mock数据
     * @param jsonSchema    json
     * @return  返回json字符串
     */
    public Map<String, Object>  mockMap(String jsonSchema) {
        // 如果定义为空
        if (StringUtils.isEmpty(jsonSchema)) {
            return new HashMap<>();
        }

        Map<String, Object> variables = new HashMap<>();
        // 根节点定义
        JsonNode rootNode = JsonUtil.toTree(jsonSchema);
        // 处理
        FlowUtils.handleNodeValue(variables, rootNode, true);
        // 返回
        return variables;
    }


}
