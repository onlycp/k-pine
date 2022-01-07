package com.kingsware.kdev.sys.ret;

import com.kingsware.kdev.core.bean.BaseManageRet;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 数据访问配置
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/6 11:52 上午
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysDataAccessRet extends BaseManageRet {

    /** 名称 */
    private String name;
    /** 状态 */
    private Integer status;
    /** 备注 **/
    private String note;
}
