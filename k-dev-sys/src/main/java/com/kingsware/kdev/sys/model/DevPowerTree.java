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
public class DevPowerTree extends BaseManageModel {

    private String parentId;
    private String note;
    private String path;
    private String name;
    private Integer orderNum;
}
