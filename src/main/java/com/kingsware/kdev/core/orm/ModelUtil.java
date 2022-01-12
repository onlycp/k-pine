package com.kingsware.kdev.core.orm;

import com.kingsware.kdev.core.orm.annotation.Column;
import com.kingsware.kdev.core.orm.annotation.Table;
import com.kingsware.kdev.core.orm.exception.OrmDbException;
import com.kingsware.kdev.core.util.BeanUtils;
import com.kingsware.kdev.core.util.StringUtils;

import java.lang.reflect.Field;

/**
 * 模型工具
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/27 4:57 下午
 */
public class ModelUtil {

    private ModelUtil() {}

    /**
     * 获取表名
     * @param tClass class
     * @return  表名
     */
    public static <T> String getTableName(Class<T> tClass) {
        String tableName = "";
        // 如果用@Table，优先从@Table里获取表名
        if (tClass.isAnnotationPresent(Table.class)) {
            Table table = tClass.getAnnotation(Table.class);
            if (StringUtils.isNotEmpty(tableName)) {
                tableName = table.value();
            }

        }
        // 如果此时表名为空，则取class名称作为表名
        if (StringUtils.isEmpty(tableName)) {
            // 类名
            String className = tClass.getSimpleName();
            // 将首字母改为小写
            String instName = StringUtils.uncapitalize(className);
            // 然后转为下划线
            tableName =  StringUtils.humpToLine(instName);
        }
        return tableName.toLowerCase();
    }

    /**
     * 获取列名
     * @param tClass        class
     * @param propName      属性名
     * @param <T>           泛型
     * @return              列名
     */
    public static <T> String getColumnName(Class<T> tClass, String propName) {
        // 获取所有的Field
        Field[] fields = BeanUtils.getAllFields(tClass);
        // 找到对应的列
        Field propField = null;
        for (Field field: fields) {
            if (field.getName().equals(propName)) {
                propField = field;
                break;
            }
        }
        // 如果找不到
        if (propField == null) {
            throw new OrmDbException(String.format("Class:%s 找不到 属性：%s", tClass.getName(), propName));
        }
        // 获取列名
        if (!propField.isAnnotationPresent(Column.class)) {
            return StringUtils.humpToLine(propField.getName());
        }
        else {
            Column column = propField.getAnnotation(Column.class);
            if (StringUtils.isEmpty(column.name())) {
                return StringUtils.humpToLine(propField.getName());
            }
            else {
                return column.name();
            }
        }
    }
}
