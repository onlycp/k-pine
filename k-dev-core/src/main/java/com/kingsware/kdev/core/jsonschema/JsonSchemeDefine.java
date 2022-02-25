package com.kingsware.kdev.core.jsonschema;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * schema根定义
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/2/24 5:52 下午
 */
@Data
public class JsonSchemeDefine {
    /** 标题 **/
    private String title;
    /** 描述 **/
    private String description;
    /** 类型 **/
    private String type;
    /** 属性 **/
    private Map<String, Map<String, Map<String, Object>>> properties = new HashMap<>();
    /** 必填字段列表 **/
    private List<String> required = new ArrayList<>();
}
