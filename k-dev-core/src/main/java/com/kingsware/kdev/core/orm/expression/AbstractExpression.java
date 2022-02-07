package com.kingsware.kdev.core.orm.expression;

/**
 * 表达式抽象类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/22 9:21 上午
 */
public class AbstractExpression implements Expression {
    /** 属性名称 **/
    private String propName;

    public AbstractExpression(String propName) {
        this.propName = propName;
    }

    public String getPropName() {
        return propName;
    }

    public void setPropName(String propName) {
        this.propName = propName;
    }
}
