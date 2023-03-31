package com.kingsware.kdev.sys.argv;

import com.kingsware.kdev.core.bean.BaseArgv;
import com.kingsware.kdev.core.kflow.bean.DebugNode;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 流程调试参数
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/2/23 11:33 上午
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysFlowDebugArgv extends BaseArgv {
    /** 流程id **/
    private String flowId;
    /** json传参 **/
    private String json;
    /** 调试节点列表 **/
    private List<DebugNode> debugger;
}
