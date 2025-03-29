package com.kingsware.kdev.core.excel.handler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingsware.kdev.core.excel.KExcel;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.kdb.KdbRet;
import com.kingsware.kdev.core.util.FileUtils;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.core.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.zip.*;

/**
 * @author chenp
 * @date 2023/11/6
 */
@Slf4j
public class FaasExcelHandler implements KExcelHandler{



    /**
     * 生成并输出到流
     *
     * @param excel excel数据
     * @param out
     */
    @Override
    public void write(KExcel excel, OutputStream out) {
        try {
            String nextStr = JsonUtil.toJsonWithoutNull(excel);
            String base64Content = Base64.getEncoder().encodeToString(JsonUtil.compressJSON(nextStr));
            // 实现一个压缩算法

            // 创建目录
//            log.info("excel文件写入中，文件内容:{}", baseStr);
            String script = String.format("kutils.fileDirectory('upload/kExcel');const str = decompressJSON('%s');koffices.renderByKExcel(str);", base64Content);
            KdbRet<String> ret = DB.kdbApi().executeScript(script);
            Map<String, Object> map = JsonUtil.toMap(ret.getResponseBody());
            String retBase64 = map.getOrDefault("data", "").toString();
            byte[] bytes = Base64.getDecoder().decode(retBase64);
            out.write(bytes);
        }
        catch (Exception e) {
            log.error("error", e);
            log.info("excel文件写失败，错误原因:{}", e.getMessage());
        }

    }

    /**
     * 从文件里读取数据
     *
     * @param sheetIndex
     * @param filePath   文件路径
     * @return 读取的数据
     */
    @Override
    public List<List<String>> read(int sheetIndex, String filePath) throws Exception {
        String base64File = Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(filePath)));
        String script = String.format("koffices.readSheetData(%d, '%s');", sheetIndex, base64File);
        KdbRet<String> ret = DB.kdbApi().executeScript(script);
        Map<String, Object> map = JsonUtil.toMap(ret.getResponseBody());
        String retBase64 = map.getOrDefault("data", "").toString();
        return new ObjectMapper().readValue(retBase64, new TypeReference<List<List<String>>>(){});
    }
}
