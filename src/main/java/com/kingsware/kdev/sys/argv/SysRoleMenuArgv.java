package com.kingsware.kdev.sys.argv;

import com.kingsware.kdev.core.bean.MultiIdArgv;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *  角色参数
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:47 上午
 */
@Data
@EqualsAndHashCode
public class SysRoleMenuArgv extends MultiIdArgv {
    /** 角色ID */
    private String sysRoleId;
}
