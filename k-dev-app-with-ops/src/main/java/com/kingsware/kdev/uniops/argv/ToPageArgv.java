package com.kingsware.kdev.uniops.argv;

import com.kingsware.kdev.core.bean.BaseArgv;
import lombok.Data;

/**
 * @author chenp
 * @date 2023/1/6
 */
@Data
public class ToPageArgv extends BaseArgv {
    /** 跳转url **/
    private String to;
    /** uniops令牌 **/
    private String opsToken;
}
