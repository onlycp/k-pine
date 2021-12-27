package com.kingsware.kdev.sys.model;

import com.kingsware.kdev.core.bean.BaseManageModel;
import com.kingsware.kdev.core.orm.annotation.Column;
import com.kingsware.kdev.core.orm.annotation.Table;

/**
 * 角色表
 * @author chenpeng
 * @version 1.0.0
 * @date 2021-12-27 10:20
 */
@Table
public class SysRole extends BaseManageModel {

    /** 字典名 */
    private String name;
    /** 字典代码 */
    private String code;
    /** 状态 **/
    private Integer status;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
