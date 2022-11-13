package com.kingsware.kdev.sys.web;

import com.kingsware.kdev.core.auth.ApiCode;
import com.kingsware.kdev.core.auth.ApiIgnore;
import com.kingsware.kdev.core.base.BaseController;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.constants.Version;
import com.kingsware.kdev.sys.argv.SysDictItemArgv;
import com.kingsware.kdev.sys.argv.SysDictItemQueryArgv;
import com.kingsware.kdev.sys.ret.SysDictItemRet;
import com.kingsware.kdev.sys.service.SysDictItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 演示控制器
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 11:23 上午
 */
@Api(value = "字典数据管理", tags = {"字典数据管理"})
@RestController
@RequestMapping("/"+ Version.V1 + "/sys-dict-item")
public class SysDictItemController extends BaseController {

    @Resource
    private SysDictItemService sysDictItemService;


    /**
     *  查询
     * @return 分页
     */
    @ApiOperation(value = "查询 " ,notes = "查询")
    @GetMapping("/query")
    public BaseRet<PageDataRet<SysDictItemRet>> page(SysDictItemQueryArgv argv) {
        return BaseRet.success(sysDictItemService.query(argv));
    }

    /**
     * 详细信息
     * @return 详细信息
     */
    @ApiOperation(value = "详情 " ,notes = "详情")
    @GetMapping("/{id}")
    public BaseRet<SysDictItemRet> get(@PathVariable String id) {
        return BaseRet.success(sysDictItemService.get(id));
    }

    /**
     *  新增
     * @return 提示
     */
    @ApiOperation(value = "新增 " ,notes = "新增")
    @PostMapping
    @ApiCode("sysinfo:dictitem:add")
    public BaseRet<?> add(@RequestBody SysDictItemArgv argv) {
        sysDictItemService.add(argv);
        return BaseRet.success();
    }


    /**
     *  编辑
     * @return 提示
     */
    @ApiOperation(value = "编辑 " ,notes = "编辑")
    @PutMapping
    @ApiCode("sysinfo:dictitem:edit")
    public BaseRet<?> edit(@RequestBody SysDictItemArgv argv) {
        sysDictItemService.edit(argv);
        return BaseRet.success();
    }

    /**
     *  删除
     * @return 提示
     */
    @ApiOperation(value = "删除 " ,notes = "删除")
    @PostMapping(value = "/delete")
    @ApiCode("sysinfo:dictitem:remove")
    public BaseRet<?> delete(@RequestBody MultiIdArgv argv) {
        sysDictItemService.delete(argv);
        return BaseRet.success();
    }

    /**
     *  获取所有字典，转换成固定格式，分别有LIST, VALUE, KEY 3种种
     *  LIST用于遍历字典
     *  VALUE用于通过
     * @return 分页
     */
    @ApiIgnore
    @ApiOperation(value = "获取所有字典 " ,notes = "获取所有字典")
    @GetMapping("/all")
    public BaseRet<Map<String, Object>> getAllDict(SysDictItemQueryArgv argv) {
        return BaseRet.success(sysDictItemService.getAllDict());
    }
}
