package com.kingsware.kdev.core.excel.handler;

import com.kingsware.kdev.core.excel.KExcel;
import com.kingsware.kdev.core.excel.KRegion;
import com.kingsware.kdev.core.excel.KSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.OutputStream;
import java.util.List;

/**
 * Poi处理类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/12 4:18 下午
 */
public class PoiExcelHandler implements KExcelHandler{
    @Override
    public void write(KExcel excel, OutputStream out) {
        // 创建workbook
        Workbook workbook = null;
        if (excel.getFileNme().endsWith(".xls")) {
            workbook = new HSSFWorkbook();
        } else {
            workbook = new XSSFWorkbook();
        }
        for (KSheet ks: excel.getSheetList()) {
            // 生成一个表格
            Sheet sheet = workbook.createSheet(ks.getName());
            // 写入单元格数据
            for (KRegion region: ks.getRegions()) {
                Row row = sheet.getRow(region.getStartCell().getRowIndex());
                if (row == null) {
                    row = sheet.createRow(region.getStartCell().getRowIndex());
                }
            }
        }
    }


    @Override
    public void writeToWeb(KExcel excel) {

    }

    @Override
    public List<List<String>> read(int sheetIndex, String filePath) {
        return null;
    }

    /**
     * 写excel xls 文件
     * @param excel excel定义
     * @param out  输出流
     */
    private void writeXls(KExcel excel, OutputStream out) {

    }

    private Workbook getWorkbook(KExcel excel, OutputStream out) {
        return null;
    }
}
