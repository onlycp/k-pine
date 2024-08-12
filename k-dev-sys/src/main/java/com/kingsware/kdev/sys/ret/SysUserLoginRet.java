package com.kingsware.kdev.sys.ret;

import com.kingsware.kdev.core.bean.BaseManageRet;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * 用户返回
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:49 上午
 */
@Data
@EqualsAndHashCode
public class SysUserLoginRet  {
    /** 令牌 */
    private String token;
    /**
     * 动作, 0: 正常登录 1：强制修改密码 2：提示修改密码 3：要求验证码
     */
    private Integer action = 0;

    /**
     * 提示信息
     */
    private String actionMessage;
    /**
     * 其他参数
     */
    private Object otherParams;
}
