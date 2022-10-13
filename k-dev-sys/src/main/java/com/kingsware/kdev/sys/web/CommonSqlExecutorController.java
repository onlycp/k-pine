package com.kingsware.kdev.sys.web;

import com.kingsware.kdev.core.auth.ApiIgnore;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.constants.Version;
import com.kingsware.kdev.sys.argv.CommonSqlExecutorArgv;
import com.kingsware.kdev.sys.service.CommonSqlExecutorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author AndyZheng
 * @version 1.0.0
 * @date 2022.10.8
 */
@Api(value = "搜索配置管理", tags = {"搜索配置管理"})
@RestController
@RequestMapping("/"+ Version.V1 + "/common/sql")
public class CommonSqlExecutorController {

    @Autowired
    private CommonSqlExecutorService commonSqlExecutorService;

    /**
     *  执行SQL
     * @return 执行SQL
     */
    @ApiOperation(value = "执行SQL " ,notes = "执行SQL")
    @ApiIgnore
    @PostMapping("/execute")
    public BaseRet<?> execute(@RequestBody CommonSqlExecutorArgv argv) {
        return BaseRet.success(commonSqlExecutorService.execute(argv));
    }
}
