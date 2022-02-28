package com.kingsware.kdev.core.jsonschema;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 类型映射
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/2/28 11:10 上午
 */
public class TypeMapping {

    private static TypeMapping instance;

    /** 映射 **/
    private Map<String, Set<String>> mapping = new HashMap<>();

    public static TypeMapping getInstance() {
        if (instance == null) {
            instance = new TypeMapping();
        }
        return instance;
    }

    private TypeMapping() {
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        // string
        Set<String> stringSet = new HashSet<>();
        // region mysql
        stringSet.add("CHAR");
        stringSet.add("VARCHAR");
        stringSet.add("TINYBLOB");
        stringSet.add("TINYTEXT");
        stringSet.add("BLOB");
        stringSet.add("TEXT");
        stringSet.add("MEDIUMBLOB");
        stringSet.add("MEDIUMTEXT");
        stringSet.add("LONGBLOB");
        stringSet.add("LONGTEXT");
        // endregion mysql
        mapping.put("string", stringSet);

        // integer
        Set<String> integerSet = new HashSet<>();
        // region mysql
        integerSet.add("TINYINT");
        integerSet.add("SMALLINT");
        integerSet.add("MEDIUMINT");
        integerSet.add("INT");
        integerSet.add("INTEGER");
        integerSet.add("BIGINT");
        // endregion mysql
        mapping.put("integer", integerSet);

        // number
        Set<String> numberSet = new HashSet<>();
        // region mysql
        numberSet.add("FLOAT");
        numberSet.add("DOUBLE");
        numberSet.add("DECIMAL");
        // endregion mysql
        mapping.put("number", numberSet);
    }

    /**
     * 获取schema的数据类型
     * @param dbDataType    数据库数据类型
     * @return  schema类型
     */
    public String getSchemaType(String dbDataType) {
        for (Map.Entry<String, Set<String>> entry: mapping.entrySet()) {
            for (String type: entry.getValue()) {
                if (type.equalsIgnoreCase(dbDataType)) {
                    return entry.getKey();
                }
            }
        }
        // 如果找不到，就返回string
        return "string";
    }
}
