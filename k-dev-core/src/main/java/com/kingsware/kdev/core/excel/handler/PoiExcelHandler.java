package com.kingsware.kdev.core.excel.handler;

import com.kingsware.kdev.core.excel.KExcel;
import com.kingsware.kdev.core.excel.KRegion;
import com.kingsware.kdev.core.excel.KRegionStyle;
import com.kingsware.kdev.core.excel.KSheet;
import com.kingsware.kdev.core.util.*;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.*;

import static org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND;

/**
 * Poi处理类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/12 4:18 下午
 */
@Slf4j
public class PoiExcelHandler implements KExcelHandler{
    /** 样式缓存 **/
    private final Map<String, XSSFCellStyle> xssfCellStyleMap = new HashMap<>();

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
                sheet.setDefaultColumnWidth(200*255);
                // 设置字体
                CellStyle cellStyle = workbook.createCellStyle();
                Font font = workbook.createFont();
                font.setCharSet(HSSFFont.DEFAULT_CHARSET);
                //更改默认字体大小
                font.setFontHeightInPoints((short) 11);
                font.setFontName("微软雅黑");
                cellStyle.setFont(font);
                CellStyle lastCellStyle = cellStyle;
                // 写入单元格数据
                for (KRegion region: ks.getRegions()) {

                    Row row = sheet.getRow(region.getStartCell().getRowIndex());
                    if (row == null) {
                        row = sheet.createRow(region.getStartCell().getRowIndex());
                    }

                    assert lastCellStyle instanceof XSSFCellStyle;
                    lastCellStyle = createCellStyle(workbook,region.getStyle(),(XSSFCellStyle) lastCellStyle);
                    // 创建单元格
                    Cell cell = row.createCell(region.getStartCell().getColumnIndex());
                    if (region.getValue() != null) {

                        if ("formula".equalsIgnoreCase(region.getType())) {
                            cell.setCellFormula(region.getValue().toString());
                        }

                        else if ("number".equalsIgnoreCase(region.getType()) || region.getValue() instanceof Number) {
                            // 此处设置数据格式
                            DataFormat df = workbook.createDataFormat();
                            if (region.getValue() instanceof Integer) {
                                lastCellStyle.setDataFormat(df.getFormat("0_ "));//数据格式只显示整数
                                cell.setCellValue(new BigDecimal(region.getValue().toString()).intValue());
                            }else{
                                lastCellStyle.setDataFormat(df.getFormat("0.00_ "));//保留两位小数点
                                cell.setCellValue(new BigDecimal(region.getValue().toString()).doubleValue());
                            }

                        }
                        else {
                            cell.setCellValue(region.getValue().toString());
                        }
                    }

                    // 设置格式
                    cell.setCellStyle(lastCellStyle);
                    // 合并单元格
                    if (!region.isSingleCell()) {
                        sheet.addMergedRegion(new CellRangeAddress(region.getStartCell().getRowIndex(), region.getEndCell().getRowIndex()
                                , region.getStartCell().getColumnIndex(), region.getEndCell().getColumnIndex()));
                    }
                }
                // 自适应列宽
                try {

                    int rowNums = sheet.getLastRowNum();
                    int maxColNum = 0;
                    for (int i=0; i< rowNums; i++) {
                        if (maxColNum < sheet.getRow(i).getLastCellNum()) {
                            maxColNum = sheet.getRow(i).getLastCellNum();
                        }
                    }
                    for (short i=0; i< maxColNum; i++) {
                        try {
//                            sheet.setColumnWidth((short) 0, (short) 250);
                            sheet.autoSizeColumn(i);
                        }
                        catch (Exception ignored) {}
                    }
                }
                catch (Exception ignored) {}
            }
            workbook.write(out);
        }
        catch (Exception e) {
            log.error("error", e);
            log.info("excel文件写失败，错误原因:{}", e.getMessage());
        }


    }

    /**
     * 创建样式
     * @param workbook  工作表
     * @param style     样式
     * @return          cell样式
     */
    private CellStyle createCellStyle(Workbook workbook, KRegionStyle style, XSSFCellStyle lastCellStyle ) {
        if (style == null) {
            return lastCellStyle;
        }
        String key = MD5Utils.md5(Objects.requireNonNull(JsonUtil.toJson(style.toString())));
        if (xssfCellStyleMap.containsKey(key)) {
            return xssfCellStyleMap.get(key);
        }
        XSSFCellStyle cellStyle = ((XSSFWorkbook) workbook).createCellStyle();
        try {
            // 背景色
            if (StringUtils.isNotEmpty(style.getBgColor())) {
                java.awt.Color color = ColorUtil.toColorFromString(style.getBgColor());
                XSSFColor xssfColor = new XSSFColor();
                xssfColor.setRGB(new byte[]{(byte) color.getRed(), (byte) color.getGreen(), (byte) color.getBlue()});
                cellStyle.setFillForegroundColor(xssfColor);
                cellStyle.setFillPattern(SOLID_FOREGROUND);


            }
            Font font = workbook.createFont();
            try {
                font.setFontName("宋体");
            }
            catch (Exception ignored) {

            }
            cellStyle.setFont(font);
            // 字体颜色
            if (StringUtils.isNotEmpty(style.getFontColor())) {
                HSSFPalette palette = ((HSSFWorkbook)workbook).getCustomPalette();
                java.awt.Color color = ColorUtil.toColorFromString(style.getBgColor());
                palette.setColorAtIndex(HSSFColor.HSSFColorPredefined.AQUA.getIndex(), (byte) color.getRed(), (byte) color.getGreen(), (byte) color.getBlue());
                font.setColor(HSSFColor.HSSFColorPredefined.AQUA.getIndex());
            }
            // 字体名称
            if (StringUtils.isNotEmpty(style.getFontName())) {
                font.setFontName(style.getFontName());
            }
            // 字体大小
            if (style.getFontSize() != null) {
                font.setFontHeightInPoints(style.getFontSize().shortValue());
            }
            if (style.isBold()) {
                font.setBold(true);
            }
            if (style.getH() != null) {
                if (style.getH() == 0) {
                    cellStyle.setAlignment(HorizontalAlignment.LEFT);
                }
                else if (style.getH() == 1) {
                    cellStyle.setAlignment(HorizontalAlignment.CENTER);
                }
                else if (style.getH() == 2) {
                    cellStyle.setAlignment(HorizontalAlignment.RIGHT);
                }

            }
            if (style.getV() != null) {
                if (style.getV() == 0) {
                    cellStyle.setVerticalAlignment(VerticalAlignment.TOP);
                }
                else if (style.getV() == 1) {
                    cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                }
                else if (style.getV() == 2) {
                    cellStyle.setVerticalAlignment(VerticalAlignment.BOTTOM);
                }

            }
        }
        catch (Exception ignored) {
            log.warn("样式设置警告:" + ignored.getMessage());
        }
        // 缓存样式, 避免创建更多cellStyle导致报错
        xssfCellStyleMap.put(key, cellStyle);
        return cellStyle;

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
