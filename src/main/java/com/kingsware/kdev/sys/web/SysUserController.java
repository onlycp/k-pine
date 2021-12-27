package com.kingsware.kdev.sys.web;

import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.constants.Version;
import com.kingsware.kdev.sys.argv.SysDemoArgv;
import com.kingsware.kdev.sys.model.to.SysUserLoginTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Api(value = "系统用户", tags = {"系统用户"})
@RestController
@RequestMapping("/"+ Version.V1 + "/sys-user")
@CrossOrigin("*")
public class SysUserController {
    /**
     *  登录
     * @return 提示
     */
    @ApiOperation(value = "登录 " ,notes = "登录")
    @PostMapping(value = "login")
    public BaseRet<?> login(@RequestBody SysUserLoginTO sysUserLoginTO) {
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("token", "test");
        return BaseRet.success(resultMap);
    }
    /**
     *  登录信息
     * @return 提示
     */
    @ApiOperation(value = "登录信息 " ,notes = "登录信息")
    @GetMapping(value = "info")
    public BaseRet<?> info(String token) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("roles", new String[] {"admin"});
        resultMap.put("name", "super admin");
        resultMap.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        resultMap.put("introduction", "I am a super administrator");
        return BaseRet.success(resultMap);
    }
    /**
     *  登出
     * @return 提示
     */
    @ApiOperation(value = "登出 " ,notes = "登出")
    @PostMapping(value = "logout")
    public BaseRet<?> logout() {
        return BaseRet.success();
    }
}
