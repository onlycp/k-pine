package com.kingsware.kdev.sys.model;

import com.kingsware.kdev.core.bean.BaseManageModel;
import com.kingsware.kdev.core.orm.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2023/4/27 17:03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table
public class SysLogicTemplate extends BaseManageModel {

    private String name;
    private String moduleId;
    private String description;
    private String nodes;
    private String links;
    private String appId;
    private String newFlowJson;
    private String flowConfig;
    private String type;
}
