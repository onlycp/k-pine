package com.kingsware.kdev.sys.manager;

import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.kdb.KdbRet;
import com.kingsware.kdev.core.util.FileUtils;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.sys.bean.FaasUploadRet;
import com.kingsware.kdev.sys.model.SysFile;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StreamUtils;

import java.io.*;
import java.nio.file.Files;
import java.util.Base64;

/**
 * 文件管理类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/3/14 11:45 上午
 */
@Slf4j
public class FileManager {

    private static FileManager instance;

    public static FileManager getInstance() {
        if (instance == null) {
            instance = new FileManager();
        }
        return instance;
    }

    private FileManager() {
    }

    /**
     * 注册文件到文件管理
     * @param file      文件
     * @param fileFrom  存储路径
     * @param saveType  保存类型    0:数据库 1:本地磁盘
     * @return 返回文件信息，失败时返回null
     */
    public SysFile register(File file,  String fileFrom, Integer saveType) {
        try {
            if (file.isDirectory()) {
                log.error("File为目录，无法注册到文件管理");
                return null;
            }
            FileInputStream inputStream = new FileInputStream(file);
            return register(inputStream, file.getName(), (int)file.length(), fileFrom, saveType);
        } catch (FileNotFoundException e) {
            log.error("文件不存在，文件路径:{}", file.getAbsolutePath());
            return null;
        }

    }

    /**
     * 注册文件到文件管理器
     * @param inputStream      输入流
     * @param fileFrom     保存的目录名
     * @param saveType  保存类型
     * @return
     */
    @SuppressWarnings("all")
    public SysFile register(InputStream inputStream, String fileName, int fileSize,  String fileFrom, Integer saveType) {
        try {
            SysFile sysFile = new SysFile();
            // 真实文件名
            String realName =  fileName;
            // 文件名
            sysFile.setFileName(realName);
            String fileExt =  FileUtils.getFileExt(realName);
            if (StringUtils.isNotEmpty(fileExt)) {
                fileExt = "." + fileExt;
            }
            realName = realName.substring(0, realName.indexOf(fileExt));
            realName = realName + "_" + System.currentTimeMillis() + fileExt;
            sysFile.setFileOriginalName(realName);
            // 文件扩展名
            sysFile.setFileExt(FileUtils.getFileExt(sysFile.getFileName()));
            // 文件大小
            sysFile.setFileSize(fileSize) ;
            // 文件来源
            sysFile.setFileFrom(fileFrom);
            // 存储方式
            sysFile.setSaveType(saveType);
            // 如果存在方式为数据库，那么这里将文件转为base54
            if(saveType == 0) {
                sysFile.setFileContent(new String(Base64.getEncoder().encode(FileCopyUtils.copyToByteArray(inputStream))));
                // 文件md5码
                sysFile.setFileMd5(FileUtils.getMD5(inputStream));
            }
            else if (saveType == 1) {
                // 相对路径

                String relativePath = File.separator + fileFrom + File.separator;
                // 磁盘存储路径
                // 获取基本路径
                String basePath = SpringContext.getProperties("file.base-path", ".");
                String filePath = basePath +  relativePath;
                File path = new File(filePath);
                boolean status = path.mkdirs();
                // 拷贝文件
                File saveFile = new File(path.getAbsolutePath() + File.separator + realName);
                FileCopyUtils.copy(inputStream, Files.newOutputStream(saveFile.toPath()));
                sysFile.setFileMd5(DigestUtils.md5Hex(Files.newInputStream(saveFile.toPath())));

                // 文件表只存储相对路径
                sysFile.setFilePath(relativePath + realName);
            }
            // faas 存储
            else if (saveType == 2) {
                KdbRet<String> kdbRet =  DB.kdbApi().uploadFile(inputStream, fileName);
                if (!"成功".equals(kdbRet.getMessage())) {
                    throw BusinessException.serviceThrow("文件存储失败,错误信息:" + kdbRet.getMessage());
                }
                FaasUploadRet faasUploadRet = JsonUtil.toBean(kdbRet.getResponseBody(), FaasUploadRet.class);
                sysFile.setFilePath(faasUploadRet.getFileName());
                // 文件md5码
                sysFile.setFileMd5(FileUtils.getMD5(inputStream));

            }
            DB.save(sysFile);
            // 返回id
            return sysFile;
        }
        catch (IOException e) {
            throw BusinessException.serviceThrow("源文件路径不存在, IO异常:" + e.getMessage());
        }
    }
}
