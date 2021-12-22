package com.kingsware.kdev.core.orm;

import com.kingsware.kdev.core.bean.BaseModel;
import com.kingsware.kdev.core.context.KClientContext;
import com.kingsware.kdev.core.orm.annotation.AutoEnum;
import com.kingsware.kdev.core.orm.annotation.Column;
import com.kingsware.kdev.core.orm.annotation.ColumnIgnore;
import com.kingsware.kdev.core.orm.annotation.Table;
import com.kingsware.kdev.core.util.BeanUtils;
import com.kingsware.kdev.core.util.DateUtils;
import com.kingsware.kdev.core.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.*;

/**
 * SQL生成器
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/21 5:40 下午
 */
public class SqlGenerator {

    /** 日志打印 **/
    private static final Logger logger  = LoggerFactory.getLogger(SqlGenerator.class);

    /** 私有构造 **/
    private SqlGenerator(){}

    /**
     * 生成insert语句
     * @param models    model列表
     * @param dataBaseTypeEnum  数据库类型
     * @param <T>   泛型
     * @return      批量插入的sql
     */
    public static <T> SqlBean insertListSql(List<T> models, DataBaseTypeEnum dataBaseTypeEnum) {
        // 获取第一个对象的class
        Class tClass = models.get(0).getClass();
        // 获取表名
        String tableName = getTableName(tClass);
        // 获取所有的Field
        Field[] fields = tClass.getFields();

        StringBuilder builder = new StringBuilder();
        builder.append(" insert into ").append(tableName).append(" ");
        // 列
        List<String> insertColumns = new ArrayList<>();
        List<String> insertValues = new ArrayList<>();
        // 参数列表
        List<Object> params = new ArrayList<>();
        // 先获取可insert的列表
        List<Field2Column> insertableFields = new ArrayList<>();
        for (Field field: fields) {
            // 如果存在@ColumnIngore，跳过
            if (field.isAnnotationPresent(ColumnIgnore.class)) {
                continue;
            }
            // 如果不存在Column，则默认可增可改
            String columnName = StringUtils.humpToLine(field.getName());
            Column column = field.getAnnotation(Column.class);
            if (column != null) {
                // 如果它不允许新增，直接跳过
                if (!column.insertable()) {
                    continue;
                }
                if (StringUtils.isNotEmpty(column.name())) {
                    columnName = column.name();
                }
            }
            // 插入的列
            insertColumns.add(columnName);
            // 保存对应关系
            Field2Column field2Column = new Field2Column(columnName, field, column);
            insertableFields.add(field2Column);
        }
        // 遍历赋值
        for (T model: models) {
            List<String> itemValues = new ArrayList<>();
            for (Field2Column field2Column: insertableFields) {
                itemValues.add("?");
                Column column = field2Column.getColumn();
                Field field = field2Column.getField();
                // 自动赋值
                if (field2Column.getColumn() != null) {
                    // 增加值
                    if (column.auto() == AutoEnum.ID) {
                        BeanUtils.setField(field, model, StringUtils.getUUID());
                    }
                    else if (column.auto() == AutoEnum.WHO) {
                        if (KClientContext.getContext().getUserInfo() != null) {
                            BeanUtils.setField(field, model, KClientContext.getContext().getUserInfo().getId());
                        }
                    }
                    else if (column.auto() == AutoEnum.WHEN) {
                        BeanUtils.setField(field, model, DateUtils.getNow());
                    }
                }
                params.add(BeanUtils.getField(field2Column.getField(), model));
            }
            String subValues = String.format("( %s )", StringUtils.joinToString(itemValues, ","));
            insertValues.add(subValues);
        }
        // 拼装sql
        builder.append(" ( ");
        builder.append(StringUtils.joinToString(insertColumns, ","));
        builder.append(" ) ");
        builder.append(" values ");
        builder.append(StringUtils.joinToString(insertValues, ","));
        // 返回结果
        SqlBean sqlBean = new SqlBean();
        sqlBean.setSql(builder.toString());
        sqlBean.setParams(params);
        return sqlBean;
    }
    /**
     * 生成insertSQL
     * @param model         模型
     * @param dataBaseTypeEnum  数据库类型
     * @return              生成后的语句
     */
    public static <T> SqlBean insertSql(T model, DataBaseTypeEnum dataBaseTypeEnum) {
        List<T> models = new ArrayList<>();
        models.add(model);
        return insertListSql(models, dataBaseTypeEnum);
    }

    /**
     * 生成update语句
     * @param model 模型
     * @param dataBaseTypeEnum  数据库类型
     * @return  sql结构
     */
    public static <T> SqlBean updateSql(T model,  DataBaseTypeEnum dataBaseTypeEnum) {
        Class tClass = model.getClass();
        // 获取表名
        String tableName = getTableName(tClass);
        // 获取所有的Field
        Field[] fields = tClass.getFields();
        // 参数对
        List<String> updateList = new ArrayList<>();
        // 参数列表
        List<Object> params = new ArrayList<>();
        // id列名
        Field idField = getIdField(tClass);
        if (idField == null) {
            throw new RuntimeException("Model缺少id列定义, 详情见@Column的auto属性");
        }
        for (Field field: fields) {
            // 如果存在@ColumnIngore，跳过
            if (field.isAnnotationPresent(ColumnIgnore.class)) {
                continue;
            }
            // 如果不存在Column，则默认可增可改
            String columnName = StringUtils.humpToLine(field.getName());
            if (!field.isAnnotationPresent(Column.class)) {
                updateList.add(String.format("%s=?", columnName));
                params.add(BeanUtils.getField(field, model));
            }
            else {
                Column column = field.getAnnotation(Column.class);
                if (StringUtils.isNotEmpty(column.name())) {
                    columnName = column.name();
                }
                if (column.auto() == AutoEnum.ID) {
                    continue;
                }
                // 如果它不允许新增，直接跳过
                if (!column.updatable()) {
                    continue;
                }

                // 增加列
                updateList.add(String.format("%s=?", columnName));
                // 增加值
                if (column.auto() == AutoEnum.WHO) {
                    if (KClientContext.getContext().getUserInfo() != null) {
                        BeanUtils.setField(field, model, KClientContext.getContext().getUserInfo().getId());
                    }
                }
                else if (column.auto() == AutoEnum.WHEN) {
                    BeanUtils.setField(field, model, DateUtils.getNow());
                }
                params.add(BeanUtils.getField(field, model));
            }
        }
        // 追加id
        params.add(BeanUtils.getField(idField, model));
        // 构建sql
        StringBuilder builder = new StringBuilder();
        builder.append("update ").append(tableName).append(" set ");
        builder.append(StringUtils.joinToString(updateList, ",")).append(" ");
        builder.append("where ");
        builder.append(String.format("%s=?", StringUtils.humpToLine(idField.getName())));
        // 返回结果
        SqlBean sqlBean = new SqlBean();
        sqlBean.setSql(builder.toString());
        sqlBean.setParams(params);
        return sqlBean;
    }

    /**
     * 生成findById的sql
     * model            模型
     * @return          查询sql
     */
    public static <T> String findByIdSql(Class<T> tClass) {
        // id列名
        Field idField = getIdField(tClass);
        if (idField == null) {
            throw new RuntimeException("Model缺少id列定义, 详情见@Column的auto属性");
        }
        // 获取表名
        String tableName = getTableName(tClass);
        return String.format("select * from %s where %s=?", tableName, StringUtils.humpToLine(idField.getName()));
    }

    public static SqlBean deleteSql(BaseModel model) {
        // id列名
        Field idField = getIdField(model.getClass());
        if (idField == null) {
            throw new RuntimeException("Model缺少id列定义, 详情见@Column的auto属性");
        }
        // 获取表名
        String tableName = getTableName(model.getClass());
        // 拼接sql
        StringBuilder builder = new StringBuilder();
        builder.append("delete from ");
        builder.append(tableName).append(" ");
        builder.append("where ");
        builder.append(String.format("%s=?", StringUtils.humpToLine(idField.getName())));
        // 参数列表
        List<Object> params = new ArrayList<>();
        params.add(BeanUtils.getField(idField, model));
        // 返回结果
        SqlBean sqlBean = new SqlBean();
        sqlBean.setSql(builder.toString());
        sqlBean.setParams(params);
        return sqlBean;
    }

    public static <T> String deleteById(Class<T> tClass) {
        // id列名
        Field idField = getIdField(tClass);
        if (idField == null) {
            throw new RuntimeException("Model缺少id列定义, 详情见@Column的auto属性");
        }
        // 获取表名
        String tableName = getTableName(tClass);
        return String.format("delete from %s where %s=?", tableName, StringUtils.humpToLine(idField.getName()));
    }


    /**
     * 获取id属性列
     * @param tClass 模型类
     * @return      id列
     */
    private static <T> Field getIdField(Class<T> tClass) {
        // 获取所有的Field
        Field[] fields = tClass.getFields();
        // 查找当前实体的id值
        Optional<Field> optionalIdField = Arrays.stream(fields).filter(it -> {
            if (it.isAnnotationPresent(Column.class)) {
                Column column = it.getAnnotation(Column.class);
                return column.auto() == AutoEnum.ID;
            }
            return false;
        }).findFirst();
        return optionalIdField.orElse(null);
    }

    /**
     * 获取表名
     * @param tClass class
     * @return  表名
     */
    private static <T> String getTableName(Class<T> tClass) {
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
        return tableName;
    }


}
