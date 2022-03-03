package com.kingsware.kdev.core.jsonschema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;


/**
 * schema根定义
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/2/24 5:52 下午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseSchemaDefine {
    /** 标题 **/
    private String title = "";
    /** 描述 **/
    private String description = "";
    /** 类型 **/
    private String type = "";
    /** 扩展数据类型 **/
    private String externType = "";
    /** 扩展信息 **/
    private Map<String, Object> extern = new HashMap<>();

}
