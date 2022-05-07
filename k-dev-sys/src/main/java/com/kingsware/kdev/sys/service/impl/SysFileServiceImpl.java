package com.kingsware.kdev.sys.service.impl;

import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.FileEntry;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.context.KClientContext;
import com.kingsware.kdev.core.excel.ExcelWorker;
import com.kingsware.kdev.core.excel.RegionDefine;
import com.kingsware.kdev.core.excel.format.RegionDictReverseFormat;
import com.kingsware.kdev.core.excel.format.RegionModelIdFormat;
import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.SqlWrapper;
import com.kingsware.kdev.core.orm.expression.Op;
import com.kingsware.kdev.core.util.*;
import com.kingsware.kdev.sys.argv.SysFileQueryArgv;
import com.kingsware.kdev.sys.manager.FileManager;
import com.kingsware.kdev.sys.model.SysFile;
import com.kingsware.kdev.sys.ret.SysFileRet;
import com.kingsware.kdev.sys.service.SysFileService;
import lombok.Cleanup;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.util.UriEncoder;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.util.*;

/**
 * 文件实现类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:36 上午
 */
@Service
@Slf4j
public class SysFileServiceImpl extends BaseServiceImpl implements SysFileService {

    /** 基础目录 **/
    @Value("${file.base-path:.}")
    private String basePath;

    @Value("${app.file-local-to-faas:false}")
    private boolean fileLocalToFaas;

    @Override
    public SysFileRet get(String id) {
        // 查询model
        SysFile model = DB.findById(SysFile.class, id);
        // 转换成ret对象
        return (SysFileRet) model2Ret(model, SysFileRet.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageDataRet<SysFileRet> query(SysFileQueryArgv argv) {
        // 拼装sql
        SqlWrapper wrapper = new SqlWrapper("select id, file_name, file_original_name, file_size, file_ext, file_md5, file_from, save_type, file_path, who_created, when_created, who_modified, when_modified from sys_file where 1=1 ");
        // 拼装查询sql
        if (StringUtils.isNotEmpty(argv.getFileName())) {
            wrapper.addCondition("file_name", Op.LIKE, "%" +argv.getFileName() +"%");
        }
        if (StringUtils.isNotEmpty(argv.getFileExt())) {
            wrapper.addCondition("file_ext", Op.LIKE, "%" +argv.getFileExt() +"%");
        }
        if (argv.getSaveType() != null) {
            wrapper.addCondition("save_type", Op.EQ, argv.getSaveType());
        }
        if (StringUtils.isNotEmpty(argv.getUploadTimes())) {
            wrapper.between("when_created", argv.getUploadTimes().split(",")[0], argv.getUploadTimes().split(",")[1]);
        }
        if (StringUtils.isNotEmpty(argv.getAppId())) {
            wrapper.appendSql(" and (app_id = ? or app_id is null)", argv.getAppId());
        }
        // 排序
        wrapper.sortBy("when_created desc");
        return (PageDataRet<SysFileRet>) query(wrapper.getSql(), wrapper.getParams(), argv, SysFile.class, SysFileRet.class);
    }

    @Override
    public void delete(MultiIdArgv argv) {
        for (String id: argv.getIds()) {
            DB.delete(SysFile.class, id);
        }
    }

    @SneakyThrows
    @Override
    public List<SysFileRet> upload(MultipartFile[] files, String fileFrom, Integer saveType) {
        List<SysFileRet> retList = new ArrayList<>();
        // 如果是自动转为faas的，那么新上传的文件也改为faas
        if (fileLocalToFaas && saveType == 1) {
            saveType = 2;
        }
        // 遍历处理文件
        for (MultipartFile file: files) {
            SysFile sysFile = FileManager.getInstance().register(file.getInputStream(), file.getOriginalFilename(), (int)file.getSize(), fileFrom, saveType, basePath);
            if (sysFile == null) {
                throw BusinessException.serviceThrow("文件保存失败");
            }
            retList.add(BeanUtils.copyObject(sysFile, SysFileRet.class));
        }

        return retList;
    }


    @Override
    public void download(String id) {
        // 进行url编码
        id = UriEncoder.decode(id);
        SysFile file = DB.findById(SysFile.class, id);
        HttpServletResponse response =  KClientContext.getContext().getResponse();
        if (file == null) {
            if (StringUtils.isUuid(id)) {
                throw BusinessException.serviceThrow("文件已被删除！");
            }
            else {
                String relativePath = "";
                String fileName =  id;
                if (id.contains("/")) {
                    int index = id.lastIndexOf("/");
                    relativePath = id.substring(0, index);
                    fileName = id.substring(index + 1);
                }
                downloadFromFaas(relativePath, fileName, fileName);
            }
        }

        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + UriEncoder.encode(file.getFileName()));
        if (file.getSaveType() == 0) {
            try {
                byte[] content = Base64.getDecoder().decode(file.getFileContent());
                response.setContentLength(content.length);
                response.getOutputStream().write(content);
                response.getOutputStream().flush();
            } catch (IOException e) {
                throw BusinessException.serviceThrow("文件读取失败");
            }
        }
        else if (file.getSaveType() == 1) {
            String absFilePath = basePath + file.getFilePath();
            File localFile = new File(absFilePath);
            ServletUtil.responseFile(localFile, file.getFileName());
        }
        else if (file.getSaveType() == 2) {
            String relativePath = "";
            String faasFileName = file.getFilePath();
            if (file.getFilePath().contains("/")) {
                int index = file.getFilePath().lastIndexOf("/");
                relativePath = file.getFilePath().substring(0, index);
                faasFileName = file.getFilePath().substring(index + 1);
            }
           downloadFromFaas(relativePath, faasFileName,  file.getFileName());
        }
    }

    /**
     * 从Faas下载文件
     * @param relativePath  文件路径
     * @param faasfileName  文件名称
     */
    private void downloadFromFaas(String relativePath, String faasfileName, String outFileName) {
        String path = basePath + File.separator + relativePath;
        File tempFile = DB.kdbApi().downloadFile(path,faasfileName);
        ServletUtil.responseFile(tempFile, outFileName);
        try {
            Files.deleteIfExists(tempFile.toPath());
        } catch (IOException e) {
            log.error("error", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void downloadZip(String ids) {

        List<FileEntry> fileList = new ArrayList<>();
        String[] idArr = ids.trim().split(",");
        // 遍历生成文件
        for (String id: idArr) {
            FileEntry file = buildFile(id);
            // 先判断文件名是否有重复
            String fileName = checkAndModifyName(file.getFileName(), fileList);
            fileList.add(new FileEntry(file.getFile(), fileName));
        }
        String fileName = DateUtils.formatDate(new Date(), DateUtils.DATE_TIME_1)+ ".zip";
        File zipFile = ZipUtils.zip(fileList, fileName);
        if (zipFile == null) {
            throw BusinessException.serviceThrow("文件压缩失败");
        }
        ServletUtil.responseFile(zipFile, fileName);

    }

    /**
     * 检查并修改文件名
     * @param fileName  文件名
     * @param fileList  已有文件列表
     * @return   返回新的文件名
     */
    private String checkAndModifyName(String fileName, List<FileEntry> fileList) {
        if (fileList.isEmpty()) {
            return fileName;
        }
        for (FileEntry file: fileList) {
            if (file.getFileName().equalsIgnoreCase(fileName)) {
                String prefix = "";
                String suffix = "";
                int lastDotIndex = fileName.lastIndexOf(".");
                if (lastDotIndex < 0) {
                    prefix = fileName;
                }
                else {
                    prefix = fileName.substring(0, lastDotIndex);
                    suffix = fileName.substring(lastDotIndex);
                }

                String newFileName = prefix + "(1)";
                if (StringUtils.isNotEmpty(suffix)) {
                    newFileName += ("." + suffix);
                }
                return checkAndModifyName(newFileName, fileList);
            }
        }
        return fileName;
    }

    /**
     * 根据系统保存的文件，创建File
     * @param id    文件id
     * @return  返回文件
     */
    private FileEntry buildFile(String id) {
        // 查找文件
        SysFile sysFile = DB.findById(SysFile.class, id);
        // 如果文件不存在，直接异常
        if (sysFile == null) {
            throw BusinessException.serviceThrow(String.format("文件不存在，文件标识:%s", id));
        }
        // 如果是直接存数据库， 则需要创建文件
        if (sysFile.getSaveType() == 0) {
            byte[] content = Base64.getDecoder().decode(sysFile.getFileContent());
            File tempFile = FileUtils.createTempFile(sysFile.getFileName());
            if (tempFile == null) {
                throw BusinessException.serviceThrow(String.format("临时文件创建失败:%s", sysFile.getFileName()));
            }
            FileUtils.writeToFile(tempFile, content);
            return new FileEntry(tempFile, sysFile.getFileName());
        }
        else {
            String absFilePath = basePath + sysFile.getFilePath();
            File localFile = new File(absFilePath);
            if (!localFile.exists()) {
                throw BusinessException.serviceThrow("文件不存在，可能被移动或删除！");
            }
            return new FileEntry(localFile, sysFile.getFileName());
        }
    }
}
