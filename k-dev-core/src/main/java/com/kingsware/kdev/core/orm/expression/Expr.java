package com.kingsware.kdev.core.orm.expression;

import java.util.ArrayList;
import java.util.List;

/**
 * 表达式列表
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/27 5:07 下午
 */
public class Expr {

    /** 表达式 **/
    private List<Expression> expressionList = new ArrayList<>();

    /**
     * 构造器
     * @return 构建器
     */
    public static Expr builder() {
        return new Expr();
    }

    /**
     * 增加查询条件
     * @param propName      属性
     * @param op            操作符
     * @param value         值
     * @return
     */
    public Expr add(String propName, String op, Object value) {
        SimpleExpression expression = new SimpleExpression(propName);
        expression.setOp(op);
        expression.setValue(value);
        expressionList.add(expression);
        return this;
    }

    /**
     * 增加查询条件
     * @param propName      属性
     * @param lowValue      低值
     * @param highValue     高值
     * @return
     */
    public Expr addBetween(String propName, Object lowValue, Object highValue) {
        BetweenExpression expression = new BetweenExpression(propName);
        expression.setLowValue(lowValue);
        expression.setHighValue(highValue);
        expressionList.add(expression);
        return this;
    }

    public List<Expression> build() {
        return this.expressionList;
    }

}
