package com.kingsware.kdev.core.util;

import java.util.*;

/**
 * map工具类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/3/24 2:40 下午
 */
public class MapUtil {

    private MapUtil(){}

    /**
     * 合并map
     * @param maps 合并map
     * @return  返回合并之后的map
     */
    @SuppressWarnings("all")
    public static Map<String, Object> mergerMap(List<Map<String, Object>> maps) {
        Map<String, Object> mergedMap = new HashMap<>();
        // 遍历合并, 不能直接put，这样会导致部分丢失
        for (Map<String, Object> map:maps) {
            for (Map.Entry<String, Object> objectEntry: map.entrySet()) {
                // 如果不包括，则直接加到合并结果里
                if (!mergedMap.containsKey(objectEntry.getKey())) {
                    mergedMap.put(objectEntry.getKey(), objectEntry.getValue());
                }
                else {
                    Object objectValue = objectEntry.getValue();
                    Object originValue = mergedMap.get(objectEntry.getKey());
                    // 如果对象值为空，直接不处理了，不用null覆盖之前的
                    if (objectValue == null) {
                        continue;
                    }
                    // 如果是map，就合并，其他的不处理
                    if (objectValue instanceof Map && originValue instanceof Map) {
                        List<Map<String, Object>> subMapList = new ArrayList<>();
                        subMapList.add((Map<String, Object>) originValue);
                        subMapList.add((Map<String, Object>) objectValue);
                        objectValue = mergerMap(subMapList);
                    }
                }
            }
        }
        return mergedMap;
    }

    public static void transMapBooleanToInt(Map<String, Object> map, String...keys) {
        for (String key: keys) {
            if (map.containsKey(key)) {
                if (map.get(key) instanceof Boolean) {
                    map.put(key, map.get(key).equals(true) ? 1: 0);
                }
                else if(map.get(key) instanceof Integer || map.get(key) instanceof Long ) {
                    map.put(key, map.get(key));
                }
                else {
                    map.put(key, 0);
                }
            }
        }

    }


    public static void transMapIntToBoolean(Map<String, Object> map, String...keys) {
        for (String key: keys) {
            if (map.containsKey(key)) {
                Object value = map.get(key);
                if (value == null) {
                    map.put(key, null);
                }
                else {
                    map.put(key, "1".equals(map.get(key).toString().trim()));
                }
            }
            else {
                map.put(key, false);
            }
        }

    }

    public static void transMapDateToString(Map<String, Object> map, String...keys) {
        for (String key: keys) {
            if (map.get(key) != null) {
                if (map.get(key) instanceof Long) {
                    map.put(key, DateUtils.formatDate(new Date((Long)map.get(key)), "yyyy-MM-dd HH:mm:ss"));
                }

            }
        }

    }
}
