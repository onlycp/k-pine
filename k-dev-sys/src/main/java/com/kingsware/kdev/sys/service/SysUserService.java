package com.kingsware.kdev.sys.service;

import com.kingsware.kdev.core.auth.BaseUserInfo;
import com.kingsware.kdev.core.base.BaseService;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.sys.argv.*;
import com.kingsware.kdev.sys.ret.SysUserLoginRet;
import com.kingsware.kdev.sys.ret.SysUserProfileRet;
import com.kingsware.kdev.sys.ret.SysUserRet;

/**
 * 用户业务类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:35 上午
 */
public interface SysUserService extends BaseService {

    /**
     * 通过id查询
     * @param id    id
     * @return      返回结果
     */
    SysUserRet get(String id);

    /**
     * 新增
     * @param argv 新增
     */
     void add(SysUserArgv argv);

    /**
     * 编辑
     * @param argv 编辑
     */
    void edit(SysUserArgv argv);

    /**
     * 编辑
     * @param argv 编辑
     * @return 查询结果
     */
     PageDataRet<SysUserRet> query(SysUserQueryArgv argv);

    /**
     * 删除
     * @param argv  查询
     */
    void delete(MultiIdArgv argv);

    SysUserLoginRet login(SysUserLoginArgv argv);

    void changePassword(SysUserChangePasswordArgv argv, String token, String ip);

    BaseUserInfo getBaseUserInfo(String s, String token);

    void editProfile(SysUserProfileArgv argv);

    SysUserProfileRet getProfile(String token, String ip);

    /**
     * 登出
     */
    void logout();

    /**
     * 登录的用户数
     * @param username
     * @return
     */
    Long onlineCount(String username);

    /**
     * 密码转换
     * @param from  原加密算法
     * @param to    目标加密算法
     * @param secret    密钥
     */
    void encryptChange(String from, String to, String secret);

    /**
     * 重置密码
     * @param argv  参数
     */
    void resetPassword(SysUserResetPasswordArgv argv);

    /**
     * ping操作
     */
    void ping();
}
