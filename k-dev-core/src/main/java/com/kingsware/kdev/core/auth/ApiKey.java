package com.kingsware.kdev.core.auth;

import lombok.Data;

/**
 *
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2023/6/25 09:14
 */
@Data
public class ApiKey {

    private String host;
    private String uuid;
    private String appId;
    private String userId;
    private String username;
    private long expireTime;
}
