package com.kingsware.kdev.sys.service.impl;

import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.FileEntry;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.constants.ContentTypeMap;
import com.kingsware.kdev.core.context.KClientContext;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.SqlWrapper;
import com.kingsware.kdev.core.orm.expression.Op;
import com.kingsware.kdev.core.plugins.CdnPlugin;
import com.kingsware.kdev.core.plugins.file.FileEncryptPlugin;
import com.kingsware.kdev.core.util.*;
import com.kingsware.kdev.sys.argv.SysFileQueryArgv;
import com.kingsware.kdev.sys.manager.FileManager;
import com.kingsware.kdev.sys.manager.NonStaticResourceHttpRequestHandler;
import com.kingsware.kdev.core.model.SysFile;
import com.kingsware.kdev.sys.ret.SysFileRet;
import com.kingsware.kdev.sys.service.SysFileService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.util.UriEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

//    /** 基础目录 **/
//    @Value("${file.base-path:.}")
//    private String basePath;
//
//    @Value("${app.file-local-to-faas:false}")
//    private boolean fileLocalToFaas;

    /**
     * 获取基础目录
     * @return
     */
    private String getBasePath() {
        return SpringContext.getProperties("file.base-path", ".");
    }

    private boolean isFileLocalToFaas() {
        String flag = SpringContext.getProperties("app.file-local-to-faas", "false");
        return "true".equalsIgnoreCase(flag);
    }

    private final NonStaticResourceHttpRequestHandler nonStaticResourceHttpRequestHandler;

    public SysFileServiceImpl(NonStaticResourceHttpRequestHandler nonStaticResourceHttpRequestHandler) {
        this.nonStaticResourceHttpRequestHandler = nonStaticResourceHttpRequestHandler;
    }

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

        if (StringUtils.isNotEmpty(fileFrom)) {
            if (fileFrom.contains(".")) {
                throw BusinessException.serviceThrow("参数不合法");
            }
        }
        List<SysFileRet> retList = new ArrayList<>();
        // 如果是自动转为faas的，那么新上传的文件也改为faas
        if (isFileLocalToFaas() && saveType == 1) {
            saveType = 2;
        }
        // 遍历处理文件
        for (MultipartFile file: files) {
            SysFile sysFile = FileManager.getInstance().register(file.getInputStream(), file.getOriginalFilename(), (int)file.getSize(), fileFrom, saveType, getBasePath());
            if (sysFile == null) {
                throw BusinessException.serviceThrow("文件保存失败");
            }
            retList.add(BeanUtils.copyObject(sysFile, SysFileRet.class));
        }

        return retList;
    }


    @Override
    public void download(String id) throws ServletException, IOException {
        downloadByPath(id);
    }

    /**
     * 下载单个文件
     * @param path
     * @throws ServletException
     * @throws IOException
     */
    private void downloadByPath(String path) throws ServletException, IOException {
        if (path.contains("..")) {
            throw BusinessException.serviceThrow("参数不合法");
        }
        // 进行url编码
        path = UriEncoder.decode(path);
        SysFile file = DB.findById(SysFile.class, path);
        HttpServletResponse response =  KClientContext.getContext().getResponse();
        HttpServletRequest request =  KClientContext.getContext().getRequest();
        response.setCharacterEncoding("utf-8");
        String fileRealPath = null;
        String fileName = "";
        String contentType = "application/octet-stream";
        if (file == null) {
            // 如果是ID格式，不是路径格式，则文件已不存在
            if (StringUtils.isUuid(path)) {
                throw BusinessException.serviceThrow("文件已被删除！");
            }
            // 通过ID找不到文件，按路径处理，共有2处方式，1种是本地文件，2种是FAAS文件
            // 在本地找是否存在文件
            File localFile = getLocalFile(path);
            if (localFile != null && localFile.exists()) {
                fileRealPath = localFile.getAbsolutePath();
                fileName = localFile.getName();
            } else {
                // 在FAAS找是否存在文件
                File faasFile = getFaasFile(path);
                if (faasFile != null && faasFile.exists()) {
                    fileRealPath = faasFile.getAbsolutePath();
                    int index = path.lastIndexOf("/");
                    if(index == -1) {
                        fileName = path;
                    }
                    else {
                        fileName = path.substring(index + 1);
                    }


                }
            }
        } else {
            fileName = file.getFileName();
            // 通过ID找得到文件，按数据库里的存储类型读文件
            if (file.getSaveType() == 0) {
                try {
                    byte[] content = Base64.getDecoder().decode(file.getFileContent());
                    response.setHeader("Content-Disposition", "attachment;filename=" + UriEncoder.encode(fileName));
                    response.setContentLength(content.length);
                    response.getOutputStream().write(content);
                    response.getOutputStream().flush();
                } catch (IOException e) {
                    throw BusinessException.serviceThrow("文件读取失败");
                }
            } else if (file.getSaveType() == 1) {
                File localFile = getLocalFile(file.getFilePath());
                if (localFile.exists()) {
                    fileRealPath = localFile.getAbsolutePath();
                }
            } else if (file.getSaveType() == 2) {
                // 在FAAS找是否存在文件
                File faasFile = getFaasFile(file.getFilePath());
                if (faasFile != null && faasFile.exists()) {
                    fileRealPath = faasFile.getAbsolutePath();
                }
            }
            else {
                CdnPlugin cdnPlugin = FileManager.getInstance().getCdn(file.getSaveType());
                if (cdnPlugin != null) {
                    fileRealPath = cdnPlugin.download(file.getFilePath());
                }
            }
        }

//        response.reset();
        String userFileName = request.getParameter("fileName");
        if (StringUtils.isNotEmpty(userFileName)) {
            // 获取后缀
            String[] arr = fileName.split("\\.");
            if (userFileName.contains(".") || arr.length != 2) {
                fileName = userFileName;
            }
            else {
                fileName = userFileName + "." + arr[1];
            }

        }
        response.setHeader("Content-Disposition", "attachment;filename=" + UriEncoder.encode(fileName));

        if (fileRealPath == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        } else {
            // 获取是否加密
            Path path1 = Paths.get(fileRealPath);
            String realFileName = path1.toFile().getName();
            String[] arr = realFileName.split("\\.");
            if (arr.length > 1 ) {
                String encryptMode = arr[arr.length-1];
                // 获取加密插件
                FileEncryptPlugin fileEncryptPlugin = getFileEncryptPlugin(encryptMode);
                log.info("文件下载，模式{}", encryptMode);
                if (fileEncryptPlugin != null) {
                    log.info("文件下载，模式{}, 已找到对应的加密插件", encryptMode);
                    File encryptFile = fileEncryptPlugin.encrypt(path1.toFile());
                    // 设置新的文件
                    fileRealPath = encryptFile.getAbsolutePath();
                }
            }
            contentType = Files.probeContentType(path1);
            response.setContentType(contentType);
            request.setAttribute(NonStaticResourceHttpRequestHandler.ATTR_FILE, fileRealPath);
        }
        nonStaticResourceHttpRequestHandler.handleRequest(request, response);
    }

    private File getLocalFile(String path) {
        String absFilePath = getBasePath() + path;
        return new File(absFilePath);
    }

    public File getFaasFile(String path) {
        String relativePath = "";
        String fileName =  path;
        if (path.contains("/")) {
            int index = path.lastIndexOf("/");
            relativePath = path.substring(0, index);
            fileName = path.substring(index + 1);
        }
        String fileExt = "." + FileUtils.getFileExt(fileName);
        String downloadPath = getBasePath() + "/" + relativePath;
        File tempFile = DB.kdbApi().downloadFile(downloadPath, fileName, "", fileExt);
        if (tempFile != null && tempFile.exists()) {
            tempFile.deleteOnExit();
        }
        return tempFile;
    }

    private String getContentType(String fileName) {
        String contentType = "application/octet-stream";
        String fileExt = FileUtils.getFileExt(fileName);
        if (fileExt != null) {
            contentType = ContentTypeMap.getContentType("." + fileExt);
        }
        return contentType;
    }

    /**
     * 从Faas下载文件
     * @param relativePath  文件路径
     * @param faasfileName  文件名称
     */
    private void downloadFromFaas(String relativePath, String faasfileName, String outFileName) {
        String path = getBasePath() + File.separator + relativePath;
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
        String userFileName = ServletUtil.request().getParameter("fileName");
        if (StringUtils.isNotEmpty(userFileName)) {
            // 获取后缀
            String[] arr = fileName.split("\\.");
            if (userFileName.contains(".") || arr.length != 2) {
                fileName = userFileName;
            }
            else {
                fileName = userFileName + ".zip";
            }

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
        else if (sysFile.getSaveType() == 1) {
            String absFilePath = getBasePath() + sysFile.getFilePath();
            File localFile = new File(absFilePath);
            if (!localFile.exists()) {
                throw BusinessException.serviceThrow("文件不存在，可能被移动或删除！");
            }
            return new FileEntry(localFile, sysFile.getFileName());
        } else if (sysFile.getSaveType() == 2) {
            // 在FAAS找是否存在文件
            File faasFile = getFaasFile(sysFile.getFilePath());
            if (!faasFile.exists()) {
                throw BusinessException.serviceThrow("文件不存在，可能被移动或删除！");
            }
            return new FileEntry(faasFile, sysFile.getFileName());
        }
        throw BusinessException.serviceThrow("文件不存在，可能被移动或删除！");
    }

    /**
     * 获取通道
     * @return  通道
     */
    public static FileEncryptPlugin getFileEncryptPlugin(String name) {
        List<FileEncryptPlugin> plugins = SpringContext.getBeansOfType(FileEncryptPlugin.class);
        for (FileEncryptPlugin plugin: plugins) {
            if (name.equalsIgnoreCase(plugin.name())) {
                return plugin;
            }
        }
        return null;
    }
}
