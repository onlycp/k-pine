package com.kingsware.kdev.sys.bean;

import lombok.Data;

/**
 * FAAS上传文件响应
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/4/28 2:59 PM
 */
@Data
public class FaasUploadRet {
    /** 文件名 **/
    private String fileName;
}
