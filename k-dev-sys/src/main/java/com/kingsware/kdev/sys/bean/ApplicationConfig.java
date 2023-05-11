package com.kingsware.kdev.sys.bean;

import lombok.Data;

/**
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2023/5/10 09:47
 */
@Data
public class ApplicationConfig {

    private String id;

    private String name;

    private String version;

    private int faasPort;

    private int pinePort;

    private String dataSource;

    private String versionType;
}
