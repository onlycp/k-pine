package com.kingsware.kdev.core.cache.open;

import lombok.Data;

/**
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/5/11 11:45 AM
 */
@Data
public class OpenAccountApiCode {
    /** 接入商id **/
    private String accessId;
    /** 接口编码 **/
    private String apiCode;

}
