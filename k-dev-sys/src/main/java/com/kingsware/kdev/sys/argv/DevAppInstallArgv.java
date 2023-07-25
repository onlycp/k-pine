package com.kingsware.kdev.sys.argv;

import lombok.Data;

@Data
public class DevAppInstallArgv {
    private String appId;
    private String channelId;
    private Integer mode;
    private Integer channel;
    private String version;
    private Integer withSysData;
    private String localFileIds;
    private String teamId;
}
