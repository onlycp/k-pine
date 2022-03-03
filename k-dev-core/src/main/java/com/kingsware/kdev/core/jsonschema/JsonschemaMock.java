package com.kingsware.kdev.core.jsonschema;

import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.core.util.RandomUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        Map<String, Object> map  = JsonUtil.toMap(jsonSchema);
        // 当前仅仅支持object
        Object mockObject = mockValue(map);
        // 转为json
        return JsonUtil.toJson(mockObject);
    }

    /**
     * 生成mock数据
     *
     * @param schema schema定义
     * @return 返回mock
     */
    private Object mockValue(Map<String, Object> schema) {
        // 如果是数组
        if (schema == null) {
            return null;
        }
        if (schema.get("type").toString().equalsIgnoreCase(JsonSchemaDataType.OBJECT.getValue())) {
            ObjectSchemaSchemaDefine objectSchemaSchemaDefine = JsonUtil.toBean(JsonUtil.toJson(schema), ObjectSchemaSchemaDefine.class);
            Map<String, Object> objectMap = new HashMap<>();
            // 遍历属性处理
            if (objectSchemaSchemaDefine != null) {
                for (Map.Entry<String, BaseSchemaDefine> entry : objectSchemaSchemaDefine.getProperties().entrySet()) {
                    objectMap.put(entry.getKey(), mockValue(JsonUtil.toMap(JsonUtil.toJson(entry.getValue()))));
                }
            }
            return objectMap;
        }
        //数组
        else if (schema.get("type").toString().equalsIgnoreCase(JsonSchemaDataType.ARRAY.getValue())) {
            ArraySchemaDefine arraySchemaDefine = JsonUtil.toBean(JsonUtil.toJson(schema), ArraySchemaDefine.class);
            assert arraySchemaDefine != null;
            Object arrayItem = mockValue(JsonUtil.toMap(JsonUtil.toJson(arraySchemaDefine.getItems())));
            List<Object> arrayResult = new ArrayList<>();
            arrayResult.add(arrayItem);
            return arrayResult;
        }
        // 字符串
        else if (schema.get("type").toString().equalsIgnoreCase(JsonSchemaDataType.STRING.getValue())) {
            return RandomUtils.randString(10);
        }
        // 布尔
        else if (schema.get("type").toString().equalsIgnoreCase(JsonSchemaDataType.BOOLEAN.getValue())) {
            return true;
        }
        // 数字
        else if (schema.get("type").toString().equalsIgnoreCase(JsonSchemaDataType.NUMBER.getValue())) {
            return 1.0;
        }
        // 整型
        else if (schema.get("type").toString().equalsIgnoreCase(JsonSchemaDataType.INTEGER.getValue())) {
            return 1;
        }
        // 其他
        else {
            return null;
        }
    }

}
