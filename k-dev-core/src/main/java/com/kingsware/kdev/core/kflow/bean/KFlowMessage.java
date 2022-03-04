package com.kingsware.kdev.core.kflow.bean;

import lombok.Data;

/**
 * 消息拆分
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/3/3 4:22 下午
 */
@Data
public class KFlowMessage {
    /** 处理器名称 **/
    private String handlerName;
    /** 数据 **/
    private String data;
}
