package com.kingsware.kdev.core.bean;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 组配置属性
 *
 * @author chenp
 * @date 2023/11/3
 */
@Data
public class GroupProperties {
    /** 组 **/
    private String group;
    /** 是否启用 **/
    private boolean enable;
    /** 配置信息 **/
    private Map<String, String> values = new HashMap<>();

    /**
     * 添加配置项值
     *
     * @param key   配置项键
     * @param value 配置项值
     */
    public void putValue(String key, String value) {
        values.put(key, value);
    }

    /**
     * 获取字符串类型配置项值
     *
     * @param key     配置项键
     * @param defaultValue 默认值
     * @return 配置项值
     */
    public String stringValue(String key, String defaultValue) {
        return values.getOrDefault(key, defaultValue);
    }

    /**
     * 获取整型配置项值
     *
     * @param key     配置项键
     * @param defaultValue 默认值
     * @return 配置项值
     */
    public int intValue(String key, int defaultValue) {
        return Integer.parseInt(values.getOrDefault(key, String.valueOf(defaultValue)));
    }

    /**
     * 获取布尔类型配置项值
     *
     * @param key     配置项键
     * @param defaultValue 默认值
     * @return 配置项值
     */
    public boolean booleanValue(String key, boolean defaultValue) {
        return Boolean.parseBoolean(values.getOrDefault(key, String.valueOf(defaultValue)));
    }
}
