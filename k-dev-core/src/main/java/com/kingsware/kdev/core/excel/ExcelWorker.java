package com.kingsware.kdev.core.excel;

import com.kingsware.kdev.core.context.KClientContext;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.excel.handler.JxlExcelHandler;
import com.kingsware.kdev.core.excel.handler.KExcelHandler;
import com.kingsware.kdev.core.excel.handler.PoiExcelHandler;
import com.kingsware.kdev.core.util.BeanUtils;
import com.kingsware.kdev.core.util.StringUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.util.UriEncoder;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/5 6:12 下午
 */
@Slf4j
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
                else if ("poi".equalsIgnoreCase(INSTANCE.properties.getParser())) {
                    INSTANCE.excelHandler = new PoiExcelHandler();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 输出到web，即直接下载
     * @param excel excel数据
     */
    public void writeToWeb(KExcel excel) {
        try {
            HttpServletResponse response =  KClientContext.getContext().getResponse();
            response.reset();
            response.setContentType("application/octet-stream");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + UriEncoder.encode(excel.getFileNme()));
            this.excelHandler.write(excel, response.getOutputStream());
        }
        catch (Exception e) {
            log.info("excel导出失败，错误原因:{}", e.getMessage());
        }
    }

    /**
     * 获取处理器
     * @return  处理器
     */
    public KExcelHandler getHandler() {
        return excelHandler;
    }

    /**
     * 从Excel文件中读取数据
     * @param filePath      文件路径
     * @param sheetIndex    sheet序号
     * @param headerDefines 文件头定义
     * @param tClass        目标类
     * @param <T>           目标泛型
     * @return              解析之后的数据
     */
    @SneakyThrows
    public <T> List<T> readFromFile(String filePath, int sheetIndex, List<RegionDefine> headerDefines, Class<T> tClass) {
        // 读取所有的数据
        List<List<String>> allContents = this.excelHandler.read(sheetIndex, filePath);
        // 将headerDefines转为map
        Map<String, RegionDefine> headerMap = headerDefines.stream().collect(Collectors.toMap(RegionDefine::getLabelName, account -> account));
        // 将数据转为实体
        List<T> resultList = new ArrayList<T>();
        // 第一行为表头
        List<String> headerList = allContents.get(0);
        for (int i = 1; i < allContents.size(); i++) {
            List<String> rowData = allContents.get(i);
            // 如果行数据为空，或者首行为空，此时就跳过
            if (rowData.isEmpty() || StringUtils.isEmpty(rowData.get(0))) {
                continue;
            }
            T entity = null;
            try {
                entity = tClass.newInstance();
            } catch (Exception e) {
                log.error("对象实例化失败，Class: {}", tClass.getName());
            }
            for (int j = 0; j < headerList.size(); j++) {
                String header = headerList.get(j);
                if (headerMap.containsKey(header)) {
                    RegionDefine define = headerMap.get(header);
                    // 如果为空，就直接跳过
                    String cellValue = rowData.get(j);
                    if (StringUtils.isEmpty(cellValue)) {
                        continue;
                    }
                    // 处理数据
                    Field field = BeanUtils.getField(tClass, define.getPropName());
                    if (field == null) {
                        continue;
                    }
                    Object realValue = cellValue;
                    if (define.getFormat() != null) {
                        realValue = define.getFormat().format(realValue, entity);
                    }

                    BeanUtils.setField(field, entity, realValue);
                }
            }
            resultList.add(entity);
        }

        return resultList;
    }
}
