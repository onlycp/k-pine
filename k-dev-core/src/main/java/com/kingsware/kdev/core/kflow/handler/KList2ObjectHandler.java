package com.kingsware.kdev.core.kflow.handler;

import com.kingsware.kdev.core.kflow.FlowUtils;
import com.kingsware.kdev.core.kflow.KFlowConstant;
import com.kingsware.kdev.core.kflow.KFlowContext;
import com.kingsware.kdev.core.kflow.bean.KdbFlowResult;

import java.util.Map;

/**
 * 对象处理类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/18 3:17 下午
 */
public class KList2ObjectHandler implements KFlowResultHandler {

    @Override
    public KdbFlowResult parser(String responseBody, KFlowContext context) {
        KdbFlowResult result = new KdbFlowResult();
        result.setType(KFlowConstant.RESULT_JSON);
        // 解析数据
        Map<String, Object> data = FlowUtils.parseList2Object(responseBody);
        result.setData(FlowUtils.processData(data, context));
        // 返回数据
        return result;
    }

    @Override
    public String name() {
        return "list2object";
    }
}
