package com.kingsware.kdev.sys.model;

import com.kingsware.kdev.core.bean.BaseManageModel;
import lombok.Data;

/**
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2024/9/3 19:53
 */
@Data
public class DevModelSql extends BaseManageModel {


    private String appId;               // 应用id
    private String title;               // 标题
    private String sourceName;          // 数据源
    private String content;             // 脚本
    private Integer status;             // 执行状态 0: 未执行 1：已执行 2：执行异常
    private Integer sqlVersion;         // 版本号
    private String messages;            // 执行结果
    private Integer ignoreExcept;       // 是否忽略错误
    private Integer execErrLine;        // 错误行号
    private String execTime;            // 执行时间
    private String execUserId;          // 执行人
}
