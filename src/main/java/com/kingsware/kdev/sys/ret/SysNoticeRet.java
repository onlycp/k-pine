package com.kingsware.kdev.sys.ret;

import com.kingsware.kdev.core.bean.BaseManageRet;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @version 1.0.0
 * @auther: crb
 * @date: 2022/01/20/11:28
 * @description:
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysNoticeRet extends BaseManageRet {
    /** 通知标题 */
    private String title;
    /** 通知内容 */
    private String content;
    /** 通知类型，1：系统维护通知，2：公告 */
    private Integer type;
    /** 启用状态：0：待启用，1：已启用 */
    private Integer status;
    /** 逻辑删除，0：未删除，1：已删除 */
    private Integer deleted;
    /** 发送数量*/
    private String sends;
}
