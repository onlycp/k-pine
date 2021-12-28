package com.kingsware.kdev.sys.ret;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author AndyZheng
 * @version 1.0.0
 * @description: 字典类型
 * @date 2021-12-27 10:20
 */
@Data
@EqualsAndHashCode
public class SysDictItemRet {

    /** id */
    private String id;
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
    /** 创建人员 **/
    private String whoCreated;
    /** 创建时间 **/
    private String whenCreated;
    /** 修改人员 **/
    private String whoModified;
    /** 修改时间 **/
    private String whenModified;

}
