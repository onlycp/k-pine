package com.kingsware.kdev.sys.service.impl;

import com.kingsware.kdev.core.jsonschema.*;
import com.kingsware.kdev.core.orm.ColumnDefine;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.TableDefine;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.sys.service.JsonSchemaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public BaseSchemaDefine createByTable(String tableName, String curd, String inOut) {
        // 先查询表结构信息
        TableDefine tableDefine = DB.table(tableName);
        // 获取列信息
        List<ColumnDefine>  columnDefines = DB.columns(tableName);
        // 生成根信息
        ObjectSchemaSchemaDefine objectSchemaDefine = new ObjectSchemaSchemaDefine();
        objectSchemaDefine.setTitle(tableName);
        // 设置基本信息
        objectSchemaDefine.setDescription(tableDefine.getComments());
        // 如果输入参数
        if ("in".equals(inOut)) {
            // 新增
            if ("c".equals(curd)) {
                inAndC(columnDefines, objectSchemaDefine);
            }
            // 更新
            else if ("u".equals(curd)) {
                inAndU(columnDefines, objectSchemaDefine);
            }
            // 查询
            else if ("r".equals(curd)) {
                inAndR(columnDefines, objectSchemaDefine);
            }
            //删除
            else if ("d".equals(curd)) {
                inAndD(objectSchemaDefine);
            }
            // 详细
            else if ("g".equals(curd)) {
                inAndG(objectSchemaDefine);
            }
        }
        else if ("out".equals(inOut)){
            objectSchemaDefine.addProperty("code", "响应码, 200：正常", "integer");
            objectSchemaDefine.addProperty("message", "响应消息", "string");
            if ("r".equals(curd)) {
                // 响应数据
                ArraySchemaDefine arrayDefine = new ArraySchemaDefine();
                arrayDefine.setTitle("响应数据");
                arrayDefine.setDescription("");
                objectSchemaDefine.getProperties().put("data", arrayDefine);
                // 设置items
                ObjectSchemaSchemaDefine item = new ObjectSchemaSchemaDefine();
                item.setTitle("对象数据");
                outAndR(columnDefines, item);
                arrayDefine.setItems(item);
            }
            // 获取单条数据
            else if ("g".equals(curd)) {
                // 设置items
                ObjectSchemaSchemaDefine item = new ObjectSchemaSchemaDefine();
                item.setTitle("对象数据");
                outAndR(columnDefines, item);
                objectSchemaDefine.getProperties().put("data", item);
            }
        }
        return objectSchemaDefine;
    }

    private void inAndG(ObjectSchemaSchemaDefine objectSchemaDefine) {
        objectSchemaDefine.addProperty("id", "id", "string");
    }


    /**
     * 基本忽略的属性
     * @return
     */
    private Set<String> getBasicIgnoreColumns() {
        Set<String> ignoreSet = new HashSet<>();
        ignoreSet.add("whoCreated");
        ignoreSet.add("whenCreated");
        ignoreSet.add("whoModified");
        ignoreSet.add("whenModified");
        return ignoreSet;
    }


    /**
     * 新增in
     * @param columnDefines  列定义
     * @param objectSchemaDefine   对象定义
     */
    private void inAndC(List<ColumnDefine> columnDefines, ObjectSchemaSchemaDefine objectSchemaDefine) {
        Set<String> ignoreSet = getBasicIgnoreColumns();
        ignoreSet.add("id");
        for (ColumnDefine column: columnDefines) {
            // 转换成json schema的数据类型
            String schemeDataType = TypeMapping.getInstance().getSchemaType(column.getType());
            // 转换成骆峰
            String key = StringUtils.lineToHump(column.getName());
            // 增加到属性
            if (!ignoreSet.contains(key)) {
                objectSchemaDefine.addProperty( key, column.getComment(), schemeDataType);
            }
        }
    }


    private void inAndU(List<ColumnDefine> columnDefines, ObjectSchemaSchemaDefine objectSchemaDefine) {
        Set<String> ignoreSet = getBasicIgnoreColumns();
        for (ColumnDefine column: columnDefines) {
            // 转换成json schema的数据类型
            String schemeDataType = TypeMapping.getInstance().getSchemaType(column.getType());
            // 转换成骆峰
            String key = StringUtils.lineToHump(column.getName());
            // 增加到属性
            if (!ignoreSet.contains(key)) {
                objectSchemaDefine.addProperty( key, column.getComment(), schemeDataType);
            }
        }
    }

    private void inAndR(List<ColumnDefine> columnDefines, ObjectSchemaSchemaDefine objectSchemaDefine) {
        Set<String> ignoreSet = getBasicIgnoreColumns();
        ignoreSet.add("id");
        // 属性查询
        for (ColumnDefine column: columnDefines) {
            // 转换成json schema的数据类型
            String schemeDataType = TypeMapping.getInstance().getSchemaType(column.getType());
            // 转换成骆峰
            String key = StringUtils.lineToHump(column.getName());
            // 增加到属性
            if (!ignoreSet.contains(key)) {
                objectSchemaDefine.addProperty( key, column.getComment(), schemeDataType);
            }
        }
        // 分页查询
        objectSchemaDefine.addProperty("pageSize", "页大小", "integer");
        objectSchemaDefine.addProperty("page", "页号，从1开始", "integer");
        objectSchemaDefine.addProperty("pageQuery", "是否分页查询", "boolean");
        objectSchemaDefine.addProperty("ids", "被选择要导出的ID 或 指定要查的ID集合,逗号分隔", "string");

    }

    private void inAndD(ObjectSchemaSchemaDefine objectSchemaDefine) {
        // ids
        // 设置数组类型
        ArraySchemaDefine ids = new ArraySchemaDefine();
        ids.setDescription("id数组");
        ids.setTitle("ids");
        // 设置子schema
        BaseSchemaDefine items = new BaseSchemaDefine();
        items.setType(JsonSchemaDataType.INTEGER.getValue());
        items.setTitle("ID");
        items.setDescription("");
        ids.setItems(items);
        objectSchemaDefine.getProperties().put("ids", ids);
    }

    private void outAndR(List<ColumnDefine> columnDefines, ObjectSchemaSchemaDefine item) {
        for (ColumnDefine column: columnDefines) {
            // 转换成json schema的数据类型
            String schemeDataType = TypeMapping.getInstance().getSchemaType(column.getType());
            // 转换成骆峰
            String key = StringUtils.lineToHump(column.getName());
            // 增加到属性
            item.addProperty( key, column.getComment(), schemeDataType);
        }
    }
}
