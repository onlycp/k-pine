package com.kingsware.kdev.core.orm;

import com.kingsware.kdev.core.bean.BaseManageModel;
import com.kingsware.kdev.core.i18n.I18n;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 唯一性判断
 */
@Data
public class DBChecker {

    /**
     * 唯一校验列定义
     */
    private List<UniColumn> uniColumnList = new ArrayList<>();
    /** 模型对象 **/
    private BaseManageModel model;


    public  DBChecker uni(String[] key, String errorMessage) {
        uniColumnList.add(new UniColumn().build(key,errorMessage));
        return this;
    }

    public  DBChecker uni(String key, String errorMessage) {
        uniColumnList.add(new UniColumn().build(new String[]{key},errorMessage));
        return this;
    }

    /**
     *
     * @param model 模型
     * @return 当前对象
     */
    public static DBChecker build(BaseManageModel model) {
        DBChecker checker = new DBChecker();
        checker.setModel(model);
        return checker;
    }

    /**
     * 判断唯一性
     * @return
     */
    public  void check() {
//        // 先获取id
//        String id = model.getId()
//        boolean isNew = true;
//        if (id != null) {
//            isNew = false;
//        }
//        for (UniColumn uni: uniColumnList) {
//            ExpressionList<T> expressionList = DB.find(entityClass).where();
//            for (String field: uni.getKey()) {
//                // 获取值
//                Object value = BeanUtil.getFieldValue(object, field);
//                if (value == null) {
//                    expressionList.add(Expr.isNull(field));
//                }
//                else {
//                    expressionList.add(Expr.eq(field, value));
//                }
//            }
//            // 如果是新增，直接判断数量即可
//            if (isNew) {
//                int count = expressionList.findCount();
//                if (count > 0) {
//                    throw ServiceException.serviceThrow(buildErrorMessage(uni));
//                }
//            }
//            else {
//                Optional<T> optional = expressionList.findOneOrEmpty();
//                if (optional.isPresent()) {
//                    Long lid = (Long) id;
//                    Long oid = (Long) BeanUtil.getFieldValue(optional.get(), "id");
//                    if (!lid.equals(oid)) {
//                        throw ServiceException.serviceThrow(buildErrorMessage(uni));
//                    }
//                }
//            }
//        }
    }

//    /**
//     * 构建错误信息
//     * @param uni   唯一信息
//     * @return
//     */
//    private String buildErrorMessage(UniColumn uni) {
//        // 如有，直接返回
//        if (StringUtils.isNotEmpty(uni.getErrorMessage())) {
//            return uni.getErrorMessage();
//        }
//        List<String> fields  = new ArrayList<>();
//        for (String k: uni.getKey()) {
//            fields.add(I18n.fieldComment(k, object.getClass()));
//        }
//        return I18n.t("common.uni.fail", "{0} 已存在！", StringUtils.joinWith("-", fields));
//    }


}
