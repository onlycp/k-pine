package com.kingsware.kdev.core.cache.dict;

import lombok.Data;

/**
 * @author AndyZheng
 * @version 1.0.0
 * @description: 字典类型
 * @date 2021-12-27 10:20
 */
@Data
public class DictItemInfo {

    /** 字典名 */
    private String name;
    /** 字典组名 */
    private String groupName;
    /** 字典类型ID */
    private String sysDictId;
    /** 字典值 */
    private String value;
    /** 字典代码 */
    private String code;
    /** 备注 */
    private String note;
    /** 排序序号 */
    private Integer orderNum;

}
