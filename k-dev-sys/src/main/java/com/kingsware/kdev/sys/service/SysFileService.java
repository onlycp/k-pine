package com.kingsware.kdev.sys.service;

import com.kingsware.kdev.core.base.BaseService;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.sys.argv.SysFileQueryArgv;
import com.kingsware.kdev.sys.ret.SysFileRet;
import com.kingsware.kdev.sys.ret.SysStaticFileRet;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 角色业务类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:35 上午
 */
public interface SysFileService extends BaseService {

    /**
     * 通过id查询
     * @param id    id
     * @return      返回结果
     */
    SysFileRet get(String id);

    /**
     * 编辑
     * @param argv 编辑
     * @return 查询结果
     */
     PageDataRet<SysFileRet> query(SysFileQueryArgv argv);

    /**
     * 删除
     * @param argv  查询
     */
    void delete(MultiIdArgv argv);

    /**
     * 文件上传
     * @param files         文件列表
     * @param fileFrom      来源
     * @param saveType     存储方式
     * @return             文件信息列表
     */
    List<SysFileRet> upload(MultipartFile[] files, String fileFrom, Integer saveType);

    /**
     * 下载文件
     * @param id    文件id
     */
    void download(String id) throws ServletException, IOException;

    /**
     * 下载zip
     * 多个文件压缩后下载
     * @param ids   id列表，用逗号分隔
     */
    void downloadZip(String ids);

    void downloadStaticFile(String path) throws ServletException, IOException;

    String compressStaticZip(String filePaths, String name)  throws IOException;

    /**
     * 从faas里下载文件
     * @param path
     * @return
     */
    File getFaasFile(String path);

    /**
     * 静态文件上传
     * @param files         静态文件列表
     * @param fileFrom      来源
     * @return             文件信息列表
     */
    List<SysFileRet> uploadStaticFile(MultipartFile[] files, String fileFrom, Boolean unzip) throws Exception;

    BaseRet<List<SysStaticFileRet>> getStaticFileTree(boolean onlyFolder) throws IOException;

    void deleteStaticFile(MultiIdArgv argv) throws IOException;

}
