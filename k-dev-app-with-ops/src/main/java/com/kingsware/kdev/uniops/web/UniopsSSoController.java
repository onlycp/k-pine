package com.kingsware.kdev.uniops.web;

import com.kingsware.kdev.core.auth.ApiIgnore;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.constants.Version;
import com.kingsware.kdev.sys.model.SysMenu;
import com.kingsware.kdev.uniops.argv.DevPublishArgv;
import com.kingsware.kdev.uniops.argv.ToPageArgv;
import com.kingsware.kdev.uniops.service.UniOpsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 业务1控制
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/6/2 11:10 AM
 */
@Controller
@RequestMapping("/"+ Version.V1 + "/uniops")
public class UniopsSSoController {

    @Resource
    private UniOpsService uniOpsService;

    /**
     * 详细信息
     */
    @ApiOperation(value = "页面 " ,notes = "页面")
    @RequestMapping("/page")
    @ApiIgnore
    public void page(ToPageArgv toPageArgv) {
        uniOpsService.page(toPageArgv);
    }


    @ApiOperation(value = "发布 " ,notes = "发布")
    @PostMapping("/publish")
    @ResponseBody
    @ApiIgnore
    public BaseRet<?> publish(@RequestBody DevPublishArgv argv) {
        this.uniOpsService.publish(argv);
        return BaseRet.successMessage("发布成功");
    }

    @ApiOperation(value = "卸载 " ,notes = "卸载")
    @PostMapping("/uninstall")
    @ResponseBody
    @ApiIgnore
    public BaseRet<?> uninstall(@RequestBody List<SysMenu> menus) {
        this.uniOpsService.uninstall(menus);
        return BaseRet.successMessage("卸载成功");
    }

}
