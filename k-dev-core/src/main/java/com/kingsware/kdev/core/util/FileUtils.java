package com.kingsware.kdev.core.util;

import ch.qos.logback.core.util.FileUtil;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.encrypt.EncryptProperties;
import com.kingsware.kdev.core.properties.FileProperties;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    private static final Pattern FILE_NAME_INVALID_CHAR_PATTERN = Pattern.compile("[/\\\\:*?\"<>|]");
    private static final Pattern FILE_FROM_INVALID_CHAR_PATTERN = Pattern.compile("[:*?\"<>|]");
    private static final Pattern WINDOWS_DRIVE_PATH_PATTERN = Pattern.compile("^[a-zA-Z]:([/\\\\].*)?$");

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
        try (InputStream inputStream = fileInputStream) {
            MessageDigest MD5 = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[8192];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
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


    public static void deleteFileOrDirectory(String path) {
        File fileOrDirectory = new File(path);
        if (!fileOrDirectory.exists()) {
            System.out.println("File or directory does not exist.");
            return;
        }

        if (fileOrDirectory.isDirectory()) {
            // 递归删除目录中的所有内容
            File[] files = fileOrDirectory.listFiles();
            if (files != null) {
                for (File file : files) {
                    deleteFileOrDirectory(file.getAbsolutePath());
                }
            }
        } else {
            // 删除文件或空目录
            if (fileOrDirectory.delete()) {
                System.out.println("File or directory deleted successfully: " + fileOrDirectory.getAbsolutePath());
            } else {
                System.out.println("Failed to delete file or directory: " + fileOrDirectory.getAbsolutePath());
            }
        }
        fileOrDirectory.delete();
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
        if (StringUtils.isEmpty(fileName)) {
            return false;
        }
        String normalizedFileName = fileName.trim();
        if (StringUtils.isEmpty(normalizedFileName) || hasControlChar(normalizedFileName)) {
            return false;
        }
        if (FILE_NAME_INVALID_CHAR_PATTERN.matcher(normalizedFileName).find()) {
            return false;
        }
        if (".".equals(normalizedFileName) || "..".equals(normalizedFileName)) {
            return false;
        }
        try {
            Path path = Paths.get(normalizedFileName).normalize();
            return !path.isAbsolute() && path.getNameCount() == 1;
        } catch (InvalidPathException e) {
            return false;
        }
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
        if (fileFrom == null) {
            return false;
        }
        String normalizedFileFrom = fileFrom.trim();
        if (StringUtils.isEmpty(normalizedFileFrom)) {
            return true;
        }
        if (hasControlChar(normalizedFileFrom)
                || normalizedFileFrom.startsWith("/")
                || normalizedFileFrom.startsWith("\\")
                || WINDOWS_DRIVE_PATH_PATTERN.matcher(normalizedFileFrom).matches()
                || FILE_FROM_INVALID_CHAR_PATTERN.matcher(normalizedFileFrom).find()) {
            return false;
        }

        String unixPath = normalizedFileFrom.replace('\\', '/');
        String[] segments = unixPath.split("/");
        for (String segment : segments) {
            if (StringUtils.isEmpty(segment) || ".".equals(segment) || "..".equals(segment)) {
                return false;
            }
        }
        try {
            Path path = Paths.get(unixPath).normalize();
            if (path.isAbsolute()) {
                return false;
            }
            for (Path segment : path) {
                if ("..".equals(segment.toString())) {
                    return false;
                }
            }
            return true;
        } catch (InvalidPathException e) {
            return false;
        }
    }

    private static boolean hasControlChar(String value) {
        for (int i = 0; i < value.length(); i++) {
            if (Character.isISOControl(value.charAt(i))) {
                return true;
            }
        }
        return false;
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


    public static String readFileToString(File file, Charset utf8) {
        StringBuilder content = new StringBuilder();
        try (InputStream stream = Files.newInputStream(file.toPath());
             BufferedReader reader = new BufferedReader(new InputStreamReader(stream, utf8))) {

            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
                content.append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    public static void writeStringToFile(File file, String replacedFileContent, Charset utf8) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(replacedFileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static byte[] readFileToByteArray(File file) {
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r")) {
            long length = randomAccessFile.length();
            byte[] byteArray = new byte[(int) length];
            randomAccessFile.readFully(byteArray);
            return byteArray;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 追加行
     * @param fileName
     * @param tring fileName,
     */
    public static void appendLine(String fileName, String lineToAppend) {
        try {
            File file = new File(fileName);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if(!file.exists()) {
                Files.createFile(file.toPath());
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
                writer.newLine(); // 添加新行
                writer.write(lineToAppend);
                writer.flush(); // 确保内容被写入磁盘
            } catch (IOException e) {
                System.err.println("Error appending to file: " + e.getMessage());
            }
        }
        catch (Exception e) {
            log.error("error", e);
        }


    }
}
