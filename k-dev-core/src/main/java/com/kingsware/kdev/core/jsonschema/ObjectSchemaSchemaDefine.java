package com.kingsware.kdev.core.jsonschema;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 数组类型
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/2/25 3:24 下午
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ObjectSchemaSchemaDefine extends BaseSchemaDefine {

    public ObjectSchemaSchemaDefine() {
        this.setType(JsonSchemaDataType.OBJECT.getValue());
    }

    /** 属性 **/
    private Map<String, BaseSchemaDefine> properties = new LinkedHashMap<>();

    /**
     * 增加属性
     * @param title  标题
     * @param key    键值
     */
    public void addProperty(String key, String title, String type) {
        BaseSchemaDefine baseDefine = new BaseSchemaDefine();
        baseDefine.setTitle(title);
        baseDefine.setType(type);
        this.properties.put(key, baseDefine);
    }

}
