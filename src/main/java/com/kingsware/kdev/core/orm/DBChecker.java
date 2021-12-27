package com.kingsware.kdev.core.orm;

import com.kingsware.kdev.core.bean.BaseManageModel;
import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.i18n.I18n;
import com.kingsware.kdev.core.orm.expression.Expr;
import com.kingsware.kdev.core.util.BeanUtils;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 唯一性判断
 */
@Data
public class DBChecker<T> {

    /**
     * 唯一校验列定义
     */
    private List<UniColumn> uniColumnList = new ArrayList<>();
    /** 模型对象 **/
    private BaseManageModel model;
    /** 对象class **/
    private Class<T> tClass;


    public  DBChecker<T> uni(String[] key, String errorMessage) {
        uniColumnList.add(new UniColumn().build(key,errorMessage));
        return this;
    }

    public  DBChecker<T> uni(String key, String errorMessage) {
        uniColumnList.add(new UniColumn().build(new String[]{key},errorMessage));
        return this;
    }

    /**
     *
     * @param model 模型
     * @return 当前对象
     */
    public static <T> DBChecker<T> build(BaseManageModel model, Class<T> tClass) {
        DBChecker<T> checker = new DBChecker<T>();
        checker.setModel(model);
        checker.setTClass(tClass);
        return checker;
    }

    /**
     * 判断唯一性
     * @return
     */
    public void checkUnique() {
        // 先获取id
        String id = model.getId();
        boolean isNew = id == null;
        for (UniColumn uni: uniColumnList) {
            Expr expr =  Expr.builder();
            for (String field: uni.getKey()) {
                // 获取值
                Object value = BeanUtils.getField(field, model);
                // 添加查询表达式
                expr.add(field, "=", value);

            }
            // 查询
            List<T> list = DB.findList(tClass, expr.build());
            // 如果是新增，直接判断数量即可
            if (isNew) {
                int count = list.size();
                if (count > 0) {
                    throw BusinessException.serviceThrow(uni.getErrorMessage());
                }
            }
            else {
                BaseManageModel queryEntity = (BaseManageModel) list.get(0);
                if (!queryEntity.getId().equals(model.getId())) {
                    throw BusinessException.serviceThrow(uni.getErrorMessage());
                }
            }
        }
    }


}
