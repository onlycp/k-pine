package com.kingsware.kdev.sys.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kingsware.kdev.core.bean.BaseModel;
import com.kingsware.kdev.core.orm.annotation.AutoEnum;
import com.kingsware.kdev.core.orm.annotation.Column;
import com.kingsware.kdev.core.orm.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

/**
 * @version 1.0.0
 * @auther: crb
 * @date: 2022/01/20/11:12
 * @description: 消息记录管理
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table
public class SysNoticeRecord extends BaseModel {
    /** id **/
    @Column(auto = AutoEnum.ID)
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
    /** 所属应用ID **/
    private String appId;
}
