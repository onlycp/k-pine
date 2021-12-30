package com.kingsware.kdev.sys.enums;

/**
 * @author andyzheng
 * @version 1.0.0
 * @description: TODO
 * @date 2021/12/30 10:30
 */
public enum MenuType {

    M("M", "目录"),
    C("C", "菜单"),
    F("F", "按钮");

    private String code;

    private String name;

    MenuType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
