package com.kingsware.kdev.core.excel;
import com.kingsware.kdev.core.util.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Excel处理工具类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/4 3:15 下午
 */
public class KExcel {

    /** 文件名称 **/
    private String fileNme;
    /** 保存当前的所有的sheet **/
    private List<KSheet> sheetList = new ArrayList<>();


    public KExcel(String fileNme) {
        this.fileNme = fileNme;
    }


    public String getFileNme() {
        return fileNme;
    }

    public List<KSheet> getSheetList() {
        return sheetList;
    }

    /**
     * 创建sheet
     * @param name  sheet名称
     * @return  sheet
     */
    public KSheet createSheet(String name) {
        // 先判断一下有没有
        Optional<KSheet> optional = sheetList.stream().filter(it -> it.getName().equals(name)).findFirst();
        if (optional.isPresent()) {
            return optional.get();
        }
        else {
            KSheet sheet = new KSheet();
            sheet.setName(name);
            sheet.setIndex(sheetList.size());
            sheetList.add(sheet);
            return sheet;
        }
    }

    /**
     * 将列表转为表格数据
     * @param defines       列定义
     * @param list         数据
     */
    public static KExcel fromDataList(String fileName, String sheetName ,List<RegionDefine> defines, List<?> list) {
        // 创建表格对象
        KExcel excel = new KExcel(fileName);
        // 创建sheet
        KSheet sheet = excel.createSheet(sheetName);
        // 写入数据
        for (int i = 0; i < defines.size(); i++) {
            RegionDefine define = defines.get(i);
            // 写表头
            sheet.addCellRegion(0, i, define.getLabelName());
            // 写数据
            for (int j = 0; j < list.size(); j++) {
                Object obj = list.get(j);
                Object cellValue = BeanUtils.getFieldValue(define.getPropName(), obj);
                if (cellValue == null) {
                    continue;
                }
                if (define.getFormat() != null) {
                    cellValue = define.getFormat().format(cellValue, obj);
                }
                sheet.addCellRegion(j + 1, i, cellValue);
            }
        }
        return excel;
    }

    /**
     * 将表头转为数据表格
     * @param fileName      文件名
     * @param sheetName    面签名
     * @param defines       表头定义
     * @return              excel
     */
    public static KExcel fromHeaderList(String fileName, String sheetName ,List<RegionDefine> defines) {
        // 创建表格对象
        KExcel excel = new KExcel(fileName);
        // 创建sheet
        KSheet sheet = excel.createSheet(sheetName);
        // 写入数据
        for (int i = 0; i < defines.size(); i++) {
            RegionDefine define = defines.get(i);
            // 写表头
            sheet.addCellRegion(1, i+1, define.getLabelName());
            // 写数据
            sheet.addCellRegion(2, i+1, define.getExample());
        }
        return excel;
    }


}
