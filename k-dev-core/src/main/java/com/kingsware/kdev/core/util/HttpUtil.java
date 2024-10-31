package com.kingsware.kdev.core.util;

import com.kingsware.kdev.core.context.NonStaticResourceHttpRequestHandler;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.exception.HttpClientException;
import com.kingsware.kdev.core.i18n.I18n;
import com.kingsware.kdev.core.orm.FaasFailRecord;
import com.kingsware.kdev.core.plugins.FaasChannelPlugin;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;

import org.yaml.snakeyaml.util.UriEncoder;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static com.kingsware.kdev.core.context.NonStaticResourceHttpRequestHandler.URL_PATH;
import static org.springframework.util.MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE;

/**
 * Http工具类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/24 8:42 上午
 */
@Slf4j
public class HttpUtil {


    private static final int TIME_OUT = 8 * 1000;                          //超时时间
    private static final String CHARSET = "utf-8";                         //编码格式
    private static final String PREFIX = "--";                            //前缀
    private static final String BOUNDARY = UUID.randomUUID().toString();  //边界标识 随机生成
    private static final String CONTENT_TYPE = "multipart/form-data";     //内容类型
    private static final String LINE_END = "\r\n";

    private static final Map<String, FaasChannelPlugin> faasChannelPluginMap = new HashMap<>();
//
//    public static final okhttp3.MediaType JSON = okhttp3.MediaType.get("application/json");
//
//    private static  OkHttpClient client = null;
//    private static final Dns SYSTEM = Dns.SYSTEM;
//
//
    static {
//        if (client == null) {
//            client = new OkHttpClient.Builder()
//                    .dns(new Dns() {
//
//                        @NotNull
//                        @Override
//                        public List<InetAddress> lookup(@NotNull String hostname) throws UnknownHostException {
//                            List<InetAddress> inetAddresses = Arrays.asList(InetAddress.getAllByName(hostname));
//                            return inetAddresses;
//                        }
//                    })
//
//                    .followRedirects(true)
//                   .build();
//        }
        loadPlugins();
    }

    /**
     * 私有构造
     */
    private HttpUtil() {
    }



    private static void loadPlugins() {
        List<Class<?>> classList =  ClassUtils.getClassesByParentClass("com.kingsware.kdev", FaasChannelPlugin.class);
        for (Class<?> tClass: classList) {
            // 生成实例
            try {
                FaasChannelPlugin plugin = (FaasChannelPlugin) tClass.newInstance();
                faasChannelPluginMap.put(plugin.name(), plugin);
            } catch (Exception e) {
                log.error("定时类扫描初始化失败:{}" , e.getMessage());
            }
        }

    }



    /**
     * Get请求
     * @param apiUrl   请求路径
     * @param headerMap 请求头
     * @return  返回结果
     */
    public static String get(String apiUrl,  Map<String, String> headerMap) throws HttpClientException{
        // http连接
        HttpURLConnection connection = null;

        try {
            URL url = new URL(apiUrl);
            // 根据URL生成HttpURLConnection
            connection = (HttpURLConnection) url.openConnection();
            // 设置body模式
            connection.setDoOutput(true);
            connection.setDoInput(true);
            // 设置post方式
            connection.setRequestMethod("GET");
            // 禁用缓存
            connection.setUseCaches(false);
            // 设置超时时间
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(1000*60*10);
            // 自动执行自定向
            connection.setInstanceFollowRedirects(true);
            // 连接复用
            connection.setRequestProperty("connection", "Keep-Alive");
            // 设置编码
            connection.setRequestProperty("charset", "utf-8");
            //  设置content-type
            connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            // 将额外的请求头加入进来
            if (headerMap != null && !headerMap.isEmpty()) {
                for (Map.Entry<String, String> entry: headerMap.entrySet()) {
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            // 建立连接
            connection.connect();
            // 获取body
            String responseBody = getBody(connection.getInputStream());
            // 获取响应结果
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // 如果是ok，直接返回body
                return responseBody;
            }
            else {
                throw new HttpClientException("Get Request Failed", connection.getResponseCode(), apiUrl, "");
            }
        } catch (IOException e) {
            throw new HttpClientException(e.getLocalizedMessage(), -1, apiUrl, "");
        }
    }


    /**
     * 调用HTTP接口方法
     *
     * 此方法对外提供调用HTTP接口的能力，它会尝试连接到指定的API URL并发送POST请求
     * 如果请求失败并满足重试条件，方法会自动重试，最多重试次数由配置项http.retry.max决定，默认为3次
     *
     * @param apiUrl API的URL地址
     * @param body 请求体内容
     * @param headerMap 请求头信息
     * @return HTTP响应内容
     */
    public static String callHttp(String apiUrl, String body, Map<String, String> headerMap) {
        // 用于记录并控制重试次数的原子整型变量
        AtomicInteger retryCount = new AtomicInteger(0);
        // 调用带有重试机制的私有HTTP调用方法
        return privateCallHttp(apiUrl, body, headerMap, retryCount);
    }

    /**
     * 私有HTTP接口调用方法
     *
     * 此方法实际执行HTTP调用，并处理可能的异常如果发生特定类型的HTTP错误（502），且未超过最大重试次数，
     * 方法会递归地调用自身以重试请求重试逻辑通过传递AtomicInteger retryCount来维护重试计数
     *
     * @param apiUrl API的URL地址
     * @param body 请求体内容
     * @param headerMap 请求头信息
     * @param retryCount 用于记录并控制重试次数的原子整型变量
     * @return HTTP响应内容
     * @throws HttpClientException 当HTTP请求失败且不再满足重试条件时抛出
     */
    private static String privateCallHttp(String apiUrl, String body, Map<String, String> headerMap, AtomicInteger retryCount) {
        // 从配置中读取最大重试次数，默认为3次
        int maxRetryCount = SpringContext.getInt("http.retry.max", 3);
        try {
            // 增加重试计数，表示已尝试过至少一次
            retryCount.getAndIncrement();
            // 执行HTTP POST请求并返回响应
            return doPost(apiUrl, body, headerMap);
        } catch (Exception e) {
            // 检查异常是否由特定的HTTP错误（502）引起，且看是否已超过最大重试次数
            if (e.getMessage().contains("502") && e.getMessage().contains("HTTP POST request") && retryCount.get() <= maxRetryCount) {
                // 如果满足重试条件，递归调用自身重试请求
                return privateCallHttp(apiUrl, body, headerMap, retryCount);
            } else {
                // 如果不再满足重试条件，抛出自定义异常指示调用失败
                throw new HttpClientException(e.getLocalizedMessage(), -1, apiUrl, body);
            }
        }
    }

    public static String doPost(String apiUrl, String body, Map<String, String> headerMap) throws IOException {
        System.setProperty("networkaddress.cache.ttl", "0");
        System.setProperty("networkaddress.cache.negative.ttl", "0");

        HttpURLConnection connection = null;
        OutputStream outputStream = null;
        InputStream inputStream = null;
        BufferedReader reader = null;
        StringBuilder responseBody = new StringBuilder();

        try {
            URL url = new URL(apiUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            // 设置高级DNS解析器
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            // 设置超时时间
            connection.setConnectTimeout(5 * 1000);
            if (apiUrl.contains("/api/async/execute")) {
                connection.setReadTimeout(5 * 1000);
            }
            else {
                String httpReadTimeout = SpringContext.getProperties("app.http-read-timeout", (5 * 60 * 1000)+"");
                connection.setReadTimeout(Integer.parseInt(httpReadTimeout));
            }

            if (headerMap!= null && !headerMap.isEmpty()) {
                headerMap.forEach(connection::setRequestProperty);
            }

            outputStream = connection.getOutputStream();
            outputStream.write(body.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                inputStream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

                String line;
                while ((line = reader.readLine()) != null) {
                    responseBody.append(line);
                }
            } else {
                throw new IOException("HTTP POST request failed with response code: " + responseCode);
            }
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
            if (reader != null) {
                reader.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            if (connection != null) {
                connection.disconnect();

            }
        }
        String response = responseBody.toString();
        if (response.length() > 1024 * 500) {
            // log.error("请求响应内容过大，请检查接口是否有返回大量数据，接口地址：{}, 长度:{}, 参数:{}", apiUrl, response.length(), StringUtils.retrench(body, 500));
        }
        return responseBody.toString();
    }


    /**
     * 调用http集群
     * @param apiUrl    a
     * @param body
     * @param headerMap
     * @param anyone 是否任意一个成功即可
     * @return
     * @throws HttpClientException
     */
    public static String callHttpCluster(String apiUrl, String body, Map<String, String> headerMap, boolean anyone) throws HttpClientException {
        String[] urls = apiUrl.split(";");
        List<String> urlList = new ArrayList<>();
        Collections.addAll(urlList, urls);
        // 打乱，避免每次都同一个节点
        Collections.shuffle(urlList);
        String responseBody = null;
        List<String> failList = new ArrayList<>();
        for (int i = 0; i< urlList.size(); i++) {
            String url = urlList.get(i);
            if (url.contains("edit")) {
                System.currentTimeMillis();
            }
            try {
                responseBody = callHttp(url, body, headerMap);
                if (anyone) {
                    return responseBody;
                }
            }
            catch (Exception e) {
                if (i == (urls.length -1) && anyone) {
                    throw e;
                }
                if (!anyone) {
                    failList.add(url);
                    // 如果全部失败，那么就直接返回异常
                    if (failList.size() == urlList.size()) {
                        throw e;
                    }
                }
            }
        }
        // 如果是要求写所有的，此时有失败，则记录一下
        if (!failList.isEmpty()) {
            List<String> lines = new ArrayList<>();
            for (String failUrl: failList) {
                FaasFailRecord record = new FaasFailRecord();
                record.setUrl(failUrl);
                record.setTime(System.currentTimeMillis());
                record.setHeaderMap(headerMap);
                record.setBody(body);
                lines.add(Base64.getEncoder().encodeToString(JsonUtil.toJson(record).getBytes(StandardCharsets.UTF_8)) );
            }
            // faas的失败日志存储目录
            String pathName = "fail";
            Path path = Paths.get(pathName);
            if (!path.toFile().exists()) {
                path.toFile().mkdirs();
            }
            // 保存文件名
            String filePath = pathName + File.separator + "faas.log";
            FileUtils.writeLineToTxt(lines, filePath);
        }
        return responseBody;
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
     * post请求， body方式
     * @param apiUrl   请求路径
     * @param body  请求内容体
     * @param headerMap 请求头
     * @return  返回结果
     */
    public static String postBody(String apiUrl, String body, Map<String, String> headerMap, boolean anyone) throws HttpClientException{
        String faasCallMode = SpringContext.getProperties("app.k-flow.call-model", "http");
        if (faasCallMode.equals("http")) {
            return callHttpCluster(apiUrl, body, headerMap, anyone);
        }
        else {
            long t1 = System.currentTimeMillis();
            FaasChannelPlugin faasChannelPlugin = faasChannelPluginMap.get(faasCallMode);
            if (faasChannelPlugin != null)  {
                return faasChannelPlugin.send(apiUrl, body, headerMap);
            }
            else {
                log.info("当前模式:{}, 找不到插件", faasCallMode);
                throw new HttpClientException("Faas plugin Not Found!", -1, apiUrl, "");
            }

        }

    }

    /**
     * 上传文件
     * @param fileName 文件名
     * @param apiUrl    接口地路
     */
    public static String uploadFile(String apiUrl, String fileName, String formName, InputStream inputStream, Map<String, Object> formMap, Map<String, String> header) {

        HttpURLConnection conn = null;
        long t1 = System.currentTimeMillis();
        try {
            URL url = new URL(apiUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(600000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);//Post 请求不能使用缓存
            //设置请求头参数
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "UTF-8");
            for (Map.Entry<String, String> entry: header.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
            conn.setRequestProperty("Content-Type", CONTENT_TYPE+";boundary=" + BOUNDARY);

            //上传参数
            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
            //getStrParams()为一个
            Map<String, String> strParams = new HashMap<>();
            formMap.forEach((k, v) -> {
                strParams.put(k, new String(v.toString().getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
            });

            dos.writeBytes(getStrParams(strParams).toString() );
            dos.flush();

            //文件上传
            String stringBuilder = PREFIX + BOUNDARY + LINE_END +
                    "Content-Disposition: form-data; name=\"" + formName + "\"; filename=\""
                    +  new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1) + "\"" + LINE_END +
                    "Content-Type: application/octet-stream" + LINE_END + //此处的ContentType不同于 请求头 中Content-Type
                    "Content-Transfer-Encoding: 8bit" + LINE_END +
                    LINE_END;// 参数头设置完以后需要两个换行，然后才是参数内容
            dos.writeBytes(stringBuilder);
            dos.flush();
            byte[] buffer = new byte[102400];
            int len = 0;
            while ((len = inputStream.read(buffer)) != -1){
                dos.write(buffer,0,len);
            }
            inputStream.close();
            dos.writeBytes(LINE_END);
            //请求结束标志
            dos.writeBytes(PREFIX + BOUNDARY + PREFIX + LINE_END);
            dos.flush();
            dos.close();
            //读取服务器返回信息
            String responseBody = getBody(conn.getInputStream());
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // 如果是ok，直接返回body
                return responseBody;
            }
            else {
                throw new HttpClientException(responseBody, conn.getResponseCode(), apiUrl, "");
            }

        } catch (Exception e) {
            log.error("error", e);
            throw BusinessException.serviceThrow("Faas upload failed");
        }finally {
            long t2 = System.currentTimeMillis();
            log.info("文件{},上传用时: {}", fileName , (t2 - t1));
            if (conn!=null){
                conn.disconnect();
            }
        }
    }


    /**
     * 上传文件
     * @param fileName 文件名
     * @param apiUrl    接口地路
     */
    public static String uploadFile(String apiUrl, String fileName, String formName, InputStream inputStream, String path, Map<String, String> headers) {

        HttpURLConnection conn = null;
        long t1 = System.currentTimeMillis();
        try {
            URL url = new URL(apiUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(600000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);//Post 请求不能使用缓存
            //设置请求头参数
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE+";boundary=" + BOUNDARY);
            for (Map.Entry<String, String> entry: headers.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
            log.info("文件上传, 文件名:{}, 路径:{}", fileName, path);
            //上传参数
            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
            //getStrParams()为一个
            Map<String, String> strParams = new HashMap<>();
            strParams.put("path", new String(path.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
            dos.writeBytes( getStrParams(strParams).toString() );
            dos.flush();

            //文件上传
            String stringBuilder = PREFIX + BOUNDARY + LINE_END +
                    "Content-Disposition: form-data; name=\"" + formName + "\"; filename=\""
                    +  new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1) + "\"" + LINE_END +
                    "Content-Type: application/octet-stream" + LINE_END + //此处的ContentType不同于 请求头 中Content-Type
                    "Content-Transfer-Encoding: 8bit" + LINE_END +
                    LINE_END;// 参数头设置完以后需要两个换行，然后才是参数内容
            dos.writeBytes(stringBuilder);
            dos.flush();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buffer)) != -1){
                dos.write(buffer,0,len);
            }
            inputStream.close();
            dos.writeBytes(LINE_END);
            //请求结束标志
            dos.writeBytes(PREFIX + BOUNDARY + PREFIX + LINE_END);
            dos.flush();
            dos.close();
            //读取服务器返回信息
            String responseBody = getBody(conn.getInputStream());
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // 如果是ok，直接返回body
                return responseBody;
            }
            else {
                throw new HttpClientException(responseBody, conn.getResponseCode(), apiUrl, "");
            }

        } catch (Exception e) {
            log.error("error", e);
            throw BusinessException.serviceThrow("Faas upload failed");
        }finally {
            long t2 = System.currentTimeMillis();
            log.info("文件{},上传用时: {}", fileName , (t2 - t1));
            if (conn!=null){
                conn.disconnect();
            }
        }
    }

    /**
     * 上传文件
     * @param fileName 文件名
     * @param apiUrl    接口地路
     */
    public static String uploadFile(String apiUrl, String fileName, String formName, InputStream inputStream, String path) {
        return uploadFile(apiUrl, fileName, formName, inputStream, path, new HashMap<>());

    }


    /**
     * 对post参数进行编码处理
     * */
    private static StringBuilder getStrParams(Map<String,String> strParams) {
        StringBuilder strSb = new StringBuilder();
        for (Map.Entry<String, String> entry : strParams.entrySet()) {
            strSb.append(PREFIX)
                    .append(BOUNDARY)
                    .append(LINE_END)
                    .append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + LINE_END)
                    .append("Content-Type: text/plain; charset=" + CHARSET + LINE_END)
                    .append("Content-Transfer-Encoding: 8bit" + LINE_END)
                    .append(LINE_END)// 参数头设置完以后需要两个换行，然后才是参数内容
                    .append(entry.getValue())
                    .append(LINE_END);
        }
        return strSb;
    }


    public static File downloadFile(String downloadUrl, String fileName) {
        return downloadFile(downloadUrl, fileName, "", "");
    }

        /**
         * 下载文件
         * @param downloadUrl       下载地址
         * @param fileName      输出流
         */
    public static File downloadFile(String downloadUrl, String fileName, String prefix, String suffix) {

        HttpURLConnection connection = null;
        try {
            URL url = new URL(downloadUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("Charset", "UTF-8");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(60000);
            connection.setDoInput(true);
            @Cleanup InputStream is = connection.getInputStream();
            File tempFile = FileUtils.createTempFile(prefix, suffix, fileName);
            assert tempFile != null;

            @Cleanup FileOutputStream outputStream = new FileOutputStream(tempFile);
            byte[] buf = new byte[100 * 1024];
            int len;
            while ((len = is.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
                ThreadUtils.sleep(0);
            }
            outputStream.flush();
            outputStream.close();
            return tempFile;
        } catch (Exception e) {
            log.error("error", e);
            throw BusinessException.serviceThrow(I18n.t("HttpUtil.fileDownloadFail", "文件下载失败"));

        } finally {
            if (connection != null)
                connection.disconnect();
        }

    }


        /**
     * 获取响应body的内容
     * @param inputStream   输入流
     * @return  body
     */
    private static String getBody(InputStream inputStream) throws IOException {
        // 缓冲区读取器
        BufferedReader reader = null;
        reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        // 循环读取流
        StringBuilder result = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }
        return result.toString();
    }

    /**
     * 获取通道
     * @return  通道
     */
    public static FaasChannelPlugin getFaasChannel(String name) {

        List<Class<?>> classList =  ClassUtils.getClassesByParentClass("com.kingsware.kdev", FaasChannelPlugin.class);
        for (Class<?> tClass: classList) {
            // 生成实例
            try {
                FaasChannelPlugin plugin = (FaasChannelPlugin) tClass.newInstance();
                if (name.equalsIgnoreCase(plugin.name())) {
                    return plugin;
                }
            } catch (Exception e) {
                log.error("定时类扫描初始化失败:{}" , e.getMessage());
            }
        }

        return null;

    }

    public static void downloadStream(String downloadUrl, String path, String fileName) {

        HttpURLConnection connection = null;
        try {
            log.info("流式下载文件开始:" + downloadUrl);
//            ServletUtil.response().sendRedirect("/download/YeCongOA_784c88fa4c504b9285923a6d9bc9c1c3.zip?path=%2Fusr%2Flocal%2Fkfaas%2Fserver%2Fupload%2Fpackage");
            URL url = new URL(downloadUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("Charset", "UTF-8");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(60000);
            connection.setDoInput(true);
            @Cleanup InputStream is = connection.getInputStream();
            if ((!FileTypeChecker.isAudioFile(path) && !FileTypeChecker.isVideoFile(fileName)) || ServletUtil.request().getHeader("Range") == null) {

                String contentLength = connection.getHeaderField("Content-Length");
                    try {
                        ServletUtil.response().setContentLengthLong(Long.parseLong(contentLength));
                    }
                    catch (Exception ignored) {

                    }

                    ServletUtil.response().setContentType(APPLICATION_OCTET_STREAM_VALUE);
                    ServletUtil.response().setHeader("Content-Disposition", "attachment;filename=" + UriEncoder.encode(fileName));
                    byte[] buf = new byte[512 * 1024];
                    int len;
                    OutputStream out = ServletUtil.response().getOutputStream();
                    while ((len = is.read(buf)) != -1) {
                        out.write(buf, 0, len);
                        out.flush();

                        //ServletUtil.response().getOutputStream().flush();
                    }
//            log.info("流式下载文件:" + fileName);
                 ServletUtil.response().getOutputStream().flush();
            }
            else {
//
//                // 多线程下载文件至
                // 创建一个空流
//
                log.info("正在下载视频: {}, {}", path, fileName);
                NonStaticResourceHttpRequestHandler nonStaticResourceHttpRequestHandler = SpringContext.getBean(NonStaticResourceHttpRequestHandler.class);
                log.info("视频大小:{}-{}", connection.getContentLength(), is.available());
                ServletUtil.request().setAttribute(NonStaticResourceHttpRequestHandler.ATTR_FILE, is);
                ServletUtil.request().setAttribute(NonStaticResourceHttpRequestHandler.URL_CONNECTION, connection);
                ServletUtil.request().setAttribute(URL_PATH, downloadUrl );
                nonStaticResourceHttpRequestHandler.handleRequest(ServletUtil.request(), ServletUtil.response());
//                videoHttpRequestHandler.handleRequest(ServletUtil.request(), ServletUtil.response());
//                HttpServletResponse response = ServletUtil.response();
//                String rangeHeader = ServletUtil.request().getHeader("Range");
//                // 设置 Range 请求头
//                if (rangeHeader != null) {
//                    connection.setRequestProperty("Range", rangeHeader);
//                }
//
//                int responseCode = connection.getResponseCode();
//                if (responseCode == HttpURLConnection.HTTP_PARTIAL || responseCode == HttpURLConnection.HTTP_OK) {
//                    String contentType = connection.getContentType();
//                    // 设置响应头
//                    response.setStatus(responseCode);
//                    response.setContentType(contentType != null ? contentType : "application/octet-stream");
//                    String contentLength = connection.getHeaderField("Content-Length");
//                    if (responseCode == HttpURLConnection.HTTP_PARTIAL) {
//                        String contentRange = connection.getHeaderField("Content-Range");
//                        response.setHeader("Content-Range", contentRange);
//                        response.setHeader("Accept-Ranges", "bytes");
//                        response.setHeader("Content-Length", String.valueOf(contentLength));
//                    }
//
//                    // 写入响应
//                    try (InputStream inputStream = connection.getInputStream()) {
//                        byte[] buffer = new byte[8192];  // 8KB 缓冲区
//                        int bytesRead;
//                        try {
//                            while ((bytesRead = inputStream.read(buffer)) != -1) {
//                                response.getOutputStream().write(buffer, 0, bytesRead);
//                            }
//                            response.flushBuffer();  // 确保所有数据都被发送
//                        } catch (ClientAbortException e) {
//                            // 客户端中止异常，记录日志
//                            System.err.println("Client aborted the connection: " + e.getMessage());
//                        } catch (IOException e) {
//                            // 其他IO异常，记录日志并重新抛出
//                            System.err.println("Error while reading/writing video stream: " + e.getMessage());
//                            throw e;
//                        }
//                    }
//                } else {
//                    response.sendError(responseCode, connection.getResponseMessage());
//                }
            }



        } catch (Exception e) {
            log.error("error", e);
            throw BusinessException.serviceThrow(I18n.t("HttpUtil.fileDownloadFail", "文件下载失败"));

        } finally {
            if (connection != null)
                connection.disconnect();
        }
    }

    /**
     * 下载文件的指定范围的部分，并返回输入流
     *
     * @param fileUrl 文件的URL地址
     * @param rangeStart 范围的起始字节位置（包括）
     * @param rangeEnd 范围的结束字节位置（包括）
     * @return InputStream 文件指定范围的输入流
     * @throws IOException 下载过程中可能抛出的IO异常
     */
    public static InputStream downloadPartialFile(String fileUrl, long rangeStart, long rangeEnd) throws IOException {
        URL url = new URL(fileUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Range", "bytes=" + rangeStart + "-" + rangeEnd);

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_PARTIAL || responseCode == HttpURLConnection.HTTP_OK) {
            return new BufferedInputStream(connection.getInputStream());
        } else {
            throw new IOException("Server returned HTTP response code: " + responseCode);
        }
    }


}
