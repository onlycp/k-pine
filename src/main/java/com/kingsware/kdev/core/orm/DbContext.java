package com.kingsware.kdev.core.orm;

import com.kingsware.kdev.core.orm.kdb.KDataBase;
import com.kingsware.kdev.core.i18n.I18n;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据库上下文字
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/21 11:52 上午
 */
public class DbContext {
    /** 数据数据库操作名 **/
    private String defaultDataBaseName = "db";
    /** 单实例 **/
    private static DbContext INSTANCE;
    /** 数据库map对 **/
    private final ConcurrentHashMap<String, DataBase> dataBaseConcurrentHashMap = new ConcurrentHashMap();
    /** 默认数据库操作 **/
    private DataBase defaultDataBase;

    /**
     * 默认构造类
     */
    private DbContext() {}

    /** 获取当前实例 **/
    public static DbContext getInstance() {
        // 如果对象为空，需要对storage进行实例
        if (Objects.isNull(INSTANCE)) {
            synchronized (DbContext.class) {
                INSTANCE = new DbContext();
            }
        }
        return INSTANCE;
    }

    /**
     * 获取默认数据库操作
     * @return  数据库操作
     */
    public DataBase getDefault() {
        //  判断有无默认数据库
        if (getDefault() == null) {
            throw new RuntimeException(I18n.t("db.not-default-database", "当前无默认的数据库连接"));
        }
        return this.defaultDataBase;
    }


    /**
     * 获取数据库操作
     * @param name  数据库名称
     * @return      数据库
     */
    public DataBase get(String name) {
        // 如果名称不为空，则从数据库map里查找
        if (!dataBaseConcurrentHashMap.containsKey(name)) {
            throw new RuntimeException(I18n.t("db.not-database", "找不到对应的数据库连接"));
        }
        return dataBaseConcurrentHashMap.get(name);
    }

    /**
     * 将数据库存到本地map中
     * @param dataBase  数据库
     */
    private void putDatabase(DataBase dataBase) {
        if (dataBase.name().equals(defaultDataBaseName)) {
            this.defaultDataBase = dataBase;
        }
        dataBaseConcurrentHashMap.put(dataBase.name(), dataBase);
    }

    /**
     * 创建数据库连接
     * @param name              名称
     * @param connectConfig     配置信息
     */
    public void createDataBase(String name, DBConnectConfig connectConfig) {
        // 如果是kdb
        if (connectConfig.getDatabaseType().equalsIgnoreCase(DataBaseTypeEnum.KDB.getValue())) {
            KDataBase dataBase = new KDataBase();
            dataBase.initDataBase(name, connectConfig);
            putDatabase(dataBase);
        }
    }


    /**
     * 将数据库从本地map中删除
     * @param name  数据库名称
     */
    public void removeDataBase(String name) {
        dataBaseConcurrentHashMap.remove(name);
    }


}
