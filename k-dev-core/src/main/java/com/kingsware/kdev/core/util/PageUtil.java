package com.kingsware.kdev.core.util;

import com.kingsware.kdev.core.bean.BasePageArgv;
import com.kingsware.kdev.core.bean.PageDataRet;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页工具类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/14 9:26 上午
 */
public class PageUtil {
    /**
     * 私有构造
     */
    private PageUtil() {};

    /**
     * 内存分页工具类
     * @param argv      分页参数
     * @param retList      列表
     * @param tClass    目标类
     * @param <T>       泛型
     * @return          分页结果
     */
    public static <T> PageDataRet<T> memoryPage(BasePageArgv argv, List<T> retList, Class<T> tClass) {
        PageDataRet<T> pageDataRet = new PageDataRet<T>();
        if (retList == null || retList.isEmpty()) {
            pageDataRet.setPage(argv.getPage());
            pageDataRet.setPageSize(argv.getPageSize());
            pageDataRet.setTotal(0);
            pageDataRet.setPageCount(0);
            pageDataRet.setList(new ArrayList<>(0));
            return pageDataRet;
        }
        pageDataRet.setPageSize(argv.getPageSize());
        if (argv.isPageQuery()) {
            // 计算页数
            int pageCount = retList.size() / argv.getPageSize();
            if ( retList.size() % argv.getPageSize() != 0) {
                pageCount ++;
            }
            pageDataRet.setPageCount(pageCount);
            pageDataRet.setPage(argv.getPage());
            pageDataRet.setTotal(retList.size());
            // 计算截取的起始序号
            int fromIndex = (argv.getPage()-1) * argv.getPageSize();
            int toIndex = argv.getPage() * argv.getPageSize();
            // 如果结果数量小于from
            if (retList.size() < fromIndex) {
                pageDataRet.setList(new ArrayList<>());
            }
            else if (retList.size() < toIndex) {
                pageDataRet.setList(retList.subList(fromIndex, retList.size()));
            }
            else {
                pageDataRet.setList(retList.subList(fromIndex, toIndex));
            }

        }
        else {
            pageDataRet.setPage(1);
            pageDataRet.setPageSize(retList.size());
            pageDataRet.setPageCount(1);
            pageDataRet.setList(retList);
        }
        return pageDataRet;
    }

    public static <T> PageDataRet<T> memoryPage(BasePageArgv argv, List<T> retList, int total) {
        PageDataRet<T> pageDataRet = new PageDataRet<>();
        pageDataRet.setList(retList);
        pageDataRet.setTotal(total);
        Integer page = argv.getPage();
        Integer pageSize = argv.getPageSize();
        pageDataRet.setPage(page);
        pageDataRet.setPageSize(pageSize);
        pageDataRet.setPageCount(total/pageSize);
        return pageDataRet;
    }

    public static <T> T mergeQueryOrder(T argv, Class T) {
        if (argv instanceof BasePageArgv) {
            // Vue代码版的排序用的是sort参数，AMIS版页面的排序用的是orderDir
            if (((BasePageArgv) argv).getOrderDir() != null) {
                ((BasePageArgv) argv).setSort(((BasePageArgv) argv).getOrderDir());
                ((BasePageArgv) argv).setOrderBy(StringUtils.humpToLine(((BasePageArgv) argv).getOrderBy()));
            }
        }
        return argv;
    }
}
