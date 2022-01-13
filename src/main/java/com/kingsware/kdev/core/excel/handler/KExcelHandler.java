package com.kingsware.kdev.core.excel.handler;

import com.kingsware.kdev.core.excel.KExcel;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * Excel写入
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/4 6:31 下午
 */
public interface KExcelHandler {

    /**
     * 生成并输出到流
     * @param excel excel数据
     */
    void write(KExcel excel, OutputStream out);


    /**
     * 从文件里读取数据
     * @param filePath      文件路径
     * @return              读取的数据
     */
    List<List<String>> read(int sheetIndex, String filePath) throws Exception ;

}
