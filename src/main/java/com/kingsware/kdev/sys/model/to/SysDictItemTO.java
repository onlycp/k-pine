package com.kingsware.kdev.sys.model.to;

import com.kingsware.kdev.core.bean.BaseModel;
import com.kingsware.kdev.core.orm.annotation.Table;

/**
 * @author AndyZheng
 * @version 1.0.0
 * @description: 字典类型
 * @date 2021-12-27 10:20
 */
public class SysDictItemTO {

    /** id */
    private String id;
    /** 字典名 */
    private String name;
    /** 字典组名 */
    private String groupName;
    /** 字典类型ID */
    private String sysDictId;
    /** 字典值 */
    private String value;
    /** 字典代码 */
    private String code;
    /** 备注 */
    private String note;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getSysDictId() {
        return sysDictId;
    }

    public void setSysDictId(String sysDictId) {
        this.sysDictId = sysDictId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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
