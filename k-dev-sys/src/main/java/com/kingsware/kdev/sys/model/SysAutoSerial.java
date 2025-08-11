package com.kingsware.kdev.sys.model;

import com.kingsware.kdev.core.bean.BaseModel;
import com.kingsware.kdev.core.orm.annotation.AutoEnum;
import com.kingsware.kdev.core.orm.annotation.Column;
import com.kingsware.kdev.core.orm.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper = true)
@Table
public class SysAutoSerial extends BaseModel {
    /**  */
    @Column(auto = AutoEnum.ID)
    private String id ;
    /** 分类 */
    private String category ;
    /** 计算方式1: 按日 2：按月 3:按年 */
    private Integer type ;
    /** 计算方式key */
    private String key ;
    /** 当前编号 */
    private Integer autoNum ;
    /** 编号长度，不够前面补0 */
    private Integer numLength ;
    /** 步长 */
    private Integer step ;
    /** 初始值 */
    private Integer startNum ;
    /** 模板 */
    private String tpl ;
    /** 是否被锁 1：已锁 0：未锁 */
    private Integer locked ;
    /**  */
    @Column(auto = AutoEnum.WHEN, updatable = false)
    private String createTime ;
    /**  */
    @Column(auto = AutoEnum.WHO, updatable = false)
    private String createUser ;
    /**  */
    @Column(auto = AutoEnum.WHEN)
    private String updateTime ;
    /**  */
    @Column(auto = AutoEnum.WHO)
    private String updateUser ;

}
