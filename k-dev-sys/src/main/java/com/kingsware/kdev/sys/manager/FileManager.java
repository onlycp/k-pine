package com.kingsware.kdev.sys.manager;

import com.kingsware.kdev.core.constants.PropertiesConstant;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.encrypt.EncryptProperties;
import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.i18n.I18n;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.kdb.KdbRet;
import com.kingsware.kdev.core.plugins.CdnPlugin;
import com.kingsware.kdev.core.util.AESUtil;
import com.kingsware.kdev.core.util.FileUtils;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.sys.bean.FaasUploadRet;
import com.kingsware.kdev.core.model.SysFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;
import java.util.regex.Matcher;

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
            String basePath = SpringContext.getProperties("file.base-path", ".");
            FileInputStream inputStream = new FileInputStream(file);
            return register(inputStream, file.getName(), (int)file.length(), fileFrom, saveType, basePath, false, false, FileUtils.getMD5(new FileInputStream(file)));
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
    public SysFile register(InputStream inputStream, String fileName, int fileSize,  String targetDir, Integer saveType, String basePath) {
       return register(inputStream, fileName, fileSize, targetDir, saveType, basePath, false);
    }

    public SysFile register(InputStream inputStream, String fileName, int fileSize,  String targetDir, Integer saveType, String basePath, boolean withoutChecking) {
        return this.register(inputStream, fileName, fileSize, targetDir, saveType, basePath, withoutChecking, false);
    }

    public SysFile register(InputStream inputStream, String fileName, int fileSize,  String targetDir, Integer saveType, String basePath, boolean withoutChecking, boolean withCrypt, String md5) {
        try {
            boolean isCloseReplaceMode = PropertiesConstant.TRUE.equals(SpringContext.getProperties("file.close-replace-mode", PropertiesConstant.FALSE));
            String fileFrom = targetDir.replaceAll("/", Matcher.quoteReplacement(File.separator));
            SysFile sysFile = new SysFile();
            // 真实文件名
            String realName =  fileName;
            // 文件名
            sysFile.setFileName(realName);
            String fileExt =  FileUtils.getFileExt(realName);

            if (!withoutChecking) {
                if (!FileUtils.checkFileNaming(fileName)) {
                    throw BusinessException.serviceThrow(I18n.t("FileManager.nameFailTip", "文件名命名不符合规范，请重新命名后再上传!"));
                }
                if (!FileUtils.checkFileFrom(fileFrom)) {
                    throw BusinessException.serviceThrow(I18n.t("FileManager.dirCheckFailTip", "文件目录命名不符合规范!") );
                }
                if (!FileUtils.checkFileExt(fileExt)) {
                    throw BusinessException.serviceThrow(fileExt + I18n.t("FileManager.suffixCheckFail", "文件后缀名不在上传文件白名单中!"));
                }
            }

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
                if(fileSize > 2*1024*1024) {
                    throw BusinessException.serviceThrow(I18n.t("FileManager.failSaveToDBTip", "文件过大，不允许存储到数据库!")) ;
                }
                sysFile.setFileContent(new String(Base64.getEncoder().encode(FileCopyUtils.copyToByteArray(inputStream))));
                // 文件md5码
                if (StringUtils.isEmpty(md5)) {
                    sysFile.setFileMd5(FileUtils.getMD5(inputStream));
                }
                else {
                    sysFile.setFileMd5(md5);
                }

            }
            else if (saveType == 1) {
                // 相对路径

                String relativePath = File.separator + fileFrom + File.separator;
                // 磁盘存储路径
                String filePath = basePath +  relativePath;
                File path = new File(filePath);
                boolean status = path.mkdirs();
                // 拷贝文件
                String saveFileName = fileName;
                if (!withoutChecking && isCloseReplaceMode) {
                    saveFileName = realName;
                }
                File saveFile = new File(path.getAbsolutePath() + "/" + saveFileName);
                FileCopyUtils.copy(inputStream, Files.newOutputStream(saveFile.toPath()));
//                sysFile.setFileMd5(FileUtils.getMD5(inputStream));
                if (StringUtils.isEmpty(md5)) {
                    sysFile.setFileMd5(FileUtils.getMD5(Files.newInputStream(saveFile.toPath())));
                }
                else {
                    sysFile.setFileMd5(md5);
                }


                // 文件表只存储相对路径
                sysFile.setFilePath(relativePath + saveFileName);
            }
            // faas 存储
            else if (saveType == 2) {
                // 相对路径
                String relativePath = "/"+ fileFrom + "/";
                // 磁盘存储路径
                String filePath = basePath +  relativePath;
                String saveFileName = fileName;
                if (!withoutChecking && isCloseReplaceMode) {
                    saveFileName = realName;
                }
                // 文件md5码
                sysFile.setFileMd5(md5);

                KdbRet<String> kdbRet =  DB.kdbApi().uploadFile(inputStream, saveFileName, filePath);
                if (!"成功".equals(kdbRet.getMessage())) {
                    throw BusinessException.serviceThrow(I18n.t("FileManager.saveToFaasFail", "文件存储失败,错误信息:{0}", kdbRet.getMessage()));
                }
                FaasUploadRet faasUploadRet = JsonUtil.toBean(kdbRet.getResponseBody(), FaasUploadRet.class);
                sysFile.setFilePath(fileFrom + "/" + faasUploadRet.getFileName());


            }
            else {
                CdnPlugin cdnPlugin = getCdn(saveType);
                if (cdnPlugin != null) {
                    // 磁盘存储路径
                    String filePath = basePath + "/" + targetDir;
                    filePath = filePath.replace("//", "/");
                    String saveFileName = fileName;
                    if (!withoutChecking && isCloseReplaceMode) {
                        saveFileName = realName;
                    }
                    cdnPlugin.upload(inputStream, saveFileName, filePath);
                    sysFile.setFilePath((filePath + "/" + saveFileName).replace("//", "/"));
                    // 文件md5码
                    sysFile.setFileMd5(FileUtils.getMD5(inputStream));
                }
            }
            // 判断是否需要加密
            if (withCrypt) {
                EncryptProperties encryptProperties = SpringContext.getBean(EncryptProperties.class);
                String afterPath = AESUtil.encrypt(sysFile.getFilePath(), encryptProperties.getAes().getSecret());
                sysFile.setFilePath("encrypt:" + afterPath);
            }
            // 静态资源不存储到数据库
            if (!"res".equals(basePath)) {
                DB.save(sysFile);
            }
            // 返回id
            return sysFile;
        }
        catch (IOException e) {
            throw BusinessException.serviceThrow(I18n.t("FileManager.pathNoExistTip", "源文件路径不存在, IO异常:{0}", e.getMessage()));
        }
    }

    public SysFile register(InputStream inputStream, String fileName, int fileSize,  String targetDir, Integer saveType, String basePath, boolean withoutChecking, boolean withCrypt) {
        return this.register(inputStream, fileName, fileSize, targetDir, saveType, basePath, withoutChecking, withCrypt, null);
    }

    /**
     * 获取cdn
     * @param saveType 存储类型
     * @return  cdn插件实例
     */
    public CdnPlugin getCdn(int saveType) {
        List<CdnPlugin> cdnPlugins = SpringContext.getBeansOfType(CdnPlugin.class);
        for (CdnPlugin plugin: cdnPlugins) {
            if (plugin.saveType() == saveType) {
                return plugin;
            }
        }
        return null;
    }
}
