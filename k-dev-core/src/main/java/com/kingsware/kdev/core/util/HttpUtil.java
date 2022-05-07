package com.kingsware.kdev.core.util;

import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.exception.HttpClientException;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

/**
 * Http工具类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/24 8:42 上午
 */
@Slf4j
public class HttpUtil {

    /**
     * 私有构造
     */
    private HttpUtil() {}

    /**
     * post请求， body方式
     * @param apiUrl   请求路径
     * @param body  请求内容体
     * @param headerMap 请求头
     * @return  返回结果
     */
    public static String postBody(String apiUrl, String body, Map<String, String> headerMap) throws HttpClientException{
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
            connection.setConnectTimeout(3000);
            connection.setReadTimeout(30000);
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
     * 上传文件
     * @param fileName 文件名
     * @param apiUrl    接口地路
     */
    public static String uploadFile(String apiUrl, String fileName, String formName, InputStream inputStream, String path) {


        try {
            String Boundary = UUID.randomUUID().toString(); // 文件边界
            // 1.开启Http连接
            HttpURLConnection conn = (HttpURLConnection) new URL(apiUrl).openConnection();
            conn.setConnectTimeout(60*1000);
            conn.setDoOutput(true); // 允许输出
            // 2.Http请求行/头
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset", "utf-8");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary="+Boundary);
            // 3.Http请求体
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            if (StringUtils.isEmpty(path)) {
                out.writeUTF("--"+Boundary+"\r\n"
                        +"Content-Disposition: form-data; name=\""+ formName+"\"; filename=\""+ fileName + "\"\r\n"
                        +"Content-Type: application/octet-stream; charset=utf-8"+"\r\n\r\n");
            }
            else {
                out.writeUTF("--"+Boundary+"\r\n"
                        +"Content-Disposition: form-data; path=\""+ path+"\"; name=\""+ formName+"\"; filename=\""+ fileName + "\"\r\n"
                        +"Content-Type: application/octet-stream; charset=utf-8"+"\r\n\r\n");
            }

            byte[] b = new byte[1024];
            int l = 0;
            while((l = inputStream.read(b)) != -1) out.write(b,0,l); // 写入文件
            out.writeUTF("\r\n--"+Boundary+"--\r\n");
            out.flush();
            out.close();
            inputStream.close();
            // 4.Http响应
            String responseBody = getBody(conn.getInputStream());
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // 如果是ok，直接返回body
                return responseBody;
            }
            else {
                throw new HttpClientException(responseBody, conn.getResponseCode(), apiUrl, "");
            }
        }
        catch (Exception e) {
            throw BusinessException.serviceThrow("Faas文件上传失败");
        }

    }

    /**
     * 下载文件
     * @param downloadUrl       下载地址
     * @param fileName      输出流
     */
    public static File downloadFile(String downloadUrl, String fileName) {

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
            File tempFile = FileUtils.createTempFile(fileName);
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
            throw BusinessException.serviceThrow("文件下载失败，异常信息:" + e.getMessage());

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
}
