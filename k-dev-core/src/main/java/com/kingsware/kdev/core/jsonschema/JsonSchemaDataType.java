package com.kingsware.kdev.core.jsonschema;

/**
 * schema 数据类型
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/2/25 2:32 下午
 */
public enum JsonSchemaDataType {
    // 以下是jsonscheme的数据类型
    OBJECT("object"),
    ARRAY("array"),
    NUMBER("number"),
    INTEGER("integer"),
    STRING("string"),
    BOOLEAN("boolean"),
    NULL("null"),
    // 以下为扩展
    ;

    private final String value;

    private JsonSchemaDataType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
