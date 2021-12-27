package com.kingsware.kdev.sys.model;

import com.kingsware.kdev.core.bean.BaseModel;
import com.kingsware.kdev.core.orm.annotation.AutoEnum;
import com.kingsware.kdev.core.orm.annotation.Column;
import com.kingsware.kdev.core.orm.annotation.Table;

/**
 * @author AndyZheng
 * @version 1.0.0
 * @description: 字典类型
 * @date 2021-12-27 10:20
 */
@Table
public class SysDict extends BaseModel {

    /** 字典名 */
    private String name;
    /** 字典代码 */
    private String code;
    /** 备注 */
    private String note;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
