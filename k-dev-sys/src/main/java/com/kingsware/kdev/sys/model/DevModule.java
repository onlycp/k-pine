package com.kingsware.kdev.sys.model;

import com.kingsware.kdev.core.bean.BaseManageModel;
import com.kingsware.kdev.core.orm.annotation.LogicDelete;
import com.kingsware.kdev.core.orm.annotation.Table;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Table
public class DevModule extends BaseManageModel {
    /** 名称 */
    private String name ;
    /** 路径 */
    private String path ;
    /** 是否有路径 */
    private Integer hasPath ;
    /** 父节点 */
    private String parentId ;
    /** 排序 */
    private Integer sort ;
    /** 是否系统 */
    private Integer isSys ;
    /** 关联应用 */
    private String appId ;

}
