package com.kingsware.kdev.sys.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingsware.kdev.core.auth.ApiIgnore;
import com.kingsware.kdev.core.base.BaseController;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.bean.ExceptionLog;
import com.kingsware.kdev.core.cache.page.PageCacheManager;
import com.kingsware.kdev.core.constants.Version;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.exception.ExceptionLogManager;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.util.FileUtils;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.sys.model.DevPageHistory;
import com.kingsware.kdev.sys.service.DevModelSqlService;
import io.swagger.annotations.Api;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.compress.compressors.lzma.LZMACompressorInputStream;
//import org.apache.commons.compress.compressors.lzma.LZMACompressorOutputStream;
//import org.brotli.dec.BrotliInputStream;
//import org.eclipse.jgit.api.Git;
//import org.eclipse.jgit.lib.Repository;
//import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
//import org.springframework.boot.autoconfigure.info.ProjectInfoProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
/**
 * 系统工具箱
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2023/6/16 11:17
 */
@Slf4j
@Api(value = "系统工具器", tags = {"系统工具器"})
@RestController
@RequestMapping("/"+ Version.V1 + "/sys-tool-box")
public class SysToolBoxController extends BaseController {

    @Resource
    private DevModelSqlService devModelSqlService;

    @GetMapping("/to-url")
    @ApiIgnore
    public void toUrl(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String url = request.getParameter("url");
        String deUrl = URLDecoder.decode(url, StandardCharsets.UTF_8.toString());
        log.info("Url302跳转:{}", deUrl);
        response.sendRedirect(deUrl);
    }

    @GetMapping("/exception")
    @ApiIgnore
    public BaseRet<?> getExceptionDetail(String id) throws UnsupportedEncodingException {
        String enableException = SpringContext.getProperties("app.exception.enable", "false");
        if ("true".equalsIgnoreCase(enableException)) {
            ExceptionLog exceptionLog = ExceptionLogManager.getInstance().read(id);
            if (exceptionLog == null) {
                return BaseRet.failMessage("未找到异常日志");
            }
            else {
                return BaseRet.success(exceptionLog);
            }
        }
        else {
            return BaseRet.failMessage("未开启异常日志");
        }

    }

    @GetMapping("/clear-page")
    @ApiIgnore
    public BaseRet<?> clearPageCache() {
        PageCacheManager.getInstance().clear();
        return BaseRet.success();
    }


    @GetMapping("/executeScript/{appId}/{sourceName}")
    public BaseRet<?> executeScript(@PathVariable String appId, @PathVariable String sourceName) {
        devModelSqlService.execute(appId, sourceName);
        return BaseRet.success();
    }



//
//    @GetMapping("/compress-testing")
//    @ApiIgnore
//    public BaseRet<?> compressTesting(String data) {
//        Map<String, Object> ret = new HashMap<>();
//        List<String> pageList = DB.findSingleAttributeList(String.class, "select page_json from dev_page_history  limit 200");
//        List<byte[]> compressList = new ArrayList<>();
//        long totalSize = 0;
//        long compressTotalSize = 0;
//        long totalTime = 0L;
//        for (String page : pageList) {
//            totalSize += page.length();
//            long t1 = System.currentTimeMillis();
//            byte[] bytes = JsonUtil.compressJSON(page);
//            totalTime += System.currentTimeMillis() - t1;
//            compressTotalSize += bytes.length;
//            compressList.add(bytes);
//
//        }
//        ret.put("原始总大小", totalSize);
//        ret.put("压缩后byte总大小", compressTotalSize);
//        ret.put("压缩耗时", totalTime);
//        ret.put("Byte压缩率:", BigDecimal.valueOf(compressTotalSize).divide(BigDecimal.valueOf(totalSize), 4, BigDecimal.ROUND_HALF_UP));
//
//
//        return BaseRet.success(ret);
//    }
//
//    @GetMapping("/git-testing")
//    @ApiIgnore
//    @SneakyThrows
//    public BaseRet<?> gitTesting() {
////        String pageId= "af21c51d808b46f5b826a4df9ca41b7d";
//        String pageId = "bc354dc1478045389d9e48a90f8984a\6";
//        List<String> pageList = DB.findSingleAttributeList(String.class, "select page_json from dev_page_history  where page_id=? order by when_created asc", pageId);
//        String gitDir = "testgit";
//        File localPath = new File(gitDir);
//        if (!localPath.exists()) {
//            localPath.mkdirs();
//        }
//        String gitPath = localPath.getPath() + "/.git";
//        Git git = null;
//        if (!new File(gitPath).exists()) {
//            git = Git.init().setDirectory(localPath).call();
//            log.info("本地仓库初始化完成.");
//        }
//        else {
//            git = Git.open(localPath);
//        }
//        // 如果文件不存在，则创建文件被加进去
//        String jsonFilePath = gitDir + "/" + pageId + ".json";
//        File jsonFile = new File(jsonFilePath);
//        if (!jsonFile.exists()) {
//            jsonFile.createNewFile();
//            git.add().addFilepattern(jsonFile.getName()).call();
//        }
//        for (String page : pageList) {
//
//            FileUtils.writeStringToFile(jsonFile, prettyJson(page), StandardCharsets.UTF_8);
//            git.add().addFilepattern(jsonFile.getName()).call();
//            git.commit().setMessage("update " + System.currentTimeMillis()).call();
//        }
//
//
//        return BaseRet.success();
//    }
//
//    @SneakyThrows
//    public String prettyJson(String compressedJson) {
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        // 解析 JSON 字符串到对象
//        Object json = objectMapper.readValue(compressedJson, Object.class);
//
//        // 格式化输出 JSON 字符串
//        String formattedJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
//        return formattedJson;
//
//    }
//
//    @SneakyThrows
//    public static byte[] compressLZMA(byte[] data)  {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        try (LZMACompressorOutputStream lzmaOut = new LZMACompressorOutputStream(baos)) {
//            lzmaOut.write(data);
//        }
//        return baos.toByteArray();
//    }
//




}
