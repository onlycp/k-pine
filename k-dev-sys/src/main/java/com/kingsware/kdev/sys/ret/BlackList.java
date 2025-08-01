package com.kingsware.kdev.sys.ret;

import lombok.Data;

import java.util.List;

@Data
public class BlackList {
    /**
     * api列表
     */
    private List<String> apiList;
    /**
     * 流程列表
     */
    private List<String> flowList;
    /**
     * 页面列表
     */
    private List<String> pageList;
}
