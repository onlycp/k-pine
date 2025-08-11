package com.kingsware.kdev.sys.model;

import com.kingsware.kdev.core.bean.BaseManageModel;
import com.kingsware.kdev.core.orm.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Table
public class DevTeam extends BaseManageModel {
    /** 团队名称 */
    private String name ;
    /** 团队负责人 */
    private String owner ;
    /** 团队简介 */
    private String description ;
    /** 是否已删除 */
    private Integer deleted ;

}
