package com.kingsware.kdev.core.db.kdb;

import com.kingsware.kdev.core.db.DBConnectConfig;
import com.kingsware.kdev.core.db.DataBase;
import com.kingsware.kdev.core.db.DataBaseTypeEnum;
import com.kingsware.kdev.core.db.PagedList;

import java.util.List;
import java.util.Map;

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
    }

    @Override
    public DBConnectConfig getConfig() {
        return this.dbConnectConfig;
    }

    @Override
    public <T> T findById(Class<T> tClass, Object id) {
        return null;
    }

    @Override
    public <T> T findOne(Class<T> tClass, String sql, Object... params) {
        return null;
    }

    @Override
    public long findCount(String sql, Object... params) {
        return 0;
    }

    @Override
    public <T> List<T> findList(Class<T> tClass, String sql, Object... params) {
        return null;
    }

    @Override
    public <T> PagedList<T> findPagedList(int startRow, int maxRow, String sql, Object... params) {
        return null;
    }

    @Override
    public <T> long delete(Class<T> tClass, Object id) {
        return 0;
    }

    @Override
    public <T> long delete(T entity) {
        return 0;
    }

    @Override
    public <T> long save(T entity) {
        return 0;
    }

    @Override
    public <T> long saveAll(List<T> list) {
        return 0;
    }

    @Override
    public <T> long update(T entity) {
        return 0;
    }

    @Override
    public <T> long updateAll(List<T> list) {
        return 0;
    }

    @Override
    public long executeUpdateSql(String sql, Object... params) {
        return 0;
    }
}
