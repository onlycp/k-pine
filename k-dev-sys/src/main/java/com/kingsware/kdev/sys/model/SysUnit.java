package com.kingsware.kdev.sys.model;

import com.kingsware.kdev.core.bean.BaseManageModel;
import com.kingsware.kdev.core.orm.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 单位部门表
 * @author chenpeng
 * @version 1.0.0
 * @date 2021-12-27 10:20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table
public class SysUnit extends BaseManageModel {

    /** 名称 */
    private String name;
    /** 父节点id */
    private String parentId;
    /** 路径 */
    private String path;
    /** 负责人 **/
    private String leader;
    /** 联系电话 **/
    private String mobile;
    /** 电子邮件 **/
    private String email;
    /** 状态 **/
    private Integer status;
    /** 备注 */
    private String note;
    /** 排序 **/
    private Integer orderNum;

}
