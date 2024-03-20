package com.kingsware.kdev.sys.ret;

import lombok.Data;

/**
 * @author chenp
 * @date 2024/3/15
 */
@Data
public class ApiRequestRet {

    /** 接口 **/
    private String id;
    /** 接口名称 */
    private String apiName;
    /** 接口路径 */
    private String apiUrl;
    /** 接口方法 */
    private String apiMethod;
    /** 应用id **/
    private String appId;
    /** 创建人 **/
    private String whenModified;
}
