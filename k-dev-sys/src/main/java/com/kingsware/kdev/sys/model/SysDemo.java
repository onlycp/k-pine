package com.kingsware.kdev.sys.model;

import com.kingsware.kdev.core.bean.BaseManageModel;
import com.kingsware.kdev.core.bean.BaseModel;
import com.kingsware.kdev.core.orm.annotation.ColumnIgnore;
import com.kingsware.kdev.core.orm.annotation.LogicDelete;
import com.kingsware.kdev.core.orm.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 演示model
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:12 上午
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table
@LogicDelete
public class SysDemo extends BaseManageModel {

    /** 名称 **/
    private String name;
    /** 性别 0：男 1： 女 **/
    private String sex;
    /** 生日 **/
    private String birthday;
    /** 描述 **/
    private String note;
    /** 吃饭时间 **/
    private String eattime;
    @ColumnIgnore
    private String iAmPlay;

}
