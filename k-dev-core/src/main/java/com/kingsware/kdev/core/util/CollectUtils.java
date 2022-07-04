package com.kingsware.kdev.core.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 集合工具类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/17 5:03 下午
 */
public class CollectUtils {

    /**
     * 私有构造函数
     */
    private CollectUtils(){}


    /**
     * 遍历并切片
     *
     * @param list     待切片的 list
     * @param pageSize 切片大小
     * @param <T>      泛型类型
     * @return
     */
    public static <T> List<List<T>> splitList(List<T> list, int pageSize) {
        List<List<T>> listArray = new ArrayList<List<T>>();
        int listSize = list.size();
        for (int i = 0; i < listSize; i += pageSize) {
            int toIndex = Math.min(i + pageSize, listSize);
            listArray.add(list.subList(i, toIndex));
        }
        return listArray;
    }


}
