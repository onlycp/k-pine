package com.kingsware.kdev.core.orm;

import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.orm.annotation.LogicDelete;
import com.kingsware.kdev.core.util.ClassUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 逻辑删除表实例
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/12 10:14 上午
 */
public class LogicDeleteTables {

    private static LogicDeleteTables instance;
    /** 逻辑删除表缓存 **/
    private Map<String, LogicDelete> logicDeleteMap = new HashMap<>();

    private LogicDeleteTables() {
    }


    public static LogicDeleteTables getInstance() {
        if (instance == null) {
            synchronized (LogicDeleteTables.class) {
                if (instance == null) {
                    instance = new LogicDeleteTables();
                    instance.scan();
                }
            }
        }
        return instance;
    }

    /**
     * 扫描所有的逻辑删除表
     */
    private void scan() {
        // 获取基础扫描的文件
        String basePackage = SpringContext.getProperties("db.base-package", "com.kingsware.kdev");
        List<Class<?>> classList = ClassUtils.getClassesByAnnotationClass(basePackage, LogicDelete.class);
        for (Class clazz: classList) {
            LogicDelete logicDelete = (LogicDelete) clazz.getAnnotation(LogicDelete.class);
            // 获取表名
            String tableName = ModelUtil.getTableName(clazz);
            this.logicDeleteMap.put(tableName.toLowerCase(), logicDelete);
        }
    }

    
    /**
     * 获取逻辑删除表定义
     * @param table 表名
     * @return      逻辑删除定义，如果不存在，返回null
     */
    public LogicDelete getTable(String table) {
        return logicDeleteMap.getOrDefault(table, null);
    }


}
