package com.kingsware.kdev.core.kmq.websocket;

import lombok.Data;

/**
 * websocket信息发送
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/5/27 5:43 PM
 */
@Data
public class WmMessageArgv {

    private String token;
    private String userId;
    private String message;
}
