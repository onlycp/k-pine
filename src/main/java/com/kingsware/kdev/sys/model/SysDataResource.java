package com.kingsware.kdev.sys.model;

import com.kingsware.kdev.core.bean.BaseManageModel;
import com.kingsware.kdev.core.orm.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 数据访问资源配置
 * @author chenpeng
 * @version 1.0.0
 * @date 2021-12-27 10:20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table
public class SysDataResource extends BaseManageModel {

    /** 名称 */
    private String name;
    /** 表名 */
    private String tableName;
    /** 标签列 **/
    private String labelField;
    /** 值列 **/
    private String valueField;
    /** 查询sql **/
    private String querySql;
    /** 是否树形结构 **/
    private Integer isTree;
    /** 是否仅仅选择树 **/
    private Integer isOnlyLeaf;
    /** 状态 */
    private Integer status;

}
