package com.kingsware.kdev.sys.model;

import com.kingsware.kdev.core.bean.BaseModel;
import com.kingsware.kdev.core.orm.annotation.AutoEnum;
import com.kingsware.kdev.core.orm.annotation.Column;
import com.kingsware.kdev.core.orm.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2023/4/27 17:06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table
public class ExtPluginTree extends BaseModel {

    @Column(auto = AutoEnum.ID)
    private String id;
    private String extName;
    private String jarName;
    private Integer type;
    private Integer status;
    private String name;
    private String clazzName;
    private String description;
    private String createUser;
    private String updateUser;
}
