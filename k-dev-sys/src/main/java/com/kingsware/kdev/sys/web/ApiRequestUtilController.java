package com.kingsware.kdev.sys.web;

import com.kingsware.kdev.core.auth.ApiIgnore;
import com.kingsware.kdev.core.auth.Dev;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.constants.Version;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author andyzheng
 * @version 1.0.0
 * @date 2022/5/16 11:23
 */
@RestController
@RequestMapping("/"+ Version.V1 + "/api-util")
public class ApiRequestUtilController {

    @RequestMapping("back-all")
    @ApiIgnore
    @Dev
    public BaseRet<Map<String, Object>> backAll(@RequestBody Map<String, Object> params) {
        return BaseRet.success(params);
    }

}
