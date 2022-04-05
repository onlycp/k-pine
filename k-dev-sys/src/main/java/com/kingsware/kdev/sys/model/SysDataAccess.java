package com.kingsware.kdev.sys.model;

import com.kingsware.kdev.core.bean.BaseManageModel;
import com.kingsware.kdev.core.orm.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 数据访问资源配置
 * @author chenpeng
 * @version 1.0.0
 * @date 2021-12-27 10:20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table
public class SysDataAccess extends BaseManageModel {

    /** 名称 */
    private String name;
    /** 状态 */
    private Integer status;
    /** 备注 **/
    private String note;
    /** 所属应用ID **/
    private String appId;

}
