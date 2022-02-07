package com.kingsware.kdev.core.orm.expression;

/**
 * 操作符
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/28 10:42 上午
 */
public enum Op {

    EXISTS(" is not null"),
    NOT_EXISTS(" is null"),
    BETWEEN(" between ? and ?"),
    EQ(" = ?"),
    NOT_EQ(" <> ?"),
    LT(" < ?"),
    LT_EQ(" <= ?"),
    GT(" > ?"),
    GT_EQ(" >= ?"),
    LIKE("like ?"),
    NOT_LIKE(" not like ? ");

    final String exp;

    private Op(String exp) {
        this.exp = exp;
    }

    public String bind() {
        return this.exp;
    }

}
