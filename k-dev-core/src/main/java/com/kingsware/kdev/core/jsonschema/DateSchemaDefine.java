package com.kingsware.kdev.core.jsonschema;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/2/25 3:24 下午
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DateSchemaDefine extends BaseSchemaDefine {

    /** 字典码 **/
    private String code;

    public DateSchemaDefine() {
        super();
    }


}
