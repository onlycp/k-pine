package com.kingsware.kdev.core.cache.dict;

import java.util.HashMap;
import java.util.Map;

/**
 * // 字典管理 单实例.
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/6 9:25 上午
 */
public class DictManager {
    /** 实例 **/
    private static DictManager instance;
    /** 字典缓存 **/
    private Map<String, String> cache = new HashMap<>();
    /** 通过名称作为key **/
    private Map<String, String> nameCache = new HashMap<>();

    public static DictManager getInstance() {
        if (instance == null) {
            synchronized (DictManager.class) {
                if (instance == null) {
                    instance = new DictManager();
                }
            }
        }
        return instance;
    }

    private DictManager() {
    }

    /**
     * 增加字典项
     * @param code  编码
     * @param name  名称
     * @param value  值
     */
    public void addDict(String code, String name, String value) {
        // 值作为key
        String key = code + "."  + value;
        cache.put(key, name);
        // 名称作为key
        String nameKey = code + "." + name;
        nameCache.put(nameKey, value);
    }

    /**
     * 获取字典项的值
     * @param code  编码
     * @param value 值
     * @return  字典名称
     */
    public String getDict(String code, String value) {
        String key = code + "."  + value;
        return cache.getOrDefault(key, null);
    }

    /**
     * 获取字典项的值
     * @param code  编码
     * @param name  名称
     * @return  字典名称
     */
    public String getDictValueByName(String code, String name) {
        String nameKey = code + "." + name;
        return nameCache.getOrDefault(nameKey, null);
    }

}
