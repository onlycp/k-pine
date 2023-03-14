package com.kingsware.kdev.uniops.argv;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * UniOps菜单
 * @author chenp
 * @date 2023/1/10
 */
@Data
public class UniOpsMenu {
    /** 是否缓存 **/
    private boolean cache;
    /** 子菜单 **/
    private List<UniOpsMenu> children = new ArrayList<>();
    /** ?? **/
    private String component;
    /** ?? **/
    private List<String> control;
    /** 是否隐藏 **/
    private boolean hidden;
    /** 图标 **/
    private String icon;
    /** 菜单唯一键 **/
    private String key;
    /** 其他链接 **/
    private String link;
    /** 名称 **/
    private String name;
    /** ?? **/
    private List<String> openkeys = new ArrayList<>();
    /** 是否允许外网访问 **/
    private boolean outernet;
    /** 排序 **/
    private int sort;
    /** 目标， 如何定义 **/
    private String target;
    /** ?? **/
    private String targetpage;
    /** 标题 **/
    private String title;
    /** ?? **/
    private String url;
    /** 青松id **/
    @JsonIgnore
    private String pineId;
}
