package com.kingsware.kdev.sys.bean;

import lombok.Data;

import java.util.List;

/**
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2023/5/10 09:49
 */
@Data
public class ExportRootData {

    private List<ExportData> exportData;

    private ApplicationConfig applicationConfig;

    private boolean downloadSys;
}
