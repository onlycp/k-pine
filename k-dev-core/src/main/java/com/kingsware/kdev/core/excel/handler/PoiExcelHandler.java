package com.kingsware.kdev.core.excel.handler;

import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.excel.KExcel;
import com.kingsware.kdev.core.excel.KRegion;
import com.kingsware.kdev.core.excel.KRegionStyle;
import com.kingsware.kdev.core.excel.KSheet;
import com.kingsware.kdev.core.plugins.file.FileEncryptPlugin;
import com.kingsware.kdev.core.util.*;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.util.FileCopyUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    /**
     * 画图的顶级管理器
     */
    private XSSFDrawing drawingPatriarch;

    @Override
    public void write(KExcel excel, OutputStream out) {

        // 创建workbook
        try {
            xssfCellStyleMap.clear();
            @Cleanup XSSFWorkbook workbook = new XSSFWorkbook();

            for (KSheet ks: excel.getSheetList()) {
                // 生成一个表格
                Sheet sheet = workbook.createSheet(ks.getName());
                sheet.setDefaultColumnWidth(200*255);
                this.drawingPatriarch = (XSSFDrawing) sheet.createDrawingPatriarch();
                // 设置字体
                XSSFCellStyle cellStyle = workbook.createCellStyle();
                Font font = workbook.createFont();
                font.setCharSet(HSSFFont.DEFAULT_CHARSET);
                //更改默认字体大小
                font.setFontHeightInPoints((short) 10);
                font.setFontName("微软雅黑");
                cellStyle.setFont(font);
                XSSFCellStyle lastCellStyle = cellStyle;
                // 写入单元格数据
                for (KRegion region: ks.getRegions()) {

                    Row row = sheet.getRow(region.getStartCell().getRowIndex());
                    if (row == null) {
                        row = sheet.createRow(region.getStartCell().getRowIndex());
                    }
                    String dataType = region.getType();
                    if (region.getValue() != null && "number".equalsIgnoreCase(region.getType()) || region.getValue() instanceof Number) {
                        // 此处设置数据格式
                        DataFormat df = workbook.createDataFormat();
                        if (region.getValue() instanceof Integer) {
                            dataType = "integer";
                        }else{
                           dataType = "number";
                        }

                    }

                    lastCellStyle = createCellStyle(workbook,region.getStyle(), dataType);
                    // 创建单元格
                    Cell cell = row.createCell(region.getStartCell().getColumnIndex());
                    if (region.getValue() != null) {

                        if ("formula".equalsIgnoreCase(region.getType())) {
                            DataFormat df = workbook.createDataFormat();
                            lastCellStyle.setDataFormat(df.getFormat("0.00_ "));//保留两位小数点
                            cell.setCellFormula(region.getValue().toString());
                        }
                        else if ("image".equals(region.getType())) {
                            try {
                                URL url = new URL("http://127.0.0.1:" + SpringContext.getProperties("server.port", "8080") +"/api/v1/sys-files/download/" + region.getValue());
                                BufferedImage bufferImg = null;
                                bufferImg = ImageIO.read(url);
                                @Cleanup ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
                                ImageIO.write(bufferImg, "png", byteArrayOut);
                                XSSFClientAnchor anchor = new XSSFClientAnchor(
                                        10000*10,
                                        10000*10,
                                        -10000*10,
                                        -10000*10,
                                        region.getStartCell().getColumnIndex(),
                                        region.getStartCell().getRowIndex(),
                                        region.getEndCell().getColumnIndex()+1,
                                        region.getEndCell().getRowIndex()+1
                                );
                                anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_AND_RESIZE);
                                XSSFPicture picture = drawingPatriarch.createPicture(anchor, workbook.addPicture(byteArrayOut.toByteArray(), XSSFWorkbook.PICTURE_TYPE_JPEG));
                                picture.setFillColor(0,0,0 );

                            }
                            catch (Exception e) {
                                log.warn("error", e);
                            }

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
                    // 设置校验
                    if (!region.getItems().isEmpty()) {
                        DataValidationHelper dvHelper = sheet.getDataValidationHelper();
                        DataValidationConstraint dvConstraint = dvHelper.createExplicitListConstraint(region.getItems().toArray(new String[0]));
                        CellRangeAddressList addressList = new CellRangeAddressList(region.getStartCell().getRowIndex(), region.getEndCell().getRowIndex(), region.getStartCell().getColumnIndex(), region.getEndCell().getColumnIndex());
                        DataValidation validation = dvHelper.createValidation(dvConstraint, addressList);
                        //这两行设置单元格只能是列表中的内容，否则报错
                        validation.setSuppressDropDownArrow(true);
                        validation.setShowErrorBox(true);
                        sheet.addValidationData(validation);
                    }
                    // 设置格式
                    try {
                        if (region.getStyle().getScale() != null && region.getStyle().getScale() >0) {
                            DataFormat df = workbook.createDataFormat();
                            StringBuilder sb = new StringBuilder();
                            for (int i=0; i<region.getStyle().getScale(); i++) {
                                sb.append("0");
                            }
                            lastCellStyle.setDataFormat(df.getFormat(String.format("0.%s_ ", sb)));
                        }
                        cell.setCellStyle(lastCellStyle);

                        if (region.getStyle().getWidth() != null) {
                            sheet.setColumnWidth(cell.getColumnIndex(), region.getStyle().getWidth() * 256);
                            if ("image".equals(region.getType())) {
                                cell.getRow().setHeight((short)(region.getStyle().getWidth() * 100));
                            }
                        }

                    }
                    catch (Exception e) {
                        log.warn("warn", e);
                    }

                    // 合并单元格
                    if (!region.isSingleCell()) {
                        sheet.addMergedRegion(new CellRangeAddress(region.getStartCell().getRowIndex(), region.getEndCell().getRowIndex()
                                , region.getStartCell().getColumnIndex(), region.getEndCell().getColumnIndex()));
                    }
                }
                if (ks.isAutoColumnSize()) {
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

            }

            // 获取文件插件
            String realFileName = excel.getFileNme();
            String[] arr = realFileName.split("\\.");
            if (arr.length > 1 ) {
                String encryptMode = arr[arr.length-1];
                // 获取加密插件
                FileEncryptPlugin fileEncryptPlugin = getFileEncryptPlugin(encryptMode);
                if (fileEncryptPlugin != null) {
                    String tempDir = "temp";
                    if (!Files.exists(new File("temp").toPath())) {
                        new File("temp").mkdirs();
                    }
                    String tempFile = tempDir + File.separator + realFileName;
                    Path path = Paths.get(tempFile);
                    Files.deleteIfExists(new File(tempFile).toPath());
                    workbook.write(Files.newOutputStream(path));
                    File encryptFile = fileEncryptPlugin.encrypt(path.toFile());
                    // 设置新的文件
                    out.write(Files.readAllBytes(encryptFile.toPath()));
                }
                else {
                    workbook.write(out);
                }
            }
            else {
                workbook.write(out);
            }


            //
        }
        catch (Exception e) {
            log.error("error", e);
            log.info("excel文件写失败，错误原因:{}", e.getMessage());
        }


    }

    /**
     * 获取通道
     * @return  通道
     */
    public static FileEncryptPlugin getFileEncryptPlugin(String name) {
        List<FileEncryptPlugin> plugins = SpringContext.getBeansOfType(FileEncryptPlugin.class);
        for (FileEncryptPlugin plugin: plugins) {
            if (name.equalsIgnoreCase(plugin.name())) {
                return plugin;
            }
        }
        return null;
    }

    /**
     * 创建样式
     * @param workbook  工作表
     * @param style     样式
     * @return          cell样式
     */
    private XSSFCellStyle createCellStyle(XSSFWorkbook workbook, KRegionStyle style, String dataType ) {
        if (style == null) {
            String key = "empty";
            if (xssfCellStyleMap.containsKey(key)) {
                return xssfCellStyleMap.get(key);
            }
            XSSFCellStyle cellStyle = workbook.createCellStyle();
            xssfCellStyleMap.put(key, cellStyle);
            return cellStyle;
        }
        String key = MD5Utils.md5(Objects.requireNonNull(JsonUtil.toJson(style.toString())) + dataType);
        if (xssfCellStyleMap.containsKey(key)) {
            return xssfCellStyleMap.get(key);
        }
        XSSFCellStyle cellStyle = workbook.createCellStyle();
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
                font.setFontName("等线");
            }
            catch (Exception ignored) {

            }
            cellStyle.setFont(font);
            // 字体颜色
            if (StringUtils.isNotEmpty(style.getFontColor())) {
                java.awt.Color color = ColorUtil.toColorFromString(style.getFontColor());
                XSSFColor xssfColor = new XSSFColor();
                xssfColor.setRGB(new byte[]{(byte) color.getRed(), (byte) color.getGreen(), (byte) color.getBlue()});
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
            // 换行处理
            if (style.isWrapText()) {
                cellStyle.setWrapText(style.isWrapText());
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
