package com.kingsware.kdev.sys.service.impl;

import com.kingsware.kdev.core.auth.AppAuthProperties;
import com.kingsware.kdev.core.auth.BaseUserInfo;
import com.kingsware.kdev.core.auth.TokenUtil;
import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.cache.session.SessionManager;
import com.kingsware.kdev.core.context.KClientContext;
import com.kingsware.kdev.core.encrypt.EncryptProperties;
import com.kingsware.kdev.core.encrypt.EncryptWorker;
import com.kingsware.kdev.core.enums.ApiSystemEnum;
import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.exception.UnauthorizedException;
import com.kingsware.kdev.core.i18n.I18n;
import com.kingsware.kdev.core.model.SysOnlineUser;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.DBChecker;
import com.kingsware.kdev.core.orm.SqlWrapper;
import com.kingsware.kdev.core.orm.expression.Expr;
import com.kingsware.kdev.core.orm.expression.Op;
import com.kingsware.kdev.core.util.AESUtil;
import com.kingsware.kdev.core.util.BeanUtils;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.sys.argv.*;
import com.kingsware.kdev.sys.model.SysUnit;
import com.kingsware.kdev.sys.model.SysRole;
import com.kingsware.kdev.sys.model.SysUser;
import com.kingsware.kdev.sys.model.SysUserRole;
import com.kingsware.kdev.sys.ret.SysUserLoginRet;
import com.kingsware.kdev.sys.ret.SysUserProfileRet;
import com.kingsware.kdev.sys.ret.SysUserRet;
import com.kingsware.kdev.sys.service.SysUserService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户业务实现类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:36 上午
 */
@Service
@Slf4j
public class SysUserServiceImpl extends BaseServiceImpl implements SysUserService {

    @Resource
    private AppAuthProperties appAuthProperties;
    @Resource
    private EncryptProperties encryptProperties;

    @Override
    public SysUserRet get(String id) {
        // 查询model
        SysUser model = DB.findById(SysUser.class, id);
        // 转换成ret对象
        SysUserRet userRet = (SysUserRet) model2Ret(model, SysUserRet.class);
        // 获取部门信息
        if (StringUtils.isNotEmpty(model.getSysUnitId())) {
            SysUnit unit = DB.findById(SysUnit.class, model.getSysUnitId());
            userRet.setSysUnitPath(unit.getPath());
        }
        // 获取角色信息
        List<SysUserRole> userRoles = DB.findList(SysUserRole.class, Expr.builder().add("sysUserId", "=", model.getId()).build());
        if ( !userRoles.isEmpty() ) {
            List<String> ids = new ArrayList<>();
            for (SysUserRole userRole: userRoles) {
                ids.add(userRole.getSysRoleId());
            }
            userRet.setSysRoleIds(StringUtils.joinToString(ids, ","));
        }
        return userRet;
    }

    @Override
    public void add(SysUserArgv argv) {
        SysUser model = BeanUtils.copyObject(argv, SysUser.class);
        model.setId(StringUtils.getUUID());
        // 把参数里的加密密码解密出来
        model.setPassword(decodeBase64(argv.getPassword()));
        // 设置密码
        model.setPassword(EncryptWorker.getInstance().encrypt(model.getPassword()));
        // 唯一性校验
        DBChecker<SysUser> checker =DBChecker.build(model, SysUser.class);
        // 名称唯一
        checker.uni("username", I18n.t("SysUser.username.unique", "用户名必须唯一"));
        // 执行校验
        checker.checkUnique();
        // 保存
        DB.save(model);
        // 保存用户和角色的关系
        saveUserRoles(model.getId(), argv.getSysRoleIds());

    }

    /**
     * 保存用户和角色的关系
     * @param userId    用户id
     * @param sysRoleIds    角色ids
     */
    private void saveUserRoles(String userId, String sysRoleIds) {
        if (StringUtils.isNotEmpty(sysRoleIds)) {
            String[] roleIds = sysRoleIds.trim().split(",");
            // 去重用
            Set<String> idSet = new HashSet<>();
            idSet.addAll(Arrays.asList(roleIds));
            List<SysUserRole> userRoles = new ArrayList<>();
            for (String roleId: idSet) {
                SysUserRole userRole = new SysUserRole();
                userRole.setSysUserId(userId);
                userRole.setSysRoleId(roleId);
                userRoles.add(userRole);
            }
            DB.saveAll(userRoles);
        }
    }

    @Override
    public void edit(SysUserArgv argv) {
        SysUser model = DB.findById(SysUser.class, argv.getId());
        model.setRealName(argv.getRealName());
        model.setMobile(argv.getMobile());
        model.setEmail(argv.getEmail());
        model.setSex(argv.getSex());
        model.setPost(argv.getPost());
        model.setSysUnitId(argv.getSysUnitId());
        model.setNote(argv.getNote());
        model.setStatus(argv.getStatus());
        // 保存
        DB.update(model);
        // 处理用户和角色的关系
        // 先移除所有关联
        DB.executeUpdateSql("delete from sys_user_role where sys_user_id=?", model.getId());
        saveUserRoles(model.getId(), argv.getSysRoleIds());
    }

    @SneakyThrows
    @Override
    @SuppressWarnings("unchecked")
    public PageDataRet<SysUserRet> query(SysUserQueryArgv argv) {
        // 拼装sql
        SqlWrapper wrapper = new SqlWrapper("select u.*, group_concat(sr.name) as sys_role_names, un.name as sys_unit_name, " +
                "un.path as sys_unit_path " +
                "from sys_user u " +
                "left join sys_unit un on un.id=u.sys_unit_id " +
                "left join sys_user_role sur on sur.sys_user_id = u.id " +
                "left join sys_role sr on sr.id = sur.sys_role_id " +
                "where 1=1 ");
        // 拼装查询sql
        if (StringUtils.isNotEmpty(argv.getUsername())) {
            wrapper.addCondition("u.username", Op.LIKE, "%" +argv.getUsername() +"%");
        }
        if (StringUtils.isNotEmpty(argv.getRealName())) {
            wrapper.addCondition("u.real_name", Op.LIKE, "%" +argv.getRealName() +"%");
        }
        if (StringUtils.isNotEmpty(argv.getMobile())) {
            wrapper.addCondition("u.mobile", Op.LIKE, "%" +argv.getMobile() +"%");
        }
        if (argv.getStatus() != null) {
            wrapper.addCondition("u.status", Op.EQ, argv.getStatus());
        }
        wrapper.groupBy("u.id");
        wrapper.sortBy("order by u.when_created desc");
        return (PageDataRet<SysUserRet>) query(wrapper.getSql(), wrapper.getParams(), argv, SysUserRet.class);
    }

    @Override
    public void delete(MultiIdArgv argv) {
        for (String id: argv.getIds()) {
            DB.delete(SysUser.class, id);
        }
    }

    @Override
    public SysUserLoginRet login(SysUserLoginArgv argv) {
        // 非空检查
        if (argv == null
                || argv.getUsername() == null
                || argv.getPassword() == null) {
            throw BusinessException.serviceThrow("请填写完整的登录信息！");
        }
        // 拼装sql
        final Integer ENABLE_STATUS = 1;
        SqlWrapper wrapper = new SqlWrapper("select * from sys_user where 1=1 ");
        wrapper.addCondition("username", Op.EQ, argv.getUsername());
        wrapper.addCondition("status", Op.EQ, ENABLE_STATUS);
        SysUser model = DB.findOne(SysUser.class, wrapper.getSql(), wrapper.getParams().toArray());
        if (model == null) {
            throw BusinessException.serviceThrow("用户名或密码有误！");
        }
        // 把参数里的加密密码解密出来
        argv.setPassword(decodeBase64(argv.getPassword()));
        if (!EncryptWorker.getInstance().validate(argv.getPassword(), model.getPassword())) {
            throw BusinessException.serviceThrow("用户名或密码有误！");
        }
        BaseUserInfo userInfo = new BaseUserInfo();
        userInfo = BeanUtils.copyObject(model, BaseUserInfo.class);
        Map<String, String> roleMap = getRoleIds(getRolesByUserId(model.getId()));
        userInfo.setRoleIds(roleMap.get("roleIds"));
        userInfo.setRoleNames(roleMap.get("roleNames"));
        userInfo.setRoleCodes(roleMap.get("roleCodes"));
        userInfo.setApiSystem(ApiSystemEnum.ADMIN);
        // 获取数据权限id
        String accessSql = "select sys_data_access_id from sys_data_access_user au inner join sys_data_access da on (da.id=au.sys_data_access_id and da.status=1) where au.sys_user_id=?";
        List<String> accessIds = DB.findSingleAttributeList(String.class, accessSql, model.getId());

        if (userInfo.getRoleIds() == null || userInfo.getRoleIds().isEmpty()) {
            throw new UnauthorizedException("你当前没有权限访问系统功能，请联系业务部门授权后，再访问系统 。");
        }
        userInfo.setAccessIds(StringUtils.joinToString(accessIds, ","));

        String token = TokenUtil.createToken(appAuthProperties.getTokenSecret(), appAuthProperties.getIss(), KClientContext.getContext().getIp(), userInfo);
        SysUserLoginRet ret = new SysUserLoginRet();
        ret.setToken(token);
        // 保存登录会话
        // 如果只允许一个登录会话, 那么先将之前的会话删除
        if (appAuthProperties.getLoginSessionOne()) {
            DB.executeUpdateSql("delete from sys_online_user where user_id = ?", model.getId());
            SessionManager.getInstance().removeByUserId(model.getId());
        }
        SysOnlineUser onlineUser = new SysOnlineUser();
        onlineUser.setUserId(model.getId());
        onlineUser.setLoginIp(KClientContext.getContext().getIp());
        onlineUser.setLoginTime(new Timestamp(System.currentTimeMillis()));
        onlineUser.setLoginToken(token);
        // 区分是jwt还是session
        if (appAuthProperties.getMockSessionExpireMinutes() <= 0) {
            onlineUser.setExpireTime(new Timestamp(System.currentTimeMillis() +  ((long) appAuthProperties.getTokenExpireMinutes() * 60 * 1000)));
        }
        else {
            onlineUser.setExpireTime(new Timestamp(System.currentTimeMillis() +  ((long) appAuthProperties.getMockSessionExpireMinutes() * 60 * 1000)));
        }
        DB.save(onlineUser);
        // 保存到缓存
        SessionManager.getInstance().addSession(onlineUser);


        return ret;
    }

    @Override
    public void changePassword(SysUserChangePasswordArgv argv, String token, String ip) {
        BaseUserInfo userInfo = TokenUtil.getUserInfoByToken(token, appAuthProperties.getTokenSecret(), appAuthProperties.getIss(), ip, appAuthProperties.getTokenExpireMinutes(), appAuthProperties.getMockSessionExpireMinutes());
        SysUser model = DB.findById(SysUser.class, userInfo.getId());
        if (model == null) {
            throw BusinessException.serviceThrow("登录凭证已失效，请重新登录！");
        }
        // 把参数里的加密密码解密出来
        argv.setOldPassword(decodeBase64(argv.getOldPassword()));
        argv.setNewPassword(decodeBase64(argv.getNewPassword()));

        if (!EncryptWorker.getInstance().validate(argv.getOldPassword(), model.getPassword())) {
            throw BusinessException.serviceThrow("旧密码有误！");
        }
        if (EncryptWorker.getInstance().validate(argv.getNewPassword(), model.getPassword())) {
            throw BusinessException.serviceThrow("重置的密码不可以与旧密码一样！");
        }
        model.setPassword(EncryptWorker.getInstance().encrypt(argv.getNewPassword()));
        // 保存
        DB.update(model);
    }

    @Override
    public BaseUserInfo getBaseUserInfo(String token, String ip) {
        BaseUserInfo userInfo = TokenUtil.getUserInfoByToken(token, appAuthProperties.getTokenSecret(), appAuthProperties.getIss(), ip, appAuthProperties.getTokenExpireMinutes(), appAuthProperties.getMockSessionExpireMinutes());
//        userInfo.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        return userInfo;
    }

    @Override
    public SysUserProfileRet getProfile(String token, String ip) {
        BaseUserInfo userInfo = TokenUtil.getUserInfoByToken(token, appAuthProperties.getTokenSecret(), appAuthProperties.getIss(), ip, appAuthProperties.getTokenExpireMinutes(), appAuthProperties.getMockSessionExpireMinutes());
        SysUser model = DB.findById(SysUser.class, userInfo.getId());
        SysUserProfileRet ret = BeanUtils.copyObject(model, SysUserProfileRet.class);
        Map<String, String> roleMap = getRoleIds(getRolesByUserId(model.getId()));
        ret.setRoleIds(roleMap.get("roleIds"));
        ret.setRoleNames(roleMap.get("roleNames"));
        return ret;
    }

    @Override
    public void logout() {
        // 清空登录会话
        SysOnlineUser onlineUser = DB.findOne(SysOnlineUser.class, Expr.builder().add("loginToken", "=", KClientContext.getContext().getToken()).build());
        if (onlineUser != null) {
            DB.delete(onlineUser);
            SessionManager.getInstance().removeSession(onlineUser.getUserId(), onlineUser.getLoginToken());
        }
    }

    @Override
    public Long onlineCount(String username) {
        // 如果不启用检验，那么直接返回0
        if (!appAuthProperties.getLoginSessionOne()) {
            return 0L;
        }
        String sql = "select count(1) from sys_online_user t0 left join sys_user t1 on t0.user_id = t1.id where t1.username = ?";
        return DB.findCount(sql, username);
    }

    @Override
    public void encryptChange(String from, String to, String secret) {
        if (StringUtils.isEmpty(secret)) {
            throw BusinessException.serviceThrow("AES密码不能为空");
        }
        if (StringUtils.isEmpty(from)) {
            throw BusinessException.serviceThrow("源加密方法不能为空");
        }
        if (StringUtils.isEmpty(from)) {
            throw BusinessException.serviceThrow("目标加密方法不能为空");
        }
        if (!secret.equals(encryptProperties.getAes().getSecret())) {
            throw BusinessException.serviceThrow("AES密钥不正确");
        }
        Set<String> supportEncrypts = new HashSet<>();
        supportEncrypts.add("aes");
        supportEncrypts.add("base64");
        if (!supportEncrypts.contains(from) || !supportEncrypts.contains(to)) {
            throw BusinessException.serviceThrow("当前只支持aes和base64加解密方式");
        }
        // 读取所有的用户
        List<SysUser> users = DB.findList(SysUser.class, Collections.emptyList());
        for (SysUser user: users) {
            String originPassword = null;
            if (from.equals("base64")) {
                originPassword = new String(Base64.getDecoder().decode(user.getPassword().getBytes(StandardCharsets.UTF_8)));
            }
            else {
                originPassword = AESUtil.decrypt(user.getPassword(), encryptProperties.getAes().getSecret());
            }
            if (!StringUtils.isAsciiPrintable(originPassword)) {
                throw BusinessException.serviceThrow("解密之后的字符串不可读，请重新确认加解密方法");
            }
            if (StringUtils.isEmpty(originPassword)) {
                throw BusinessException.serviceThrow("存在密码无法解密，请重新确认加解密方法");
            }

            String afterPassword = null;
            if ("base64".equals(to)) {
                afterPassword = new String(Base64.getEncoder().encode(originPassword.getBytes(StandardCharsets.UTF_8)));
            }
            else {
                afterPassword = AESUtil.encrypt(originPassword, encryptProperties.getAes().getSecret());
            }
            if (StringUtils.isEmpty(afterPassword)) {
                throw BusinessException.serviceThrow("存在密码无法加密，请重新确认加解密方法");
            }
            user.setPassword(afterPassword);
        }
        DB.updateAll(users);
    }

    @Override
    public void resetPassword(SysUserResetPasswordArgv argv) {
        SysUser model = DB.findById(SysUser.class, argv.getUserId());
        if (model == null) {
            throw BusinessException.serviceThrow("用户不存在！");
        }
        // 把参数里的加密密码解密出来
        argv.setPassword(decodeBase64(argv.getPassword()));
        if (EncryptWorker.getInstance().validate(argv.getPassword(), model.getPassword())) {
            throw BusinessException.serviceThrow("重置的密码不可以与旧密码一样！");
        }
        model.setPassword(EncryptWorker.getInstance().encrypt(argv.getPassword()));
        // 保存
        DB.update(model);
    }

    @Override
    public void ping() {
        try {
            SessionManager.getInstance().getByToken(KClientContext.getContext().getUserInfo().getId(), KClientContext.getContext().getToken()).ping();
        }
        catch (Exception e) {
            log.warn("error", e);
        }

    }

    @Override
    public void editProfile(SysUserProfileArgv argv) {
        SysUser model = DB.findById(SysUser.class, argv.getId());
        model.setRealName(argv.getRealName());
        model.setMobile(argv.getMobile());
        model.setEmail(argv.getEmail());
        model.setAvatar(argv.getAvatar());
        model.setSex(argv.getSex());
        // 保存
        DB.update(model);
    }

    /**
     * 把List<SysRole>
     * @param list
     * @return
     */
    private Map<String, String> getRoleIds(List<SysRole> list) {
        Map<String, String> roleMap = new HashMap<>();
        StringBuilder roleIds = new StringBuilder();
        StringBuilder roleNames = new StringBuilder();
        StringBuilder roleCodes = new StringBuilder();
        list.forEach(item -> {
            if (roleIds.length() != 0) {
                roleIds.append(",");
            }
            roleIds.append(item.getId());
            if (roleNames.length() != 0) {
                roleNames.append(",");
            }
            roleNames.append(item.getName());
            if (roleCodes.length() != 0) {
                roleCodes.append(",");
            }
            roleCodes.append(item.getCode());
        });
        roleMap.put("roleIds", roleIds.toString());
        roleMap.put("roleNames", roleNames.toString());
        roleMap.put("roleCodes", roleCodes.toString());
        return roleMap;
    }

    private List<SysRole> getRolesByUserId(String userId) {
        // 拼装sql
        final Integer ENABLE_STATUS = 1;
        SqlWrapper wrapper = new SqlWrapper("select sr.* from sys_user_role sur left join sys_role sr on sr.id = sur.sys_role_id where 1=1 ");
        wrapper.addCondition("sys_user_id", Op.EQ, userId);
        wrapper.addCondition("sr.status", Op.EQ, ENABLE_STATUS);
        return DB.findList(SysRole.class, wrapper.getSql(), wrapper.getParams().toArray());
    }

    private String decodeBase64(String source) {
        return new String(Base64.getDecoder().decode(source));
    }

}
