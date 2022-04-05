package com.kingsware.kdev.sys.argv;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: crb
 * @version: 1.0.0
 * @date: 2022/01/20/11:33
 * @description:
 */
@Data
@EqualsAndHashCode
public class SysNoticeArgv {
    /** 通知id */
    private String id;
    /** 通知标题 */
    private String title;
    /** 通知内容 */
    private String content;
    /** 通知类型，1：系统维护通知，2：公告 */
    private Integer type;
    /** 启用状态：0：待启用，1：已启用 */
    private Integer status;
    /** 所属应用ID **/
    private String appId;
}
