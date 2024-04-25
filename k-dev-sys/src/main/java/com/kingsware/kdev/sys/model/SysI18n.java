package com.kingsware.kdev.sys.model;

import com.kingsware.kdev.core.bean.BaseManageModel;
import com.kingsware.kdev.core.orm.annotation.LogicDelete;
import com.kingsware.kdev.core.orm.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Table
public class SysI18n extends BaseManageModel {
    /** 键名 */
    private String i18nKey ;
    /** 国际化配置信息，JSON保存 */
    private String message ;
    /** 归属应用ID */
    private String appId ;

}