package com.kingsware.kdev.biz.kw.argv;

import lombok.Data;

/**
 * @author andyzheng
 * @version 1.0.0
 * @description: 公司信息表
 * @date 2022/1/5 10:13
 */
@Data
public class KwCompanyArgv {
    /** 机构id */
    private String id;
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
