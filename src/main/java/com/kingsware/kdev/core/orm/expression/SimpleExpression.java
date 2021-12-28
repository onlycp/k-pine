package com.kingsware.kdev.core.orm.expression;

/**
 * Between查询
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 6:32 下午
 */
public class SimpleExpression extends AbstractExpression {
    /** 值 **/
    private Object value;
    /** 符号 **/
    private String op;

    public SimpleExpression(String propName) {
        super(propName);
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }
}
