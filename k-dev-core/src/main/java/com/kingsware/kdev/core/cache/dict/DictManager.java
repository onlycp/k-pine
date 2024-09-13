package com.kingsware.kdev.core.cache.dict;

import com.kingsware.kdev.core.bean.SysDictItemRet;
import com.kingsware.kdev.core.bean.SysDictRet;
import com.kingsware.kdev.core.i18n.I18n;
import com.kingsware.kdev.core.util.CollectUtils;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.core.util.StringUtils;
import lombok.Data;

import java.util.*;

/**
 * // 字典管理 单实例.
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/6 9:25 上午
 */
@Data
public class DictManager {
    /** 实例 **/
    private static DictManager instance;
    /** 字典缓存 **/
    private Map<String, String> cache = new HashMap<>();
    /** 通过名称作为key **/
    private Map<String, String> nameCache = new HashMap<>();

    private List<SysDictRet> dictList = new ArrayList<>();

    private List<SysDictItemRet> dictItemList = new ArrayList<>();

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

    public Map<String, Object> getAllDict() {
        Map<String, Object> resultMap = new HashMap<>();
        // getAllDict
        long t1 = System.currentTimeMillis();
        List<SysDictRet> dictList = DictManager.getInstance().getDictList();
        long t2 = System.currentTimeMillis();
        List<SysDictItemRet> dictItemList = DictManager.getInstance().getDictItemList();
        long t3 = System.currentTimeMillis();
        List<SysDictItemRet> newDictItemList = JsonUtil.toListBean(JsonUtil.toJson(dictItemList), SysDictItemRet.class);
        // 处理国际化
        for (SysDictItemRet item : newDictItemList) {
            String translate = I18n.parseScript(item.getAppId(), item.getName());
            if (StringUtils.isNotEmpty(translate)) {
                item.setName(translate);
            }
        }

        // setMap
        resultMap.put("KEY", getKeyMap(dictList, newDictItemList));
        resultMap.put("VALUE", getValueMap(dictList, newDictItemList));
        resultMap.put("LIST", getListMap(dictList, newDictItemList));

        return resultMap;
    }

    private List<Map<String, String>> getDetailByDictId(List<SysDictItemRet> sysDictItemList, String dictId) {
        List<Map<String, String>> list = new ArrayList<>();
        for (SysDictItemRet detail : sysDictItemList) {
            if (detail.getSysDictId().equals(dictId)) {
                Map<String, String> map = new HashMap();
                map.put("KEY", detail.getValue());
                map.put("VALUE", detail.getName());
                list.add(map);
            }
        }
        return list;
    }

    private Map<String, Map<String, String>> getKeyMap(List<SysDictRet> dictList, List<SysDictItemRet> sysDictItemList) {
        Map<String, Map<String, String>> resultMap = new HashMap<>();
        for (SysDictRet dict : dictList) {
            Map<String, String> keyMap = getDetailKeyMapByDictId(sysDictItemList, dict.getId());
            if (StringUtils.isNotEmpty(dict.getCode())) {
                resultMap.put(dict.getCode(), keyMap);
            }
        }
        return resultMap;
    }

    private Map<String, Map<String, String>> getValueMap(List<SysDictRet> dictList, List<SysDictItemRet> sysDictItemList) {
        Map<String, Map<String, String>> resultMap = new HashMap<>();
        for (SysDictRet dict : dictList) {
            Map<String, String> valueMap = getDetailValueMapByDictId(sysDictItemList, dict.getId());
            if (StringUtils.isNotEmpty(dict.getCode())) {
                resultMap.put(dict.getCode(), valueMap);
            }
        }
        return resultMap;
    }

    private Map<String, List<Map<String, String>>> getListMap(List<SysDictRet> dictList, List<SysDictItemRet> sysDictItemList) {
        Map<String, List<Map<String, String>>> map = new HashMap<>();
        for (SysDictRet dict : dictList) {
            List<Map<String, String>> detailList = getDetailByDictId(sysDictItemList, dict.getId());
            if (StringUtils.isNotEmpty(dict.getCode())) {
                map.put(dict.getCode(), detailList);
            }
        }
        return map;
    }

    private Map<String, String> getDetailKeyMapByDictId(List<SysDictItemRet> sysDictItemList, String dictId) {
        Map<String, String> map = new HashMap<>();
        for (SysDictItemRet detail : sysDictItemList) {
            if (detail.getSysDictId().equals(dictId) && StringUtils.isNotEmpty(detail.getValue())) {
                map.put(detail.getValue(), detail.getValue());
            }
        }
        return map;
    }

    private Map<String, String> getDetailValueMapByDictId(List<SysDictItemRet> sysDictItemList, String dictId) {
        Map<String, String> map = new HashMap<>();
        for (SysDictItemRet detail : sysDictItemList) {
            if (detail.getSysDictId().equals(dictId) && StringUtils.isNotEmpty(detail.getValue())) {
                map.put(detail.getValue(), detail.getName());
            }
        }
        return map;
    }


}
