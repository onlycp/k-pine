package com.kingsware.kdev.core.kflow.function;

import com.kingsware.kdev.core.auth.DataAccessUtil;
import com.kingsware.kdev.core.auth.SqlLink;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.core.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 函数类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/4/27 3:41 PM
 */
@Slf4j
public class Functions {

    /**
     * 获取数据权限sql
     * @param tableName  表名
     * @param alias      alias
     * @return           权限sql
     */
    public static String getDataAccessSql(String tableName, String alias) {
        String authoritySql = DataAccessUtil.getDataAccessSql(tableName, alias, SqlLink.EXISTS);
        if (StringUtils.isNotEmpty(authoritySql)) {
            return " and " + authoritySql;
        }
        else {
            return "";
        }
    }

    /**
     * 获取所有的系统配置
     * @return
     */
    public static String props() {
//        log.info("全部配置:{}",JsonUtil.toJson(SpringContext.getProperties()));
        return JsonUtil.toJson(SpringContext.getProperties());
    }

    /**
     * 调用方法
     * @param methodName    方法名
     * @param params        参数
     * @return              调用结果
     */
    public static Object call(String methodName, List<Object> params) {
        log.info("Function: {}, {},", methodName, params);
        // 获取所有方法
        Method[] methods = Functions.class.getDeclaredMethods();
        // 方法
        Method invokeMethod = null;
        // 查询方法
        for (Method method: methods) {
            // 简单的来，通过名称和参数个数对比
            if (method.getName().equals(methodName) && params.size() == method.getParameterCount()) {
                invokeMethod = method;
            }
        }
        if (invokeMethod == null) {
            throw BusinessException.serviceThrow("函数不存在或输入参数不符：" + methodName);
        }
        try {
            return invokeMethod.invoke(null, params.toArray(new Object[0]));
        }
        catch (IllegalAccessException | InvocationTargetException e) {
            throw BusinessException.serviceThrow("函数调用异常：" + methodName + ", 异常信息:" + e.getMessage());
        }
    }
}
