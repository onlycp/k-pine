package com.kingsware.kdev.core.kflow.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.kingsware.kdev.core.kflow.FlowUtils;
import com.kingsware.kdev.core.kflow.KFlowConstant;
import com.kingsware.kdev.core.kflow.KFlowContext;
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
public class KBase64ToFileResultHandler implements KFlowResultHandler {

    @Override
    public KdbFlowResult parser(String responseBody, KFlowContext context) {


        KdbFlowResult result = new KdbFlowResult();
        result.setType(KFlowConstant.RESULT_FILE);
        int index = responseBody.indexOf("|");
        String fileName = responseBody.substring(0, index);
        String body = responseBody.substring(index+1);
        String retBody = body;
        Map<String, Object> data = JsonUtil.toMap(body);
        if (data == null) {
            body = retBody;
        }
        else  {
            try {
                JsonNode jsonNode = JsonUtil.toTree(context.getOutArgv());
                Object finalBody = FlowUtils.processData(data, context, jsonNode);
                body =  JsonUtil.toJson(finalBody);
            }
            catch (Exception e) {
                body = retBody;
            }
        }
        byte[] resultBody = body.getBytes(StandardCharsets.UTF_8);
        if (Base64Utils.isBase64(body)) {
            resultBody = Base64.getDecoder().decode(body.getBytes(StandardCharsets.UTF_8));
        }
        KdbRetFile kdbRetFile = new KdbRetFile();
        kdbRetFile.setFileName(fileName);
        kdbRetFile.setData(Objects.requireNonNull(resultBody));
        result.setData(kdbRetFile);
        // 返回数据
        return result;
    }

    @Override
    public String name() {
        return "base64ToFile";
    }
}
