package com.kingsware.kdev.core.excel;

import java.io.OutputStream;

/**
 * Excel写入
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/4 6:31 下午
 */
public interface KExcelWriter {

    /**
     * 生成并输出到流
     * @param excel excel数据
     */
    OutputStream write(KExcel excel);

}
