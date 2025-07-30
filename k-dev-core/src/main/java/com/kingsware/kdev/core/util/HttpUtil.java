package com.kingsware.kdev.core.util;

import com.kingsware.kdev.core.bean.FaasRequestBody;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.exception.HttpClientException;
import com.kingsware.kdev.core.i18n.I18n;
import com.kingsware.kdev.core.plugins.FaasChannelPlugin;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Http工具类 - 基于OkHttp重构
 *
 * @author chen peng
 * @version 2.0.0
 * @date 2024/01/01
 */
@Slf4j
public class HttpUtil {

    private static final OkHttpClient client;
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static final MediaType FORM = MediaType.get("multipart/form-data");
    private static final Map<String, FaasChannelPlugin> faasChannelPluginMap = new HashMap<>();

    static {
        // 配置OkHttp客户端
        int maxRetryCount = 3;
        client = new OkHttpClient.Builder()
                .dns(new ClusterDns()) // 使用自定义DNS支持集群
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(new RetryInterceptor(maxRetryCount)) // 使用Interceptor实现重试
                .build();

        loadPlugins();
    }

    /**
     * 私有构造
     */
    private HttpUtil() {
    }

    /**
     * 加载插件
     */
    private static void loadPlugins() {
        List<Class<?>> classList = ClassUtils.getClassesByParentClass("com.kingsware.kdev", FaasChannelPlugin.class);
        for (Class<?> tClass : classList) {
            // 生成实例
            try {
                FaasChannelPlugin plugin = (FaasChannelPlugin) tClass.newInstance();
                faasChannelPluginMap.put(plugin.name(), plugin);
            } catch (Exception e) {
                log.error("定时类扫描初始化失败:{}", e.getMessage());
            }
        }
    }

        /**
     * 执行HTTP POST请求
     *
     * @param apiUrl API的URL地址
     * @param body 请求体内容
     * @param headerMap 请求头信息
     * @return HTTP响应内容
     * @throws IOException 当HTTP请求失败时抛出
     */
    public static String post(String apiUrl, String body, Map<String, String> headerMap) throws IOException {
        try {
            // 构建请求体
            RequestBody requestBody = RequestBody.create(body, JSON);

            // 构建请求头
            Headers.Builder headersBuilder = new Headers.Builder();
            if (headerMap != null) {
                headerMap.forEach(headersBuilder::add);
            }


            // 构建请求
            Request request = new Request.Builder()
                    .url(apiUrl)
                    .post(requestBody)
                    .headers(headersBuilder.build())
                    .build();

            // 执行请求
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    String errorBody = response.body() != null ? response.body().string() : "";
                    throw new IOException("HTTP request failed with code: " + response.code() +
                                        (errorBody.isEmpty() ? "" : ", Error: " + errorBody));
                }

                ResponseBody responseBody = response.body();
                return responseBody != null ? responseBody.string() : "";
            }

        } catch (Exception e) {
            log.error("HTTP POST request failed: {}", e.getMessage(), e);
            throw new IOException("HTTP POST request failed", e);
        }
    }



    /**
     * 执行HTTP GET请求
     *
     * @param apiUrl API的URL地址
     * @param headerMap 请求头信息
     * @return HTTP响应内容
     * @throws IOException 当HTTP请求失败时抛出
     */
    public static String get(String apiUrl, Map<String, String> headerMap) throws Exception {
        try {
            // 构建请求头
            Headers.Builder headersBuilder = new Headers.Builder();
            if (headerMap != null) {
                headerMap.forEach(headersBuilder::add);
            }

            // 构建请求
            Request request = new Request.Builder()
                    .url(apiUrl)
                    .headers(headersBuilder.build())
                    .build();

            // 执行请求
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    String errorBody = response.body() != null ? response.body().string() : "";
                    throw new IOException("HTTP GET request failed with code: " + response.code() +
                                        (errorBody.isEmpty() ? "" : ", Error: " + errorBody));
                }

                ResponseBody responseBody = response.body();
                return responseBody != null ? responseBody.string() : "";
            }

        } catch (Exception e) {
            log.error("HTTP GET request failed: {}", e.getMessage(), e);
            throw new IOException("HTTP GET request failed", e);
        }
    }

    /**
     * 上传文件
     *
     * @param apiUrl API的URL地址要
     * @param fileName 文件名
     * @param formName 表单字段名
     * @param inputStream 文件输入流
     * @param path 文件路径
     * @return HTTP响应内容
     * @throws BusinessException 当HTTP请求失败时抛出
     */
    public static String uploadFile(String apiUrl, String fileName, String formName,
                                  InputStream inputStream, String path) throws BusinessException {
        try {
            // 构建MultipartBody
            MultipartBody.Builder bodyBuilder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM);

            // 添加文件路径参数
            if (path != null) {
                bodyBuilder.addFormDataPart("path", path);
            }

            // 添加文件
            byte[] fileBytes = readInputStream(inputStream);
            bodyBuilder.addFormDataPart(formName, fileName,
                RequestBody.create(fileBytes, MediaType.get("application/octet-stream")));

            // 构建请求
            Request request = new Request.Builder()
                    .url(apiUrl)
                    .post(bodyBuilder.build())
                    .build();

            // 执行请求
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    String errorBody = response.body() != null ? response.body().string() : "";
                    throw new IOException("File upload failed with code: " + response.code() +
                                        (errorBody.isEmpty() ? "" : ", Error: " + errorBody));
                }

                ResponseBody responseBody = response.body();
                return responseBody != null ? responseBody.string() : "";
            }

        } catch (Exception e) {
            log.error("File upload failed: {}", e.getMessage(), e);
            throw new BusinessException("File upload failed:" +  e.getMessage());
        }
    }

    /**
     * 上传文件（支持额外参数）
     *
     * @param apiUrl API的URL地址
     * @param fileName 文件名
     * @param formName 表单字段名
     * @param inputStream 文件输入流
     * @param params 额外参数
     * @param header 请求头
     * @return HTTP响应内容
     * @throws BusinessException 当上传失败时抛出
     */
    public static String uploadFile(String apiUrl, String fileName, String formName,
                                  InputStream inputStream, Map<String, String> params, Map<String, String> header) throws BusinessException {
        try {
            // 构建MultipartBody
            MultipartBody.Builder bodyBuilder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM);

            // 添加额外参数
            if (params != null) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    bodyBuilder.addFormDataPart(entry.getKey(), entry.getValue());
                }
            }

            // 添加文件
            byte[] fileBytes = readInputStream(inputStream);
            bodyBuilder.addFormDataPart(formName, fileName,
                RequestBody.create(fileBytes, MediaType.get("application/octet-stream")));

            // 构建请求头
            Headers.Builder headersBuilder = new Headers.Builder();
            if (header != null) {
                header.forEach(headersBuilder::add);
            }

            // 添加Authorization Token
            String token = SpringContext.getProperties("faas.token", "JWDNCUZlHnIUfBGJ2BNs2P44");
            headersBuilder.add("Authorization", "Bearer " + token);

            // 构建请求
            Request request = new Request.Builder()
                    .url(apiUrl)
                    .post(bodyBuilder.build())
                    .headers(headersBuilder.build())
                    .build();

            // 执行请求
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    String errorBody = response.body() != null ? response.body().string() : "";
                    throw new IOException("File upload failed with code: " + response.code() +
                                        (errorBody.isEmpty() ? "" : ", Error: " + errorBody));
                }

                ResponseBody responseBody = response.body();
                return responseBody != null ? responseBody.string() : "";
            }

        } catch (Exception e) {
            log.error("File upload failed: {}", e.getMessage(), e);
            throw new BusinessException("File upload failed:" +  e.getMessage());
        }
    }

    /**
     * 下载文件
     *
     * @param downloadUrl 下载地址
     * @param fileName 文件名
     * @param prefix 文件前缀
     * @param suffix 文件后缀
     * @return 下载的文件
     * @throws IOException 当下载失败时抛出
     */
    public static File downloadFile(String downloadUrl, String fileName, String prefix, String suffix) throws IOException {
        try {
            Request request = new Request.Builder()
                    .url(downloadUrl)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new IOException("File download failed with code: " + response.code());
                }

                ResponseBody responseBody = response.body();
                if (responseBody == null) {
                    throw new IOException("Response body is null");
                }

                File tempFile = FileUtils.createTempFile(prefix, suffix, fileName);
                if (tempFile == null) {
                    throw new IOException("Failed to create temp file");
                }

                try (FileOutputStream outputStream = new FileOutputStream(tempFile);
                     InputStream inputStream = responseBody.byteStream()) {

                    byte[] buffer = new byte[8192];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    outputStream.flush();
                }

                return tempFile;
            }

        } catch (Exception e) {
            log.error("File download failed: {}", e.getMessage(), e);
            throw new IOException("File download failed", e);
        }
    }

    /**
     * 下载文件（简化版本）
     *
     * @param downloadUrl 下载地址
     * @param fileName 文件名
     * @return 下载的文件
     * @throws IOException 当下载失败时抛出
     */
    public static File downloadFile(String downloadUrl, String fileName) throws IOException {
        return downloadFile(downloadUrl, fileName, "", "");
    }

    /**
     * 下载流
     *
     * @param downloadUrl 下载地址
     * @param path 文件路径
     * @param userFileName 用户文件名
     * @throws IOException 当下载失败时抛出
     */
    public static void downloadStream(String downloadUrl, String path, String userFileName) throws IOException {
        try {
            Request request = new Request.Builder()
                    .url(downloadUrl)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new IOException("File download failed with code: " + response.code());
                }

                ResponseBody responseBody = response.body();
                if (responseBody == null) {
                    throw new IOException("Response body is null");
                }

                String outFileName = userFileName;
                if (StringUtils.isEmpty(userFileName)) {
                    outFileName = path;
                }

                // 确保目录存在
                File targetDir = new File(path);
                if (!targetDir.exists()) {
                    targetDir.mkdirs();
                }

                File targetFile = new File(targetDir, outFileName);

                try (FileOutputStream outputStream = new FileOutputStream(targetFile);
                     InputStream inputStream = responseBody.byteStream()) {

                    byte[] buffer = new byte[8192];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    outputStream.flush();
                }

                log.info("File downloaded successfully: {}", targetFile.getAbsolutePath());
            }

        } catch (Exception e) {
            log.error("File download failed: {}", e.getMessage(), e);
            throw new IOException("File download failed", e);
        }
    }

    /**
     * 读取输入流到字节数组
     */
    private static byte[] readInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[8192];
        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        return buffer.toByteArray();
    }

    /**
     * 集群DNS实现 - 支持多个URL的负载均衡
     */
    private static class ClusterDns implements okhttp3.Dns {
        @Override
        public List<InetAddress> lookup(String hostname) throws UnknownHostException {
            // 检查是否是集群URL（用分号分隔）
            if (hostname.contains(";")) {
                String[] urls = hostname.split(";");
                List<InetAddress> addresses = new ArrayList<>();

                // 随机选择一个URL进行解析
                String selectedUrl = urls[new Random().nextInt(urls.length)];
                String selectedHostname = extractHostname(selectedUrl);

                // 使用系统DNS解析选中的主机名
                List<InetAddress> systemAddresses = okhttp3.Dns.SYSTEM.lookup(selectedHostname);
                addresses.addAll(systemAddresses);

                return addresses;
            } else {
                // 普通URL，使用系统DNS
                return okhttp3.Dns.SYSTEM.lookup(hostname);
            }
        }

        private String extractHostname(String url) {
            try {
                return new java.net.URL(url).getHost();
            } catch (Exception e) {
                return url;
            }
        }
    }



    /**
     * post请求， body方式
     * @param apiUrl   请求路径
     * @param body  请求内容体
     * @param headerMap 请求头
     * @param anyone 是否任意一个成功即可
     * @return  返回结果
     */
    public static String postBody(String apiUrl, String body, Map<String, String> headerMap, boolean anyone) throws HttpClientException{
        // 检查是否是集群URL（用分号分隔）
        if (apiUrl.contains(";")) {
            String[] urls = apiUrl.split(";");
            List<String> urlList = Arrays.asList(urls);
            Collections.shuffle(urlList);

            for (String url : urlList) {
                try {
                    String response = post(url, body, headerMap);
                    if (anyone) {
                        return response;
                    }
                } catch (Exception e) {
                    log.warn("HTTP request failed for URL: {}, error: {}", url, e.getMessage());
                    if (!anyone) {
                        // 如果不是anyone模式，继续尝试下一个URL
                        continue;
                    }
                }
            }

            // 所有URL都失败了
            if (anyone) {
                throw new HttpClientException("All cluster nodes failed", -1, apiUrl, body);
            }
            return null; // anyone=false且所有节点都失败时返回null
        } else {
            // 单个URL，直接调用post方法
            try {
                return post(apiUrl, body, headerMap);
            } catch (Exception e) {
                throw new HttpClientException(e.getLocalizedMessage(), -1, apiUrl, body);
            }
        }
    }


    public static String postFaas1(String apiUrl, String body, Map<String, String> headerMap) throws HttpClientException{
        String faasCallMode = SpringContext.getProperties("app.k-flow.call-model", "http");
        if (faasCallMode.equals("http")) {
            // HTTP模式，检查是否需要加密
            boolean enableEncrypt = SpringContext.getProperties("faas.enable-encrypt", "true").equalsIgnoreCase("true");
            if (enableEncrypt) {
                // 加密签名模式
                long t1 = System.currentTimeMillis();
                String signSecret = SpringContext.getProperties("faas.signSecret", "JRc7ciSE2n75sJf4bY3RK56Y");
                long timestamp = System.currentTimeMillis();
                String encodedBody = SecurityUtil.encrypt(body, signSecret + timestamp + (timestamp%9));

                String signValue = SecurityUtil.generateHmacSignature(encodedBody, timestamp, signSecret);
                long t2 = System.currentTimeMillis();
//                log.info("Signature generation completed, time: {}ms", t2 - t1);

                FaasRequestBody faasRequestBody = new FaasRequestBody();
                faasRequestBody.setBody(encodedBody);
                faasRequestBody.setTimestamp(timestamp);
                faasRequestBody.setSignature(signValue);

                String encryptedBody = JsonUtil.toJson(faasRequestBody);
                return postBody(apiUrl, encryptedBody, headerMap, true);
            } else {
                // 普通模式
                return postBody(apiUrl, body, headerMap, true);
            }
        } else {
            // 插件模式
            long t1 = System.currentTimeMillis();
            FaasChannelPlugin faasChannelPlugin = faasChannelPluginMap.get(faasCallMode);
            if (faasChannelPlugin != null) {
                try {
                    String result = faasChannelPlugin.send(apiUrl, body, headerMap);
                    long t2 = System.currentTimeMillis();
                    log.info("FaaS plugin [{}] call completed, time: {}ms", faasCallMode, t2 - t1);
                    return result;
                } catch (Exception e) {
                    log.error("FaaS plugin [{}] call failed: {}", faasCallMode, e.getMessage(), e);
                    throw new HttpClientException("FaaS plugin call failed: " + e.getMessage(), -1, apiUrl, body);
                }
            } else {
                log.info("当前模式:{}, 找不到插件", faasCallMode);
                throw new HttpClientException("Faas plugin Not Found!", -1, apiUrl, "");
            }
        }
    }


    /**
     * FaaS插件调用方法（内部实现）
     * @param apiUrl   请求路径
     * @param body  请求内容体
     * @param headerMap 请求头
     * @return  返回结果
     */
    public static String postFaas(String apiUrl, String body, Map<String, String> headerMap) throws HttpClientException{
        String faasCallMode = SpringContext.getProperties("app.k-flow.call-model", "http");
        if (faasCallMode.equals("http")) {
            // HTTP模式，检查是否需要加密
            boolean enableEncrypt = SpringContext.getProperties("faas.enable-encrypt", "false").equalsIgnoreCase("true");
            if (enableEncrypt) {
                long t1 = System.currentTimeMillis();
                // 加密签名模式
                String signSecret = SpringContext.getProperties("faas.signSecret", "JRc7ciSE2n75sJf4bY3RK56Y");
                long timestamp = System.currentTimeMillis();
                String encodedBody = SecurityUtil.encrypt(body, signSecret + timestamp + (timestamp%9));
//                String afterDecrypt = SecurityUtil.decrypt(encodedBody, signSecret);

                String signValue = SecurityUtil.generateHmacSignature(encodedBody, timestamp, signSecret);

                FaasRequestBody faasRequestBody = new FaasRequestBody();
                faasRequestBody.setBody(encodedBody);
                faasRequestBody.setTimestamp(timestamp);
                faasRequestBody.setSignature(signValue);

                String encryptedBody = JsonUtil.toJson(faasRequestBody);
                long t2 = System.currentTimeMillis();
                log.info("Signature generation completed, time: {}ms", t2 - t1);

                return postBody(apiUrl, encryptedBody, headerMap, true);
            } else {
                // 普通模式
                return postBody(apiUrl, body, headerMap, true);
            }
        } else {
            // 插件模式
            long t1 = System.currentTimeMillis();
            FaasChannelPlugin faasChannelPlugin = faasChannelPluginMap.get(faasCallMode);
            if (faasChannelPlugin != null) {
                try {
                    String result = faasChannelPlugin.send(apiUrl, body, headerMap);
                    long t2 = System.currentTimeMillis();
                    log.info("FaaS plugin [{}] call completed, time: {}ms", faasCallMode, t2 - t1);
                    return result;
                } catch (Exception e) {
                    log.error("FaaS plugin [{}] call failed: {}", faasCallMode, e.getMessage(), e);
                    throw new HttpClientException("FaaS plugin call failed: " + e.getMessage(), -1, apiUrl, body);
                }
            } else {
                log.info("当前模式:{}, 找不到插件", faasCallMode);
                throw new HttpClientException("Faas plugin Not Found!", -1, apiUrl, "");
            }
        }
    }

    /**
     * post请求， body方式
     * @param apiUrl   请求路径
     * @param body  请求内容体
     * @param headerMap 请求头
     * @return  返回结果
     */
    public static String postBody(String apiUrl, String body, Map<String, String> headerMap) throws HttpClientException{
        return postBody(apiUrl, body, headerMap, true);
    }

    /**
     * 重试拦截器 - 使用OkHttp Interceptor实现重试机制
     */
    private static class RetryInterceptor implements okhttp3.Interceptor {
        private final int maxRetry;

        public RetryInterceptor(int maxRetry) {
            this.maxRetry = maxRetry;
        }

        @Override
        public okhttp3.Response intercept(okhttp3.Interceptor.Chain chain) throws IOException {
            okhttp3.Request request = chain.request();
            IOException exception = null;
            okhttp3.Response response = null;
            int retryCount = 0;

            while (retryCount < maxRetry) {
                try {
                    response = chain.proceed(request);
                    // 如果响应成功，直接返回
                    if (response.isSuccessful()) {
                        return response;
                    }
                    // 如果响应不成功，关闭响应体并继续重试
                    if (response.body() != null) {
                        response.body().close();
                    }
                    retryCount++;
                    if (retryCount < maxRetry) {
                        log.warn("HTTP request failed with code: {}, retrying... (attempt {}/{})",
                                response.code(), retryCount, maxRetry);
                    }
                } catch (IOException e) {
                    exception = e;
                    retryCount++;
                    if (retryCount < maxRetry) {
                        log.warn("HTTP request failed with exception: {}, retrying... (attempt {}/{})",
                                e.getMessage(), retryCount, maxRetry);
                    }
                }
            }

            // 所有重试都失败了
            if (exception != null) {
                throw exception;
            } else if (response != null) {
                return response;
            } else {
                throw new IOException("All retry attempts failed");
            }
        }
    }


}
