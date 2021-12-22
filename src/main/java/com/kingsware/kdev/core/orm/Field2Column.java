package com.kingsware.kdev.core.orm;

import com.kingsware.kdev.core.orm.annotation.Column;

import java.lang.reflect.Field;

/**
 * Field和Column的对应关系
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/22 11:46 上午
 */
public class Field2Column {


    /** 列名 **/
    private String columnName;
    /** Field **/
    private Field field;
    /** column 注释 **/
    private Column column;

    public Field2Column(String columnName, Field field, Column column) {
        this.columnName = columnName;
        this.field = field;
        this.column = column;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }
}
