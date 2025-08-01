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
        }

        // 删除文件或空目录
        if (fileOrDirectory.delete()) {
            System.out.println("File or directory deleted successfully: " + fileOrDirectory.getAbsolutePath());
        } else {
            System.out.println("Failed to delete file or directory: " + fileOrDirectory.getAbsolutePath());
        }
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

    /**
     * 根据MIME类型获取文件扩展名
     * @param mimeType MIME类型
     * @return 文件扩展名
     */
    public static String getExtensionFromMimeType(String mimeType) {
        if (mimeType == null || mimeType.isEmpty()) {
            return "";
        }
        
        switch (mimeType.toLowerCase()) {
            // 图片类型
            case "image/jpeg": return "jpg";
            case "image/jpg": return "jpg";
            case "image/png": return "png";
            case "image/gif": return "gif";
            case "image/bmp": return "bmp";
            case "image/webp": return "webp";
            case "image/svg+xml": return "svg";
            case "image/tiff": return "tiff";
            case "image/x-icon": return "ico";
            case "image/x-ms-bmp": return "bmp";
            case "image/pjpeg": return "jpg";
            case "image/x-png": return "png";
            case "image/x-portable-pixmap": return "ppm";
            case "image/x-portable-graymap": return "pgm";
            case "image/x-portable-bitmap": return "pbm";
            case "image/x-xbitmap": return "xbm";
            case "image/x-xpixmap": return "xpm";
            case "image/x-rgb": return "rgb";
            case "image/x-xwindowdump": return "xwd";
            case "image/x-cmu-raster": return "ras";
            case "image/x-portable-anymap": return "pnm";
            case "image/x-quicktime": return "qtif";
            case "image/x-photoshop": return "psd";
            case "image/x-coreldraw": return "cdr";
            case "image/x-canon-crw": return "crw";
            case "image/x-canon-cr2": return "cr2";
            case "image/x-nikon-nef": return "nef";
            case "image/x-adobe-dng": return "dng";
            case "image/x-sony-arw": return "arw";
            case "image/x-fuji-raf": return "raf";
            case "image/x-olympus-orf": return "orf";
            case "image/x-panasonic-rw2": return "rw2";
            case "image/x-samsung-srw": return "srw";
            case "image/x-pentax-pef": return "pef";
            case "image/x-kodak-dcr": return "dcr";
            case "image/x-kodak-kdc": return "kdc";
            case "image/x-minolta-mrw": return "mrw";
            case "image/x-sigma-x3f": return "x3f";
            case "image/x-hasselblad-3fr": return "3fr";
            case "image/x-hasselblad-fff": return "fff";
            case "image/x-phaseone-iiq": return "iiq";
            case "image/x-leaf-mos": return "mos";
            case "image/x-raw": return "raw";
            case "image/x-dcraw": return "raw";
            
            // 文档类型
            case "application/pdf": return "pdf";
            case "application/msword": return "doc";
            case "application/vnd.openxmlformats-officedocument.wordprocessingml.document": return "docx";
            case "application/vnd.ms-excel": return "xls";
            case "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet": return "xlsx";
            case "application/vnd.ms-powerpoint": return "ppt";
            case "application/vnd.openxmlformats-officedocument.presentationml.presentation": return "pptx";
            case "text/plain": return "txt";
            case "text/html": return "html";
            case "text/css": return "css";
            case "text/javascript": return "js";
            case "application/json": return "json";
            case "application/xml": return "xml";
            
            // 压缩文件类型
            case "application/zip": return "zip";
            case "application/x-rar-compressed": return "rar";
            case "application/x-7z-compressed": return "7z";
            case "application/gzip": return "gz";
            case "application/x-tar": return "tar";
            
            // 音频类型
            case "audio/mpeg": return "mp3";
            case "audio/wav": return "wav";
            case "audio/ogg": return "ogg";
            case "audio/mp4": return "m4a";
            case "audio/flac": return "flac";
            
            // 视频类型
            case "video/mp4": return "mp4";
            case "video/avi": return "avi";
            case "video/mpeg": return "mpeg";
            case "video/quicktime": return "mov";
            case "video/x-msvideo": return "avi";
            case "video/x-ms-wmv": return "wmv";
            case "video/webm": return "webm";
            case "video/ogg": return "ogv";
            
            // 字体类型
            case "font/ttf": return "ttf";
            case "font/otf": return "otf";
            case "font/woff": return "woff";
            case "font/woff2": return "woff2";
            
            // 其他常见类型
            case "application/octet-stream": return "bin";
            case "application/x-shockwave-flash": return "swf";
            case "application/x-java-archive": return "jar";
            case "application/x-executable": return "exe";
            case "application/x-dosexec": return "exe";
            case "application/x-msdownload": return "exe";
            
            default: return "";
        }
    }
}
