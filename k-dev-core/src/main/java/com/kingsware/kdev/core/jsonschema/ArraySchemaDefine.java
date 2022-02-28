package com.kingsware.kdev.core.jsonschema;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 数组类型
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/2/25 3:24 下午
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ArraySchemaDefine extends BaseSchemaDefine {

    public ArraySchemaDefine() {
        this.setType(JsonSchemaDataType.ARRAY.getValue());
    }

    /** 数据项 **/
    private BaseSchemaDefine items;
}
