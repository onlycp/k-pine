package com.kingsware.kdev.core.db;

import com.kingsware.kdev.core.bean.BaseModel;

/**
 * SQL生成器
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/21 5:40 下午
 */
public class SqlGenerator {

    /** 私有构造 **/
    private SqlGenerator(){}

    /**
     * 生成SQL
     * @param model         模型
     * @param sqlTypeEnum   语句类型
     * @return              生成后的语句
     */
    public SqlBean createSql(BaseModel model, SqlTypeEnum sqlTypeEnum) {
        return null;
    }
}
