package com.kingsware.kdev.core.bean;

import lombok.Data;

@Data
public class FaasRequestBody {

    private long timestamp;
    private String signature;
    private String body;
}
