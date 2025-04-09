package com.kingsware.kdev.core.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.cache.api.ApiInfo;
import com.kingsware.kdev.core.context.KClientContext;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.encrypt.SM4Utils;
import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.i18n.I18n;
import com.kingsware.kdev.core.kflow.bean.KFlowUploadFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Base64Utils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;
import org.yaml.snakeyaml.util.UriEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

/**
 * servlet工具类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/3/4 3:53 下午
 */
@Slf4j
@SuppressWarnings("all")
public class ServletUtil {

    private ServletUtil() {
    }

    /**
     * 获取http请求
     * @return http请求
     */
    public static HttpServletRequest request() {
        return KClientContext.getContext().getRequest();
//        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    /**
     * 是否Ajax请求
     * @param request   请求
     * @return
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        if (request.getHeader("content-type") != null && request.getHeader("content-type").contains("application/json")) {
            return true;
        }
        String header = request.getHeader("X-Requested-With");
        return header != null && header.equals("XMLHttpRequest");
    }

    public static boolean isPage(HttpServletRequest request) {
        boolean isAjax = isAjaxRequest(request);
        if(isAjax) {
            return false;
        }
        if (request.getRequestURI().endsWith("js") || request.getRequestURI().endsWith("css") || request.getRequestURI().endsWith("png") || request.getRequestURI().endsWith("jpg") || request.getRequestURI().endsWith("gif") || request.getRequestURI().endsWith("ico") || request.getRequestURI().endsWith("svg") || request.getRequestURI().endsWith("woff")) {
            return false;
        }
        return true;
    }



    /**
     * 判断referer是否包含配置的url标识
     * @return  是否
     */
    public static boolean isRefererRule(HttpServletRequest request) {
        String referer = request.getHeader("referer");
        if (StringUtils.isEmpty(referer)) {
            return false;
        }
        String refererUrl = SpringContext.getProperties("app.ignore.referer", "");
        String[] refererUrls = refererUrl.split(";");
        for (String item: refererUrls) {
            if (StringUtils.isEmpty(item)) {
                continue;
            }
            if (referer.contains(item)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取http请求
     * @return http响应
     */
    public static HttpServletResponse response() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
    }

    /**
     * 响应文件
     * @param localFile 文件
     */
    public static void responseFile(File localFile, String fileName) {

        HttpServletResponse response = KClientContext.getContext().getResponse();
        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + UriEncoder.encode(fileName));

        if (!localFile.exists()) {
            throw BusinessException.serviceThrow(I18n.t("ServletUtil.error1", "文件不存在，可能被移动或删除！"));
        }
        FileInputStream ins = null;
        BufferedInputStream bis = null;
        try {
            response.setContentLength((int)localFile.length());
            ins = new FileInputStream(localFile);
            bis = new BufferedInputStream(ins);
            byte[] buff = new byte[1024];
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                response.getOutputStream().write(buff, 0, i);
                response.getOutputStream().flush();
            }
            response.getOutputStream().flush();
            response.getOutputStream().close();

        }
        catch (FileNotFoundException e) {
            throw BusinessException.serviceThrow(I18n.t("ServletUtil.error1", "文件不存在，可能被移动或删除！"));
        }
        catch (IOException e) {
            throw BusinessException.serviceThrow(I18n.t("ServletUtil.fileReadFail", "文件读取失败"));
        }
        finally {
            try {
                if (ins != null) {
                    ins.close();
                }
                if (bis != null) {
                    bis.close();
                }
            }
            catch (Exception e) {
                log.info("文件关闭失败");
            }
        }
    }

    /**
     * 响应文件
     * @param fileName 文件名称
     * @param data 文件内容
     */
    public static void responseFile(HttpServletResponse response, String fileName, byte[] data) {
        try {
            response.reset();
            response.setContentType("application/octet-stream");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + UriEncoder.encode(fileName));
            try {
                response.setContentLength(data.length);
                response.getOutputStream().write(data);
                response.getOutputStream().flush();
            } catch (IOException e) {
                throw BusinessException.serviceThrow(I18n.t("ServletUtil.fileWriteFail", "文件写入失败"));
            }
        }
        catch (Exception e) {
            log.info("文件写入失败，错误原因:{}", e.getMessage());
        }
    }


    /**
     * 响应文件
     * @param response 响应
     * @param html html内容
     */
    public static void responseHtml(HttpServletResponse response, String html) {
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        try (PrintWriter writer = response.getWriter()) {
            writer.write(html);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    /**
     * 获取cookie值
     * @param name              名称
     * @param defaultValue      默认值
     * @return         cookie值
     */
    public static String getCookie(String name, String defaultValue) {
        try {
            // 获取http请求
            HttpServletRequest request = KClientContext.getContext().getRequest();
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie: cookies) {
                if (cookie.getName().equalsIgnoreCase(name)) {
                    return cookie.getValue();
                }
            }
        }
        catch (Exception ignored) {

        }

        return defaultValue;
    }

    /**
     * 获取cookie值
     * @param name              名称
     * @param defaultValue      默认值
     * @return         cookie值
     */
    public static String getCookie(HttpServletRequest request, String name, String defaultValue) {
        try {
            // 获取http请求
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie: cookies) {
                if (cookie.getName().equalsIgnoreCase(name)) {
                    return cookie.getValue();
                }
            }
        }
        catch (Exception ignored) {

        }

        return defaultValue;
    }

    /**
     * 获取访问者IP
     * @return  ip
     */
    public static String getClientIp(HttpServletRequest request) {
        final String UNKNOWN = "unknown";
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)){
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)){
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)){
            ip = request.getHeader("X-Real-IP");
        }
        if(ip == null || ip.length()==0 || UNKNOWN.equalsIgnoreCase(ip)){
            ip = request.getRemoteAddr();
        }

        // 当前获取到的IP多于1个时，只取第1个
        return getFirstIp(ip);
    }

    /**
     * 截取第一个IP，如没有多于1个，则返回原来的内容
     * @param ipString
     * @return
     */
    private static String getFirstIp(String ipString) {
        if (ipString == null && ipString.indexOf(",") != -1) {
            return ipString;
        }
        String[] ipArr = ipString.trim().split(",");
        return ipArr[0];
    }

    /**
     * 获取请求参数
     * @param api       api信息
     * @param path      路径
     * @return          请求参数
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> getRequestParams(ApiInfo api, String path, HttpServletRequest request, String requestBody, boolean needFile) {

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> params = new HashMap<>();
        // 获取query和form-data
        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            String value = request.getParameter(name);
            params.put(name, value);
        }
        // 获取path变量
        if (api != null) {
            Map<String, Object> pathVariables = getPathVariables(path, api.getApiUrl());
            params.putAll(pathVariables);
        }
        // 判断是文件还是raw提交
        String contentType = request.getContentType();
        // 获取文件
            if (StringUtils.isNotEmpty(contentType) && contentType.toLowerCase().contains("multipart/form-data")) {
                if (needFile) {
                    MultipartResolver resolver = new StandardServletMultipartResolver();
                    MultipartHttpServletRequest multipartHttpServletRequest = resolver.resolveMultipart(request);
                    // 获取所有文件
                    Map<String, MultipartFile> fileMap = multipartHttpServletRequest.getFileMap();
                    for (Map.Entry<String, MultipartFile> multipartFileEntry: fileMap.entrySet()) {
                        KFlowUploadFile uploadFile = new KFlowUploadFile();
                        // Base64.getEncoder().encodeToString("1".getBytes());
                        // 原始文件名
                        uploadFile.setOriginFileName(multipartFileEntry.getValue().getOriginalFilename());
                        // 文件大小
                        uploadFile.setFileSize(multipartFileEntry.getValue().getSize());
                        // 名称
                        uploadFile.setName(multipartFileEntry.getValue().getName());
                        // content type
                        uploadFile.setContentType(multipartFileEntry.getValue().getContentType());
                        // 文件内容
                        try {
                            uploadFile.setFileContent(Base64Utils.encodeToString(multipartFileEntry.getValue().getBytes()));
                        }
                        catch (IOException e) {
                            log.error("文件转换失败，原始文件名:{}，名称:{}，{}", uploadFile.getOriginFileName(), uploadFile.getName(), e );
                        }
                        // 将文件加入到流程变量中
                        params.put(multipartFileEntry.getKey(), uploadFile);
                    }
                }

            }
            else {
                // 获取body
                String body = requestBody;
                if (StringUtils.isNotEmpty(body)) {
                    try {
                        Map<String, Object> argv = objectMapper.readValue(body, Map.class);
                        params.putAll(argv);
                    }
                    catch (Exception e) {
                        //log.error("error", e);
                    }
                }
                // 将body加到变量中
                Map<String, Object> requestMap = new HashMap<>();
                requestMap.put("body", body);
                requestMap.put("path", path);
                requestMap.put("apiName", api != null ? api.getApiName() : "");
                requestMap.putAll(getRequestData());
                params.put("request", requestMap);

            }

        // 返回
        return params;
    }

    public static Map<String, Object> getRequestData() {
        if (KClientContext.getContext() != null && KClientContext.getContext().getRequest() != null) {
            // 将body加到变量中
            Map<String, Object> requestMap = new HashMap<>();
            requestMap.put("ip", ServletUtil.getClientIp(KClientContext.getContext().getRequest()));
            requestMap.put("lang", I18n.lang(KClientContext.getContext().getRequest()));
            requestMap.put("method", KClientContext.getContext().getRequest().getMethod());
            requestMap.put("headers", getHeaders(KClientContext.getContext().getRequest()));
            return requestMap;
        }
        return new HashMap<>();

    }

    public static Map<String, Object> getHeaders(HttpServletRequest request) {
        Map<String, Object> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.put(headerName, request.getHeader(headerName));
        }
        return headers;
    }

    /**
     * 获取Body
     * @param request   Http请求
     * @return  返回body
     */
    private static String getBody(HttpServletRequest request) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder("");
        try {
            br = request.getReader();
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
            br.close();
        }
        catch (IOException e) {
            log.error("error", e);
        }
        finally {
            if (null != br) {
                try {
                    br.close();
                }
                catch (IOException e) {
                    log.error("error", e);
                }
            }
        }
        return sb.toString();
    }


    /**
     *
     * @param path          路径
     * @param patternUrl    匹配路径
     * @return              返回路径变量
     */
    private static Map<String, Object> getPathVariables(String path, String patternUrl) {
        Map<String, Object> variables = new HashMap<>();
        // 匹配路径
        String[] pUrls = patternUrl.split("/");
        // 前端传过来的路径
        String[] uiUrls = path.split("/");
        // 取最小长度
        int minLength = Math.min(pUrls.length, uiUrls.length);;
        for (int i = 0; i< minLength; i++) {
            String pVar = pUrls[i].trim();
            String uVar = uiUrls[i].trim();
            if (pVar.startsWith("{") && pVar.endsWith("}")) {
                String realName = pVar.substring(1, pVar.length()-1);
                variables.put(realName, uVar);
            }
        }
        return variables;
    }


    /**
     * 输出json
     * @param object    对象
     */
    public static void responseJson(HttpServletResponse response, Object object) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try (PrintWriter out = response.getWriter()) {
            String responseBody = new ObjectMapper().writeValueAsString(object);
            out.append(responseBody);
        } catch (IOException e) {
            log.error("error", e);
        }
    }

    /**
     * 输出json
     * @param object    对象
     */
    public static void responseJsonWithEncrypt(HttpServletResponse response, Object object) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try (PrintWriter out = response.getWriter()) {
            if (object instanceof Map) {
                Object data = ((Map) object).get("data");
                if (data != null) {
                    // 如果是对象，就对它进行加密和处理
                    Object dat = encryptObject(data);
                    ((Map) object).put("data", dat);

                }
            } else if (object instanceof BaseRet) {
                Object data = ((BaseRet) object).getData();
                if (data != null) {
                    // 如果是对象，就对它进行加密和处理
                    Object dat = encryptObject(data);
                    ((BaseRet) object).setData(dat);
                }
            }
            String responseBody = new ObjectMapper().writeValueAsString(object);
            out.append(responseBody);
        } catch (IOException e) {
            log.error("error", e);
        }
    }

    public static String rot47(String input) {
        StringBuilder result = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (c >= '!' && c <= '~') { // 判断是否为可打印 ASCII 字符
                // 对字符进行 ROT47 移位
                result.append((char) (((c - 33 + 47) % 94) + 33));
            } else {
                // 如果不是可打印字符，直接保留
                result.append(c);
            }
        }
        return result.toString();
    }

    public static Object encryptObject(Object object) {
        if (object == null) {
            return object;
        }
        if (object instanceof String) {
            return object;
        }
        // 如果是数字类型或者布尔类型，以及其他简单类型，就不加密
        if (object instanceof Number || object instanceof Boolean || object instanceof Character || object instanceof Enum) {
            return object;
        }
        try {
            String encryptMethod = SpringContext.getProperties("encrypt.http.encrypt-method", "");
            if ("cs".equalsIgnoreCase(encryptMethod)) {
                String responseBody = new ObjectMapper().writeValueAsString(object);
                String base64str = Base64.getEncoder().encodeToString(responseBody.getBytes());
                base64str = rot47(base64str);
//                String str =  CaesarCipher.encrypt(base64str, 5);
                responseBody = "enc##" + encryptMethod +"##"+ base64str;
                return responseBody;
            }
        }
        catch (Exception ignored) {

        }

        return object;
    }

    /**
     * 输出json
     * @param content    对象
     */
    public static void responseJsonString(HttpServletResponse response, String content) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try (PrintWriter out = response.getWriter()) {
            out.append(content);
        } catch (IOException e) {
            log.error("error", e);
        }
    }

    public static void responseRaw(HttpServletResponse response, String content) {
        if (content == null || content.isEmpty()) {
            return;
        }

        // 设置默认编码
        response.setCharacterEncoding("UTF-8");
        
        // 根据内容格式判断 Content-Type
        String contentType = "text/plain";
        if (content.trim().startsWith("<")) {
            // XML 格式
            if (content.trim().startsWith("<?xml")) {
                contentType = "application/xml";
            } else if (content.trim().startsWith("<html") || content.trim().startsWith("<!DOCTYPE html")) {
                contentType = "text/html";
            }
        } else if (content.trim().startsWith("{") || content.trim().startsWith("[")) {
            // JSON 格式
            contentType = "application/json";
        }

        // 设置 Content-Type
        response.setContentType(contentType + ";charset=UTF-8");

        try {
            response.getWriter().write(content);
            response.getWriter().flush();
        } catch (IOException e) {
            throw new RuntimeException("响应内容写入失败", e);
        }
    }

    /**
     * 打印返回参数
     *
     * @param response 响应
     */
    public static String getResponseBody(ContentCachingResponseWrapper response) {
        ContentCachingResponseWrapper wrapper = WebUtils.getNativeResponse(response,
                ContentCachingResponseWrapper.class);
        if (wrapper != null) {
            byte[] buf = wrapper.getContentAsByteArray();
            if (buf.length > 0) {
                String payload;
                try {
//                    log.info("Response Length:" + buf.length);
                    payload = new String(buf, 0, buf.length, wrapper.getCharacterEncoding());
//                    log.info("Response Length Finish:" + buf.length);
                } catch (UnsupportedEncodingException e) {
                    payload = "[unknown]";
                }
                return payload;
            }
        }
        return "{}";
    }


    /**
     * 生成请求的唯一标识符。
     * 该标识符基于请求的URI、查询字符串、请求体以及用户信息（如果可用）计算得出。
     * 首先，将URI、查询参数和请求体信息（如果提供）放入一个TreeMap中，
     * 然后从TreeMap中构建一个字符串，该字符串不包含空格、换行符、单引号和双引号，
     * 最后对该字符串进行MD5哈希计算，生成请求的唯一标识符。
     *
     * @param uri 请求的URI
     * @param queryString 查询字符串，如果有的话
     * @param requestBody 请求体，如果有的话
     * @return 请求的唯一标识符，为MD5哈希值的字符串表示
     */
    public static String getRequestUuid(String uri, String queryString, String requestBody, HttpServletRequest request) {
        String requestUuid = request.getHeader("_request_uuid");
        if (StringUtils.isNotEmpty(requestUuid)) {
            return requestUuid;
        }
        requestUuid = request.getHeader("request-uuid");
        if (StringUtils.isNotEmpty(requestUuid)) {
            return requestUuid;
        }
        TreeMap<String, Object> stringObjectTreeMap = new TreeMap<>();
        stringObjectTreeMap.put("request_uri", uri);
        stringObjectTreeMap.put("request_method", request);
        // 处理查询字符串，将其解析并添加到map中
        if (StringUtils.isNotEmpty(queryString)) {
            String[] arr = queryString.split("&");
            for (String str : arr) {
                String[] arr2 = str.split("=");
                // 将查询参数添加到map中
                if (arr2.length == 2) {
                    stringObjectTreeMap.put(arr2[0], arr2[1]);
                } else {
                    stringObjectTreeMap.put(arr2[0], null);
                }
            }
        }

        // 处理请求体，如果是一个JSON字符串，则将其解析并添加到map中
        if (StringUtils.isNotEmpty(requestBody)) {
            try {
                Map<String, Object> map = JsonUtil.toMap(requestBody);
                stringObjectTreeMap.putAll(map);
            } catch (Exception e) {
                log.error("error", e);
            }
        }

        // 如果存在用户信息，则将用户ID添加到map中
        if (KClientContext.getContext() != null && KClientContext.getContext().getUserInfo() != null && StringUtils.isNotEmpty(KClientContext.getContext().getUserInfo().getId())) {
            stringObjectTreeMap.put("request_userId", KClientContext.getContext().getUserInfo().getId());
        }

        // 从map中移除特定的键
        stringObjectTreeMap.remove("t");
        stringObjectTreeMap.remove("uniopsToken");

        // 构建map到字符串的表示，键值对之间使用"&"分隔
        StringBuffer stringBuffer = new StringBuffer();
        for (Map.Entry<String, Object> entry : stringObjectTreeMap.entrySet()) {
            if (entry.getValue() != null) {
                if (entry.getValue() instanceof Map) {
                    stringBuffer.append(entry.getKey()).append("=").append(JsonUtil.toJson(entry.getValue()));
                } else {
                    stringBuffer.append(entry.getKey()).append("=").append(entry.getValue());
                }
                stringBuffer.append("&");
            }
        }

        // 移除字符串中的空格、换行、单引号、双引号，为计算哈希做准备
        String regex = "[\\s\\n\\t\'\"]+";
        String str1 = Pattern.compile(regex).matcher(stringBuffer.toString()).replaceAll("");

        // 对处理后的字符串计算MD5哈希值，返回结果
        return MD5Utils.md5(str1);
    }


    /**
     * 打印请求参数
     *
     * @param request 请求
     */
    public static String getRequestBody(ContentCachingRequestWrapper request) {
        ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
        if (wrapper != null) {
            byte[] buf = wrapper.getContentAsByteArray();
            if (buf.length > 0) {
                String payload;
                try {
                    payload = new String(buf, 0, buf.length, wrapper.getCharacterEncoding());
                } catch (UnsupportedEncodingException e) {
                    payload = "[unknown]";
                }
                return payload.replaceAll("\\n", "");
            }
        }
        return "{}";
    }


    /**
     * 获取上下文路径records.get(0).
     * @return 上下文路径
     */
    public static String getContextPath() {
        String contextPath = SpringContext.getProperties("server.servlet.context-path", "");
        String virtualPath = SpringContext.getProperties("server.servlet.virtual-path", "");
        return contextPath + virtualPath;
    }

    public static void printResponseHeaders(HttpServletRequest request, HttpServletResponse response) {
//        StringBuilder sb = new StringBuilder();
//        sb.append("【ui-access】资源访问：" +request.getRequestURI()).append("\n");
//        sb.append("【ui-access】================================================================================================================================================================================").append("\n");
//        Enumeration<String> headerNames = request.getHeaderNames();
//        while (headerNames.hasMoreElements()) {
//            String headerName = headerNames.nextElement();
//            sb.append(String.format("【ui-access】[request-header] %s: %s", headerName, request.getHeader(headerName))).append("\n");
//        }
//        sb.append("【ui-access】########################################################################################").append("\n");
//        for (String headerName : response.getHeaderNames()) {
//            sb.append(String.format("【ui-access】[response-header] %s: %s", headerName, response.getHeader(headerName))).append("\n");
//        }
//        sb.append("【ui-access】================================================================================================================================================================================").append("\n");
//        log.info(sb.toString());
    }



}
