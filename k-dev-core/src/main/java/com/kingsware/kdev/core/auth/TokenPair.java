package com.kingsware.kdev.core.auth;

import lombok.Data;

/**
 * @author chenp
 * @date 2024/3/1
 */
@Data
public class TokenPair {
    private String token;
    private String md5;

    public TokenPair(String token, String md5) {
        this.token = token;
        this.md5 = md5;
    }
}
