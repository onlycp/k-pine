package com.kingsware.kdev.core.excel.handler;

import com.kingsware.kdev.core.excel.KExcel;

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

    }

    @Override
    public void writeToWeb(KExcel excel) {

    }

    @Override
    public List<List<String>> read(int sheetIndex, String filePath) {
        return null;
    }
}
