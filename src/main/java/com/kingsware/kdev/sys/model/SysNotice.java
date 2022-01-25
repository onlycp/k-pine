package com.kingsware.kdev.sys.model;

import com.kingsware.kdev.core.bean.BaseManageModel;
import com.kingsware.kdev.core.orm.annotation.LogicDelete;
import com.kingsware.kdev.core.orm.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @version 1.0.0
 * @auther: crb
 * @date: 2022/01/20/11:12
 * @description: 消息管理
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table
@LogicDelete
public class SysNotice extends BaseManageModel {
    /** 通知标题 */
    private String title;
    /** 通知内容 */
    private String content;
    /** 通知类型，1：系统维护通知，2：公告 */
    private Integer type;
    /** 启用状态：0：待启用，1：已启用 */
    private Integer status;
}
