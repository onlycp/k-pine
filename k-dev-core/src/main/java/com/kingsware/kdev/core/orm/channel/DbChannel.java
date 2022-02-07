package com.kingsware.kdev.core.orm.channel;

import com.kingsware.kdev.core.orm.DBConnectConfig;

import java.util.List;

/**
 * 数据库通道基类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/22 8:55 上午
 */
public interface DbChannel {

    /**
     * 通道名称
     * @return  通道名
     */
    String name();

    /**
     * 设置配置文件
     * @param config 配置文件
     */
    void setConfig(DBConnectConfig config);

    /**
     * 执行SQL，返回单条记录
     * @param sql       sql语句
     * @param tClass    class
     * @param objects   参数列表
     * @param <T>       泛型
     * @return          查询结果
     */
    <T> T queryForObject(String sql, Class<T> tClass, List<Object> objects);

    /**
     * 返回记录条数
     * @param sql       sql语句
     * @param objects   参数列表
     * @return          记录条数
     */
    long queryForCount(String sql, List<Object> objects);

    /**
     * 返回记录条数
     * @param sql       sql语句
     * @param objects   参数列表
     */
    void executeSql(String sql, List<Object> objects);

    /**
     * 执行SQL，返回列表
     * @param sql       sql语句
     * @param tClass    class
     * @param objects   参数列表
     * @param <T>       泛型
     * @return          查询结果
     */
     <T> List<T> queryForList(String sql, Class<T> tClass, List<Object> objects);


    /**
     * 查找单个属性的列表
     * @param sql       sql语句
     * @param tClass    class
     * @param objects   参数列表
     * @param <T>       泛型
     * @return          属性列表
     */
     <T> List<T> queryForAttribute(String sql, Class<T> tClass, List<Object> objects);

    /**
     * 调用http post接口
     * @param url
     * @param params
     * @return
     */
    String httpPost(String url, Object params);
}
