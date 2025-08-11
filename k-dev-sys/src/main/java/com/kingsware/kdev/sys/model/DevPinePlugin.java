package com.kingsware.kdev.sys.model;

import com.kingsware.kdev.core.bean.BaseManageModel;
import com.kingsware.kdev.core.orm.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Table
public class DevPinePlugin extends BaseManageModel {
    /** 插件名称 */
    private String pluginName ;
    /** 插件版本号 */
    private String pluginVersion ;
    /** 插件作者 */
    private String author ;
    /** 文件id */
    private String fileId ;
    /** 是否启动 */
    private Integer enableStatus ;
    /** 归属应用id */
    private String appId ;
    /** 说明 */
    private String note ;
}
