package com.kingsware.kdev.core.model;

import com.kingsware.kdev.core.bean.BaseManageModel;
import com.kingsware.kdev.core.orm.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 接口表
 * @author chenpeng
 * @version 1.0.0
 * @date 2021-12-27 10:20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table
public class SysApiMock extends BaseManageModel {

    /** 名称 */
    private String name ;
    /** url */
    private String url ;
    /** 请求方法  */
    private String requestMethod ;
    /** 请求参数 */
    private String requestBody ;
    /** 替换参数 */
    private String replaceBody ;
    /** 组名 */
    private String groupName ;
    /** 表达式 */
    private String assertExpr ;
    /** 是否启用 */
    private Integer enableMock ;
    /** mock_md5 **/
    private String mockMd5;

}
