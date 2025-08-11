package com.kingsware.kdev.sys.initialize;

import com.kingsware.kdev.core.base.SystemInitialize;
import com.kingsware.kdev.core.i18n.I18n;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.expression.Expr;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.sys.model.SysConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.RequestContext;

import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

/**
 * 青松安装包初始化
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/3/28 4:37 下午
 */
@Component
@Slf4j
public class KManifestInitialize implements SystemInitialize {



    @Override
    public void execute() {
        try {
            Manifest manifest = new Manifest(RequestContext.class.getResourceAsStream("/META-INF/MANIFEST.MF"));
            Attributes attributes = manifest.getMainAttributes();
//            log.info("MANIFEST：{}", JsonUtil.toJson(attributes));
            String buildTime = attributes.getValue("Build-Time");
            if (StringUtils.isEmpty(buildTime)) {
                return;
            }
            String version = attributes.getValue("Implementation-Version");
            String key = "application.buildVersion";
            String value = String.format("%s-%s", version, buildTime);
            // 查数据库看是否已存在
            SysConfig sysConfig = DB.findOne(SysConfig.class, Expr.builder().add("code", "=", key).build());
            if (sysConfig != null) {
                sysConfig.setValue(value);
                DB.update(sysConfig);
            }
            else {
                sysConfig = new SysConfig();
                sysConfig.setIsSys(1);
                sysConfig.setName(I18n.t("platform.version", "平台版本号"));
                sysConfig.setCode(key);
                sysConfig.setValueType(0);
                sysConfig.setNote(I18n.t("platform.version", "平台版本号"));
                DB.save(sysConfig);
            }



            System.out.println(System.currentTimeMillis());
        } catch (IOException ex) {
            log.warn("Error reading manifest file information", ex);
        }
    }

    @Override
    public int sort() {
        return 5;
    }
}
