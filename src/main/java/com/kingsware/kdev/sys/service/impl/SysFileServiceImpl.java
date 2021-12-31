package com.kingsware.kdev.sys.service.impl;

import com.google.common.io.Files;
import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.context.KClientContext;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.SqlWrapper;
import com.kingsware.kdev.core.orm.expression.Op;
import com.kingsware.kdev.core.util.BeanUtils;
import com.kingsware.kdev.core.util.FileUtils;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.sys.argv.SysFileQueryArgv;
import com.kingsware.kdev.sys.model.SysFile;
import com.kingsware.kdev.sys.ret.SysFileRet;
import com.kingsware.kdev.sys.service.SysFileService;
import lombok.SneakyThrows;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

/**
 * 文件实现类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:36 上午
 */
@Service
public class SysFileServiceImpl extends BaseServiceImpl implements SysFileService {

    /** 基础目录 **/
    @Value("${file.base-path:.}")
    private String basePath;

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
            wrapper.addCondition("code", Op.LIKE, "%" +argv.getFileExt() +"%");
        }
        if (StringUtils.isNotEmpty(argv.getUploadTimes())) {
            wrapper.between("when_created", argv.getUploadTimes().split(",")[0], argv.getUploadTimes().split(",")[1]);
        }
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
        // 遍历处理文件
        for (MultipartFile file: files) {
            SysFile sysFile = new SysFile();
            // 文件名
            sysFile.setFileName(file.getOriginalFilename());
            // 真实文件名
            String realName = file.getOriginalFilename() + "_" + System.currentTimeMillis();
            sysFile.setFileOriginalName(realName);
            // 文件扩展名
            sysFile.setFileExt(FileUtils.getFileExt(sysFile.getFileName()));
            // 文件大小
            sysFile.setFileSize((int)file.getSize());
            //  文件md5码
            sysFile.setFileMd5(FileUtils.getMD5(file.getInputStream()));
            // 文件来源
            sysFile.setFileFrom(fileFrom);
            // 存储方式
            sysFile.setSaveType(saveType);
            // 如果存在方式为数据库，那么这里将文件转为base54
            if(saveType == 0) {
                sysFile.setFileContent(new String(Base64.getEncoder().encode(file.getBytes())));
            }
            else if (saveType == 1) {
                String filePath = basePath + File.separator + fileFrom + File.separator;
                File path = new File(filePath);
                if (!path.exists()) {
                    path.mkdirs();
                }
                File saveFile = new File(filePath + realName);
                sysFile.setFilePath(filePath + realName);
                file.transferTo(saveFile);
            }
            DB.save(sysFile);
            retList.add(BeanUtils.copyObject(sysFile, SysFileRet.class));
        }

        return retList;
    }

    @SneakyThrows
    @Override
    public void download(String id) {

        SysFile file = DB.findById(SysFile.class, id);
        HttpServletResponse response =  KClientContext.getContext().getResponse();
        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.getFileSize());
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(file.getFileName().getBytes(), "ISO8859-1"));
        if (file.getSaveType() == 0) {
            response.getOutputStream().write(Base64.getDecoder().decode(file.getFileContent()));
            response.getOutputStream().flush();
        }
        else if (file.getSaveType() == 1) {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file.getFilePath()));
            byte[] buff = new byte[1024];
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                response.getOutputStream().write(buff, 0, i);
                response.getOutputStream().flush();
            }

        }

    }
}
