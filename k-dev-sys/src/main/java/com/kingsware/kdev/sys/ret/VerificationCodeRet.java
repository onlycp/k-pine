package com.kingsware.kdev.sys.ret;

import lombok.Data;

@Data
public class VerificationCodeRet {

    /** 唯一识别ID，预留给未来有分布式缓存方案时，使用，目前暂时使用可解密方案 */
    private String uuid;

    /** 加密后的验证码 */
    private String encryptCode;

    /** 验证码图片 */
    private String imageBase64String;

}
