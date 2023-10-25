package com.kingsware.kdev.core.kflow.tcp;

import lombok.Data;

/**
 * @author chenp
 * @date 2023/10/18
 */
@Data
public class TcpCustomMessage {
    /** 类型 **/
    private String type;
    /** 数据 **/
    private String data;
}
