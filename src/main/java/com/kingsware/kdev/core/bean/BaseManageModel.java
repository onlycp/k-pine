package com.kingsware.kdev.core.bean;

import java.io.Serializable;

/**
 * 管理类model
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/21 5:49 下午
 */
public class BaseManageModel extends BaseModel {
    /** id **/
    private String id;
    /** 创建人员 **/
    private String whoCreated;
    /** 创建时间 **/
    private String whenCreated;
    /** 修改人员 **/
    private String whoModified;
    /** 修改时间 **/
    private String whenModified;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWhoCreated() {
        return whoCreated;
    }

    public void setWhoCreated(String whoCreated) {
        this.whoCreated = whoCreated;
    }

    public String getWhenCreated() {
        return whenCreated;
    }

    public void setWhenCreated(String whenCreated) {
        this.whenCreated = whenCreated;
    }

    public String getWhoModified() {
        return whoModified;
    }

    public void setWhoModified(String whoModified) {
        this.whoModified = whoModified;
    }

    public String getWhenModified() {
        return whenModified;
    }

    public void setWhenModified(String whenModified) {
        this.whenModified = whenModified;
    }
}
