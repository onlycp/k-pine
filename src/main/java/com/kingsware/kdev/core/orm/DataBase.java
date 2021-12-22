package com.kingsware.kdev.core.orm;

import com.kingsware.kdev.core.orm.channel.DbChannel;

import java.util.List;

/**
 *  数据库操作接口类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/21 11:07 上午
 */
public interface DataBase {

    /**
     * 数据源名称
     * @return  返回数据源名称
     */
    String name();

    /**
     * 数据库类型
     * @return  数据库类型
     */
    DataBaseTypeEnum databaseType();

    /**
     * 设置通道
     */
    void setChannel(DbChannel dbChannel);

    /**
     * 初始化数据库连接
     * @param name  名称
     * @param dbConnectConfig    数据库连接信息
     */
    void initDataBase(String name, DBConnectConfig dbConnectConfig);

    /**
     * 获取数据库配置信息
     * @return  数据库配置信息
     */
    DBConnectConfig getConfig();

    /**
     * 通过id查询
     * @param id    通过id查询
     * @param <T>   实体泛型
     * @return      返回对应实体
     */
    <T> T findById(Class<T> tClass, Object id );

    /**
     * 通过SQL查询单条记录
     * @param tClass    实体泛型
     * @param sql       查询sql
     * @param params    参数列表
     * @param <T>       泛型
     * @return          单个实体或空
     */
    <T> T findOne(Class<T> tClass, String sql, Object... params);

    /**
     * 查询数量
     * @param sql       查询sql
     * @param params    参数列表
     * @return          行数
     */
    long findCount(String sql, Object... params);

    /**
     * 通过SQL列表
     * @param tClass    实体泛型
     * @param sql       查询sql
     * @param params    参数列表
     * @param <T>       泛型
     * @return          单个实体或空
     */
    <T> List<T> findList(Class<T> tClass, String sql, Object... params);

    /**
     * 分页查询
     * @param startRow      起始行号
     * @param maxRow        最大行号(应对分页数)
     * @param sql           查询sql
     * @param params        参数列表
     * @param <T>           泛型
     * @return              分页查询结果
     */
    <T> PagedList<T> findPagedList(int startRow, int maxRow, String sql, Object... params);

    /**
     * 通过id删除
     * @param tClass    实体泛型
     * @param id        ID
     * @return          影响行数
     */
    <T> long delete(Class<T> tClass, Object id);

    /**
     * 删除实体
     * @param entity    实体
     * @param <T>       泛型
     * @return          影响行数
     */
    <T> long delete(T entity);

    /**
     *  保存实体
     * @param entity    实体
     * @param <T>       泛型
     * @return          影响行数
     */
    <T> long save(T entity);


    /**
     * 批量保存实体
     * @param list      实体列表
     * @param <T>       泛型
     * @return          影响行数
     */
    <T> long saveAll(List<T> list);



    /**
     *  更新实体
     * @param entity    实体
     * @param <T>       泛型
     * @return          影响行数
     */
    <T> long update(T entity);

    /**
     *  批量更新实体
     * @param list      实体列表
     * @param <T>       泛型
     * @return          影响行数
     */
    <T> long updateAll(List<T> list);


    /**
     * 执行SQL
     * @param sql       sql
     * @param params    参数列表
     * @return          影响行数
     */
    long executeUpdateSql(String sql, Object... params);



}
