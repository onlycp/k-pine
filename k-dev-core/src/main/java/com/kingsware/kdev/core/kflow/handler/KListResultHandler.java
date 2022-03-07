package com.kingsware.kdev.core.kflow.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.kingsware.kdev.core.kflow.FlowUtils;
import com.kingsware.kdev.core.kflow.KFlowConstant;
import com.kingsware.kdev.core.kflow.KFlowContext;
import com.kingsware.kdev.core.kflow.bean.KdbFlowResult;
import com.kingsware.kdev.core.util.JsonUtil;

import java.util.List;
import java.util.Map;

/**
 * 对象处理类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/18 3:17 下午
 */
public class KListResultHandler implements KFlowResultHandler {

    @Override
    public KdbFlowResult parser(String responseBody, KFlowContext context) {
        KdbFlowResult result = new KdbFlowResult();
        result.setType(KFlowConstant.RESULT_JSON);
        // 解析数据
        List<Map<String, Object>> data = FlowUtils.parseList(responseBody);
        JsonNode jsonNode = JsonUtil.toTree(context.getOutArgv());
        result.setData(FlowUtils.processData(data, context, jsonNode));
        // 返回数据
        return result;
    }

    @Override
    public String name() {
        return "list";
    }
}
