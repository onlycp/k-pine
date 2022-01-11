package com.kingsware.kdev.core.excel.format;

import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.expression.Expr;
import com.kingsware.kdev.core.util.BeanUtils;

/**
 * 字典格式
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/6 8:56 上午
 */
public class RegionModelIdFormat implements RegionFormat {

    /** 目标model **/
    private Class tClass;
    /** 属性名 **/
    private String propName;

    public RegionModelIdFormat(Class<?> tClass, String propName) {
        this.tClass = tClass;
        this.propName = propName;
    }

    @Override
    public Object format(Object value, Object model){
        // 值为空，就返回空值
        if (value == null) {
            return null;
        }
        // 从数据库里查数据
        Object entity = DB.findOne(tClass, Expr.builder().add(propName, "=",  value).build());
        if (entity == null) {
            return null;
        }
        else  {
            return BeanUtils.getFieldValue("id", entity);
        }

    }
}
