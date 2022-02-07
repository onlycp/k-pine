package com.kingsware.kdev.core.orm;

import java.util.List;

/**
 * 分页结果返回
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/21 11:37 上午
 */
public class PagedList<T> {
    /** 结果集 **/
    private List<T> List;
    /** 总数 **/
    private int totalCount;
    /** 总页数 **/
    int pageCount;
    /** 页大小 **/
    int pageSize;
    /** 页序号 **/
    int pageIndex;

    public java.util.List<T> getList() {
        return List;
    }

    public void setList(java.util.List<T> list) {
        List = list;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }
}
