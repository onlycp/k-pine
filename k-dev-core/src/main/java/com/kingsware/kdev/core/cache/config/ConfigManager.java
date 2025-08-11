package com.kingsware.kdev.core.cache.config;

import java.util.HashMap;
import java.util.Map;

/**
 * // 系统参数管理 单实例.
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/6 9:25 上午
 */
public class ConfigManager {
    /** 实例 **/
    private static ConfigManager instance;
    /** 系统参数缓存 **/
    private Map<String, SysConfigInfo> cache = new HashMap<>();

    public static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    private ConfigManager() {
    }

    /**
     * 增加系统参数项
     * @param code
     * @param config
     */
    public void addItem(String code, SysConfigInfo config) {
        // 值作为key
        String key = code;
        cache.put(key, config);
    }

    /**
     * 获取系统参数项的值
     * @param code  编码
     * @return  系统参数名称
     */
    public SysConfigInfo getItem(String code) {
        String key = code;
        return cache.getOrDefault(key, null);
    }

    /**
     * 获取所有的文本类型配置
     * @return 返回
     */
    public Map<String, String> getAllTextConfig() {
        Map<String, String> params = new HashMap<>();
        cache.forEach((k, v) -> {
            if (v.getValueType() == 0) {
                params.put(k, v.getValue());
            }
        });
        return params;

    }

    /**
     * 获取所有的文本类型配置
     * @return 返回
     */
    public Map<String, String> getAllConfig() {
        Map<String, String> params = new HashMap<>();
        cache.forEach((k, v) -> {
            params.put(k, v.getValue());
        });
        return params;

    }

}
