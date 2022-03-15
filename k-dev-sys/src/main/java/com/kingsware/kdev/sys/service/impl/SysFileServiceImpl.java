package com.kingsware.kdev.sys.service.impl;

import com.kingsware.kdev.core.base.BaseServiceImpl;
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
import com.kingsware.kdev.core.util.BeanUtils;
import com.kingsware.kdev.core.util.FileUtils;
import com.kingsware.kdev.core.util.StringUtils;
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
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.util.UriEncoder;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
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
@Slf4j
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
            wrapper.addCondition("file_ext", Op.LIKE, "%" +argv.getFileExt() +"%");
        }
        if (argv.getSaveType() != null) {
            wrapper.addCondition("save_type", Op.EQ, argv.getSaveType());
        }
        if (StringUtils.isNotEmpty(argv.getUploadTimes())) {
            wrapper.between("when_created", argv.getUploadTimes().split(",")[0], argv.getUploadTimes().split(",")[1]);
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
        // 遍历处理文件
        for (MultipartFile file: files) {
            SysFile sysFile = FileManager.getInstance().register(file.getInputStream(), file.getOriginalFilename(), (int)file.getSize(), fileFrom, saveType);
            if (sysFile == null) {
                throw BusinessException.serviceThrow("文件保存失败");
            }
            retList.add(BeanUtils.copyObject(sysFile, SysFileRet.class));
        }

        return retList;
    }

    @Override
    public void download(String id) {

        SysFile file = DB.findById(SysFile.class, id);
        if (file == null) {
            throw BusinessException.serviceThrow("文件已被删除！");
        }
        HttpServletResponse response =  KClientContext.getContext().getResponse();
        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + UriEncoder.encode(file.getFileName()));
        if (file.getSaveType() == 0) {
            try {
                byte[] content = Base64.getDecoder().decode(file.getFileContent());
                response.setContentLength((int) content.length);
                response.getOutputStream().write(Base64.getDecoder().decode(file.getFileContent()));
                response.getOutputStream().flush();
            } catch (IOException e) {
                throw BusinessException.serviceThrow("文件读取失败");
            }
        }
        else if (file.getSaveType() == 1) {
            String absFilePath = basePath + file.getFilePath();
            File localFile = new File(absFilePath);
            if (!localFile.exists()) {
                throw BusinessException.serviceThrow("文件不存在，可能被移动或删除！");
            }
            FileInputStream ins = null;
            BufferedInputStream bis = null;
            try {
                response.setContentLength((int)new File(absFilePath).length());
                ins = new FileInputStream(absFilePath);
                bis = new BufferedInputStream(ins);
                byte[] buff = new byte[1024];
                int i = 0;
                while ((i = bis.read(buff)) != -1) {
                    response.getOutputStream().write(buff, 0, i);
                    response.getOutputStream().flush();
                }
                response.getOutputStream().close();

            }
            catch (FileNotFoundException e) {
                throw BusinessException.serviceThrow("文件不存在");
            }
            catch (IOException e) {
                throw BusinessException.serviceThrow("文件读取失败");
            }
            finally {
                try {
                    if (ins != null) {
                        ins.close();
                    }
                    if (bis != null) {
                        bis.close();
                    }
                }
                catch (Exception e) {
                    log.info("文件关闭失败");
                }

            }

        }

    }
}
