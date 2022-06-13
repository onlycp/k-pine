package com.kingsware.kdev.core.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingsware.kdev.core.cache.api.ApiInfo;
import com.kingsware.kdev.core.context.KClientContext;
import com.kingsware.kdev.core.context.SpringContext;
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
import org.yaml.snakeyaml.util.UriEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * servlet工具类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/3/4 3:53 下午
 */
@Slf4j
public class ServletUtil {

    private ServletUtil() {
    }

    /**
     * 获取http请求
     * @return http请求
     */
    public static HttpServletRequest request() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
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
            throw BusinessException.serviceThrow("文件不存在，可能被移动或删除！");
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
            throw BusinessException.serviceThrow("文件不存在");
        }
        catch (IOException e) {
            throw BusinessException.serviceThrow("文件读取失败");
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
                throw BusinessException.serviceThrow("文件写入失败");
            }
        }
        catch (Exception e) {
            log.info("文件写入失败，错误原因:{}", e.getMessage());
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
        return ip;
    }

    /**
     * 获取请求参数
     * @param api       api信息
     * @param path      路径
     * @return          请求参数
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> getRequestParams(ApiInfo api, String path, HttpServletRequest request) {

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
        Map<String, Object> pathVariables = getPathVariables(path, api.getApiUrl());
        params.putAll(pathVariables);
        // 判断是文件还是raw提交
        String contentType = request.getContentType();

        // 获取文件
        if (StringUtils.isNotEmpty(contentType) && contentType.toLowerCase().contains("multipart/form-data")) {
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
                    log.error("文件转换成功，原始文件名:{}，名称:{}，{}", uploadFile.getOriginFileName(), uploadFile.getName(), e );
                }
                // 将文件加入到流程变量中
                params.put(multipartFileEntry.getKey(), uploadFile);
            }
        }
        else {
            // 获取body
            String body = getBody(request);
            if (StringUtils.isNotEmpty(body)) {
                try {
                    Map<String, Object> argv = objectMapper.readValue(body, Map.class);
                    params.putAll(argv);
                }
                catch (Exception e) {
                    log.error("error", e);
                }
            }
            // 将body加到变量中
            Map<String, Object> requestMap = new HashMap<>();
            requestMap.put("body", body);
            requestMap.put("lang", I18n.lang());
            params.put("request", requestMap);
        }

        // 返回
        return params;
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
            out.append(new ObjectMapper().writeValueAsString(object));
        } catch (IOException e) {
            log.error("error", e);
        }
    }

}
