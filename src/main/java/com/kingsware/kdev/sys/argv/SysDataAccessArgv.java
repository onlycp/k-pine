package com.kingsware.kdev.sys.argv;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *  数据访问组配置
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:47 上午
 */
@Data
@EqualsAndHashCode
public class SysDataAccessArgv {
    /** idc**/
    private String id;
    /** 名称 */
    private String name;
    /** 状态 */
    private Integer status;
    /** 备注 **/
    private String note;

}
