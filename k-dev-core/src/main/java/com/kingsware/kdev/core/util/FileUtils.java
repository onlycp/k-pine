package com.kingsware.kdev.core.util;

import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.encrypt.EncryptProperties;
import com.kingsware.kdev.core.properties.FileProperties;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 文件处理工具类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/31 4:30 下午
 */
@Slf4j
@Component
public class FileUtils {
    /** hex码 **/
    private static final char[] hexCode = "0123456789ABCDEF".toCharArray();

    private FileUtils(){
    }

//    @Value("${}")
//    public void setFileExtWriteList(String writeList) {
//
//    }
//    static {
//        FileProperties fileProperties = SpringContext.getBean(FileProperties.class);
//        List writeList = new ArrayList<String>();
//        if (StringUtils.isNotEmpty(fileProperties.getWriteList())) {
//            String[] arr = fileProperties.getWriteList().trim().split(",");
//            writeList = Arrays.asList(arr);
//        }
//        FileUtils.fileExtWriteList = writeList;
//    }

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
     * 从输入流中读取文本内容
     * @param stream 输入流
     * @return  按行返回
     */
    public static List<String> readAllLine(InputStream stream) {
        InputStreamReader isr = null;
        BufferedReader br = null;
        List<String> lines = new ArrayList<>();
        try {
            isr = new InputStreamReader(stream);
            br = new BufferedReader(isr);
            String str;
            // 通过readLine()方法按行读取字符串
            while ((str = br.readLine()) != null) {
                lines.add(str);
            }
        } catch (IOException e) {
            log.error("error", e);
        } finally {
            // 统一在finally中关闭流，防止发生异常的情况下，文件流未能正常关闭
            try {
                if (br != null) {
                    br.close();
                }
                if (isr != null) {
                    isr.close();
                }
            } catch (IOException e) {
                log.error("error", e);
            }
        }
        return lines;

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

    /**
     *
     * @param fileName
     * @return
     */
    public static boolean checkFileNaming(String fileName) {
        Pattern pattern = Pattern.compile(".*[/\\\\:*?|]|(\\.\\.).*");
        return !pattern.matcher(fileName).matches();
    }

    public static boolean checkFileExt(String extName) {
        FileProperties fileProperties = SpringContext.getBean(FileProperties.class);
        List writeList = new ArrayList<String>();
        if (StringUtils.isNotEmpty(fileProperties.getWriteList())) {
            String[] arr = fileProperties.getWriteList().trim().split(",");
            writeList = Arrays.asList(arr);
        }
        return writeList.contains(extName.toLowerCase());
    }

    public static boolean checkFileFrom(String fileFrom) {
        boolean flag = true;
        flag = !fileFrom.startsWith("/");
        if (!flag) {
            return false;
        }
        Pattern pattern = Pattern.compile(".*[:*?|]|(\\.\\.).*");
        return !pattern.matcher(fileFrom).matches();
    }

    /**
     * 判断是否是文本文件
     * @param f
     * @return
     * @throws IOException
     */
    public static boolean isTextFile(File f) {
        try {
            String type = Files.probeContentType(f.toPath());
            if (type == null) {
                //type couldn't be determined, assume binary
                return false;
            } else if (type.startsWith("text")) {
                return true;
            } else {
                //type isn't text
                return false;
            }
        }
        catch (Exception e) {
            log.error("error", e);
        }
        return false;

    }

    /**
     * 写入行
     * @param list
     * @param filePath
     */
    public static void writeLineToTxt(List<String> list,String filePath){
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<list.size();i++){
            sb.append(list.get(i)).append("\r\n");
        }
        try{
            @Cleanup FileWriter writer = new FileWriter(filePath, true);
            @Cleanup BufferedWriter bw = new BufferedWriter(writer);
            bw.write(sb.toString());
        }catch(Exception e){
            log.error("error", e);
        }

    }

    /**
     * 目录拷贝
     * @param sourcePath
     * @param newPath
     * @throws IOException
     */
    public static void copyDir(String sourcePath, String newPath) throws IOException {

        File file = new File(sourcePath);
        String[] filePath = file.list();
        if (!(new File(newPath)).exists()) {
            (new File(newPath)).mkdirs();
        }
        assert filePath != null;
        for (String s : filePath) {
            if ((new File(sourcePath + File.separator + s)).isDirectory()) {
                copyDir(sourcePath + File.separator + s, newPath + File.separator + s);
            }
            if (new File(sourcePath + File.separator + s).isFile()) {
                copyFile(sourcePath + File.separator + s, newPath + file.separator + s);

            }
        }
    }

    /**
     * 拷贝文件
     * @param oldPath   旧目录
     * @param newPath   新目录
     * @throws IOException
     */
    public static void copyFile(String oldPath, String newPath) throws IOException {

        File oldFile = new File(oldPath);
        File file = new File(newPath);
        @Cleanup FileInputStream in = new FileInputStream(oldFile);
        @Cleanup FileOutputStream out = new FileOutputStream(file);
        byte[] buffer=new byte[2097152];
        while((in.read(buffer)) != -1){
            out.write(buffer);
        }
        out.close();
        in.close();
    }



}
