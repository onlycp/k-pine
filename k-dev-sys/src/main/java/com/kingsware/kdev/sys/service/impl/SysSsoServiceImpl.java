package com.kingsware.kdev.sys.service.impl;


import com.kingsware.kdev.core.context.KClientContext;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.i18n.I18n;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.sys.argv.SysSsoArgv;
import com.kingsware.kdev.sys.argv.SysUserLoginArgv;
import com.kingsware.kdev.sys.model.SysUser;
import com.kingsware.kdev.sys.ret.SysUserLoginRet;
import com.kingsware.kdev.sys.service.SysSsoService;
import com.kingsware.kdev.sys.service.SysUserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class SysSsoServiceImpl implements SysSsoService {

    @Resource
    private SysUserService sysUserService;


    /**
     * 登录
     *
     * @param ssoArgv
     * @return
     */
    @Override
    public SysUserLoginRet doLogin(SysSsoArgv ssoArgv) {
        try {
            String str = "{\"errorUrl\": \"/\",\"successUrl\":\"/\",\"username\": \"chenp\"}";
            // 查询安全令牌
            String secretKey = SpringContext.getProperties("sso.key", "ZiKaPsJkw9AFjztH");
            if (!secretKey.equals(ssoArgv.getSecretKey())) {
                throw new BusinessException(I18n.t("SysSsoServiceImpl.authFail", "认证不合法！"));
            }
            log.info("用户自动登录:{}", JsonUtil.toJson(ssoArgv));
            // 根据账号查询用户并进行登录
            SysUser user = null;
            if (ssoArgv.getType() == null || ssoArgv.getType() == 1) {
                user = DB.findOne(SysUser.class, "select username, password from sys_user where username=?", ssoArgv.getUid());
            }
            else {
                user = DB.findOne(SysUser.class, "select username, password from sys_user where id=?", ssoArgv.getUid());
            }
            SysUserLoginArgv loginArgv = new SysUserLoginArgv();
            loginArgv.setUsername(user.getUsername());
            loginArgv.setPassword(user.getPassword());
            // 调用登录接口
            if (KClientContext.getContext() != null) {
                KClientContext.getContext().setValidateCodeFlag(false);
                KClientContext.getContext().setValidatePassFlag(false);
            }
            SysUserLoginRet ret = sysUserService.login(JsonUtil.beanToMap(loginArgv));
            log.info("自动登录结果:"  + JsonUtil.toJson(ret));
            return ret;
        }
        catch (BusinessException businessException) {
            throw businessException;
        }
        catch (Exception e) {
            log.error("error", e);
            throw BusinessException.serviceThrow(I18n.t("SysSsoServiceImpl.autoLoginFail", "自动登录失败") +  ":" + e.getMessage());
        }
    }


}
