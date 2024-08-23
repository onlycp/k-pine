package com.kingsware.kdev.core.kflow.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.excel.KExcel;
import com.kingsware.kdev.core.excel.KRegion;
import com.kingsware.kdev.core.excel.KRegionStyle;
import com.kingsware.kdev.core.excel.KSheet;
import com.kingsware.kdev.core.kflow.FlowUtils;
import com.kingsware.kdev.core.kflow.KFlowConstant;
import com.kingsware.kdev.core.kflow.KFlowContext;
import com.kingsware.kdev.core.kflow.bean.KdbFlowResult;
import com.kingsware.kdev.core.util.DateUtils;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.core.util.ServletUtil;

import java.util.*;

/**
 * 对象处理类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/18 3:17 下午
 */
public class KExcelExResultHandler implements KFlowResultHandler {

    @Override
    @SuppressWarnings("unchecked")
    public KdbFlowResult parser(String responseBody, KFlowContext context) {
        KdbFlowResult result = new KdbFlowResult();
        result.setType(KFlowConstant.RESULT_EXCEL);
        String body = responseBody.trim();
        Map<String, Object> map = JsonUtil.toMap(body);
        if (map == null) {
            return null;
        }
        // 获取文件名
        String fileName = map.getOrDefault("name", "Excel—" + DateUtils.DATE_TIME_1).toString() ;
        if (!fileName.contains(".xls")) {
            fileName = fileName + ".xlsx";
        }

        // 创建k-excel
        KExcel kExcel = new KExcel(fileName);
        // sheets
        List<Map<String, Object>> sheets = (List<Map<String, Object>> )map.getOrDefault("sheets", new ArrayList<Map<String, Object>>());
        // 处理sheet
        for(Map<String, Object> sheetMap: sheets) {
            String sheetName = sheetMap.get("name").toString();
            KSheet kSheet = kExcel.createSheet(sheetName);
            if (sheetMap.containsKey("autoColumnSize")) {
                kSheet.setAutoColumnSize((Boolean)sheetMap.get("autoColumnSize"));
            }
            Integer sheetIndex = Integer.parseInt(sheetMap.get("index").toString());
            List<Map<String, Object>> regions = (List<Map<String, Object>>)sheetMap.get("regions");
            // 遍历regions
            for (Map<String, Object> regionMap: regions) {
                int x1 = Integer.parseInt(regionMap.get("x1").toString());
                int y1 = Integer.parseInt(regionMap.get("y1").toString());
                KRegionStyle style = null;
                String type = null;
                if (regionMap.containsKey("style")) {
                    style = JsonUtil.mapToBean((Map<String, Object>)regionMap.get("style"), KRegionStyle.class);
                }
                if (regionMap.containsKey("type")) {
                    type = regionMap.get("type").toString();
                }
                Object value = regionMap.get("value");
                KRegion region = null;
                if (regionMap.containsKey("x2") && regionMap.containsKey("y2")) {
                    int x2 = Integer.parseInt(regionMap.get("x2").toString());
                    int y2 = Integer.parseInt(regionMap.get("y2").toString());
                    region = kSheet.addRegion(x1, y1, x2, y2, value, style);
                }
                else {
                    region = kSheet.addCellRegion(x1, y1, value, style);
                }
                region.setType(type);
                if ("image".equals(region.getType())) {
                    region.setValue("http://127.0.0.1:" + SpringContext.getProperties("server.port", "8080") + ServletUtil.getContextPath()  + "/api/v1/sys-files/download/" + region.getValue());
                }
                // 判断是否有items
                if (regionMap.containsKey("items")) {
                    Object items = regionMap.get("items");
                    if (items instanceof Iterable) {
                        Iterable it = (Iterable)items;
                        KRegion finalRegion = region;
                        it.forEach(ite -> finalRegion.getItems().add(ite.toString()));
                    }
                }
            }
        }
        result.setData(kExcel);
        // 返回数据
        return result;
    }

    @Override
    public String name() {
        return "kexcel";
    }
}
