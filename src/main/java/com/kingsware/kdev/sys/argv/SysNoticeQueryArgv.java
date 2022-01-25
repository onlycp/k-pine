package com.kingsware.kdev.sys.argv;

import com.kingsware.kdev.core.bean.BasePageArgv;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: crb
 * @version: 1.0.0
 * @date: 2022/01/20/11:33
 * @description:
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysNoticeQueryArgv extends BasePageArgv {
    /** 通知标题 */
    private String title;
    /** 通知内容 */
    private String content;
    /** 通知类型，1：系统维护通知，2：公告 */
    private Integer type;
}
