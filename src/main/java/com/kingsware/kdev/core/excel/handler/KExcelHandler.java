package com.kingsware.kdev.core.excel.handler;

import com.kingsware.kdev.core.excel.KExcel;

import java.io.OutputStream;

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
     * 输出到web，即直接下载
     * @param excel excel数据
     */
    void writeToWeb(KExcel excel);

}
