package com.kingsware.kdev.sys.web;

import com.kingsware.kdev.core.auth.ApiIgnore;
import com.kingsware.kdev.core.auth.Dev;
import com.kingsware.kdev.core.base.BaseController;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.bean.ExceptionLog;
import com.kingsware.kdev.core.bean.FaasRequestBody;
import com.kingsware.kdev.core.cache.page.PageCacheManager;
import com.kingsware.kdev.core.constants.Version;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.exception.ExceptionLogManager;
import com.kingsware.kdev.core.util.HttpUtil;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.core.util.SecurityUtil;
import com.kingsware.kdev.sys.service.DevApplicationService;
import io.swagger.annotations.Api;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
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
    private DevApplicationService devApplicationService;

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
    public BaseRet<Void> getExceptionDetail(String id) throws UnsupportedEncodingException {
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
    public BaseRet<Void> clearPageCache() {
        PageCacheManager.getInstance().clear();
        return BaseRet.success();
    }

    /**
     * Mock FaaS接口 - 模拟FaaS服务响应
     */
    @PostMapping("/mock-faas")
    @ApiIgnore
    public BaseRet<Void> mockFaas(@RequestBody FaasRequestBody faasRequestBody) {
        Map<String, Object> response = new HashMap<>();

        try {
            // 获取签名密钥
            String signSecret = SpringContext.getProperties("faas.signSecret", "JRc7ciSE2n75sJf4bY3RK56Y");

            // 验证签名
            String receivedBody = faasRequestBody.getBody();
            Long receivedTimestamp = faasRequestBody.getTimestamp();
            String receivedSignature = faasRequestBody.getSignature();

            // 计算期望的签名
            String expectedSignature = SecurityUtil.generateHmacSignature(receivedBody, receivedTimestamp, signSecret);

            // 验证签名是否匹配
            boolean signatureValid = expectedSignature.equals(receivedSignature);

            // 验证时间戳（允许5分钟的时间差）
            long currentTime = System.currentTimeMillis();
            long timeDiff = Math.abs(currentTime - receivedTimestamp);
            boolean timestampValid = timeDiff <= 5 * 60 * 1000; // 5分钟

            if (!signatureValid) {
                response.put("status", "error");
                response.put("message", "签名验证失败");
                response.put("receivedSignature", receivedSignature);
                response.put("expectedSignature", expectedSignature);
                log.error("签名验证失败: 接收到的签名={}, 期望的签名={}", receivedSignature, expectedSignature);
                return BaseRet.failMessage("签名验证失败");
            }

            if (!timestampValid) {
                response.put("status", "error");
                response.put("message", "时间戳验证失败");
                response.put("receivedTimestamp", receivedTimestamp);
                response.put("currentTimestamp", currentTime);
                response.put("timeDiff", timeDiff);
                log.error("时间戳验证失败: 接收到的时间戳={}, 当前时间戳={}, 时间差={}ms",
                         receivedTimestamp, currentTime, timeDiff);
                return BaseRet.failMessage("时间戳验证失败");
            }

            // 解密请求体（如果需要）
            String decryptedBody = receivedBody;
            try {
                decryptedBody = SecurityUtil.decrypt(receivedBody, signSecret + receivedTimestamp  + (receivedTimestamp%9));
                log.info("请求体解密成功：" + decryptedBody);
            } catch (Exception e) {
                log.warn("请求体解密失败，使用原始请求体: {}", e.getMessage());
            }

            // 构建成功响应
            response.put("status", "success");
            response.put("message", "Mock FaaS接口调用成功");
            response.put("timestamp", System.currentTimeMillis());
            response.put("receivedBody", receivedBody);
            response.put("decryptedBody", decryptedBody);
            response.put("signatureValid", signatureValid);
            response.put("timestampValid", timestampValid);
            response.put("verificationTime", System.currentTimeMillis());

            log.info("Mock FaaS接口验证成功: 签名={}, 时间戳={}", signatureValid, timestampValid);
            return BaseRet.success(response);

        } catch (Exception e) {
            log.error("Mock FaaS接口验证过程中发生异常: {}", e.getMessage(), e);
            response.put("status", "error");
            response.put("message", "验证过程中发生异常: " + e.getMessage());
            return BaseRet.failMessage("验证过程中发生异常: " + e.getMessage());
        }
    }

    /**
     * 通过HttpUtil.postFaas调用Mock FaaS接口
     */
    @GetMapping("/test-faas-call")
    @ApiIgnore
    public BaseRet<Void> testFaasCall() {
        try {
            String mockFaasUrl = "http://127.0.0.1:8080/" + Version.V1 + "/sys-tool-box/mock-faas";
            Map<String, Object> requestData = new HashMap<>();
            requestData.put("testParam", "testValue");
            requestData.put("timestamp", System.currentTimeMillis());
            String requestBody = JsonUtil.toJson(requestData);
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json");
            headers.put("X-Test-Header", "test-header-value");

            log.info("开始调用Mock FaaS接口: {}", mockFaasUrl);
            log.info("请求体: {}", requestBody);

            String response = HttpUtil.postFaas1(mockFaasUrl, requestBody, headers);

            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("response", response);
            result.put("callTime", System.currentTimeMillis());

            log.info("Mock FaaS接口调用成功: {}", response);
            return BaseRet.success(result);

        } catch (Exception e) {
            log.error("Mock FaaS接口调用失败: {}", e.getMessage(), e);
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("error", e.getMessage());
            result.put("callTime", System.currentTimeMillis());
            return BaseRet.failMessage("Mock FaaS接口调用失败: " + e.getMessage());
        }
    }

    /**
     * 下载黑名单配置文件（明文）
     */
    @GetMapping("/download-blacklist-plain")
    @ApiIgnore
    @Dev
    public void downloadBlackListPlain(HttpServletResponse response) throws IOException {
        try {
            String content = devApplicationService.generateBlackListConfig(false);

            response.setContentType("text/yaml;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=blacklist-plain.yml");

            response.getWriter().write(content);
            response.getWriter().flush();

            log.info("黑名单配置文件（明文）下载成功");
        } catch (Exception e) {
            log.error("下载黑名单配置文件（明文）失败: {}", e.getMessage(), e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("下载失败: " + e.getMessage());
        }
    }

    /**
     * 下载黑名单配置文件（密文）
     */
    @GetMapping("/download-blacklist-encrypted")
    @ApiIgnore
    @Dev
    public void downloadBlackListEncrypted(HttpServletResponse response) throws IOException {
        try {
            String content = devApplicationService.generateBlackListConfig(true);

            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=blacklist-encrypted.yml");

            response.getWriter().write(content);
            response.getWriter().flush();

            log.info("黑名单配置文件（密文）下载成功");
        } catch (Exception e) {
            log.error("下载黑名单配置文件（密文）失败: {}", e.getMessage(), e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("下载失败: " + e.getMessage());
        }
    }
//
//    @GetMapping("/compress-testing")
//    @ApiIgnore
//    public BaseRet<Void> compressTesting(String data) {
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
//    public BaseRet<Void> gitTesting() {
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
