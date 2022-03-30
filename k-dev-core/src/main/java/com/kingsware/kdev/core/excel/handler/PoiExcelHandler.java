package com.kingsware.kdev.core.excel.handler;

import ch.qos.logback.core.rolling.helper.FileStoreUtil;
import com.kingsware.kdev.core.excel.KExcel;
import com.kingsware.kdev.core.excel.KRegion;
import com.kingsware.kdev.core.excel.KSheet;
import com.kingsware.kdev.core.util.ExceptionUtils;
import com.kingsware.kdev.core.util.RandomUtils;
import com.kingsware.kdev.core.util.StringUtils;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Poi处理类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/12 4:18 下午
 */
@Slf4j
public class PoiExcelHandler implements KExcelHandler{
    @Override
    public void write(KExcel excel, OutputStream out) {

        // 创建workbook
        try {
            @Cleanup Workbook workbook = null;
            if (excel.getFileNme().endsWith(".xls")) {
                workbook = new HSSFWorkbook();
            } else {
                workbook = new XSSFWorkbook();
            }
            for (KSheet ks: excel.getSheetList()) {
                // 生成一个表格
                Sheet sheet = workbook.createSheet(ks.getName());
                // 设置字体
                CellStyle cellStyle = workbook.createCellStyle();
                Font font = workbook.createFont();
                font.setCharSet(HSSFFont.DEFAULT_CHARSET);
                //更改默认字体大小
                font.setFontHeightInPoints((short) 11);
                font.setFontName("微软雅黑");
                cellStyle.setFont(font);
                // 写入单元格数据
                for (KRegion region: ks.getRegions()) {

                    Row row = sheet.getRow(region.getStartCell().getRowIndex());
                    if (row == null) {
                        row = sheet.createRow(region.getStartCell().getRowIndex());
                    }
                    // 创建单元格
                    Cell cell = row.createCell(region.getStartCell().getColumnIndex());
                    cell.setCellValue(region.getValue() == null ? "" : region.getValue().toString());
                    // 设置格式
                    cell.setCellStyle(cellStyle);
                    // 合并单元格
                    if (!region.isSingleCell()) {
                        sheet.addMergedRegion(new CellRangeAddress(region.getStartCell().getRowIndex(), region.getEndCell().getRowIndex()
                                , region.getStartCell().getColumnIndex(), region.getEndCell().getColumnIndex()));
                    }
                }
                // 自适应列宽
                short columnNum = sheet.getRow(0).getLastCellNum();
                for (short i=0; i< columnNum; i++) {
                    sheet.autoSizeColumn(i);
                }
            }
            workbook.write(out);
        }
        catch (Exception e) {
            log.info("excel文件写失败，错误原因:{}", e.getMessage());
        }


    }

    private List<List<String>> readRetry(int sheetIndex, String filePath) throws Exception {
        // 创建工作表
        Workbook workbook = null;
        File file = null;
        try {
            file = File.createTempFile("excel" +System.currentTimeMillis() + RandomUtils.randomNumeric(4), "");
            FileCopyUtils.copy(new File(filePath), file);
            workbook = WorkbookFactory.create(file, "", true);
            //设置格式
            CellStyle style1 = workbook.createCellStyle();
            // 获取sheet
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            // 返回内容
            List<List<String>> allContents = new ArrayList<>();
            // 读取数据
            int rowCount = sheet.getLastRowNum() + 1;
            int colCount = 0;
            for (int rowIndex = 0; rowIndex < rowCount; rowIndex ++) {

                List<String> contents = new ArrayList<>();
                Row row = sheet.getRow(rowIndex);
                if (row == null) {
                    continue;
                }
                if (rowIndex == 0) {
                    colCount = row.getLastCellNum();
                }
                for (int columnIndex = 0; columnIndex < colCount; columnIndex++ ) {
                    if (columnIndex >= row.getLastCellNum()) {
                        contents.add("");
                    }
                    else {
                        Cell cell = row.getCell(columnIndex);
                        if (cell == null) {
                            contents.add("");
                        }
                        else {
                            cell.setCellType(CellType.STRING);
                            if (rowIndex == 0) {
                                if (StringUtils.isNotEmpty(cell.getStringCellValue().trim())) {
                                    contents.add(cell.getStringCellValue().trim());
                                }
                            }
                            else  {
                                contents.add(cell.getStringCellValue()== null ? "": cell.getStringCellValue().trim());
                            }
                        }
                    }
                }
                if (rowIndex == 0) {
                    colCount = contents.size();
                }
                boolean isAllEmpty = true;
                for (String content: contents) {
                    if (StringUtils.isNotEmpty(content)) {
                        isAllEmpty = false;
                        break;
                    }
                }
                if (!isAllEmpty) {
                    allContents.add(contents);
                }

            }
            return allContents;
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            if (workbook != null) {
                workbook.close();
            }
            if (file != null) {
                try {
                    Files.deleteIfExists(file.toPath());
                }
                catch (Exception e) {

                }
            }
        }
    }

    @Override
    public List<List<String>> read(int sheetIndex, String filePath) throws Exception {
        int i = 0;
        while (i < 10) {
            try {
                return readRetry(sheetIndex, filePath);
            }
            catch (Exception e) {
                String stackMessage = ExceptionUtils.getStackTrace(e);
                // 为了应对现场windows服务器，WorkbookFactory.create发生异常的问题
                if (!stackMessage.contains("WorkbookFactory.create")) {
                    throw e;
                }
                log.info("Excel WorkbookFactory.create()异常，将继续重试, 当前读取次数; {}", i);
            }
            finally {
                i++;
            }

        }
        return null;
    }

}
