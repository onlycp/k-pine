package com.kingsware.kdev.sys.web;

import com.kingsware.kdev.core.auth.ApiCode;
import com.kingsware.kdev.core.auth.ApiIgnore;
import com.kingsware.kdev.core.base.BaseController;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.constants.Version;
import com.kingsware.kdev.sys.argv.SysLoginLogQueryArgv;
import com.kingsware.kdev.sys.ret.SysLoginLogRet;
import com.kingsware.kdev.sys.service.SysLoginLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * 演示控制器
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 11:23 上午
 */
@Api(value = "登录日志", tags = {"登录日志"})
@RestController
@RequestMapping("/"+ Version.V1 + "/sys-login-logs")
public class SysLoginLogController extends BaseController {

    @Resource
    private SysLoginLogService sysLoginLogService;


    /**
     *  查询
     * @return 分页
     */
    @ApiOperation(value = "查询 " ,notes = "查询")
    @GetMapping("/query")
    public BaseRet<PageDataRet<SysLoginLogRet>> page(SysLoginLogQueryArgv argv) {
        return BaseRet.success(sysLoginLogService.query(argv));
    }

    /**
     * 详细信息
     * @return 详细信息
     */
    @ApiOperation(value = "详情 " ,notes = "详情")
    @GetMapping("/{id}")
    public BaseRet<SysLoginLogRet> get(@PathVariable String id) {
        return BaseRet.success(sysLoginLogService.get(id));
    }

    /**
     * 导出
     */
    @ApiOperation(value = "导出 " ,notes = "导出")
    @GetMapping("/export")
    @ApiCode("sysinfo:loginlog:export")
    public void download(SysLoginLogQueryArgv argv) {
        sysLoginLogService.export(argv);
    }

//    @GetMapping("/test-trans")
//    @ApiIgnore
//    public BaseRet<?> testTrans(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        sysLoginLogService.testTran();
//        return BaseRet.success();
//    }

}
