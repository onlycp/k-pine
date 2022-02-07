package com.kingsware.kdev.core.bean;

import java.util.List;

/**
 * 分页数据返回结构
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/20 11:43 上午
 */
public class PageDataRet<T> {
    /** 总数 **/
    private Integer total;
    /** 当前页 **/
    private Integer page;
    /** 页大小 **/
    private Integer pageSize;
    /** 总页数 **/
    private Integer pageCount;
    /** 数据 **/
    private List<T> list;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
