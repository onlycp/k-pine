package com.kingsware.kdev.sys.argv;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *  数据访问配置参数
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:47 上午
 */
@Data
@EqualsAndHashCode
public class SysDataResourceArgv {
    /** idc**/
    private String id;
    /** 名称 */
    private String name;
    /** 表名 */
    private String tableName;
    /** 查询sql **/
    private String querySql;
    /** 是否树形结构 **/
    private Integer isTree;
    /** 是否仅仅选择树 **/
    private Integer isOnlyLeaf;
    /** 状态 */
    private Integer status;
    /** 额外sql **/
    private String extraSql;
    /** 所属应用ID **/
    private String appId;

}
