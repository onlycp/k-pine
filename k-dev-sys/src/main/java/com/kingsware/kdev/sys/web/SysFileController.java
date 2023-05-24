package com.kingsware.kdev.sys.web;

import com.kingsware.kdev.core.auth.ApiIgnore;
import com.kingsware.kdev.core.base.BaseController;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.constants.Version;
import com.kingsware.kdev.core.context.KClientContext;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.sys.argv.SysFileQueryArgv;
import com.kingsware.kdev.sys.ret.SysFileRet;
import com.kingsware.kdev.sys.ret.SysStaticFileRet;
import com.kingsware.kdev.sys.service.SysFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * 演示控制器
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 11:23 上午
 */
@Api(value = "文件管理", tags = {"文件管理"})
@Slf4j
@Controller
@RequestMapping("/"+ Version.V1 + "/sys-files")
public class SysFileController extends BaseController {

    @Resource
    private SysFileService sysFileService;

    /**
     *  查询
     * @return 分页
     */
    @ApiOperation(value = "查询 " ,notes = "查询")
    @GetMapping("/query")
    @ResponseBody
    public BaseRet<PageDataRet<SysFileRet>> page(SysFileQueryArgv argv) {
        return BaseRet.success(sysFileService.query(argv));
    }

    /**
     * 详细信息
     * @return 详细信息
     */
    @ApiOperation(value = "详情 " ,notes = "详情")
    @GetMapping("/{id}")
    @ResponseBody
    public BaseRet<SysFileRet> get(@PathVariable String id) {
        return BaseRet.success(sysFileService.get(id));
    }

    /**
     *  删除
     * @return 提示
     */
    @ApiOperation(value = "删除 " ,notes = "删除")
    @PostMapping(value = "/delete")
    @ResponseBody
    public BaseRet<?> delete(@RequestBody MultiIdArgv argv) {
        sysFileService.delete(argv);
        return BaseRet.success();
    }

    /**
     *  删除
     * @return 提示
     */
    @ApiOperation(value = "删除静态文件" ,notes = "删除静态文件")
    @PostMapping(value = "/deleteStaticFile")
    @ResponseBody
    public BaseRet<?> deleteStaticFile(@RequestBody MultiIdArgv argv) throws IOException {
        sysFileService.deleteStaticFile(argv);
        return BaseRet.success();
    }

    /**
     * 文件上传
     * @param files         文件列表
     * @param fileFrom      来源
     * @param saveType     存储方式
     * @return             文件信息列表
     */
    @ApiOperation(value = "文件上传 " ,notes = "文件上传")
    @PostMapping(value = "/upload/{fileFrom}/{saveType}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    @ApiIgnore
    public BaseRet<List<SysFileRet>> upload(@RequestParam("files") MultipartFile[] files, @PathVariable String fileFrom, @PathVariable Integer saveType) {
        return BaseRet.success(sysFileService.upload(files, fileFrom, saveType));
    }

    /**
     * 文件上传，使
     * @param files         文件列表
     * @param fileFrom      来源
     * @param saveType     存储方式
     * @return             文件信息列表
     */
    @ApiOperation(value = "文件上传 " ,notes = "文件上传")
    @PostMapping(value = "/uploadFile", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    @ApiIgnore
    public BaseRet<List<SysFileRet>> uploadFile(@RequestParam("files") MultipartFile[] files, String fileFrom, Integer saveType) {
//        fileFrom格式要求：aaa/bbb 前后无斜扛
        if (saveType == null) {
            saveType = 1;
        }
        return BaseRet.success(sysFileService.upload(files, fileFrom, saveType));
    }

    /**
     * 静态文件上传，使
     * @param files         文件列表
     * @param fileFrom      来源
     * @return             文件信息列表
     */
    @ApiOperation(value = "静态文件上传 " ,notes = "静态文件上传")
    @PostMapping(value = "/uploadStaticFile", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public BaseRet<List<SysFileRet>> uploadStaticFile(@RequestParam("files") MultipartFile[] files, String fileFrom, boolean unzip) throws Exception {
        return BaseRet.success(sysFileService.uploadStaticFile(files, fileFrom, unzip));
    }

    /**
     * 文件下载
     * @param id         文件id
     */
    @ApiOperation(value = "文件下载 " ,notes = "文件下载")
    @GetMapping("/download/{id}")
    @ApiIgnore
    public void download(@PathVariable String id) throws ServletException, IOException {
        sysFileService.download(id);
    }


    /**
     * 文件下载
     */
    @ApiOperation(value = "文件下载 " ,notes = "文件下载")
    @GetMapping("/download/**")
    @ApiIgnore
    public void downloadWithPath(HttpServletRequest httpServletRequest) throws ServletException, IOException {
        // 获取后面多层目录
        String uri = httpServletRequest.getRequestURI();
        String prefix = httpServletRequest.getContextPath() + "/"+ Version.V1 + "/sys-files" + "/download/";
        String relativePath = uri.replaceFirst(prefix,"");

        sysFileService.download(relativePath);
    }


    /**
     * 文件下载
     */
    @ApiOperation(value = "文件下载 " ,notes = "文件下载")
    @GetMapping("/downloadStatic/**")
    @ApiIgnore
    public void downloadStatic(HttpServletRequest httpServletRequest) throws ServletException, IOException {
        // 获取后面多层目录
        String uri = httpServletRequest.getRequestURI();
        String prefix = httpServletRequest.getContextPath() + "/"+ Version.V1 + "/sys-files" + "/downloadStatic/";
        String relativePath = uri.replaceFirst(prefix,"");

        sysFileService.downloadStaticFile(relativePath);
    }

    /**
     * 文件下载
     * @param ids         文件ids，多个用逗号隔开
     */
    @ApiOperation(value = "文件下载 " ,notes = "文件下载")
    @GetMapping("/downloadZip/{ids}")
    @ApiIgnore
    public void downloadZip(@PathVariable String ids) {
        sysFileService.downloadZip(ids);
    }

    @ApiOperation(value = "静态文件树 " ,notes = "静态文件树")
    @GetMapping("/getStaticFileTree")
    @ResponseBody
    @ApiIgnore
    public BaseRet<List<SysStaticFileRet>> getStaticFileTree(boolean onlyFolder) throws IOException {
        return sysFileService.getStaticFileTree(onlyFolder);
    }
}
