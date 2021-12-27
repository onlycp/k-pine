package com.kingsware.kdev.core.bean;

/**
 * 分页查询条件
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/20 10:44 上午
 */
public class BasePageArgc {
    /** 页大小 **/
    private Integer pageSize = 20;
    /** 页号 **/
    private Integer page;
    /** 是否分页 **/
    private boolean pageQuery;
    /** 排序字段 **/
    private String orderBy;
    /** 排序方式, asc或desc **/
    private String sort;


    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public boolean isPageQuery() {
        return pageQuery;
    }

    public void setPageQuery(boolean pageQuery) {
        this.pageQuery = pageQuery;
    }
}
