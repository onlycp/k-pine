package com.kingsware.kdev.core.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.security.MessageDigest;

/**
 * 文件处理工具类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/31 4:30 下午
 */
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
        } finally {
            try {
                if (fileInputStream != null){
                    fileInputStream.close();
                }
            } catch (IOException ignored) {
            }
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
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return sbf.toString();
    }
}
