package com.kingsware.kdev.sys.service.impl;

import com.kingsware.kdev.core.jsonschema.JsonSchemeDefine;
import com.kingsware.kdev.sys.service.JsonSchemaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * json schema service impl
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/2/25 9:05 上午
 */
@Slf4j
@Service
public class JsonSchemaServiceImpl implements JsonSchemaService {
    @Override
    public JsonSchemeDefine createByTable(String tableName, String curd, String inOut) {

        return null;
    }
}
