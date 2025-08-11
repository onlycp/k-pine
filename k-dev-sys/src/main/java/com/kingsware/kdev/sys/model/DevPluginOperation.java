package com.kingsware.kdev.sys.model;

import com.kingsware.kdev.core.bean.BaseManageModel;
import com.kingsware.kdev.core.orm.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Table
public class DevPluginOperation extends BaseManageModel {
    /** 编码 */
    private String code ;
    /** 标签 */
    private String tags ;
    /** API */
    private String apiId ;
    /** 标题 */
    private String title ;
    /** 描述 */
    private String notes ;
    /** 示例 */
    private String cases ;
    /** 成功示例 */
    private String successResp ;
    /** 失败示例 */
    private String errorResp ;
    /** 请求参数 */
    private String inParams ;
    /** 序号 */
    private Integer orderNum ;

}
