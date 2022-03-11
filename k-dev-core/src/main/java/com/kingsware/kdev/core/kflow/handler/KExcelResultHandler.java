package com.kingsware.kdev.core.kflow.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.kingsware.kdev.core.excel.KExcel;
import com.kingsware.kdev.core.excel.KSheet;
import com.kingsware.kdev.core.kflow.FlowUtils;
import com.kingsware.kdev.core.kflow.KFlowConstant;
import com.kingsware.kdev.core.kflow.KFlowContext;
import com.kingsware.kdev.core.kflow.bean.KdbFlowResult;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.core.util.StringUtils;

import java.util.*;

/**
 * 对象处理类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/18 3:17 下午
 */
public class KExcelResultHandler implements KFlowResultHandler {

    @Override
    @SuppressWarnings("unchecked")
    public KdbFlowResult parser(String responseBody, KFlowContext context) {
        KdbFlowResult result = new KdbFlowResult();
        result.setType(KFlowConstant.RESULT_EXCEL);
        int index = responseBody.indexOf("|");
        String fileName = responseBody.substring(0, index);
        String body = responseBody.substring(index+1);

        // 创建k-excel
        KExcel kExcel = new KExcel(fileName);
        // 创建sheet
        KSheet sheet = kExcel.createSheet("sheet1");
        // 遍历获取表头
        int rowIndex = 0;
        int columnIndex = 0;
        // 设置表头
        JsonNode jsonNode = JsonUtil.toTree(context.getOutArgv());
        JsonNode itemsNode = jsonNode.get("items");
        // 获取属性
        JsonNode propertiesNode = itemsNode.get("properties");
        Iterator<String> propertyNames = propertiesNode.fieldNames();
        // 遍历读取
        List<String> columnKeys = new ArrayList<>();
        while (propertyNames.hasNext()) {
            String pName = propertyNames.next();
            JsonNode pNode = propertiesNode.get(pName);
            String title = pNode.get("title").asText();
            sheet.addCellRegion(rowIndex, columnIndex++, title);
            // 获取扩展类型
            String externType = pNode.get("externType").asText();
            // 如果是字典
            if (KFlowConstant.EXTERN_TYPE_DICT.equals(externType)) {
                columnKeys.add(String.format("%s$label", pName));
            }
            else {
                columnKeys.add(pName);
            }
        }

        // 调整行号
        rowIndex = 1;
        // 解析数据
        Object data = FlowUtils.parseList(body);
        // 转换之后的数据
        List<Map<String, Object>> rows = (List<Map<String, Object>>)FlowUtils.processData(data, context, jsonNode);
        // 遍历写到excel
        for (Map<String, Object> rowMap: rows) {
            List<String> rowStrings = new ArrayList<>();
            for (String columnKey: columnKeys) {
                String cellValue = Optional.ofNullable(rowMap.getOrDefault(columnKey, null)).orElse("").toString();
                rowStrings.add(cellValue);
            }
            sheet.addRow(rowIndex++, rowStrings);

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
