package com.kingsware.kdev.core.excel.handler;

import com.kingsware.kdev.core.context.KClientContext;
import com.kingsware.kdev.core.excel.KExcel;
import com.kingsware.kdev.core.excel.KRegion;
import com.kingsware.kdev.core.excel.KSheet;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.CellFormat;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import lombok.extern.slf4j.Slf4j;


import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * JXL Excel处理器
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/5 5:41 下午
 */
@Slf4j
public class JxlExcelHandler implements KExcelHandler{

    @Override
    public void write(KExcel excel, OutputStream out) {
        try {
            // 打开文件
            WritableWorkbook book  =  Workbook.createWorkbook(out);
            // 写sheet
            for (KSheet kSheet: excel.getSheetList()) {
                // 创建sheet（jxl序号是从0开始)）
                WritableSheet sheet  = book.createSheet(kSheet.getName(), kSheet.getIndex());
                // 开始写region
                for (KRegion region: kSheet.getRegions()) {
                    Label label = new Label( region.getStartCell().getColumnIndex()-1 , region.getStartCell().getRowIndex()-1,  region.getValue() !=null ? region.getValue().toString() :"" );
                    sheet.addCell(label);
                    WritableCellFormat cellFormat = new WritableCellFormat();
                    // 判断是否需要合并
                    if (!region.isSingleCell()) {
                        sheet.mergeCells(region.getStartCell().getColumnIndex()-1 , region.getStartCell().getRowIndex()-1, region.getEndCell().getColumnIndex()-1 , region.getEndCell().getRowIndex()-1);
                    }
                }
            }
            // 输出到流
            book.write();
            book.close();
            out.close();
        }
        catch (Exception e) {
            log.info("excel文件写失败，错误原因:{}", e.getMessage());
        }
    }

    @Override
    public void writeToWeb(KExcel excel) {
        try {
            HttpServletResponse response =  KClientContext.getContext().getResponse();
            response.reset();
            response.setContentType("application/octet-stream");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(excel.getFileNme().getBytes(), "ISO8859-1"));
            write(excel, response.getOutputStream());
        }
        catch (Exception e) {
            log.info("excel导出失败，错误原因:{}", e.getMessage());
        }

    }

    @Override
    public List<List<String>> read(int sheetIndex, String filePath) {

        try {
            Workbook workbook = Workbook.getWorkbook(new File(filePath));
            Sheet sheet = workbook.getSheet(sheetIndex);
            // 返回内容
            List<List<String>> allContents = new ArrayList<>();
            // 读取数据
            for (int rowIndex = 0; rowIndex < sheet.getRows(); rowIndex ++) {
                List<String> contents = new ArrayList<>();
                for (int columnIndex = 0; columnIndex < sheet.getColumns(); columnIndex++ ) {
                    contents.add(sheet.getCell(columnIndex, rowIndex).getContents().trim());
                }
                allContents.add(contents);
            }
            return allContents;


        } catch (Exception e) {
            log.info("excel数据读取失败，错误原因:{}", e.getMessage());
        }
        return null;
    }
}
