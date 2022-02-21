package com.kingsware.kdev.core.kflow.define;

import lombok.Data;

/**
 *  条件定义
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/19 6:18 下午
 */
@Data
public class ConditionDefinition {

    private Decision decision;

    public static ConditionDefinition createDecisionCondition(String expr) {
        Decision d = new Decision();
        d.setExpr(expr);

        ConditionDefinition conditionDefinition = new ConditionDefinition();
        conditionDefinition.setDecision(d);
        return conditionDefinition;
    }

}
