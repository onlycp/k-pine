package com.kingsware.kdev.core.kflow.tcp;

import lombok.Data;

/**
 * @author chenp
 * @date 2023/10/12
 */
@Data
public class KLogData {

    private String sessionID;
    private String log;
    private String windowId;
    private String t;
}
