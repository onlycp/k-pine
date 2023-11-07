package com.kingsware.kdev.sys.service.impl;

import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.*;
import com.kingsware.kdev.core.constants.ContentTypeMap;
import com.kingsware.kdev.core.context.KClientContext;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.kflow.KFlowContext;
import com.kingsware.kdev.core.kflow.KdbFlowExecutor;
import com.kingsware.kdev.core.kflow.bean.ErrorResult;
import com.kingsware.kdev.core.kflow.bean.KdbFlowResult;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.SqlWrapper;
import com.kingsware.kdev.core.orm.expression.Op;
import com.kingsware.kdev.core.orm.kdb.KdbRet;
import com.kingsware.kdev.core.plugins.CdnPlugin;
import com.kingsware.kdev.core.plugins.file.FileEncryptPlugin;
import com.kingsware.kdev.core.util.*;
import com.kingsware.kdev.sys.argv.SysFileQueryArgv;
import com.kingsware.kdev.sys.bean.FileDecryptInfo;
import com.kingsware.kdev.sys.manager.FileManager;
import com.kingsware.kdev.sys.manager.NonStaticResourceHttpRequestHandler;
import com.kingsware.kdev.core.model.SysFile;
import com.kingsware.kdev.sys.ret.SysFileRet;
import com.kingsware.kdev.sys.ret.SysStaticFileRet;
import com.kingsware.kdev.sys.service.SysFileService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.util.UriEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipOutputStream;

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
    private final String STATIC_FILE_FOLD = "res";

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
            wrapper.appendSql(" and (app_id = ?)", argv.getAppId());
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
        return uploadFile(files, fileFrom, saveType, false, false);
    }

    @Override
    public List<SysFileRet> uploadStaticFile(MultipartFile[] files, String fileFrom, Boolean unzip) throws Exception {
        if (StringUtils.isEmpty(fileFrom)) {
            fileFrom = ".";
        }
        return uploadFile(files, fileFrom, 1, true, unzip);
    }

    @Override
    public BaseRet<List<SysStaticFileRet>> getStaticFileTree(boolean onlyFolder) throws IOException {
        String path = "file:" + STATIC_FILE_FOLD + File.separator + "/**";
        String rootPath = "file:*.*";
        Resource[] rootResources = SpringContext.getResources(rootPath);
        String rootPathUrl = rootResources[0].getFile().getParent() + "/";
        log.info("rootPathUrl: " + rootPathUrl);

        Resource[] resources = SpringContext.getResources(path);
        List<String> files = new ArrayList<>();
        List<SysStaticFileRet> fileTree = new ArrayList<>();
        if (resources != null) {
            for (Resource resource : resources) {
                SysStaticFileRet sFile = new SysStaticFileRet();
                String fileName = resource.getFilename();
                File file = resource.getFile();
                if (onlyFolder && !file.isDirectory()) {
                    continue;
                }
                String parentName = resource.getFile().getParent();
//                System.out.println(file.isDirectory());
                sFile.setFileFrom(parentName.replace(rootPathUrl, ""));
                sFile.setFileName(fileName);
                sFile.setFileOriginalName(fileName);
                sFile.setIsFold(file.isDirectory());
                sFile.setWhenModified(new Timestamp(file.lastModified()));
                sFile.setSaveType(1);
                if (file.exists() && file.isFile()) {
//                    try (InputStream inputStream = resource.getInputStream()){
//                        sFile.setFileMd5(FileUtils.getMD5(inputStream));
//                    }

                    sFile.setFileExt(FileUtils.getFileExt(fileName));
                    sFile.setFileSize((int) file.length());
                }
                sFile.setFilePath(file.getPath().replace(rootPathUrl, ""));
                fileTree.add(sFile);
//                System.out.println(sFile);
//                System.out.println(resource.getURL());
                log.info("每个文件路径: " + resource.getURL());
                if (fileName != null && resource.isFile()) {
                    files.add(fileName);
                }
            }
        }

        List<SysStaticFileRet> tmpFileTree = new ArrayList<>();
        fileTree.sort((a, b) -> {
            return b.getIsFold().compareTo(a.getIsFold());
        });
        fileTree.stream().forEach(item -> {
            if (item.getIsFold()) {
                List<SysStaticFileRet> list = item.getChildren();
                list = fileTree.stream().filter(sub -> sub.getFileFrom().equals(item.getFilePath())).map(sub -> {
                    sub.setParentFileName(item.getFileName());
                    return sub;
                }).collect(Collectors.toList());

                item.setChildren(list);
            }

        });
        tmpFileTree = fileTree.stream().filter(item -> item.getFileFrom().equals(STATIC_FILE_FOLD)).collect(Collectors.toList());
//        System.out.println(tmpFileTree);
        return BaseRet.success(tmpFileTree);
    }

    @Override
    public void deleteStaticFile(MultiIdArgv argv) throws IOException {
        for (String id: argv.getIds()) {
            String rootPath = "file:" + id;
            if (!FileUtils.checkFileFrom(id)) {
                throw BusinessException.serviceThrow("文件目录命名不符合规范!");
            }
            Resource[] rootResources = SpringContext.getResources(rootPath);
            if (rootResources == null && rootResources.length == 0) {
                continue;
            }
            Resource resource = rootResources[0];
            File file = resource.getFile();
            if (file == null || !file.exists()) {
                continue;
            }
            if (!file.delete() && file.isDirectory()) {
                Path path = Paths.get(resource.getURL().getPath());
                Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        Files.delete(file);
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                        Files.delete(dir);
                        return FileVisitResult.CONTINUE;
                    }
                });
            }

        }
    }

    private List<SysFileRet> uploadFile(MultipartFile[] files, String fileFrom, Integer saveType, boolean isLocal, boolean unzip) throws Exception {
//        if (StringUtils.isNotEmpty(fileFrom)) {
//            if (fileFrom.contains(".")) {
//                throw BusinessException.serviceThrow("参数不合法");
//            }
//        }
        // 判断是否需要加密
        String widthEncryptString = ServletUtil.request().getParameter("withDecrypt");
        int widthEncrypt = 0;
        if (StringUtils.isNotEmpty(widthEncryptString)) {
            widthEncrypt = Integer.parseInt("widthEncryptString");
        }

        List<SysFileRet> retList = new ArrayList<>();
        // 如果是自动转为faas的，那么新上传的文件也改为faas
        if (!isLocal && isFileLocalToFaas() && saveType == 1) {
            saveType = 2;
        }
        // 遍历处理文件
        for (MultipartFile file: files) {
            if (!FileUtils.checkFileNaming(file.getOriginalFilename())) {
                throw BusinessException.serviceThrow("文件名命名不符合规范，请重新命名后再上传!");
            }
            if (!FileUtils.checkFileFrom(fileFrom)) {
                throw BusinessException.serviceThrow("文件目录命名不符合规范!");
            }
            String fileExt = FileUtils.getFileExt(file.getOriginalFilename());
            if (!FileUtils.checkFileExt(fileExt)) {
                throw BusinessException.serviceThrow(FileUtils.getFileExt(file.getOriginalFilename()) + "文件后缀名不在上传文件白名单中!");
            }
            try (InputStream inputStream = file.getInputStream()){
                SysFile sysFile = FileManager.getInstance().register(inputStream, file.getOriginalFilename(), (int)file.getSize(), fileFrom, saveType, isLocal ? STATIC_FILE_FOLD : getBasePath(), isLocal);
                if (sysFile == null) {
                    throw BusinessException.serviceThrow("文件保存失败");
                }
                if (unzip && fileExt.equalsIgnoreCase("zip")) {
                    ZipUtils.unzip(STATIC_FILE_FOLD, STATIC_FILE_FOLD + sysFile.getFilePath());
                }
                retList.add(BeanUtils.copyObject(sysFile, SysFileRet.class));
            }
        }

        return retList;
    }


    @Override
    public void download(String id) throws ServletException, IOException {
        downloadByPath(id, false);
    }

    @Override
    public void downloadStaticFile(String path) throws ServletException, IOException {
        downloadByPath(path, true);
    }



       /**
     * 验证文件权限
     * @param id 文件ID
     * @param path 文件路径
     * @return 验证结果，true为验证通过，false为验证不通过
     */
       @SuppressWarnings("all")
    private void validateFilePermission(String id, String path) {
        // 拼接完整的文件路径
        String basePath = SpringContext.getProperties("file.bash-path", "");
        if (StringUtils.isNotEmpty(basePath)) {
            basePath += File.separator;
        }
        String fullPath = basePath  + path;
        // 获取系统路径
        String systemPath = new File(fullPath).getAbsolutePath();
        // 创建文件解密信息对象
        FileDecryptInfo fileDecryptInfo = new FileDecryptInfo();
        // 设置文件路径
        fileDecryptInfo.setPath(systemPath);
        // 设置文件ID
        fileDecryptInfo.setId(id);
        // 设置路径（与上面重复）
        fileDecryptInfo.setPath(path);
        // 获取文件权限流程ID
        GroupProperties groupProperties = SpringContext.getGroupProperties("app.file-permission");
        if (!groupProperties.isEnable()) {
            return;
        }
        // 获取所有路径配置
        Set<String> paths = new HashSet<>();
        for (String key: groupProperties.getValues().keySet()) {
            String subKey = key.substring(key.indexOf("app.file-permission.") + 1);
            String dir = basePath + subKey;
            File file = new File(fullPath);
            File directory = new File(dir);
            if (file.getAbsolutePath().startsWith(directory.getAbsolutePath())) {
                String flowId = groupProperties.stringValue(key, "");
                if (StringUtils.isNotEmpty(flowId)) {
                    // 获取视图模型
                    KFlowContext context = KFlowContext.createBaseContext( "{}",  "{}");
                    Map<String,Object> params = JsonUtil.beanToMap(fileDecryptInfo);

                    if(KClientContext.getContext().getUserInfo() == null) {
                        throw BusinessException.serviceThrow("此文件需要登录才允许访问");
                    }
                    // 执行文件权限流程
                    KdbFlowResult result = KdbFlowExecutor.getInstance().execute(flowId, "", params, context, false, false);
                    // 获取结果数据
                    Map<String, Object> data = (Map<String, Object>)result.getData();
                    boolean success = data.get("success") == null? false : (boolean)data.get("success");;
                    if (!success) {
                        throw BusinessException.serviceThrow("您无权限下载此文件");
                    }
                }
            }
        }


    }



    /**
     * 下载单个文件
     * @param path
     * @throws ServletException
     * @throws IOException
     */
    private void downloadByPath(String path, boolean isStatic) throws ServletException, IOException {
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
        String userFileName = request.getParameter("fileName");
        String contentType = "application/octet-stream";
        File tmpFile = null;
        if (file == null) {
            // 如果是ID格式，不是路径格式，则文件已不存在
            if (StringUtils.isUuid(path)) {
                throw BusinessException.serviceThrow("文件已被删除！");
            }
            // 通过ID找不到文件，按路径处理，共有2处方式，1种是本地文件，2种是FAAS文件
            // 在本地找是否存在文件
            File localFile = getLocalFile(path);
            if (isStatic) {
                localFile = getStaticFile(path);
                if (localFile.isDirectory()) {
                    File zipFile = ZipUtils.zipDirectory(localFile, localFile.getAbsolutePath());
                    ServletUtil.responseFile(zipFile, localFile.getName() + ".zip");
                    return;
                }
            }
            if (localFile.exists()) {
                fileRealPath = localFile.getAbsolutePath();
                fileName = localFile.getName();
            } else {
                // 检测文件权限
                validateFilePermission(null, path);
                //
                if (!FileTypeChecker.isAudioFile(path) && !FileTypeChecker.isVideoFile(path)) {
                    FaasFileInfo fileInfo = getFaasFileInfo(path);
                    DB.kdbApi().downloadStream(fileInfo.getPath(), fileInfo.getName(), userFileName);
                    return;
                }
                else {
                    File faasFile = getFaasFile(path);
                    if (faasFile != null && faasFile.exists()) {
                        fileRealPath = faasFile.getAbsolutePath();
                        tmpFile = faasFile;
                    }
                }

            }
        } else {
            // 检测文件权限
            validateFilePermission(file.getId(),  file.getFilePath());
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
                if (!FileTypeChecker.isAudioFile(file.getFilePath()) && !FileTypeChecker.isVideoFile(file.getFilePath())) {
                    FaasFileInfo fileInfo = getFaasFileInfo(file.getFilePath());
                    DB.kdbApi().downloadStream(fileInfo.getPath(), fileInfo.getName(), userFileName);
                    return;
                }
                else {
                    File faasFile = getFaasFile(file.getFilePath());
                    if (faasFile != null && faasFile.exists()) {
                        fileRealPath = faasFile.getAbsolutePath();
                        tmpFile = faasFile;
                    }
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
            if (tmpFile != null) {
                request.setAttribute(NonStaticResourceHttpRequestHandler.ATTR_FILE, tmpFile);
            }
        }
        nonStaticResourceHttpRequestHandler.handleRequest(request, response);
    }

    /**
     * 根据给定的路径获取本地文件
     * @param path 文件路径
     * @return 对应的本地文件对象
     */
    private File getLocalFile(String path) {
        String absFilePath = getBasePath().endsWith("/") ? getBasePath() : getBasePath() + File.separator + path;
        return new File(absFilePath);
    }

    private File getStaticFile(String path) {
       return new File(path);
    }

    public File getFaasFile(String path) {

        FaasFileInfo fileInfo = getFaasFileInfo(path);
        File tempFile = DB.kdbApi().downloadFile(fileInfo.getPath(), fileInfo.getName(), "", fileInfo.getFileExt());
        if (tempFile != null && tempFile.exists()) {
            tempFile.deleteOnExit();
//            tempFile.delete();
        }
        return tempFile;
    }

    public FaasFileInfo getFaasFileInfo(String path) {
        String relativePath = "";
        String fileName =  path;
        if (path.contains("/")) {
            int index = path.lastIndexOf("/");
            relativePath = path.substring(0, index);
            fileName = path.substring(index + 1);
        }
        String fileExt = "." + FileUtils.getFileExt(fileName);
        String downloadPath = getBasePath() + "/" + relativePath;
        FaasFileInfo fileInfo = new FaasFileInfo();
        fileInfo.setPath(downloadPath);
        fileInfo.setFileExt(fileExt);
        fileInfo.setName(fileName);
        return fileInfo;
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
     * @param faasFileName  文件名称
     */
    private void downloadFromFaas(String relativePath, String faasFileName, String outFileName) {
        String path = getBasePath() + File.separator + relativePath;
        File tempFile = DB.kdbApi().downloadFile(path,faasFileName);
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
