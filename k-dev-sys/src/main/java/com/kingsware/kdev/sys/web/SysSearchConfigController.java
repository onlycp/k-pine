package com.kingsware.kdev.sys.web;

import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.constants.Version;
import com.kingsware.kdev.sys.argv.SysSearchConfigQueryArgv;
import com.kingsware.kdev.sys.ret.SysRoleRet;
import com.kingsware.kdev.sys.service.SysSearchConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author AndyZheng
 * @version 1.0.0
 * @date 2022.10.8
 */
@Api(value = "搜索配置管理", tags = {"搜索配置管理"})
@RestController
@RequestMapping("/"+ Version.V1 + "/sys-search-config")
public class SysSearchConfigController {

    @Autowired
    private SysSearchConfigService sysSearchConfigService;
    /**
     *  查询
     * @return 分页
     */
    @ApiOperation(value = "查询 " ,notes = "查询")
    @GetMapping("/table/query")
    public BaseRet<PageDataRet<?>> page(SysSearchConfigQueryArgv argv) {
        return BaseRet.success(sysSearchConfigService.query(argv));
    }
}
