package com.kingsware.kdev.core.cache.refer;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * PageLoadTime 类用于记录页面加载时间及相关统计。
 * @author chenp
 * @date 2024/5/10
 */
@Data
public class PageLoadTime {
    // 记录开始时间
    private long start;
    // 记录最后一个请求的结束时间
    private long lastEnd;
    // 上一个页面的引用
    private String refer;
    // 用于标识的令牌
    private String token;
    // 请求计数器
    private AtomicInteger requestCount = new AtomicInteger(0);
    // 响应计数器
    private AtomicInteger responseCount = new AtomicInteger(0);
    // 页面加载项时间列表
    private List<PageLoadItemTime> itemTimes = new ArrayList<>();

    /**
     * 添加页面加载项时间记录。
     * @param itemTime 页面加载项的时间记录对象，包含加载项的URL和加载时间。
     */
    public void addItemTime(PageLoadItemTime itemTime) {
        itemTimes.add(itemTime);
    }

    /**
     * 更新页面加载项时间记录。
     * 如果已存在相同URL的加载项时间记录，则更新其结束时间。
     * @param url 需要更新的页面加载项的时间记录对象，包含加载项的URL和新的结束时间。
     */
    public void updateItemTime(String url) {
        // 遍历时间记录列表，寻找与传入URL相同的记录并更新其结束时间
        for (PageLoadItemTime time : itemTimes) {
            if (time.getUrl().equals(url)) {
                time.setEnd(System.currentTimeMillis());
                return;
            }
        }
    }

    /**
     * 判断当前页面加载是否结束。
     *
     * @return boolean 如果请求计数小于等于响应计数，返回true，表示结束；否则返回false。
     */
    public boolean isEnd() {
        return requestCount.get() <= responseCount.get();
    }

    /**
     * 格式化并返回页面加载详情的字符串。
     * 该方法不接受参数，返回一个描述页面加载过程的格式化字符串，包括页面引用、加载时间以及各个子项的请求详情。
     *
     * @return 描述页面加载过程的格式化字符串。
     */
    public String formatResult() {
        StringBuffer sb = new StringBuffer();
        // 新起一行，作为格式化字符串的开始
        sb.append("\n");
        // 添加页面引用、加载时间的信息
        sb.append(String.format("[Page-Load] Page: %s \n", refer));
        sb.append(String.format("[Page-Load] Time: %d - %d ,Take: %d ms\n", this.start, this.lastEnd, (this.lastEnd - this.start)));
        // 添加子项请求详情的标题
        sb.append(String.format("[Page-Load] Items======================================== \n"));
        // itemTimes 倒序
//        itemTimes.sort((o1, o2) -> (int) (o2.getStart() - o1.getStart()));
        // 如果子项时间记录多于一个，则遍历并添加每个子项的请求和时间信息
        if (itemTimes.size()  > 1) {
            for (int i=1; i<itemTimes.size(); i++) {
                sb.append(String.format("[Page-Load] [%d] Request: %s , Time: %d - %d， Take: %d\n", i , itemTimes.get(i).getUrl(), itemTimes.get(i).getStart(), itemTimes.get(i).getEnd(), (getItemTimes().get(i).getEnd() - getItemTimes().get(i).getStart())));
            }
        }
        // 添加结束标志
        sb.append(String.format("[Page-Load] End========================================== \n"));
        return sb.toString();
    }

}
