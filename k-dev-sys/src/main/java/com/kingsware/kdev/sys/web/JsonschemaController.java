package com.kingsware.kdev.sys.web;

import com.kingsware.kdev.core.base.BaseController;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.constants.Version;
import com.kingsware.kdev.core.jsonschema.BaseSchemaDefine;
import com.kingsware.kdev.sys.service.JsonSchemaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;

/**
 * json schema controller
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/2/25 4:42 下午
 */
@Api(value = "JSON SCHEMA", tags = {"JSONSCHEMA"})
@RestController
@RequestMapping("/"+ Version.V1 + "/sys-json-schemas")
public class JsonschemaController extends BaseController {

    @Resource
    private JsonSchemaService jsonSchemaService;

    /**
     * 详细信息
     * @return 详细信息
     */
    @ApiOperation(value = "表schema " ,notes = "表schema")
    @GetMapping("/table/{tableName}/{curd}/{inOut}")
    public BaseRet<BaseSchemaDefine> table(@PathVariable String tableName, @PathVariable String curd, @PathVariable String inOut) {
        return BaseRet.success(jsonSchemaService.createByTable(tableName, curd, inOut));
    }
}
