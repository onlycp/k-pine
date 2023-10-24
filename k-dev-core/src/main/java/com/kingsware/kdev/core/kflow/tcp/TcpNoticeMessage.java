package com.kingsware.kdev.core.kflow.tcp;

import lombok.Data;

/**
 * @author chenp
 * @date 2023/10/18
 */
@Data
public class TcpNoticeMessage {
    /** 谁发送 **/
    private String fromWho;
    /** 谁接收 **/
    private String toWho;
    /** 消息内容 **/
    private String message;
    /** 标题 **/
    private String title;
}
