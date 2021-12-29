package com.kingsware.kdev.sys.ret;

import com.kingsware.kdev.core.bean.BaseManageRet;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 单位返回
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:49 上午
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUnitRet extends BaseManageRet {

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
    /** 子节点 **/
    private List<SysUnitRet> children;
}
