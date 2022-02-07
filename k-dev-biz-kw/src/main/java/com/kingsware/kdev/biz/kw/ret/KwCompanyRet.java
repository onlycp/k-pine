package com.kingsware.kdev.biz.kw.ret;

import com.kingsware.kdev.core.bean.BaseManageRet;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author andyzheng
 * @version 1.0.0
 * @description: 公司信息表
 * @date 2022/1/5 10:13
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class KwCompanyRet extends BaseManageRet {
    /** 单位编号 */
    private String serialNumber;
    /** 单位名称 */
    private String name;
    /** 单位简称 */
    private String shortName;
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
    /** 备注 */
    private String remark;
}
