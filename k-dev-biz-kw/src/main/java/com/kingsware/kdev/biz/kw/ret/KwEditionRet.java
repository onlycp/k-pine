package com.kingsware.kdev.biz.kw.ret;

import com.kingsware.kdev.core.bean.BaseManageModel;
import com.kingsware.kdev.core.bean.BaseManageRet;
import com.kingsware.kdev.core.orm.annotation.LogicDelete;
import com.kingsware.kdev.core.orm.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author andyzheng
 * @version 1.0.0
 * @description: 银行版本
 * @date 2022/1/4 18:09
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class KwEditionRet extends BaseManageRet {
    /** 机构id */
    private String mechanismId;
    /** 机构名称 */
    private String mechanismName;
    /** 版本名称 */
    private String name;
    /** 密码最大错误次数 */
    private Integer passwordMaxRetried;
    /** 是否有 */
    private Integer ukey;
    /** 访问路径 */
    private String path;
    /** 系统类型 */
    private String bankType;
    /** 版本状态 */
    private Integer status;
    /** 版本说明 */
    private String description;
    /** 备用字段1 */
    private String reserve1;
    /** 备用字段2 */
    private String reserve2;
    /** 备用字段3 */
    private String reserve3;
    /** 备用字段4 */
    private String reserve4;
    /** 备用字段5 */
    private String reserve5;
}
