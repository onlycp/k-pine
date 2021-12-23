package com.kingsware.kdev.sys.model;

import com.kingsware.kdev.core.bean.BaseModel;
import com.kingsware.kdev.core.orm.annotation.AutoEnum;
import com.kingsware.kdev.core.orm.annotation.Column;
import com.kingsware.kdev.core.orm.annotation.ColumnIgnore;
import com.kingsware.kdev.core.orm.annotation.Table;

/**
 * 演示model
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:12 上午
 */
@Table
public class SysDemoModel extends BaseModel {

    /** id c**/
    @Column(auto = AutoEnum.ID)
    private String id;
    /** 名称 **/
    private String name;
    /** 性别 0：男 1： 女 **/
    private Integer sex;
    /** 生日 **/
    private String birthday;
    /** 描述 **/
    private String note;
    /** 吃饭时间 **/
    private String eatTime;
    @ColumnIgnore
    private String iAmPlay;

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

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getEatTime() {
        return eatTime;
    }

    public void setEatTime(String eatTime) {
        this.eatTime = eatTime;
    }

    public String getiAmPlay() {
        return iAmPlay;
    }

    public void setiAmPlay(String iAmPlay) {
        this.iAmPlay = iAmPlay;
    }
}
