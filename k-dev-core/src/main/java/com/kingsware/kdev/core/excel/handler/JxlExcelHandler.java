//package com.kingsware.kdev.core.excel.handler;
//
//import com.kingsware.kdev.core.excel.KExcel;
//import com.kingsware.kdev.core.excel.KRegion;
//import com.kingsware.kdev.core.excel.KSheet;
//import jxl.Sheet;
//import jxl.Workbook;
//import jxl.write.Label;
//import jxl.write.WritableCellFormat;
//import jxl.write.WritableSheet;
//import jxl.write.WritableWorkbook;
//import lombok.Cleanup;
//import lombok.extern.slf4j.Slf4j;
//
//import java.io.File;
//import java.io.OutputStream;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * JXL Excel处理器
// *
// * @author chen peng
// * @version 1.0.0
// * @date 2022/1/5 5:41 下午
// */
//@Slf4j
//public class JxlExcelHandler implements KExcelHandler{
//
//    @Override
//    public void write(KExcel excel, OutputStream out) {
//        try {
//            // 打开文件
//            @Cleanup WritableWorkbook book  =  Workbook.createWorkbook(out);
//            // 写sheet
//            for (KSheet kSheet: excel.getSheetList()) {
//                // 创建sheet（jxl序号是从0开始)）
//                WritableSheet sheet  = book.createSheet(kSheet.getName(), kSheet.getIndex());
//                // 开始写region
//                for (KRegion region: kSheet.getRegions()) {
//                    Label label = new Label( region.getStartCell().getColumnIndex() , region.getStartCell().getRowIndex(),  region.getValue() !=null ? region.getValue().toString() :"" );
//                    sheet.addCell(label);
//                    WritableCellFormat cellFormat = new WritableCellFormat();
//                    // 判断是否需要合并
//                    if (!region.isSingleCell()) {
//                        sheet.mergeCells(region.getStartCell().getColumnIndex() , region.getStartCell().getRowIndex(), region.getEndCell().getColumnIndex()-1 , region.getEndCell().getRowIndex()-1);
//                    }
//                }
//            }
//            // 输出到流
//            book.write();
//            book.close();
//            out.close();
//        }
//        catch (Exception e) {
//            log.info("excel文件写失败，错误原因:{}", e.getMessage());
//        }
//    }
//
//
//    @Override
//    public List<List<String>> read(int sheetIndex, String filePath)  throws Exception{
//
//        @Cleanup Workbook workbook = Workbook.getWorkbook(new File(filePath));
//        Sheet sheet = workbook.getSheet(sheetIndex);
//        // 返回内容
//        List<List<String>> allContents = new ArrayList<>();
//        // 读取数据
//        for (int rowIndex = 0; rowIndex < sheet.getRows(); rowIndex ++) {
//            List<String> contents = new ArrayList<>();
//            for (int columnIndex = 0; columnIndex < sheet.getColumns(); columnIndex++ ) {
//                contents.add(sheet.getCell(columnIndex, rowIndex).getContents().trim());
//            }
//            allContents.add(contents);
//        }
//        return allContents;
//    }
//}
