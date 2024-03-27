package com.kingsware.kdev.core.orm.kdb;

import com.kingsware.kdev.core.bean.BaseModel;
import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.orm.*;
import com.kingsware.kdev.core.orm.annotation.LogicDelete;
import com.kingsware.kdev.core.orm.channel.DbChannel;
import com.kingsware.kdev.core.orm.channel.KDBHttpChannel;
import com.kingsware.kdev.core.orm.exception.OrmDbException;
import com.kingsware.kdev.core.orm.expression.Expr;
import com.kingsware.kdev.core.orm.expression.Expression;
import com.kingsware.kdev.core.util.BeanUtils;
import com.kingsware.kdev.core.util.CollectUtils;
import com.kingsware.kdev.core.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * KDB数据库实现
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/21 2:43 下午
 */
public class KDataBase extends KdbApiAbstract implements DataBase, KdbApi {
    /** 名称 **/
    private String name;
    /** 数据库连接信息 **/
    private KDBConnectConfig dbConnectConfig;
    /** 通道 **/
    private DbChannel channel;


    @Override
    public String name() {
        return this.name;
    }

    @Override
    public DataBaseTypeEnum databaseType() {
        return DataBaseTypeEnum.KDB;
    }

    @Override
    public void initDataBase(String name, DBConnectConfig dbConnectConfig) {
        this.name = name;
        this.dbConnectConfig = (KDBConnectConfig)dbConnectConfig;
        // 生成通道
        if ("kdbHttp".equalsIgnoreCase(dbConnectConfig.getChannel())) {
            KDBHttpChannel kdbHttpChannel = new KDBHttpChannel();
            kdbHttpChannel.setConfig(dbConnectConfig);
            this.channel = kdbHttpChannel;
        }
    }

    @Override
    public DBConnectConfig getConfig() {
        return this.dbConnectConfig;
    }

    @Override
    public <T> T findById(Class<T> tClass, Object id) {
        String sql = SqlGenerator.findByIdSql(tClass);
        return channel.queryForObject(sql, tClass, Collections.singletonList(id));

    }

    @Override
    public <T> T findOne(Class<T> tClass, String sql, Object... params) {
        return channel.queryForObject(sql, tClass,  Arrays.asList(params));
    }

    @Override
    public <T> T findOne(Class<T> tClass, List<Expression> expressionList) {
        SqlWrapper sqlWrapper = SqlGenerator.findSql(tClass, expressionList, this.getConfig().getInnerType());
        List<T> list = this.findList(tClass, sqlWrapper.getSql(), sqlWrapper.getParams().toArray());
        if (list.isEmpty()) {
            return null;
        }
        else if (list.size() == 1) {
            return list.get(0);
        }
        else {
            throw new OrmDbException("查询数量时，应保持只有一条记录");
        }
    }

    @Override
    public <T> T findSingleAttribute(Class<T> tClass, String sql, Object... params) {
        List<T> result = this.findSingleAttributeList(tClass, sql, params);
        if (result.size() > 1) {
            throw new OrmDbException("查询数量时，应保持只有一条记录");
        }
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public long findCount(String sql, Object... params) {
        return channel.queryForCount(sql,  Arrays.asList(params));
    }

    @Override
    public <T> long findCount(Class<T> tClass, List<Expression> expressionList) {
        SqlWrapper sqlWrapper = SqlGenerator.findSql(tClass, expressionList, this.getConfig().getInnerType());
        return this.findCount(SqlGenerator.getListSql2CountSql(sqlWrapper.getSql()),  sqlWrapper.getParams().toArray());
    }

    @Override
    public <T> List<T> findList(Class<T> tClass, String sql, Object... params) {
        return channel.queryForList(sql, tClass,  Arrays.asList(params));
    }

    @Override
    public <T> List<T> findList(Class<T> tClass, List<Expression> expressionList) {
        SqlWrapper sqlWrapper = SqlGenerator.findSql(tClass, expressionList, this.getConfig().getInnerType());
        return this.findList(tClass, sqlWrapper.getSql(), sqlWrapper.getParams().toArray());
    }

    @Override
    public <T> List<T> findSingleAttributeList(Class<T> tClass, String sql, Object... params) {
        return channel.queryForAttribute(sql, tClass, Arrays.asList(params));
    }

    @Override
    public <T> PagedList<T> findPagedList(Class<T> tClass, int page, int pageSize, String sql, Object... params) {
        // 先查询总数
        String selectCountSql = SqlGenerator.getListSql2CountSql(sql);
        long count = channel.queryForCount(selectCountSql, Arrays.asList(params));
        // 查询数据
        String limitSql = " limit ? offset ?";
        List<Object> objects = new ArrayList<>(Arrays.asList(params));
        // 计算limit
        int from = (page - 1) * pageSize;
        if (this.getConfig().getInnerType().equalsIgnoreCase("SQLServer")) {
            if (sql.toLowerCase().contains("order by"))  {
                limitSql = " offset ? rows fetch next ? rows only";
            }
            else {
                throw BusinessException.serviceThrow("SqlServer分页查询必须包括order by语句！ SQL:" + sql);
            }

            // 加入from
            objects.add(from);
            // 加入pageSize
            objects.add(pageSize);
        }
        else if (this.getConfig().getInnerType().equalsIgnoreCase("Oracle")) {
            sql = "select * from (" + sql + ") table_alias ";
            limitSql = " where ROWNUM <= ? AND ROWNUM > ?";
            objects.add(from + pageSize);
            objects.add(from);

        }
        else {
            // 加入pageSize
            objects.add(pageSize);
            // 加入from
            objects.add(from);
        }
        String dataQuerySql = sql + limitSql;

        // 查询数据
        List<T> data =  channel.queryForList(dataQuerySql, tClass,  objects);
        // 计算总页数
        int pageCount = (int) (count / pageSize);
        if (count % pageSize != 0) {
            pageCount++;
        }
        // 组装结果
        PagedList<T> pagedList = new PagedList<>();
        pagedList.setList(data);
        pagedList.setPageCount(pageCount);
        pagedList.setPageIndex(page);
        pagedList.setTotalCount(((int) count));
        pagedList.setPageSize(pageSize);
        return pagedList;
    }

    @Override
    public <T> long delete(Class<T> tClass, Object id) {
        String sql = SqlGenerator.deleteById(tClass);
        return channel.executeSql(sql,Collections.singletonList(id));
    }

    @Override
    public <T> long delete(T entity) {
        SqlWrapper sqlWrapper = SqlGenerator.deleteSql((BaseModel) entity);
        return channel.executeSql(sqlWrapper.getSql(), sqlWrapper.getParams());
    }

    @Override
    public <T> long save(T entity) {
        SqlWrapper sqlWrapper = SqlGenerator.insertSql((BaseModel) entity, this.getConfig().getInnerType());
        return channel.executeSql(sqlWrapper.getSql(),  sqlWrapper.getParams());

    }

    @Override
    public <T> long saveAll(List<T> list) {
        int defaultBatchNum = 20;
        if (list.size() < defaultBatchNum) {
            defaultBatchNum = list.size();
        }
        int batchNum =  getBatchAddUpdateCount() == 0 ? defaultBatchNum: getBatchAddUpdateCount();
        List<List<T>> listList = CollectUtils.splitList(list, batchNum);
        for (List<T> subList: listList) {
            SqlWrapper sqlWrapper = SqlGenerator.insertListSql(subList , this.getConfig().getInnerType());
            channel.executeSql(sqlWrapper.getSql(), sqlWrapper.getParams());
        }
        return list.size();
    }

    /**
     * 获取批处理数量
     * @return 数据批数据数量
     */
    private int getBatchAddUpdateCount() {
        if (this.getConfig().getInnerType().equalsIgnoreCase("sqlserver")) {
            return 50;
        }
        // 由于在处理clob字段时，select xx from dual 在批量插入时，需要保存类型一致，因此只能一条条插入
        else if (this.getConfig().getInnerType().equalsIgnoreCase("oracle")) {
            return 1;
        }

        return 0;
    }

    @Override
    public <T> long update(T entity) {
        SqlWrapper sqlWrapper = SqlGenerator.updateSql(entity , this.getConfig().getInnerType());
        return channel.executeSql(sqlWrapper.getSql(), sqlWrapper.getParams());
    }

    @Override
    public <T> long updateAll(List<T> list) {
        // 先for处理
        long result = 0;
        for (T t: list) {
            result += update(t);
        }
        return result;
    }

    @Override
    public <T> long saveOrUpdate(T entity, Class<T> tClass) {
        return batchSaveOrUpdate(Collections.singletonList(entity), tClass);
    }

    @Override
    public <T> long batchSaveOrUpdate(List<T> entities, Class<T> tClass) {
        // 处理一下。看哪些是新增，哪些是编辑的
        int totalCount = 0;
        // 1. id为空，认为是新增
        List<T> addList = new ArrayList<>();
        List<T> updateList = new ArrayList<>();
        for (T entity: entities) {
            Object id = BeanUtils.getFieldValue("id", entity);
            if (id == null || StringUtils.isEmpty(id.toString())) {
                addList.add(entity);
            }
            else {
                // 判断表里是否已存在
                long count = this.findCount(tClass, Expr.builder().add("id", "=", id).build());
                if (count == 0) {
                    String tableName = ModelUtil.getTableName(tClass);
                    LogicDelete logicDelete = LogicDeleteTables.getInstance().getTable(tableName);
                    if (logicDelete != null) {
                        // 拼接sql
                        StringBuilder builder = new StringBuilder();
                        builder.append("delete from ").append(tableName).append(" ");
                        builder.append("where id=?");
                        DB.executeUpdateSql(builder.toString(), id);
                    }
                    addList.add(entity);
                }
                else {
                    updateList.add(entity);
                }

            }
        }
        // 批量新增
        if (!addList.isEmpty()) {
            totalCount += this.saveAll(addList);
        }
        if (!updateList.isEmpty()) {
            totalCount += this.updateAll(updateList);
        }

        return totalCount;
    }

    @Override
    public long executeUpdateSql(String sql, Object... params) {
        return channel.executeSql(sql,Arrays.asList(params));
    }

    @Override
    public TableDefine table(String tableName) {
        // 如果是mysql
        String sql = "";
        if (dbConnectConfig.getInnerType().equalsIgnoreCase(DataBaseTypeEnum.MYSQL.getValue())) {
            sql = "select table_name as table_name,table_comment as comments from information_schema.tables where table_schema = database() and table_name = ?";
        }
        return this.findOne(TableDefine.class, sql, tableName);
    }

    @Override
    public List<ColumnDefine> columns(String tableName) {
        // 如果是mysql
        String sql = "";
        if (dbConnectConfig.getInnerType().equalsIgnoreCase(DataBaseTypeEnum.MYSQL.getValue())) {
            sql = "select column_name as name,data_type as type,column_comment as comment,character_maximum_length as max_length,if(is_nullable='YES', true, false) as is_nullable, column_key as column_key from information_schema.columns where table_schema = database() and table_name = ?";
        }
        return this.findList(ColumnDefine.class, sql, tableName);
    }


    @Override
    String[] getServer() {
        return this.dbConnectConfig.getServer().split(";");
    }
}
