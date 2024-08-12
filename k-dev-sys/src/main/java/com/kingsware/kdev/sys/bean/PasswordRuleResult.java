package com.kingsware.kdev.sys.bean;

import lombok.Data;

import java.util.List;

/**
 * 密码校验结果
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2024/7/23 10:55
 */
@Data
public class PasswordRuleResult {

    /**
     * 是否校验通过
     */
    private boolean success = true;
    /**
     * 校验不通过的错误信息
     */
    private List<String> errorMessages;
}
