package com.kingsware.kdev.biz.kw.ret;

import com.kingsware.kdev.core.bean.BaseManageRet;
import lombok.Data;

/**
 * 任务信息返回
 *
 * @author amzc
 * @version 1.0.0
 * @date 2022/1/11 15:28
 **/
@Data
public class KwQueueTaskRet extends BaseManageRet {
    /** 任务名称 */
    private String name;
    /** 任务数据 */
    private String data;
    /** 错误信息 **/
    private String error;
    /** 描述信息 **/
    private String description;
    /** 任务类型 **/
    private Integer type;
    /** 任务状态 **/
    private Integer status;
}
