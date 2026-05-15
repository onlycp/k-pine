package com.kingsware.kdev.core.util;

import com.kingsware.kdev.core.bean.FileEntry;
import com.kingsware.kdev.core.i18n.I18n;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
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

    public static void zip(File[] files, String outFilePath, String deleteRootPath) {
        try {
            File outFile = new File(outFilePath);
            File parentFile = outFile.getParentFile();
            if (parentFile != null) {
                parentFile.mkdirs();
            }
            ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(outFilePath)));
            for (File file : files) {
                copyToZip(file, zos, deleteRootPath);
            }
            zos.close();
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }

    private static void copyToZip(File source, ZipOutputStream zos, String deleteRootPath) throws Exception {
        if (source.isDirectory()) {
            File[] files = source.listFiles();
            for (File file : files) {
                copyToZip(file, zos, deleteRootPath);
            }
        } else {
            // deleteRootPath 去除业务上的路径前缀
            String entryPath = source.getPath();
            if (deleteRootPath != null && entryPath.startsWith(deleteRootPath)) {
                entryPath = entryPath.substring(deleteRootPath.length() + 1);
            }
            ZipEntry zipEntry = new ZipEntry(entryPath);
            zos.putNextEntry(zipEntry);
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(source));
            byte[] buffer = new byte[1024];
            int read;
            while ((read = bis.read(buffer, 0, 1024)) >= 0) {
                zos.write(buffer, 0, read);
            }
            zos.closeEntry();
        }
    }
    
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
                zos.closeEntry();
            }
            return tmpFile.toFile();
        } catch (FileNotFoundException e) {
            log.error("文件不存在，{}", e.getMessage());
        } catch (IOException e) {
            log.error("文件io读写失败, {}", e.getMessage());
        }
        return null;

    }

    public static void getZipList(File sourceDir, String parentDir, List<FileEntry> entries) {
        File[] files = sourceDir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    getZipList(file, parentDir, entries);
                } else {
                    FileEntry entry = new FileEntry();
                    entry.setFile(file);
                    String entryName = file.getAbsolutePath().replace(parentDir, "");
                    entryName = entryName.startsWith(File.separator) ? entryName.substring(1) : entryName;
                    entry.setFileName(entryName);
                    entries.add(entry);
                }
            }
        }
    }


    public static File zipDirectory(File sourceDir, String parentDir) throws IOException {
        List<FileEntry> entries = new ArrayList<>();
        getZipList(sourceDir, parentDir, entries);
       return zip(entries, "1");
    }

    public static void unzip(String destDirPath, String path) throws Exception {
        unzip(destDirPath, path, "GBK");
    }

    public static void unzip(String destDirPath, String path, String charset) throws Exception {
        File file = new File(path);
        if (!file.exists()) {
            throw new Exception(I18n.t("ZipUtils.pathNotFound", "源目标路径：[{0}] 不存在...", path));
        }
        // 开始解压
        ZipFile zipFile = null;
        try {
            File destDir = new File(destDirPath).getCanonicalFile();
            if (!destDir.exists()) {
                destDir.mkdirs();
            }
            zipFile = new ZipFile(file, Charset.forName(charset));
            Enumeration<?> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                File targetFile = resolveZipEntry(destDir, entry);
                // 如果是文件夹，就创建个文件夹
                if (entry.isDirectory()) {
                    targetFile.mkdirs();
                    FileUtils.hardenUploadDirectories(destDir, targetFile);
                } else {
                    // 如果是文件，就先创建一个文件，然后用io流把内容copy过去
                    // 保证这个文件的父文件夹必须要存在
                    if (!targetFile.getParentFile().exists()) {
                        targetFile.getParentFile().mkdirs();
                    }
                    FileUtils.hardenUploadDirectories(destDir, targetFile.getParentFile());
                    targetFile.createNewFile();
                    // 将压缩文件内容写入到这个文件中
                    try (InputStream is = zipFile.getInputStream(entry);
                         FileOutputStream fos = new FileOutputStream(targetFile)) {
                        int len;
                        int BUFFER_SIZE = 1024;
                        byte[] buf = new byte[BUFFER_SIZE];
                        while ((len = is.read(buf)) != -1) {
                            fos.write(buf, 0, len);
                        }
                    }
                    FileUtils.hardenUploadedFile(targetFile);
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

    private static File resolveZipEntry(File destDir, ZipEntry entry) throws IOException {
        File targetFile = new File(destDir, entry.getName()).getCanonicalFile();
        String destDirPath = destDir.getPath();
        String targetPath = targetFile.getPath();
        if (!targetPath.equals(destDirPath) && !targetPath.startsWith(destDirPath + File.separator)) {
            throw new IOException("zip entry is outside target dir: " + entry.getName());
        }
        return targetFile;
    }
}
