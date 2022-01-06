package com.kingsware.kdev.sys.ret;

import com.kingsware.kdev.core.bean.BaseManageRet;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 数据访问配置
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/6 11:52 上午
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysDataResourceRet extends BaseManageRet {

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
