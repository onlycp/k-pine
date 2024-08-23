package com.kingsware.kdev.core.cache.page;

import com.kingsware.kdev.core.cache.kcache.LruCache;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.i18n.I18n;
import com.kingsware.kdev.core.model.DevPage;
import com.kingsware.kdev.core.orm.DB;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author chenp
 * @date 2024/5/8
 */
@Slf4j
public class PageCacheManager {
    private static PageCacheManager instance = new PageCacheManager();

    private LruCache cache = new LruCache(100);
    public static PageCacheManager getInstance() {
        if (instance == null) {
            instance = new PageCacheManager();
        }
        return instance;
    }

    private PageCacheManager() {
    }

    /**
     * 检查给定的路径是否存在于系统中。
     *
     * @param path 要检查的路径字符串。
     * @return 如果路径存在，则返回true；如果不存在或发生异常，则返回false。
     */
    public boolean contains(String path) {
        try {
            // 通过路径获取对应的DevPage对象
            DevPage page = getByPath(path);
            // 如果获取对象不为null，表示路径存在
            return page != null;
        }
        catch (Exception e) {
            // 发生异常，认为路径不存在或有其他错误
            return false;
        }
    }

    public void clear() {
        cache.clean();
    }


    /**
     * 通过页面路径获取页面信息
     * @param path
     * @return
     */
    public DevPage getByPath(String path) {
        log.info("path == " + path);
        String menuSearchOrder = SpringContext.getProperties("app.menu.search-order", "menu");
        Object cacheObj = cache.get(path);
        if (cacheObj != null){
            return (DevPage) cacheObj;
        }
        if ("menu".equalsIgnoreCase(menuSearchOrder)) {
//            log.info("请求页面信息:{}", path );
            List<DevPage> pages =  DB.findList(DevPage.class, " select dp.* from dev_page dp left join sys_menu sm on (dp.id = sm.page_id and sm.menu_type='C' and sm.status=1) where sm.full_path=? and deleted=0 ", path);
            if (!pages.isEmpty()) {
                DevPage page = pages.get(0);
                cache.put(path, page, 5 * 60 * 1000);
                return pages.get(0);
            }
            // 通过菜单去读取
            pages = DB.findList(DevPage.class, " select * from dev_page where (path = ? or id = ?) and deleted=0 ", path, path);
            if (!pages.isEmpty()) {
                DevPage page = pages.get(0);
                cache.put(path, page, 5 * 60 * 1000);
                return pages.get(0);
            }
            else {
                throw BusinessException.serviceThrow(I18n.t("DevPageServiceImpl.pageNotFound", "找不到页面"));
            }
        }
        else {
//            log.info("请求页面信息:{}", path );
            List<DevPage> pages =  DB.findList(DevPage.class, " select * from dev_page where (path = ? or id = ?) and deleted=0 ", path, path);

            if (!pages.isEmpty()) {
                DevPage page = pages.get(0);
                cache.put(path, page, 5 * 60 * 1000);
                return pages.get(0);
            }
            // 通过菜单去读取
            pages =  DB.findList(DevPage.class, " select dp.* from dev_page dp left join sys_menu sm on (dp.id = sm.page_id and sm.menu_type='C' and sm.status=1) where sm.full_path=? and deleted=0 ", path);
            if (!pages.isEmpty()) {
                DevPage page = pages.get(0);
                cache.put(path, page, 5 * 60 * 1000);
                return pages.get(0);
            }
            else {
                throw BusinessException.serviceThrow(I18n.t("DevPageServiceImpl.pageNotFound", "找不到页面"));
            }
        }

    }



}
