package com.kingsware.kdev.sys.ret;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kingsware.kdev.core.bean.BaseSimpleRet;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 新增流程实体
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/5 3:06 下午
 */
@Data
public class SysKdbFlowRet extends BaseSimpleRet {
    /** 流程id **/
    private String id;
    /** 流程内容 **/
    private String content;
    /** 名称 **/
    private String name;
    /** 描述 **/
    private String description = "";
    /** 创建时间 **/
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Timestamp whenCreated;
    /** 更新时间 **/
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Timestamp whenModified;
}
