package com.kingsware.kdev.core.util;

import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.exception.HttpClientException;
import com.kingsware.kdev.core.orm.FaasFailRecord;
import com.kingsware.kdev.core.plugins.FaasChannelPlugin;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

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



    /**
     * 私有构造
     */
    private HttpUtil() {}




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
                throw new HttpClientException("Get请求失败", connection.getResponseCode(), apiUrl, "");
            }
        } catch (IOException e) {
            throw new HttpClientException(e.getLocalizedMessage(), -1, apiUrl, "");
        }
    }


    public static String callHttp(String apiUrl, String body, Map<String, String> headerMap) throws HttpClientException {
        // http连接
        HttpURLConnection connection = null;
        // 输出流
        OutputStream outputStream = null;

        try {
            URL url = new URL(apiUrl);
            // 根据URL生成HttpURLConnection
            connection = (HttpURLConnection) url.openConnection();
            // 设置body模式
            connection.setDoOutput(true);
            connection.setDoInput(true);
            // 设置post方式
            connection.setRequestMethod("POST");
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
            // 设置参数
            outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.write(body.getBytes(StandardCharsets.UTF_8));
            // 关闭输出流
            outputStream.flush();
            outputStream.close();
            // 获取body
            String responseBody = getBody(connection.getInputStream());
            // 获取响应结果
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // 如果是ok，直接返回body
                return responseBody;
            }
            else {
                throw new HttpClientException(body, connection.getResponseCode(), apiUrl, body);
            }
        } catch (IOException e) {
            throw new HttpClientException(e.getLocalizedMessage(), -1, apiUrl, body);
        }
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
        FaasChannelPlugin faasChannelPlugin = getFaasChannel(faasCallMode);
        if (faasChannelPlugin == null) {
            return callHttpCluster(apiUrl, body, headerMap, anyone);
        }
        else {
            return faasChannelPlugin.send(apiUrl, body, headerMap);
        }
    }


    /**
     * 上传文件
     * @param fileName 文件名
     * @param apiUrl    接口地路
     */
    public static String uploadFile(String apiUrl, String fileName, String formName, InputStream inputStream, String path) {


        HttpURLConnection conn = null;
        try {
            URL url = new URL(apiUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setReadTimeout(TIME_OUT);
            conn.setConnectTimeout(TIME_OUT);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);//Post 请求不能使用缓存
            //设置请求头参数
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE+";boundary=" + BOUNDARY);
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
            throw BusinessException.serviceThrow("Faas文件上传失败");
        }finally {
            if (conn!=null){
                conn.disconnect();
            }
        }
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
            byte[] buf = new byte[2 * 1024];
            int len;
            while ((len = is.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.flush();
            outputStream.close();
            return tempFile;
        } catch (Exception e) {
            log.error("error", e);
            throw BusinessException.serviceThrow("文件下载失败");

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
        List<FaasChannelPlugin> faasChannelPlugins = SpringContext.getBeansOfType(FaasChannelPlugin.class);
        for (FaasChannelPlugin plugin: faasChannelPlugins) {
            if (name.equalsIgnoreCase(plugin.name())) {
                return plugin;
            }
        }
        return null;
    }
}
