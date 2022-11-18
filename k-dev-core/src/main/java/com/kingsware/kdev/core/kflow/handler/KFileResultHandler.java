package com.kingsware.kdev.core.kflow.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.kingsware.kdev.core.excel.KExcel;
import com.kingsware.kdev.core.excel.KSheet;
import com.kingsware.kdev.core.kflow.FlowUtils;
import com.kingsware.kdev.core.kflow.KFlowConstant;
import com.kingsware.kdev.core.kflow.KFlowContext;
import com.kingsware.kdev.core.kflow.bean.KdbFlowResult;
import com.kingsware.kdev.core.kflow.bean.KdbRetFile;
import com.kingsware.kdev.core.util.JsonUtil;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 对象处理类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/18 3:17 下午
 */
public class KFileResultHandler implements KFlowResultHandler {

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
        KdbRetFile kdbRetFile = new KdbRetFile();
        kdbRetFile.setFileName(fileName);
        kdbRetFile.setData(Objects.requireNonNull(body.getBytes(StandardCharsets.UTF_8)));
        result.setData(kdbRetFile);
        // 返回数据
        return result;
    }

    @Override
    public String name() {
        return "file";
    }
}
