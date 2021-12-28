package com.kingsware.kdev.core.orm.kdb;

import com.kingsware.kdev.core.bean.BaseModel;
import com.kingsware.kdev.core.orm.*;
import com.kingsware.kdev.core.orm.channel.DbChannel;
import com.kingsware.kdev.core.orm.channel.KDBHttpChannel;
import com.kingsware.kdev.core.orm.expression.Expression;

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
public class KDataBase implements DataBase {
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
    public long findCount(String sql, Object... params) {
        return channel.queryForCount(sql,  Arrays.asList(params));
    }

    @Override
    public <T> long findCount(Class<T> tClass, List<Expression> expressionList) {
        SqlWrapper sqlWrapper = SqlGenerator.findSql(tClass, expressionList);
        return this.findCount(SqlGenerator.getListSql2CountSql(sqlWrapper.getSql()),  sqlWrapper.getParams().toArray());
    }

    @Override
    public <T> List<T> findList(Class<T> tClass, String sql, Object... params) {
        return channel.queryForList(sql, tClass,  Arrays.asList(params));
    }

    @Override
    public <T> List<T> findList(Class<T> tClass, List<Expression> expressionList) {
        SqlWrapper sqlWrapper = SqlGenerator.findSql(tClass, expressionList);
        return this.findList(tClass, sqlWrapper.getSql(), sqlWrapper.getParams().toArray());
    }

    @Override
    public <T> PagedList<T> findPagedList(Class<T> tClass, int page, int pageSize, String sql, Object... params) {
        // 先查询总数
        String selectCountSql = SqlGenerator.getListSql2CountSql(sql);
        Long count = channel.queryForCount(selectCountSql, Arrays.asList(params));
        // 查询数据
        String dataQuerySql = sql + "limit ?,?";
        // 计算limit
        int from = (page - 1) * pageSize;
        List<Object> objects = new ArrayList<>(Arrays.asList(params));
        // 加入from
        objects.add(from);
        // 加入pageSize
        objects.add(pageSize);
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
        pagedList.setTotalCount((count.intValue()));
        pagedList.setPageSize(pageSize);
        return pagedList;
    }

    @Override
    public <T> long delete(Class<T> tClass, Object id) {
        String sql = SqlGenerator.deleteById(tClass);
        channel.executeSql(sql,Collections.singletonList(id));
        return 1;
    }

    @Override
    public <T> long delete(T entity) {
        SqlWrapper sqlWrapper = SqlGenerator.deleteSql((BaseModel) entity);
        channel.executeSql(sqlWrapper.getSql(), sqlWrapper.getParams());
        return 1;
    }

    @Override
    public <T> long save(T entity) {
        SqlWrapper sqlWrapper = SqlGenerator.insertSql((BaseModel) entity, DataBaseTypeEnum.KDB);
        channel.executeSql(sqlWrapper.getSql(),  sqlWrapper.getParams());
        return 1;

    }

    @Override
    public <T> long saveAll(List<T> list) {
        SqlWrapper sqlWrapper = SqlGenerator.insertListSql(list , DataBaseTypeEnum.KDB);
        channel.executeSql(sqlWrapper.getSql(), sqlWrapper.getParams());
        return list.size();
    }

    @Override
    public <T> long update(T entity) {
        SqlWrapper sqlWrapper = SqlGenerator.updateSql(entity , DataBaseTypeEnum.KDB);
        channel.executeSql(sqlWrapper.getSql(), sqlWrapper.getParams());
        return 1;
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
    public long executeUpdateSql(String sql, Object... params) {
        channel.executeSql(sql,Arrays.asList(params));
        // 由于kdb没有返回具体行，所以统一返回1
        return 1;
    }
}
