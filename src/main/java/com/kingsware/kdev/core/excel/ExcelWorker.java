package com.kingsware.kdev.core.excel;

import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.encrypt.EncryptProperties;
import com.kingsware.kdev.core.encrypt.EncryptWorker;
import com.kingsware.kdev.core.encrypt.inst.AESInstance;
import com.kingsware.kdev.core.encrypt.inst.Base64Instance;
import com.kingsware.kdev.core.encrypt.inst.MD5Instance;
import com.kingsware.kdev.core.excel.handler.JxlExcelHandler;
import com.kingsware.kdev.core.excel.handler.KExcelHandler;

import java.util.Objects;

/**
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/5 6:12 下午
 */
public class ExcelWorker {

    /** 单实例 **/
    private static ExcelWorker INSTANCE;
    /** 处理器 **/
    private KExcelHandler excelHandler;
    /** 配置 **/
    private ExcelProperties properties;

    /**
     * 私有构造
     */
    private ExcelWorker() {}


    /** 获取当前实例 **/
    public static ExcelWorker getInstance() {
        // 如果对象为空，需要对storage进行实例
        if (Objects.isNull(INSTANCE)) {
            synchronized (ExcelWorker.class) {
                // 创建实例
                INSTANCE = new ExcelWorker();
                INSTANCE.properties = SpringContext.getBean(ExcelProperties.class);
                // 创建具体实例
                if ("jxl".equalsIgnoreCase(INSTANCE.properties.getParser())) {
                    INSTANCE.excelHandler = new JxlExcelHandler();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 获取处理器
     * @return  处理器
     */
    public KExcelHandler getHandler() {
        return excelHandler;
    }
}
