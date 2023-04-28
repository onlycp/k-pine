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
public class ExtPluginInterface extends BaseModel {

    @Column(auto = AutoEnum.ID)
    private String id;
    private String name;
    private String respType;
    private String content;
    private String description;
    private String pluginId;
    private String createUser;
    private String updateUser;
    private Integer deleted;
}
