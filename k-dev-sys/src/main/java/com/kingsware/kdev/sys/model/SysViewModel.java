package com.kingsware.kdev.sys.model;

import com.kingsware.kdev.core.bean.BaseManageModel;
import com.kingsware.kdev.core.orm.annotation.LogicDelete;
import com.kingsware.kdev.core.orm.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 视图模型
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/29 3:44 下午
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table
@LogicDelete
public class SysViewModel extends BaseManageModel {
    /** 名称 **/
    private String name;
    /** 标签 **/
    private String tag;
    /** 描述 **/
    private String note;
    /** 所属应用ID **/
    private String appId;
}
