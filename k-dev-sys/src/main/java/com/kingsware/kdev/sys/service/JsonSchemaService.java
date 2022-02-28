package com.kingsware.kdev.sys.service;

import com.kingsware.kdev.core.jsonschema.BaseSchemaDefine;

/**
 * Json schema 业务类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/2/24 6:43 下午
 */
public interface JsonSchemaService {

    /**
     * 获取
     * @param tableName 表名
     * @param curd 增删改查类型
     * @param inOut 输入输出
     * @return  json定义
     */
    BaseSchemaDefine createByTable(String tableName, String curd, String inOut);
}
