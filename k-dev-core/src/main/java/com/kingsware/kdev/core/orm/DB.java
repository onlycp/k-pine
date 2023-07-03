package com.kingsware.kdev.core.orm;

import com.kingsware.kdev.core.orm.exception.OrmDbException;
import com.kingsware.kdev.core.orm.expression.Expression;
import com.kingsware.kdev.core.orm.kdb.KDataBase;
import com.kingsware.kdev.core.orm.kdb.KdbApi;
import com.kingsware.kdev.core.orm.kdb.KdbRet;
import com.kingsware.kdev.core.orm.kdb.TransactionInfo;

import java.util.List;

/**
 * 简易数据库操作类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/21 5:15 下午
 */
public class DB {

    /** 数据库上下文字 **/
    private static final DbContext context = DbContext.getInstance();
    /** 默认构造函数 **/
    private DB() {}
    /**
     * 获取默认数据库
     * @return  数据库
     */
    public static DataBase getDefault() {
        return context.getDefault();
    }

    /**
     * 通过名称获取数据库
     * @param name  名称
     * @return  数据库
     */
    public static DataBase byName(String name) {
        return context.get(name);
    }

    /**
     * 通过id查询
     * @param id    通过id查询
     * @param <T>   实体泛型
     * @return      返回对应实体
     */
    public static <T> T findById(Class<T> tClass, Object id ) {
        return getDefault().findById(tClass, id);
    }

    /**
     * 通过SQL查询单条记录
     * @param tClass    实体泛型
     * @param sql       查询sql
     * @param params    参数列表
     * @param <T>       泛型
     * @return          单个实体或空
     */
    public static <T> T findOne(Class<T> tClass, String sql, Object... params) {
        return getDefault().findOne(tClass, sql, params);
    }

    /**
     * 查询单个对象
     * @param tClass           实体泛型
     * @param expressionList    表达式列表
     * @param <T>               泛型
     * @return                  单个对象，如果不存在，则返回null
     */
    public static <T> T findOne(Class<T> tClass, List<Expression> expressionList) {
        return getDefault().findOne(tClass, expressionList);
    }

    /**
     * 通过SQL查询单个属性
     * @param tClass    实体泛型
     * @param sql       查询sql
     * @param params    参数列表
     * @param <T>       泛型
     * @return          单个实体或空
     */
    public static <T> T findSingleAttribute(Class<T> tClass, String sql, Object... params) {
        return getDefault().findSingleAttribute(tClass, sql, params);
    }

    /**
     * 通过SQL查询单个属性
     * @param tClass    实体泛型
     * @param sql       查询sql
     * @param params    参数列表
     * @param <T>       泛型
     * @return          单个实体或空
     */
    public static <T> List<T> findSingleAttributeList(Class<T> tClass, String sql, Object... params) {
        return getDefault().findSingleAttributeList(tClass, sql, params);
    }

    /**
     * 查询数量
     * @param sql       查询sql
     * @param params    参数列表
     * @return          行数
     */
    public static long findCount(String sql, Object... params) {
        return getDefault().findCount(sql, params);
    }

    /**
     * 通过表达式查询数量
     * @param tClass            目标类
     * @param expressionList    表达式列表
     * @param <T>               泛型
     * @return                  数量
     */
    public static <T> long findCount(Class<T> tClass, List<Expression> expressionList) {
        return getDefault().findCount(tClass, expressionList);
    }

    /**
     * 通过SQL列表
     * @param tClass    实体泛型
     * @param sql       查询sql
     * @param params    参数列表
     * @param <T>       泛型
     * @return          单个实体或空
     */
    public static <T> List<T> findList(Class<T> tClass, String sql, Object... params) {
        return getDefault().findList(tClass, sql, params);
    }

    /**
     * 通过表达式查询列表
     * @param tClass            目标类
     * @param expressionList    表达式列表
     * @param <T>               泛型
     * @return          单个实体或空
     */
    public static <T> List<T> findList(Class<T> tClass, List<Expression> expressionList) {
        return getDefault().findList(tClass, expressionList);
    }

    /**
     * 分页查询
     * @param page          页号
     * @param pageSize      页大小
     * @param sql           查询sql
     * @param params        参数列表
     * @param <T>           泛型
     * @return              分页查询结果
     */
    public static <T> PagedList<T> findPagedList(Class<T> tClass, int page, int pageSize, String sql, Object... params) {
        return getDefault().findPagedList(tClass, page, pageSize, sql, params);
    }

    /**
     * 通过id删除
     * @param tClass    实体泛型
     * @param id        ID
     * @return          影响行数
     */
    public static <T> long delete(Class<T> tClass, Object id) {
        return getDefault().delete(tClass, id);
    }

    /**
     * 删除实体
     * @param entity    实体
     * @param <T>       泛型
     * @return          影响行数
     */
    public static <T> long delete(T entity) {
        return getDefault().delete(entity);
    }

    /**
     *  保存实体
     * @param entity    实体
     * @param <T>       泛型
     * @return          影响行数
     */
    public static <T> long save(T entity) {
        return getDefault().save(entity);
    }


    /**
     * 批量保存实体
     * @param list      实体列表
     * @param <T>       泛型
     * @return          影响行数
     */
    public static <T> long saveAll(List<T> list) {
        return getDefault().saveAll(list);
    }


    /**
     * 新增或保存
     * @param entity    实体
     * @return          影响行数
     * @param <T>       泛型
     */
    public static <T> long saveOrUpdate(T entity, Class<T> tClass) {
        return getDefault().saveOrUpdate(entity, tClass);
    }

    /**
     * 批量保存或新增
     * @param entities  实体
     * @return          影响行数
     * @param <T>       泛型
     */
    public static <T> long batchSaveOrUpdate(List<T> entities, Class<T> tClass) {
        if(entities == null) {
            return 0;
        }
        return getDefault().batchSaveOrUpdate(entities, tClass);
    }


    /**
     *  更新实体
     * @param entity    实体
     * @param <T>       泛型
     * @return          影响行数
     */
    public static  <T> long update(T entity) {
        return getDefault().update(entity);
    }

    /**
     *  批量更新实体
     * @param list      实体列表
     * @param <T>       泛型
     * @return          影响行数
     */
    public static <T> long updateAll(List<T> list) {
        return getDefault().updateAll(list);
    }


    /**
     * 执行SQL
     * @param sql       sql
     * @param params    参数列表
     * @return          影响行数
     */
    public static long executeUpdateSql(String sql, Object... params) {
        return getDefault().executeUpdateSql(sql, params);
    }

    /**
     * 获取默认的kdbApi
     * @return  返回kdbApi
     */
    public static KdbApi kdbApi() {
       return kdbApi(context.getDefault().name());
    }

    /**
     * 通过名称获取kdb的api
     * @param dbName    数据库名称
     * @return 返回kdbApi
     */
    public static KdbApi kdbApi(String dbName) {
        DataBase dataBase = DB.byName(dbName);
        if (dataBase instanceof KDataBase) {
            return ((KDataBase)dataBase);
        }
        else {
            throw new OrmDbException("找不到对应KDB Api");
        }
    }

    public static String transaction(TransactionInfo tran) {
         return getDefault().transaction(tran);
    }
    /**
     * 获取表定义
     * @param tableName 表名
     * @return  表定义
     */
    public static TableDefine table(String tableName) {
        return getDefault().table(tableName);
    }

    /**
     * 获取表的列定义
     * @param tableName 表名
     * @return  表定义
     */
    public static List<ColumnDefine> columns(String tableName) {
        return getDefault().columns(tableName);
    }
}
