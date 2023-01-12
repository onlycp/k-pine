package com.kingsware.kdev.sys.web;

import com.kingsware.kdev.core.auth.ApiCode;
import com.kingsware.kdev.core.auth.ApiIgnore;
import com.kingsware.kdev.core.auth.TokenUtil;
import com.kingsware.kdev.core.base.BaseController;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.constants.Version;
import com.kingsware.kdev.core.util.AESUtil;
import com.kingsware.kdev.core.util.ServletUtil;
import com.kingsware.kdev.core.util.VerifyCodeUtils;
import com.kingsware.kdev.sys.argv.*;
import com.kingsware.kdev.sys.ret.SysUserRet;
import com.kingsware.kdev.sys.ret.VerificationCodeRet;
import com.kingsware.kdev.sys.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 演示控制器
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 11:23 上午
 */
@Api(value = "用户管理", tags = {"用户管理"})
@RestController
@RequestMapping("/"+ Version.V1 + "/sys-users")
@Slf4j
public class SysUserController extends BaseController {

    @Resource
    private SysUserService sysUserService;

    /**
     *  登录
     * @return 提示
     */
    @ApiIgnore
    @ApiOperation(value = "登录" ,notes = "登录")
    @PostMapping(value = "login")
    public BaseRet<?> login(@RequestBody Map<String, Object> argv) {
        return BaseRet.success(sysUserService.login(argv));
    }
    /**
     *  登录信息
     * @return 提示
     */
    @ApiOperation(value = "个人信息 " ,notes = "个人信息")
    @GetMapping(value = "info")
    public BaseRet<?> info(HttpServletRequest request) {
        String ip = ServletUtil.getClientIp(request);
        String token = TokenUtil.getTokenString(request);
        return BaseRet.success(sysUserService.getBaseUserInfo(token, ip));
    }
    /**
     *  登出
     * @return 提示
     */
    @ApiOperation(value = "登出 " ,notes = "登出")
    @PostMapping(value = "logout")
    public BaseRet<?> logout() {
        sysUserService.logout();
        return BaseRet.success();
    }

    /**
     *  查询
     * @return 分页
     */
    @ApiOperation(value = "查询 " ,notes = "查询")
    @GetMapping("/query")
    @ApiCode("user_query")
    public BaseRet<PageDataRet<SysUserRet>> page(SysUserQueryArgv argv) {
        return BaseRet.success(sysUserService.query(argv));
    }

    /**
     * 详细信息
     * @return 详细信息
     */
    @ApiOperation(value = "详情 " ,notes = "详情")
    @GetMapping("/{id}")
    @ApiCode("user_query")
    public BaseRet<SysUserRet> get(@PathVariable String id) {
        return BaseRet.success(sysUserService.get(id));
    }

    /**
     *  新增
     * @return 提示
     */
    @ApiOperation(value = "新增 " ,notes = "新增")
    @PostMapping
    @ApiCode("sysinfo:user:add")
    public BaseRet<?> add(@RequestBody SysUserArgv argv) {
        sysUserService.add(argv);
        return BaseRet.success();
    }


    /**
     *  编辑
     * @return 提示
     */
    @ApiOperation(value = "编辑 " ,notes = "编辑")
    @PutMapping
    @ApiCode("sysinfo:user:edit")
    public BaseRet<?> edit(@RequestBody SysUserArgv argv) {
        sysUserService.edit(argv);
        return BaseRet.success();
    }

    /**
     * 按批量添加用户角色
     * @param argv
     * @return
     */
    @ApiOperation(value = "按批量添加用户角色" ,notes = "按批量添加用户角色")
    @PostMapping("addRoles")
    @ApiCode("sysinfo:user:addRoles")
    public BaseRet<?> addRoles(@RequestBody SysUserArgv argv) {
        sysUserService.addRoles(argv);
        return BaseRet.success();
    }

    /**
     *  删除
     * @return 提示
     */
    @ApiOperation(value = "删除 " ,notes = "删除")
    @PostMapping(value = "/delete")
    @ApiCode("sysinfo:user:remove")
    public BaseRet<?> delete(@RequestBody MultiIdArgv argv) {
        sysUserService.delete(argv);
        return BaseRet.success();
    }

    /**
     *  获取用户基本信息
     * @return 提示
     */
    @ApiOperation(value = "获取用户基本信息 " ,notes = "获取用户基本信息")
    @GetMapping(value = "/get-profile")
    public BaseRet<?> getProfile(HttpServletRequest request) {
        String ip = ServletUtil.getClientIp(request);
        String token = TokenUtil.getTokenString(request);
        return BaseRet.success(sysUserService.getProfile(token, ip));
    }

    /**
     *  修改用户基本信息
     * @return 提示
     */
    @ApiOperation(value = "修改用户基本信息 " ,notes = "修改用户基本信息")
    @PostMapping(value = "/edit-profile")
    public BaseRet<?> editProfile(@RequestBody SysUserProfileArgv argv) {
        sysUserService.editProfile(argv);
        return BaseRet.success();
    }

    /**
     *  修改密码
     * @return 提示
     */
    @ApiOperation(value = "修改密码 " ,notes = "修改密码")
    @PostMapping(value = "/change-password")
    public BaseRet<?> changePassword(HttpServletRequest request, @RequestBody SysUserChangePasswordArgv argv) {
        String ip = ServletUtil.getClientIp(request);
        String token = TokenUtil.getTokenString(request);
        sysUserService.changePassword(argv, token, ip);
        return BaseRet.success();
    }

    /**
     *  修改密码
     * @return 提示
     */
    @ApiOperation(value = "重置密码 " ,notes = "重置密码")
    @PostMapping(value = "/reset-password")
    @ApiCode("sysinfo:user:resetpwd")
    public BaseRet<?> resetPassword(HttpServletRequest request, @RequestBody SysUserResetPasswordArgv argv) {
        sysUserService.resetPassword(argv);
        return BaseRet.success();
    }

    @ApiOperation(value = "登录会话数量 " ,notes = "登录会话数量")
    @GetMapping("/session-count/{username}")
    @ApiIgnore
    public BaseRet<Long> getOnlineSessionCount(@PathVariable String username) {
        return BaseRet.success(sysUserService.onlineCount(username));
    }

    /**
     *  获取用户基本信息
     * @return 提示
     */
    @ApiOperation(value = "加密转换 " ,notes = "加密转换")
    @GetMapping(value = "/encryptChange/{from}/{to}/{secret}")
    @ApiIgnore
    public BaseRet<?> encryptChange(@PathVariable String from, @PathVariable String to, @PathVariable String secret)  {
        sysUserService.encryptChange(from, to, secret);
        return BaseRet.success();
    }

    /**
     *  获取用户基本信息
     * @return 提示
     */
    @GetMapping(value = "/ping")
    @ApiIgnore
    public BaseRet<?> ping() {
        sysUserService.ping();
        return BaseRet.success();
    }

    /**
     *  获取用户基本信息
     * @return 提示
     */
    @PostMapping(value = "/passwordValidate")
    @ApiIgnore
    public BaseRet<?> passwordValidate(String password, String appId) {
        return BaseRet.success(sysUserService.passwordValidate(password, appId));
    }

    @GetMapping("/getVerifyCode")
    @ApiIgnore
    public BaseRet<VerificationCodeRet> getVerificationCode() throws IOException {
        return sysUserService.getVerificationCode();
    }

    @PostMapping("validVerifyCode")
    @ApiIgnore
    public BaseRet<?> validVerificationCode(String uuid, String code, String encryptCode) {
        return sysUserService.validVerificationCode(uuid, code, encryptCode);
    }
}
