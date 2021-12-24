package com.kingsware.kdev.core.orm.expression;

/**
 * Between查询
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 6:32 下午
 */
public class BetweenExpression extends AbstractExpression {
    /** 低值 **/
    private Object lowValue;
    /** 高值 **/
    private Object highValue;

    public BetweenExpression(String propName) {
        super(propName);
    }
}
