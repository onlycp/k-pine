package com.kingsware.kdev.core.kflow.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.kingsware.kdev.core.kflow.FlowUtils;
import com.kingsware.kdev.core.kflow.KFlowConstant;
import com.kingsware.kdev.core.kflow.KFlowContext;
import com.kingsware.kdev.core.kflow.bean.KdbCustomResource;
import com.kingsware.kdev.core.kflow.bean.KdbFlowResult;
import com.kingsware.kdev.core.kflow.bean.KdbRetFile;
import com.kingsware.kdev.core.util.Base64Utils;
import com.kingsware.kdev.core.util.JsonUtil;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import java.util.Objects;

/**
 * 对象处理类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/18 3:17 下午
 */
public class KCustomResponseResultHandler implements KFlowResultHandler {

    @Override
    public KdbFlowResult parser(String responseBody, KFlowContext context) {
        KdbFlowResult result = new KdbFlowResult();
        result.setType(KFlowConstant.RESULT_CUSTOM);

        Map<String, Object> data = JsonUtil.toMap(responseBody);
        KdbCustomResource customResource = new KdbCustomResource();
        customResource.setData(data.getOrDefault("data", "").toString().getBytes(StandardCharsets.UTF_8));
        customResource.setCharacterEncoding(data.getOrDefault("characterEncoding", "UTF-8").toString());
        customResource.setContentType(data.getOrDefault("contentType", "text/html").toString());
        result.setData(customResource);
        // 返回数据
        return result;
    }

    @Override
    public String name() {
        return "custom";
    }
}
