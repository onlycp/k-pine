package com.kingsware.kdev.core.util;

import com.kingsware.kdev.core.bean.FileEntry;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * zip工具
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/3/21 1:38 下午
 */
@Slf4j
public class ZipUtils {

    /**
     * 文件读取缓冲区大小
     */
    private static final int CACHE_SIZE = 1024;

    private ZipUtils() {}

    /**
     *  压缩文件
     * @param fileList      文件列表
     * @param zipFileName   压缩
     * @return  压缩后的zip文件
     */
    public static File zip(List<FileEntry> fileList, String zipFileName) {
        try {
            Path tmpFile = Files.createTempFile(zipFileName, "zip");
            @Cleanup OutputStream out = new FileOutputStream(tmpFile.toFile());
            @Cleanup BufferedOutputStream bos = new BufferedOutputStream(out);
            @Cleanup ZipOutputStream zos = new ZipOutputStream(bos);
            // 遍历加入压缩文件中

            byte[] cache = new byte[CACHE_SIZE];
            for (FileEntry file: fileList) {
                @Cleanup InputStream is = new FileInputStream(file.getFile());
                @Cleanup BufferedInputStream bis = new BufferedInputStream(is);
                zos.putNextEntry(new ZipEntry(file.getFileName()));
                int nRead;
                while ((nRead = bis.read(cache, 0, CACHE_SIZE)) != -1) {
                    zos.write(cache, 0, nRead);
                }
            }
            return tmpFile.toFile();
        } catch (FileNotFoundException e) {
            log.error("文件不存在，{}", e.getMessage());
        } catch (IOException e) {
            log.error("文件io读写失败, {}", e.getMessage());
        }
        return null;

    }

    public static void unzip(String destDirPath, String path) throws Exception {
        File file = new File(path);
        if (!file.exists()) {
            throw new Exception("源目标路径：[" + path + "] 不存在...");
        }
        // 开始解压
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(file, Charset.forName("GBK"));
            Enumeration<?> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                // 如果是文件夹，就创建个文件夹
                if (entry.isDirectory()) {
                    String dirPath = destDirPath + "/" + entry.getName();
                    File dir = new File(dirPath);
                    dir.mkdirs();
                } else {
                    // 如果是文件，就先创建一个文件，然后用io流把内容copy过去
                    File targetFile = new File(destDirPath + "/" + entry.getName());
                    // 保证这个文件的父文件夹必须要存在
                    if (!targetFile.getParentFile().exists()) {
                        targetFile.getParentFile().mkdirs();
                    }
                    targetFile.createNewFile();
                    // 将压缩文件内容写入到这个文件中
                    InputStream is = zipFile.getInputStream(entry);
                    FileOutputStream fos = new FileOutputStream(targetFile);
                    int len;
                    int BUFFER_SIZE = 1024;
                    byte[] buf = new byte[BUFFER_SIZE];
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }
                    // 关流顺序，先打开的后关闭
                    fos.close();
                    is.close();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("unzip error from ZipUtils", e);
        } finally {
            if (zipFile != null) {
                try {
                    zipFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void zipDirectory(File sourceDir, String parentDir, ZipOutputStream zos) throws IOException {
        File[] files = sourceDir.listFiles();
        byte[] buffer = new byte[1024];

        assert files != null;
        for (File file : files) {
            if (file.isDirectory()) {
                zipDirectory(file, parentDir, zos);
            } else {
                String entryName = file.getAbsolutePath().replace(parentDir, "");
                ZipEntry ze = new ZipEntry(entryName);
                zos.putNextEntry(ze);

                try (FileInputStream fis = new FileInputStream(file)) {
                    int length;
                    while ((length = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, length);
                    }
                }
                zos.closeEntry();
            }
        }
    }


}
