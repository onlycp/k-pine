package com.kingsware.kdev.core.bean;

import com.kingsware.kdev.core.orm.annotation.AutoEnum;
import com.kingsware.kdev.core.orm.annotation.Column;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

/**
 * 管理类model
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/21 5:49 下午
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BaseManageModel extends BaseModel {
    /** id **/
    @Column(auto = AutoEnum.ID)
    private String id;
    /** 创建人员 **/
    @Column(auto = AutoEnum.WHO, updatable = false)
    private String whoCreated;
    /** 创建时间 **/
    @Column(auto = AutoEnum.WHEN, updatable = false)
    private Timestamp whenCreated;
    /** 修改人员 **/
    @Column(auto = AutoEnum.WHO)
    private String whoModified;
    /** 修改时间 **/
    @Column(auto = AutoEnum.WHEN)
    private Timestamp whenModified;

    /**
     * 清空星星
     */
    public void cleanAuthor() {
        this.whenCreated = null;
        this.whenModified = null;
        this.whoCreated = null;
        this.whoModified = null;
    }

}
