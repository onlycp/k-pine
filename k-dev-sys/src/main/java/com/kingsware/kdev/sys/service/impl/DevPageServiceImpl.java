package com.kingsware.kdev.sys.service.impl;

import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.i18n.I18n;
import com.kingsware.kdev.core.kflow.bean.KdbRetFile;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.DBChecker;
import com.kingsware.kdev.core.orm.SqlWrapper;
import com.kingsware.kdev.core.orm.expression.Op;
import com.kingsware.kdev.core.util.*;
import com.kingsware.kdev.sys.argv.CopyContextArgv;
import com.kingsware.kdev.sys.argv.DevPageArgv;
import com.kingsware.kdev.sys.argv.DevPageQueryArgv;
import com.kingsware.kdev.sys.bean.CopyProcessData;
import com.kingsware.kdev.sys.manager.CopyAppManager;
import com.kingsware.kdev.core.model.DevPage;
import com.kingsware.kdev.sys.ret.DevPageRet;
import com.kingsware.kdev.sys.service.DevPageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 业务实现类
 * @author AndyZheng
 * @version 1.0.0
 * @date 2022-02-13 10:20
 */
@Service
@Slf4j
public class DevPageServiceImpl extends BaseServiceImpl implements DevPageService {

    @Value("${amis.version:2.4.1}")
    private String amisVersion;

    @Value("${app.menu.search-order:menu}")
    private String menuSearchOrder;

    @Override
    public DevPageRet get(String id) {
        // 查询model
        DevPage model = DB.findById(DevPage.class, id);
        // 转换成ret对象
        return (DevPageRet) model2Ret(model, DevPageRet.class);
    }

    @Override
    // @KCache(onlyForProd = true)
    public DevPageRet getByPath(String path) {
        if ("menu".equalsIgnoreCase(menuSearchOrder)) {
//            log.info("请求页面信息:{}", path );
            List<DevPageRet> pages =  DB.findList(DevPageRet.class, " select dp.* from dev_page dp left join sys_menu sm on (dp.id = sm.page_id and sm.menu_type='C' and sm.status=1) where sm.full_path=? and deleted=0 ", path);


            if (!pages.isEmpty()) {
                return pages.get(0);
            }
            // 通过菜单去读取
            pages = DB.findList(DevPageRet.class, " select * from dev_page where (path = ? or id = ?) and deleted=0 ", path, path);
            if (!pages.isEmpty()) {
                return pages.get(0);
            }
            else {
                throw BusinessException.serviceThrow(I18n.t("DevPageServiceImpl.pageNotFound", "找不到页面"));
            }
        }
        else {
//            log.info("请求页面信息:{}", path );
            List<DevPageRet> pages =  DB.findList(DevPageRet.class, " select * from dev_page where (path = ? or id = ?) and deleted=0 ", path, path);

            if (!pages.isEmpty()) {
                return pages.get(0);
            }
            // 通过菜单去读取
            pages =  DB.findList(DevPageRet.class, " select dp.* from dev_page dp left join sys_menu sm on (dp.id = sm.page_id and sm.menu_type='C' and sm.status=1) where sm.full_path=? and deleted=0 ", path);
            if (!pages.isEmpty()) {
                return pages.get(0);
            }
            else {
                throw BusinessException.serviceThrow(I18n.t("DevPageServiceImpl.pageNotFound", "找不到页面"));
            }
        }




    }

    @Override
    public void add(DevPageArgv argv) {
        DevPage model = BeanUtils.copyObject(argv, DevPage.class);
        // 唯一校验
        checkUnique(model);
        // 保存
        DB.save(model);
    }

    @Override
    public void edit(DevPageArgv argv) {
        DevPage model = DB.findById(DevPage.class, argv.getId());
        // 唯一校验
        BeanUtils.copyProperties(argv, model);
//        checkUnique(model);
        // 保存
        DB.update(model);
    }

    @Override
    public void checkUnique(DevPage model) {
        // 唯一性校验
        DBChecker<DevPage> checker =DBChecker.build(model, DevPage.class);
        // 页面路径唯一
        checker.uni("path", I18n.t("DevPage.path.unique", model.getPath() + "页面路径必须唯一"));
        // 执行校验
        checker.checkUnique();
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageDataRet<DevPageRet> query(DevPageQueryArgv argv) {
        // 拼装sql
        SqlWrapper wrapper = new SqlWrapper("select * from dev_page where 1=1 and deleted=0 ");
        if (StringUtils.isNotEmpty(argv.getAppId())) {
            wrapper.addCondition("app_id", Op.EQ, argv.getAppId());
        }
        if (StringUtils.isNotEmpty(argv.getName())) {
            wrapper.addCondition("name", Op.LIKE, "%" + argv.getName() + "%");
        }
        wrapper.sortBy("when_created desc");
        return (PageDataRet<DevPageRet>) query(wrapper.getSql(), wrapper.getParams(), argv, DevPage.class, DevPageRet.class);
    }

    @Override
    public void delete(MultiIdArgv argv) {
        for (String id: argv.getIds()) {
            DB.delete(DevPage.class, id);
        }
    }

    /**
     * 查看页面
     *
     * @param id
     */
    @Override
    public void render (String id) {
        //控response以什么码表写数据
        ServletUtil.response().setCharacterEncoding("utf-8");
        //指定浏览器以什么码表解码服务器发送的数据
        ServletUtil.response().setContentType("text/html; charset=UTF-8");
        try (PrintWriter writer = ServletUtil.response().getWriter()) {

            // 查找页面
            DevPage model = DB.findById(DevPage.class, id);
            // 读取模板文件
            File file = ResourceUtils.getFile("classpath:templates/page.html");
            String html = FileUtils.readFile(file);
            // 替换标题
            Map<String, String> context = new HashMap<>();
            context.put("title", model.getName());
            context.put("json", model.getPageJson());
            String renderHtml = TemplateUtil.render(html, context);
            writer.write(renderHtml);
        }
        catch (Exception e) {
            throw BusinessException.serviceThrow(I18n.t("DevPageServiceImpl.pageRenderFail", "页面渲染失败") );
        }

    }


    @Override
    public void copyData(String id, CopyContextArgv context) {
        // 拷贝
        CopyProcessData copyProcessData = new CopyProcessData();
        // 拷贝
        CopyAppManager.getInstance().copyPageData(id, context, copyProcessData);
        // 开始
        CopyAppManager.getInstance().action(copyProcessData, context);
    }


    @Override
    public void exportPine(MultiIdArgv argv) {

        CopyContextArgv contextArgv = new CopyContextArgv();
        contextArgv.setDeepCopy(1);
        contextArgv.setUrlSuffix("v1");
        contextArgv.setCodeSuffix("v1");
        contextArgv.setTargetAppId("hello-world");
        contextArgv.setSourceAppId("hello-world");
        contextArgv.setWithSystemData(1);
        contextArgv.setNameSuffix("hello-world");
        CopyProcessData copyProcessData = new CopyProcessData();
        for (String id: argv.getIds()) {
            CopyAppManager.getInstance().copyPageData(id, contextArgv, copyProcessData);
        }
        KdbRetFile retFile = CopyAppManager.getInstance().exportPine(copyProcessData);
        ServletUtil.responseFile(ServletUtil.response(), "Page" + DateUtils.formatDate(new Date(), DateUtils.DATE_TIME_1) + ".pine", retFile.getData());
    }
}
