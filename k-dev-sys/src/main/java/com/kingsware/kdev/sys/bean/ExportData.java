package com.kingsware.kdev.sys.bean;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 导出数据
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2023/5/10 09:46
 */
@Data
public class ExportData {

    /** 类型 **/
    private String type;
    /** id **/
    private List<String> ids = new ArrayList<>();

    /**
     *
     * @param type
     * @return
     */
    public static ExportData createData(String type) {
        ExportData exportData = new ExportData();
        exportData.setIds(null);
        exportData.setType(type);
        return exportData;
    }

    /**
     *
     * @param type
     * @return
     */
    public static ExportData createData(String type, Set<String> ids) {
        ExportData exportData = new ExportData();
        List<String> resIds = new ArrayList<>();
        resIds.addAll(ids);
        exportData.setIds(resIds);
        exportData.setType(type);
        return exportData;
    }
}
