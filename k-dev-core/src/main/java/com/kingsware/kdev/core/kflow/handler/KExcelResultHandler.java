package com.kingsware.kdev.core.kflow.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.kingsware.kdev.core.excel.KExcel;
import com.kingsware.kdev.core.excel.KSheet;
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
public class KExcelResultHandler implements KFlowResultHandler {

    @Override
    public KdbFlowResult parser(String responseBody, KFlowContext context) {
        KdbFlowResult result = new KdbFlowResult();
        result.setType(KFlowConstant.RESULT_EXCEL);
        int index = responseBody.indexOf("|");
        String fileName = responseBody.substring(0, index);
        String body = responseBody.substring(index+1);
        // 解析数据
        List<Map<String, Object>> data = FlowUtils.parseList(body);
        Object rows = FlowUtils.processData(data, context);
        // 创建k-excel
        KExcel kExcel = new KExcel(fileName);
        // 创建sheet
        KSheet sheet = kExcel.createSheet("sheet1");
//        // 设置表头
//        Map<String, Object> rootMap = JsonUtil.toMap(context.getOutArgv());
//        // 获取item
//        Map<String, Object> itemMap = (Map<String, Object>)rootMap.get("items");
//        // 获取属性
//        Map<String, Object> propertiesMap = (Map<String, Object>)itemMap.get("properties");
//        // 遍历获取表头
//        int rowIndex = 0;
//        int columnIndex = 0;
//        for (Map.Entry<String, Object> entry: propertiesMap.entrySet()) {
//            Map<String, Object> propertyMap = (Map<String, Object>)entry.getValue();
//            sheet.addCellRegion(rowIndex, columnIndex++, propertyMap.get("title"));
//        }
        // 设置表头
        JsonNode jsonNode = JsonUtil.toTree(context.getOutArgv());
        JsonNode itemsNode = jsonNode.get("items");
        // 获取属性
        JsonNode propertiesNode = itemsNode.get("properties");
        // 遍历获取表头
        int rowIndex = 0;
        int columnIndex = 0;
        while (propertiesNode.elements().hasNext()) {
            JsonNode pNode = propertiesNode.elements().next();
            sheet.addCellRegion(rowIndex, columnIndex++, pNode.get("title").asText());
        }

        result.setData(kExcel);
        // 返回数据
        return result;
    }

    @Override
    public String name() {
        return "excel";
    }
}
