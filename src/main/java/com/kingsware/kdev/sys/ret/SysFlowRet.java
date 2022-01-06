package com.kingsware.kdev.sys.ret;

import com.kingsware.kdev.core.bean.BaseSimpleRet;
import lombok.Data;

/**
 * 新增流程实体
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/5 3:06 下午
 */
@Data
public class SysFlowRet extends BaseSimpleRet {
    /** 流程id **/
    private String flowID;
    /** 流程内容 **/
    private String content;
}
