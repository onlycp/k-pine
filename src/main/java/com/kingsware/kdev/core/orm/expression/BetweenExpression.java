package com.kingsware.kdev.core.orm.expression;

/**
 * Between表达式
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/22 9:25 上午
 */
public class BetweenExpression extends AbstractExpression {
    /** 高值 **/
    private final Object valueHigh;
    /** 低值 **/
    private final Object valueLow;

    /**
     * 构造函数
     * @param propertyName  属性名
     * @param valueLow      低值
     * @param valueHigh     高值
     */
    public BetweenExpression(String propertyName, Object valueLow, Object valueHigh) {
        super(propertyName);
        this.valueLow = valueLow;
        this.valueHigh = valueHigh;
    }

}
