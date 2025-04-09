package com.kingsware.kdev.sys.devops;

import lombok.Data;

@Data
public class DataCopyParam {
    private String host;
    private String username;
    private String password;
    private Boolean task;
}
