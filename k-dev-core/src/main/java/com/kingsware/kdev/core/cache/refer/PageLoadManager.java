package com.kingsware.kdev.core.cache.refer;

import com.kingsware.kdev.core.config.UiConfig;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.util.*;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

/**
 * @author chenp
 * @date 2024/5/10
 */
@Slf4j
public class PageLoadManager {
    private static PageLoadManager instance = new PageLoadManager();

    private final Map<String, PageLoadTime> referTimes = new ConcurrentHashMap<>();

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public static PageLoadManager getInstance() {
        if (instance == null) {
            instance = new PageLoadManager();
        }
        return instance;
    }

    private PageLoadManager() {
        scheduler.scheduleAtFixedRate(this::calcReferTime, 0, 1, TimeUnit.SECONDS);
    }

    /**
     * 启动计算方法，用于记录请求的开始时间、计算请求耗时等。
     *
     * @param request HttpServletRequest对象，用于获取请求信息。
     * @param response HttpServletResponse对象，用于设置响应信息。在这个方法中未使用，但作为接口参数以保持一致性。
     */
    public void startCalculate(HttpServletRequest request, HttpServletResponse response) {
        String url = request.getRequestURI();
        if (url.startsWith(request.getContextPath())) {
            url = url.substring(request.getContextPath().length());
        }
        if (url.endsWith("/api/v1/dev-page/getByPath")) {
            url =  request.getParameter("path");
        }
        // 获取请求头中的引用页面地址、当前请求的URI和生成的token
        String referer = getReferer(request);
        if(StringUtils.isEmpty(referer)) {
            return;
        }
        String token = ServletUtil.getCookie("k_session_id", "");
        if (StringUtils.isEmpty(token)) {
            return;
        }
//        log.info("referer:{}", referer);
        // 根据referer、url和token计算出一个MD5值，用作唯一标识
        String md5 = MD5Utils.md5(referer  + token);
        Long now = System.currentTimeMillis();
        // 检查是否之前记录过这个请求
        if (referTimes.containsKey(md5)) {
            PageLoadTime pageLoadTime = referTimes.get(md5);
            pageLoadTime.getRequestCount().incrementAndGet();
            // 更新请求的结束时间
            pageLoadTime.setLastEnd(now);
            // 加入明细项
            PageLoadItemTime pageLoadItemTime = new PageLoadItemTime();
            pageLoadItemTime.setUrl(request.getRequestURI());
            pageLoadItemTime.setStart(now);
            pageLoadTime.addItemTime(pageLoadItemTime);

        } else {
            UiConfig uiConfig = SpringContext.getBean(UiConfig.class);
            if (uiConfig.isStaticsResource(url)) {
                return;
            }
            if (uiConfig.isFrontRouter(url, request) ) {
                // 如果是第一次记录这个请求，则初始化开始时间和结束时间，并加入到map中
                log.info("页面加载开始【{}】:{}, url:{}", md5, referer, url );
                PageLoadTime pageLoadTime = new PageLoadTime();
                pageLoadTime.setStart(System.currentTimeMillis());
                pageLoadTime.setLastEnd(now);
                pageLoadTime.getRequestCount().incrementAndGet();
                pageLoadTime.setToken(token);
                pageLoadTime.setRefer(referer);
                // 加入明细项
                PageLoadItemTime pageLoadItemTime = new PageLoadItemTime();
                pageLoadItemTime.setUrl(request.getRequestURI());
                pageLoadItemTime.setStart(now);
                pageLoadTime.addItemTime(pageLoadItemTime);
                // 保存
                referTimes.put(md5, pageLoadTime);
            }

        }

    }


    /**
     * 结束计算方法，用于记录请求的结束时间。
     *
     * @param request HttpServletRequest对象，用于获取请求信息。
     * @param response HttpServletResponse对象，用于设置响应信息。在这个方法中未使用，但作为接口参数以保持一致性。
     */
    public void endCalculate(HttpServletRequest request, HttpServletResponse response) {



        String referer = getReferer(request);
        if(StringUtils.isEmpty(referer)) {
            return;
        }
        String token = ServletUtil.getCookie("k_session_id", "");
        if (StringUtils.isEmpty(token)) {
            return;
        }

        // 根据referer、url和token计算出一个MD5值，用作唯一标识
        String md5 = MD5Utils.md5(referer  + token);
        Long now = System.currentTimeMillis();
        // 如果请求记录存在，则更新其结束时间
        if (referTimes.containsKey(md5)) {
            PageLoadTime pageLoadTime = referTimes.get(md5);
            pageLoadTime.setLastEnd(now);
            pageLoadTime.getResponseCount().incrementAndGet();
            // 更新明细项的结束时间
            pageLoadTime.updateItemTime(request.getRequestURI());
        }

    }

    private String getReferer(HttpServletRequest request) {
        // 获取请求头中的引用页面地址、当前请求的URI和生成的token
        String referer = request.getHeader("referer");
        String requestURL = request.getRequestURL().toString(); // 获取URL的字符串表示
        String queryString = request.getQueryString(); // 获取查询参数部分
        String absUrl = requestURL + (queryString != null ? "?" + queryString : "");
        if (absUrl.endsWith("v1/websocket")) {
            return null;
        }
        if (ServletUtil.isAjaxRequest(request) && StringUtils.isEmpty(referer)) {
            return null;
        }
        if (StringUtils.isEmpty(referer)) {
            referer = absUrl;
        }
        UiConfig uiConfig = SpringContext.getBean(UiConfig.class);
        if (uiConfig.isFrontRouter(request.getRequestURI(), request)) {
            referer = absUrl;
        }
        return referer;
    }

    /**
     * 计算并记录所有请求的耗时，移除过期的请求记录。
     */
    private void calcReferTime() {
        // 遍历所有请求记录，移除耗时超过2秒的记录并记录耗时信息
        Set<String> removeKeys = new HashSet<>();
        referTimes.forEach((k, v) -> {
            if (v.isEnd()) {
                Long now = System.currentTimeMillis();
                if ((now - v.getLastEnd()) > 1000 * 4) {
                    removeKeys.add(k);

                }
            }
        });
        removeKeys.forEach((k) -> {
            PageLoadTime v = referTimes.get(k);
            // log.info("referer, k:{}", k);
            log.info(v.formatResult());
//            FileUtils.appendLine("res/PageLoad.log", JsonUtil.toJson(v));
//            log.info("页面加载结束【{}】:{}, 用时:{}", v.getRefer(), k, (v.getLastEnd() - v.getStart()));
            referTimes.remove(k);
        });
    }

}
