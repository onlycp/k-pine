package com.kingsware.kdev.core.orm;


import java.util.*;

/**
 * 数据库关键字
 */
public class SqlKeywords {

    /**
     * 关键字
     */
    private static final Map<String, Set<String>> keywords = new HashMap<>();
    static {
        /** sql server **/
        Set<String> sqlServerKeywords = new HashSet<>();
        sqlServerKeywords.add("distributed");
        sqlServerKeywords.add("file");
        keywords.put("SQLServer", sqlServerKeywords);
        /** h2 **/
        Set<String> h2ServerKeywords = new HashSet<>();
        h2ServerKeywords.add("value");
        keywords.put("H2", h2ServerKeywords);
        /** GBase **/
        Set<String> gbaseServerKeywords = new HashSet<>();
        gbaseServerKeywords.add("distributed");
        keywords.put("GBase", gbaseServerKeywords);
        /** dm **/
        Set<String> dmKeywords = new HashSet<>();
        dmKeywords.add("distributed");
        dmKeywords.add("distributed");
        keywords.put("DM", dmKeywords);
        /** oracle **/
        Set<String> oracleKeywords = new HashSet<>();
        oracleKeywords.add("online");
        keywords.put("Oracle", oracleKeywords);
        /** xugu **/
        Set<String> xuguKeywords = new HashSet<>();
        xuguKeywords.add("number");
        xuguKeywords.add("order");
        keywords.put("Xugu", xuguKeywords);
        /** postgresql **/
        Set<String> postgresKeywords = new HashSet<>();
        postgresKeywords.add("distributed");
        postgresKeywords.add("online");
        keywords.put("Postgresql", postgresKeywords);
    }


    /**
     * 判断是否关键字
     * @param columnName        名称
     * @param dataBaseTypeEnum  数据库类型
     * @return
     */
    public static boolean isKeyword(String columnName, String dataBaseTypeEnum) {
        if (!keywords.containsKey(dataBaseTypeEnum)) {
            return false;
        }
        Set<String> keys = keywords.get(dataBaseTypeEnum);
        return keys.contains(columnName.toLowerCase());
    }

    /**
     * 包括列名
     * @param columnName        名称
     * @param dataBaseTypeEnum  数据库类型
     * @return
     */
    public static String wrapperColumn(String columnName, String dataBaseTypeEnum) {
        if (!isKeyword(columnName, dataBaseTypeEnum)) {
            return columnName;
        }
        if (dataBaseTypeEnum.equalsIgnoreCase("SQLServer")) {
            return "[" +  columnName + "]";
        }
        else if (dataBaseTypeEnum.equalsIgnoreCase("Mysql")) {
            return "`"+ columnName + "`";
        }
        else if (dataBaseTypeEnum.equalsIgnoreCase("H2")) {
            return "`"+ columnName + "`";
        }
        else if (dataBaseTypeEnum.equalsIgnoreCase("GBase")) {
            return "`"+ columnName + "`";
        }
        else if (dataBaseTypeEnum.equalsIgnoreCase("DM")) {
            return "\""+ columnName.toUpperCase() + "\"";
        }
        else if (dataBaseTypeEnum.equalsIgnoreCase("Oracle")) {
            return "\""+ columnName.toUpperCase() + "\"";
        }
        else if (dataBaseTypeEnum.equalsIgnoreCase("Xugu")) {
            return "\""+ columnName.toUpperCase() + "\"";
        }
        else if (dataBaseTypeEnum.equalsIgnoreCase("PostgreSQL")) {
            return "\""+ columnName + "\"";
        }
        return columnName;
    }

    /**
     * 包装属性
     * @param columns           属性列
     * @param dataBaseTypeEnum  数据库类型
     * @return
     */
    public static List<String> wrapperColumn(List<String> columns, String dataBaseTypeEnum) {

        List<String> wrapperColumns = new ArrayList<>(columns.size());
        for (String col: columns) {
            wrapperColumns.add(wrapperColumn(col, dataBaseTypeEnum));
        }
        return wrapperColumns;
    }
}
