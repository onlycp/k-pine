package com.kingsware.kdev.sys.web;

import com.kingsware.kdev.core.auth.ApiIgnore;
import com.kingsware.kdev.core.base.BaseController;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.constants.Version;
import com.kingsware.kdev.sys.argv.SysFileQueryArgv;
import com.kingsware.kdev.sys.ret.SysFileRet;
import com.kingsware.kdev.sys.service.SysFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
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
     * 文件上传
     * @param files         文件列表
     * @param fileFrom      来源
     * @param saveType     存储方式
     * @return             文件信息列表
     */
    @PostMapping("/upload/{fileFrom}/{saveType}")
    @ResponseBody
    @ApiIgnore
    public BaseRet<List<SysFileRet>> upload(@RequestParam("files") MultipartFile[] files, @PathVariable String fileFrom, @PathVariable Integer saveType) {
        return BaseRet.success(sysFileService.upload(files, fileFrom, saveType));
    }

    /**
     * 文件下载
     * @param id         文件id
     */
    @GetMapping("/download/{id}")
    @ApiIgnore
    public void download(@PathVariable String id) {
        sysFileService.download(id);
    }
}
