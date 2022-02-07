package com.kingsware.kdev.biz.kw.argv;

import com.kingsware.kdev.core.bean.BasePageArgv;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author andyzheng
 * @version 1.0.0
 * @description: 公司信息表列表参数
 * @date 2022/1/4 18:09
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class KwCompanyQueryArgv extends BasePageArgv {
    /** 单位编号 */
    private String serialNumber;
    /** 单位名称 */
    private String name;
    /** 单位简称 */
    private String shortName;
}
