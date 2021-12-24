package com.kingsware.kdev.core.util;

import com.kingsware.kdev.core.exception.HttpClientException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Http工具类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/24 8:42 上午
 */
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
            connection.setRequestProperty("Content-Type", "application/json");
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
