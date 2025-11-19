package com.kingsware.kdev.sys.util;

import com.kingsware.kdev.core.auth.BaseUserInfo;
import com.kingsware.kdev.core.context.KClientContext;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ClientUtil {
    /**
     * 当前登录用户是否有管理员权限
     */
    public static boolean isAdmin(){
        boolean isAdmin = false;
        BaseUserInfo userInfo = KClientContext.getContext().getUserInfo();
        String roleCodes = userInfo.getRoleCodes();
        if (roleCodes != null && !roleCodes.trim().isEmpty()) {
            isAdmin = Arrays.stream(roleCodes.split(",")).collect(Collectors.toList()).contains("admin");
        }
        return isAdmin;
    }
}
