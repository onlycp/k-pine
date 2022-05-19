package com.kingsware.kdev.sys.argv;

import lombok.Data;

/**
 * 授权激活
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/5/17 9:39 AM
 */
@Data
public class SysLicenseActive {
    /** 机器码 **/
    private String mac;
    /** license **/
    private String license;
}
