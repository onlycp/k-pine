package com.kingsware.kdev.sys.model;

import com.kingsware.kdev.core.bean.BaseManageModel;
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
public class SysViewModelFlow extends BaseManageModel {
    /**
     * 模型id
     **/
    private String viewModelId;
    /**
     * 流程id
     **/
    private String flowId;
    /** 所属应用ID **/
    private String appId;

}

