package com.kingsware.kdev.core.util;

import com.kingsware.kdev.core.bean.FileEntry;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.zip.ZipEntry;
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
}
