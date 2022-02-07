package com.kingsware.kdev.sys.ret;

import com.kingsware.kdev.core.bean.BaseManageRet;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色返回
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:49 上午
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysViewModelRet extends BaseManageRet {
    /** 名称 **/
    private String name;
    /** 标签 **/
    private String tag;
    /** 描述 **/
    private String note;
}
