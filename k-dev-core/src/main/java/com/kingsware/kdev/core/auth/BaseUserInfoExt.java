package com.kingsware.kdev.core.auth;

import lombok.Data;

import java.util.Set;

/**
 * @author chenp
 * @date 2024/3/1
 */
@Data
public class BaseUserInfoExt extends BaseUserInfo{

    private Set<String> permissions;
}
