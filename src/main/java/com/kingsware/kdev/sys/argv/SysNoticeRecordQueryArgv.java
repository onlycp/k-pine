package com.kingsware.kdev.sys.argv;

import com.kingsware.kdev.core.bean.BasePageArgv;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: crb
 * @version: 1.0.0
 * @date: 2022/01/20/11:34
 * @description:
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysNoticeRecordQueryArgv extends BasePageArgv {
    /** 发送人名称 */
    private String fromWhoName;
    /** 接收人 */
    private String toWhoName;
    /** 通知标题 */
    private String title;
    /** 通知内容 */
    private String content;
}
