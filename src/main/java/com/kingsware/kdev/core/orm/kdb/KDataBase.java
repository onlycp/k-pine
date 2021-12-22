package com.kingsware.kdev.core.orm.kdb;

import com.kingsware.kdev.core.bean.BaseModel;
import com.kingsware.kdev.core.orm.*;
import com.kingsware.kdev.core.orm.channel.DbChannel;
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
    public void setChannel(DbChannel dbChannel) {
        this.channel = dbChannel;
    }

    @Override
    public void initDataBase(String name, DBConnectConfig dbConnectConfig) {
        this.name = name;
        this.dbConnectConfig = (KDBConnectConfig)dbConnectConfig;
    }

    @Override
    public DBConnectConfig getConfig() {
        return this.dbConnectConfig;
    }

    @Override
    public <T> T findById(Class<T> tClass, Object id) {
        String sql = SqlGenerator.findByIdSql(tClass);
        return channel.execute(sql, tClass, id);

    }

    @Override
    public <T> T findOne(Class<T> tClass, String sql, Object... params) {
        return channel.execute(sql, tClass,  params);
    }

    @Override
    public long findCount(String sql, Object... params) {
        return channel.execute(sql, Long.class,  params);
    }

    @Override
    public <T> List<T> findList(Class<T> tClass, String sql, Object... params) {
        return channel.executeList(sql, tClass,  params);
    }

    @Override
    public <T> PagedList<T> findPagedList(int startRow, int maxRow, String sql, Object... params) {

        return null;
    }

    @Override
    public <T> long delete(Class<T> tClass, Object id) {
        String sql = SqlGenerator.deleteById(tClass);
        return channel.execute(sql, Long.class,  id);
    }

    @Override
    public <T> long delete(T entity) {
        SqlBean sqlBean = SqlGenerator.deleteSql((BaseModel) entity);
        return  channel.execute(sqlBean.getSql(), Long.class, sqlBean.getParams().toArray());
    }

    @Override
    public <T> long save(T entity) {
        SqlBean sqlBean = SqlGenerator.insertSql((BaseModel) entity, DataBaseTypeEnum.KDB);
        return  channel.execute(sqlBean.getSql(), Long.class, sqlBean.getParams().toArray());

    }

    @Override
    public <T> long saveAll(List<T> list) {
        SqlBean sqlBean = SqlGenerator.insertListSql(list , DataBaseTypeEnum.KDB);
        return  channel.execute(sqlBean.getSql(), Long.class, sqlBean.getParams().toArray());
    }

    @Override
    public <T> long update(T entity) {
        SqlBean sqlBean = SqlGenerator.updateSql(entity , DataBaseTypeEnum.KDB);
        return  channel.execute(sqlBean.getSql(), Long.class, sqlBean.getParams().toArray());
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
        return  channel.execute(sql, Long.class, params);
    }
}
