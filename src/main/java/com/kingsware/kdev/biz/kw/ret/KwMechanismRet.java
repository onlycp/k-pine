package com.kingsware.kdev.biz.kw.ret;

import com.kingsware.kdev.core.bean.BaseManageRet;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 机构返回
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:49 上午
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class KwMechanismRet extends BaseManageRet {
    /** 银行名称 */
    private String bankName;
    /** 银行代码 */
    private String bankNumber;
    /** 简称 **/
    private String bankShort;
    /** 银行类型 **/
    private Integer bankType;
    /** 备用字段1 **/
    private String reserve1;
    /** 备用字段2 **/
    private String reserve2;
    /** 备用字段3 **/
    private String reserve3;
    /** 备用字段4 **/
    private String reserve4;
    /** 备用字段5 **/
    private String reserve5;
}
