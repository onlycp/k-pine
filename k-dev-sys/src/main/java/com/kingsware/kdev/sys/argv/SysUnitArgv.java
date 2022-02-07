package com.kingsware.kdev.sys.argv;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *  单位参数
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:47 上午
 */
@Data
@EqualsAndHashCode
public class SysUnitArgv {
    /** idc**/
    private String id;
    /** 名称 */
    private String name;
    /** 父节点id */
    private String parentId;
    /** 负责人 **/
    private String leader;
    /** 联系电话 **/
    private String mobile;
    /** 电子邮件 **/
    private String email;
    /** 状态 **/
    private Integer status;
    /** 排序 **/
    private Integer orderNum;
    /** 备注 */
    private String note;
}
