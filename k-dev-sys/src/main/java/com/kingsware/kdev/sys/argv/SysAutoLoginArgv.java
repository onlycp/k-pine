package com.kingsware.kdev.sys.argv;

import lombok.Data;

/**
 * @author chenp
 * @date 2023/3/15
 */
@Data
public class SysAutoLoginArgv {

    /**
     * 令牌
     */
    private String token;
    /**
     * 成功跳转url
     */
    private String successUrl;
    /**
     * 失败跳转url
     */
    private String failUrl;
    /**
     * 成功提示
     */
    private String successMsg;
    /**
     * 失败提示
     */
    private String failMsg;
}
