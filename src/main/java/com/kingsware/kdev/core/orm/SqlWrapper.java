package com.kingsware.kdev.core.orm;

import com.kingsware.kdev.core.auth.DataAccessUtil;
import com.kingsware.kdev.core.auth.SqlLink;
import com.kingsware.kdev.core.orm.expression.Op;
import com.kingsware.kdev.core.util.StringUtils;
import lombok.Data;

import java.util.*;

/**
 * SQL对象
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/21 5:41 下午
 */
@Data
public class SqlWrapper {
    /** sql buffer **/
    private StringBuffer sqlBuffer = new StringBuffer();

    /** 参数 **/
    private List<Object> params = new ArrayList<>();

    public SqlWrapper() {
    }

    public SqlWrapper(String sql) {
        this.sqlBuffer.append(sql);
    }

    /**
     * 增加查询条件
     * @param columnName    列名
     * @param op            操作符
     * @param objects       参数
     */
    public void addCondition(String columnName, Op op, Object... objects) {
        sqlBuffer.append(" and ");
        sqlBuffer.append(columnName).append(" ");
        sqlBuffer.append(op.bind());
        params.addAll(Arrays.asList(objects));
    }

    /**
     * 增加查询条件
     * @param columnName    列名
     * @param bind            操作符
     * @param objects       参数
     */
    public void addCondition(String columnName, String bind, Object... objects) {
        sqlBuffer.append(" and ");
        sqlBuffer.append(columnName).append(" ");
        sqlBuffer.append(bind);
        params.addAll(Arrays.asList(objects));
    }

    /**
     * 增加查询条件
     * @param condition 条件语句
     * @param objects   参数
     */
    public void addCondition(String condition, Object... objects) {
        sqlBuffer.append(" and ");
        sqlBuffer.append(condition);
        params.addAll(Arrays.asList(objects));
    }

    /**
     * In 查询
     * @param columnName 列表
     * @param inSet      id的集合
     */
    public void in(String columnName, Collection<Object> inSet) {
        sqlBuffer.append(" and ");
        sqlBuffer.append(columnName);
        sqlBuffer.append(" in ( ");
        for (Object obj: inSet) {
            sqlBuffer.append( "?,");
            params.add(obj);
        }
        // 移除最后一个"."
        String tempSql = sqlBuffer.substring(0, sqlBuffer.length()-1);
        sqlBuffer.setLength(0);
        sqlBuffer.append(tempSql);
        sqlBuffer.append(") ");
    }

    /**
     * 区间查询
     * @param columnName    属性名
     * @param lowValue      低值
     * @param highValue     高值
     */
    public void between(String columnName, Object lowValue, Object highValue) {
        sqlBuffer.append(" and ( ");
        sqlBuffer.append(columnName);
        sqlBuffer.append(" between ? and ? ) ");
        params.add(lowValue);
        params.add(highValue);
    }

    /**
     * 加入权限sql
     * @param tableName  数据库表名
     * @param alias      简写
     */
    public void withAuthority(String tableName, String alias) {
        // 获取权限sql
        String authoritySql = DataAccessUtil.getDataAccessSql(tableName, alias, SqlLink.EXISTS);
        if (StringUtils.isNotEmpty(authoritySql)) {
            sqlBuffer.append(" and ");
            sqlBuffer.append(authoritySql);
        }
    }

    /**
     * 排序
     * @param sortBy
     */
    public void sortBy(String sortBy) {
        if (sortBy.trim().toLowerCase().startsWith("order by")) {
            sqlBuffer.append(" ");
            sqlBuffer.append(sortBy);
            sqlBuffer.append(" ");
        }
        else {
            sqlBuffer.append(" ");
            sqlBuffer.append("order by ");
            sqlBuffer.append(sortBy);
            sqlBuffer.append(" ");
        }
    }


    /**
     * 增加group by
     * @param groupBy   group by字符串
     */
    public void groupBy(String groupBy) {
        if (groupBy.trim().toLowerCase().startsWith("group by")) {
            sqlBuffer.append( " ");
            sqlBuffer.append(groupBy);
            sqlBuffer.append(" ");
        }
        else {
            sqlBuffer.append(" ");
            sqlBuffer.append("group by ");
            sqlBuffer.append(groupBy);
            sqlBuffer.append(" ");
        }
    }

    /**
     * 返回查询sql
     * @return      sql
     */
    public String getSql() {
        return sqlBuffer.toString();
    }

    /**
     * 重新设置sql
     * @param sql   sql
     */
    public void setSql(String sql) {
        this.sqlBuffer.setLength(0);
        this.sqlBuffer.append(sql);
    }


}
