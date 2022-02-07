package com.kingsware.kdev.sys.ret;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kingsware.kdev.core.bean.BaseSimpleRet;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

/**
 * @author: crb
 * @version: 1.0.0
 * @date: 2022/01/20/11:28
 * @description:
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysNoticeRecordRet extends BaseSimpleRet {
    private String id;
    /** 发送人 */
    private String fromWho;
    /** 发送人名称 */
    private String fromWhoName;
    /** 接收人 */
    private String toWho;
    /** 接收人 */
    private String toWhoName;
    /** 关联通知 */
    private String noticeId;
    /** 是否已读，0：未读，1：已读 */
    private Integer isRead;
    /** 阅读通知时间 */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Timestamp readTime;
    /** 接收通知时间，1：系统维护通知，2：公告 */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Timestamp noticeTime;
    /** 通知标题 */
    private String title;
    /** 通知内容 */
    private String content;
}
