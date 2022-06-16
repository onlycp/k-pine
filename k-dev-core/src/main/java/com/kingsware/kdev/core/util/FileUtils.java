package com.kingsware.kdev.core.util;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.util.List;
import java.util.stream.Stream;

/**
 * 文件处理工具类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/31 4:30 下午
 */
@Slf4j
public class FileUtils {
    /** hex码 **/
    private static final char[] hexCode = "0123456789ABCDEF".toCharArray();

    private FileUtils(){}

    /**
     * 获取文件扩展名
     * @param fileName  文件名
     * @return      扩展名，如果有，就返回空字符串
     */
    public static String getFileExt(String fileName) {
        if (StringUtils.isEmpty(fileName)) {
            return "";
        }
        String[] tmp = fileName.split("\\.");
        if (tmp.length != 1){
            return tmp[tmp.length-1];
        }
        return "";
    }

    /**
     * 获取一个文件的md5值(可处理大文件)
     * @return md5 value
     */
    public static String getMD5(InputStream fileInputStream) {
        try {
            MessageDigest MD5 = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[8192];
            int length;
            while ((length = fileInputStream.read(buffer)) != -1) {
                MD5.update(buffer, 0, length);
            }
            return toHexString(MD5.digest());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     *
     * @param data
     * @return
     */
    public static String toHexString(byte[] data) {
        StringBuilder r = new StringBuilder(data.length * 2);
        for (byte b : data) {
            r.append(hexCode[(b >> 4) & 0xF]);
            r.append(hexCode[(b & 0xF)]);
        }
        return r.toString();
    }


    /**
     * 读取文本内容
     * @param file  文件路径
     * @return      返回文本内容
     */
    public static String readFileText(File file) {
        BufferedReader reader = null;
        StringBuffer sbf = new StringBuffer();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                sbf.append(tempStr).append(" ");
            }
            reader.close();
            return sbf.toString();
        } catch (IOException e) {
            log.error("error", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    log.error("error", e1);
                }
            }
        }
        return sbf.toString();
    }


    public static String readFile(File file) {
        try {
            List<String> lines  = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
            StringBuilder stringBuffer = new StringBuilder();
            for (String line: lines) {
                stringBuffer.append(line);
            }
            return stringBuffer.toString();
        }
        catch (Exception e) {
            log.error("error", e);
        }
        return "";

    }


    /**
     * 创建临时文件
     * @param fileName  临时文件名
     * @return  返回临时文件
     */
    public static File createTempFile(String fileName) {
        // 获取文件名和后续名
        String prefix = "";
        String suffix = "";
        return createTempFile(prefix, suffix, fileName);
    }



    /**
     * 创建临时文件
     * @param fileName  临时文件名
     * @return  返回临时文件
     */
    public static File createTempFile(String prefix, String suffix, String fileName) {
        // 获取文件名和后续名
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex < 0) {
            prefix = fileName;
        }
        else {
            prefix = fileName.substring(0, lastDotIndex);
            suffix = fileName.substring(lastDotIndex);
        }
        try {
            return File.createTempFile(prefix, suffix);
        } catch (IOException e) {
            log.error("临时文件创建失败，文件名:{}", fileName);
            return null;
        }
    }

    /**
     * 写入文件
     * @param file  目标文件
     * @param buf   buf
     */
    public static void writeToFile(File file, byte[] buf) {
        try {
            Files.write(file.toPath(), buf);
        } catch (IOException e) {
            log.error("文件写入失败，文件名:{}", file.getName());
        }
    }
}
