package com.kingsware.kdev.core.orm;

import com.kingsware.kdev.core.bean.BaseModel;
import com.kingsware.kdev.core.context.KClientContext;
import com.kingsware.kdev.core.orm.annotation.AutoEnum;
import com.kingsware.kdev.core.orm.annotation.Column;
import com.kingsware.kdev.core.orm.annotation.ColumnIgnore;
import com.kingsware.kdev.core.orm.annotation.LogicDelete;
import com.kingsware.kdev.core.orm.expression.BetweenExpression;
import com.kingsware.kdev.core.orm.expression.Expression;
import com.kingsware.kdev.core.orm.expression.SimpleExpression;
import com.kingsware.kdev.core.util.BeanUtils;
import com.kingsware.kdev.core.util.DateUtils;
import com.kingsware.kdev.core.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.MessageFormat;
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
    public static <T> SqlWrapper insertListSql(List<T> models, String dataBaseTypeEnum) {
        // 获取第一个对象的class
        Class<?> tClass = models.get(0).getClass();
        // 获取表名
        String tableName = ModelUtil.getTableName(tClass);
        // 获取所有的Field
        Field[] fields = BeanUtils.getAllFields(tClass);

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
        // 获取是否逻辑删除
        LogicDelete logicDelete = LogicDeleteTables.getInstance().getTable(tableName);
        if (logicDelete != null) {
            insertColumns.add(logicDelete.column());
        }

        // 遍历赋值
        for (T model: models) {
            List<String> itemValues = new ArrayList<>();
            for (Field2Column field2Column: insertableFields) {
                Column column = field2Column.getColumn();
                Field field = field2Column.getField();
                // 自动赋值
                if (field2Column.getColumn() != null) {
                    // 先判断属性性是否为空，如果不为空，则跳过，不进行设置值
                    Object distFieldValue = BeanUtils.getFieldValue(field, model);
                    if (distFieldValue == null || StringUtils.isEmpty(distFieldValue.toString())) {
                        autoWrite(column, field, model);
                    }

                }
                itemValues.add(addParams(field2Column.getField(), model, params, dataBaseTypeEnum));

            }
            if (logicDelete != null) {
                params.add(logicDelete.defValue());
                itemValues.add("?");
            }
            if (dataBaseTypeEnum.equalsIgnoreCase("oracle")) {
                String subValues = String.format("(select %s from dual)", StringUtils.joinToString(itemValues, ","));
                insertValues.add(subValues);
            }
            else {
                String subValues = String.format("( %s )", StringUtils.joinToString(itemValues, ","));
                insertValues.add(subValues);
            }

        }
        // 拼装sql
        builder.append(" ( ");
        builder.append(StringUtils.joinToString(SqlKeywords.wrapperColumn(insertColumns, dataBaseTypeEnum), ","));
        builder.append(" ) ");
        if (dataBaseTypeEnum.equalsIgnoreCase("Oracle")) {
            builder.append(StringUtils.joinToString(insertValues, " union all "));
        }
        else {
            builder.append(" values ");
            builder.append(StringUtils.joinToString(insertValues, ","));
        }


        // 返回结果
        SqlWrapper sqlWrapper = new SqlWrapper(builder.toString());
        sqlWrapper.setParams(params);
        return sqlWrapper;
    }

    /**
     * 增加参数
     * @param field     属性
     * @param model     模型
     * @param params    参数
     */
    private static String addParams(Field field, Object model, List<Object> params, String dbType) {
        Object value = BeanUtils.getFieldValue(field, model);
        String variable = "?";
        if (value == null) {
            params.add(null);
            return variable;
        }

        if (value instanceof Timestamp) {
            Timestamp timestamp = (Timestamp) value;
            params.add(DateUtils.formatDate(new Date(timestamp.getTime()), DateUtils.DATE_TIME));
        }
        else if (value instanceof Boolean) {
            params.add(Boolean.TRUE.equals(value) ? 1: 0);
        }
        else if (value instanceof Integer) {
            boolean toStr = false;
            if (field.isAnnotationPresent(Column.class)) {
                Column column = field.getAnnotation(Column.class);
                if (StringUtils.isNotEmpty(column.intToStrSchemeType()) && column.intToStrSchemeType().toLowerCase().contains(dbType.toLowerCase())) {
                    toStr = true;
                }
            }
            params.add(toStr ? value.toString(): value);
        }
        else if (value instanceof String ) {
            if (((String) value).length() >=2000 && "oracle".equalsIgnoreCase(dbType)) {
                List<String> items = StringUtils.subStringToArray((String) value, 2000);
                List<String> tmps = new ArrayList<>();
                for (String str: items) {
                    tmps.add("to_clob(?)");
                    params.add(str);
                }
                variable = StringUtils.joinToString(tmps, "||");
            }
            else {
                params.add(value);
            }


        }
        else {
            params.add(value);
        }
        return variable;
    }



    /**
     * 生成insertSQL
     * @param model         模型
     * @param dataBaseTypeEnum  数据库类型
     * @return              生成后的语句
     */
    public static <T> SqlWrapper insertSql(T model, String dataBaseTypeEnum) {
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
    public static <T> SqlWrapper updateSql(T model, String dataBaseTypeEnum) {
        Class<?> tClass = model.getClass();
        // 获取表名
        String tableName = ModelUtil.getTableName(tClass);
        // 获取所有的Field
        Field[] fields = BeanUtils.getAllFields(tClass);
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
                updateList.add(String.format("%s=%s", SqlKeywords.wrapperColumn(columnName, dataBaseTypeEnum), addParams(field, model, params, dataBaseTypeEnum)));
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
                // 自动设置值
                autoWrite(column, field, model);
                // 增加列
                updateList.add(String.format("%s=%s", SqlKeywords.wrapperColumn(columnName, dataBaseTypeEnum), addParams(field, model, params, dataBaseTypeEnum)));
            }
        }
        // 追加id
        params.add(BeanUtils.getFieldValue(idField, model));
        // 构建sql
        String builder = "update " + tableName + " set " +
                StringUtils.joinToString(updateList, ",") + " " +
                "where " +
                String.format("%s=?", StringUtils.humpToLine(idField.getName()));
        // 返回结果
        SqlWrapper sqlWrapper = new SqlWrapper(builder);
        sqlWrapper.setSql(builder);
        sqlWrapper.setParams(params);
        return sqlWrapper;
    }

    private static void autoWrite(Column column, Field field, Object model) {
        // 增加值
        if (column.auto() == AutoEnum.ID) {
            BeanUtils.setField(field, model, StringUtils.getUUID());
        }
        else if (column.auto() == AutoEnum.WHO) {
            if (KClientContext.getContext() != null && KClientContext.getContext().getUserInfo() != null) {
                BeanUtils.setField(field, model, KClientContext.getContext().getUserInfo().getId());
            }
            else {
                BeanUtils.setField(field, model, " ");
            }
        }
        else if (column.auto() == AutoEnum.WHEN) {
            if (field.getType().isAssignableFrom(Timestamp.class)) {
                BeanUtils.setField(field, model, new Timestamp(System.currentTimeMillis()));
            }
            else if (field.getType().isAssignableFrom(String.class)){
                BeanUtils.setField(field, model, DateUtils.getNow());
            }

        }
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
        String tableName = ModelUtil.getTableName(tClass);
        // 逻辑删除
        LogicDelete logicDelete = LogicDeleteTables.getInstance().getTable(tableName);
        if (logicDelete == null) {
            return String.format("select * from %s where %s=?", tableName, StringUtils.humpToLine(idField.getName()));
        }
        else {
            return String.format("select * from %s where %s=? and %s=%d ", tableName, StringUtils.humpToLine(idField.getName()), logicDelete.column(), logicDelete.defValue());
        }

    }

    /**
     * 通过表达式查询的sql
     * @param tClass            目标类
     * @param expressionList    表达式列表
     * @param <T>               泛型
     * @return                  sql
     */
    public static <T> SqlWrapper findSql(Class<T> tClass, List<Expression> expressionList, String dataBaseTypeEnum ) {
        SqlWrapper sqlWrapper = new SqlWrapper();
        // 获取表名
        String tableName = ModelUtil.getTableName(tClass);
        // 拼接sql
        StringBuilder builder = new StringBuilder();
        builder.append("select * from ").append(tableName).append(" ");
        // 逻辑删除
        LogicDelete logicDelete = LogicDeleteTables.getInstance().getTable(tableName);
        if (logicDelete != null) {
            builder.append(String.format("where %s=%d ", SqlKeywords.wrapperColumn(logicDelete.column(), dataBaseTypeEnum), logicDelete.defValue()));
        }
        else {
            builder.append("where 1=1 ");
        }
        // 拼接查询条件
        for (Expression expression: expressionList) {
            // 简单表达式
            if (expression instanceof SimpleExpression) {
                SimpleExpression simpleExpression = (SimpleExpression)expression;
                // 获取列名
                String columnName = ModelUtil.getColumnName(tClass, simpleExpression.getPropName());
                // 拼接sql
                builder.append("and ").append(columnName).append(" ");
                builder.append(simpleExpression.getOp()).append(" ");
                builder.append("? ");
                sqlWrapper.getParams().add(simpleExpression.getValue());
            }
            // 区间查询
            else if (expression instanceof BetweenExpression) {
                BetweenExpression betweenExpression = (BetweenExpression)expression;
                // 获取列名
                String columnName = ModelUtil.getColumnName(tClass, betweenExpression.getPropName());
                // 拼接sql
                builder.append("and ").append(columnName).append(" ");
                builder.append("between ? and ? ");
                sqlWrapper.getParams().add(betweenExpression.getLowValue());
                sqlWrapper.getParams().add(betweenExpression.getHighValue());
            }
        }
        sqlWrapper.setSql(builder.toString());
        return sqlWrapper;
    }

    /**
     * 将查询列表的sql转为查询个数的
     * @param sql   原始sql
     * @return      查询个数的sql
     */
    public static String getListSql2CountSql(String sql) {

        String cleanSql = StringUtils.clean(sql);
        // 简单处理，后续优化
        String lowerCaseSql = cleanSql.toLowerCase();
        String select  = "select ";
        String from = " from";
        int selectIndex = lowerCaseSql.indexOf(select);
        int fromIndex = lowerCaseSql.indexOf(from);
        // 取头取尾
        String head = cleanSql.substring(0, selectIndex + select.length());
        String tail = cleanSql.substring(fromIndex);
        // 去掉order by
        int orderByIndex = tail.toLowerCase().lastIndexOf("order by");
        if (orderByIndex > -1) {
            String orderBy = tail.substring(orderByIndex);
            if (!orderBy.contains("(") && !orderBy.contains(")")) {
                tail = tail.substring(0, orderByIndex);
            }
        }
        // 返回查询sql
        return head + "count(1)"  + tail;
    }

    public static void main(String[] argc) {
        String sql = "SELECT * from kw_water kw \n" +
                "LEFT JOIN kw_bank_account kba on kw.account = kba.account and kba.deleted = 0 \n" +
                "LEFT JOIN kw_bank_account_expand kbae on kba.relation_id = kbae.id \n" +
                "LEFT JOIN kw_edition ke on kba.edition_id = ke.id and ke.deleted = 0 \n" +
                "LEFT JOIN kw_edition_account kea on kea.id = kba.edition_account_id and kea.deleted = 0 \n" +
                "LEFT JOIN kw_mechanism km on ke.mechanism_id = km.id and km.deleted = 0 \n" +
                "WHERE kw.deleted = 0 AND kw.transaction_date >= '2024-03-01 00:00:00' AND kw.transaction_date <= '2024-03-31 23:59:59'\n" +
                "AND kba.id IN (SELECT ar.data_id FROM sys_data_access_resource ar WHERE ar.access_id in ('-1','b77dbf5887524bbfb7e7e274c360c11f')) \n" +
                "or kba.id in (select kbattt.id from kw_bank_account kbattt left join kw_bank_account_expand kbaett on kbattt.account= kbaett.account where kbaett.pro_pm_account='chengzhuo' or kbaett.trust_accounting_account='chengzhuo')";
        String str = getListSql2CountSql(sql);
        System.out.println(str);
    }

    public static SqlWrapper deleteSql(BaseModel model) {
        // id列名
        Field idField = getIdField(model.getClass());
        if (idField == null) {
            throw new RuntimeException("Model缺少id列定义, 详情见@Column的auto属性");
        }
        // 返回结果
        SqlWrapper sqlWrapper = new SqlWrapper();
        // 获取表名
        String tableName = ModelUtil.getTableName(model.getClass());
        // 逻辑删除
        LogicDelete logicDelete = LogicDeleteTables.getInstance().getTable(tableName);
        if (logicDelete == null) {
            sqlWrapper.setSql(MessageFormat.format("delete from {0} where {1}", tableName, String.format("%s=?", StringUtils.humpToLine(idField.getName()))));

        }
        else {
            String sql = MessageFormat.format("update {0} set {1}={2} where {3}=?", tableName, logicDelete.column(), logicDelete.defDeleteValue(),StringUtils.humpToLine(idField.getName()));
            sqlWrapper.setSql(sql);
        }
        // 参数列表
        List<Object> params = new ArrayList<>();
        params.add(BeanUtils.getFieldValue(idField, model));
        sqlWrapper.setParams(params);
        return sqlWrapper;
    }

    public static <T> String deleteById(Class<T> tClass) {
        // id列名
        Field idField = getIdField(tClass);
        if (idField == null) {
            throw new RuntimeException("Model缺少id列定义, 详情见@Column的auto属性");
        }
        // 获取表名
        String tableName = ModelUtil.getTableName(tClass);
        // 逻辑删除
        LogicDelete logicDelete = LogicDeleteTables.getInstance().getTable(tableName);
        if (logicDelete == null) {
            return String.format("delete from %s where %s=?", tableName, StringUtils.humpToLine(idField.getName()));
        }
        else {
            return MessageFormat.format("update {0} set {1}={2} where {3}=?", tableName, logicDelete.column(), logicDelete.defDeleteValue(),StringUtils.humpToLine(idField.getName()));

        }

    }



    /**
     * 获取id属性列
     * @param tClass 模型类
     * @return      id列
     */
    private static <T> Field getIdField(Class<T> tClass) {
        // 获取所有的Field
        Field[] fields = BeanUtils.getAllFields(tClass);
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

}
